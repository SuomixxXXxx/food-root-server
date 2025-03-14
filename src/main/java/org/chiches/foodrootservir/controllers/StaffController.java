package org.chiches.foodrootservir.controllers;

import org.chiches.foodrootservir.services.impl.PdfServiceImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/staff/")
public class StaffController {

    private final PdfServiceImpl pdfService;

    public StaffController(PdfServiceImpl pdfService) {
        this.pdfService = pdfService;
    }

    @GetMapping(path = "getOrdersReport")
    public ResponseEntity<byte[]> getOrderReport(@RequestParam String date) {
        byte[] report = pdfService.getOrdersReport(date);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=orders.pdf");
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(report);
    }

    @GetMapping(path = "getDishesReport")
    public ResponseEntity<byte[]> getDishesReport(@RequestParam String startDate, @RequestParam String finishDate) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=orders.pdf");

        byte[] report = pdfService.getDishesReport(startDate, finishDate);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(report);
    }

}
