package org.chiches.foodrootservir.services;

import org.springframework.http.ResponseEntity;

public interface PdfService {
    byte[] getOrdersReport(String date);
    byte[] getDishesReport(String startDate, String finishDate);
}
