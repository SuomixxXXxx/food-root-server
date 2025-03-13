package org.chiches.foodrootservir.controllers;

import org.chiches.foodrootservir.services.impl.PdfServiceImpl;
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
        ResponseEntity<byte[]> responseEntity = pdfService.getOrdersReport(date);
        return responseEntity;
    }

    @GetMapping(path = "getDishesReport")
    public ResponseEntity<byte[]> getDishesReport(@RequestParam String startDate, @RequestParam String finishDate) {
        ResponseEntity<byte[]> responseEntity = pdfService.getDishesReport(startDate, finishDate);
        return responseEntity;
    }

}
