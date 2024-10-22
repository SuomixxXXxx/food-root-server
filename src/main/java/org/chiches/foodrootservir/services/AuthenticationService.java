package org.chiches.foodrootservir.services;

import org.chiches.foodrootservir.dto.TokenDTO;
import org.chiches.foodrootservir.dto.UserDTO;

public interface AuthenticationService {
    TokenDTO authenticate(String email, String password);
    TokenDTO register(UserDTO userDto);
    TokenDTO refresh(String refreshToken);


}
