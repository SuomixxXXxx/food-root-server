package org.chiches.foodrootservir.services.impl;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.chiches.foodrootservir.entities.OrderEntity;
import org.chiches.foodrootservir.repositories.OrderRepository;
import org.chiches.foodrootservir.services.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PdfServiceImpl implements PdfService {

    private final OrderRepository orderRepository;

    public PdfServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public ResponseEntity<byte[]> getOrdersReport(String date) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime start = localDate.atStartOfDay();
        LocalDateTime finish = localDate.atTime(LocalTime.MAX);
        List<OrderEntity> orderEntities = orderRepository.findAllByDateOfCreationBetween(start, finish);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        document.add(new Paragraph(date + ", orders"));
        Table table = new Table(3);
        table.addCell(new Cell().add(new Paragraph("Order ID")));
        table.addCell(new Cell().add(new Paragraph("Time")));
        table.addCell(new Cell().add(new Paragraph("Total Price")));
        for (OrderEntity order : orderEntities) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(order.getId()))));
            table.addCell(new Cell().add(new Paragraph(order.getDateOfCreation().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(order.getFullPrice()))));
        }
        document.add(table);
        document.close();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline; filename=orders.pdf");
        ResponseEntity<byte[]> responseEntity = ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
        return responseEntity;
    }
}
