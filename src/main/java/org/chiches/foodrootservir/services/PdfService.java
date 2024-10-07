package org.chiches.foodrootservir.services;

import org.springframework.http.ResponseEntity;

public interface PdfService {
    public ResponseEntity<byte[]> getOrdersReport(String date);
    public ResponseEntity<byte[]> getDishesReport(String startDate, String finishDate);
}
