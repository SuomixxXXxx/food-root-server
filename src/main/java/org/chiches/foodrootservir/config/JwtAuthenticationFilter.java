package org.chiches.foodrootservir.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.chiches.foodrootservir.entities.RefreshTokenEntity;
import org.chiches.foodrootservir.misc.CookieUtil;
import org.chiches.foodrootservir.security.JwtService;
import org.chiches.foodrootservir.security.RefreshTokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_PREFIX_HEADER = "Bearer ";
    public static final String BEARER_PREFIX_COOKIE  = "Bearer_";
    public static final String JWT_COOKIE_NAME      = "jwtToken";
    public static final String REFRESH_COOKIE_NAME  = "refreshToken";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final RefreshTokenService refreshTokenService;
    private final CookieUtil cookieUtil;

    public JwtAuthenticationFilter(JwtService jwtService,
                                   UserDetailsService userDetailsService,
                                   RefreshTokenService refreshTokenService,
                                   CookieUtil cookieUtil) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.refreshTokenService = refreshTokenService;
        this.cookieUtil = cookieUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String bearerHeader = Optional
                .ofNullable(request.getHeader(AUTH_HEADER))
                .filter(h -> h.startsWith(BEARER_PREFIX_HEADER))
                .map(h -> h.substring(BEARER_PREFIX_HEADER.length()))
                .orElse(null);

        Cookie[] cookies = request.getCookies();
        Cookie jwtCookie     = findCookie(cookies, JWT_COOKIE_NAME);
        Cookie refreshCookie = findCookie(cookies, REFRESH_COOKIE_NAME);

        String bearerCookie = (jwtCookie != null && jwtCookie.getValue().startsWith(BEARER_PREFIX_COOKIE))
                ? jwtCookie.getValue().substring(BEARER_PREFIX_COOKIE.length())
                : null;

        String jwtToken = bearerHeader != null ? bearerHeader : bearerCookie;
        if (jwtToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String login = jwtService.extractUserName(jwtToken);

        if (login != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(login);

            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                setAuthentication(userDetails, request);
            } else if (jwtService.isTokenExpired(jwtToken)) {
                refreshIfPossible(userDetails, refreshCookie, response, request);
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private void refreshIfPossible(UserDetails userDetails,
                                   Cookie refreshCookie,
                                   HttpServletResponse response,
                                   HttpServletRequest request) {
        if (refreshCookie == null || refreshCookie.getValue() == null) return;

        String refreshToken = refreshCookie.getValue();
        RefreshTokenEntity entity = refreshTokenService.verifyExpiration(refreshToken);
        if (entity == null || refreshTokenService.findByToken(refreshToken).isEmpty()) return;

        Cookie newRefreshCookie = cookieUtil.createRefreshCookie(entity.getToken());
        response.addCookie(newRefreshCookie);

        String newJwt = jwtService.generateToken(userDetails);
        Cookie newJwtCookie = cookieUtil.createJWTCookie(newJwt);
        response.addCookie(newJwtCookie);

        setAuthentication(userDetails, request);
    }

    private Cookie findCookie(Cookie[] cookies, String name) {
        if (cookies == null) return null;
        for (Cookie c : cookies) {
            if (name.equals(c.getName())) return c;
        }
        return null;
    }
}
