package org.chiches.foodrootservir.services.impl;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.chiches.foodrootservir.entities.DishItemEntity;
import org.chiches.foodrootservir.entities.OrderContentEntity;
import org.chiches.foodrootservir.entities.OrderEntity;
import org.chiches.foodrootservir.repositories.OrderContentRepository;
import org.chiches.foodrootservir.repositories.OrderRepository;
import org.chiches.foodrootservir.services.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PdfServiceImpl implements PdfService {

    private final OrderRepository orderRepository;
    private final OrderContentRepository orderContentRepository;

    public PdfServiceImpl(OrderRepository orderRepository, OrderContentRepository orderContentRepository) {
        this.orderRepository = orderRepository;
        this.orderContentRepository = orderContentRepository;
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
        try {
            PdfFont font = PdfFontFactory.createFont("Times_New_Roman.ttf", PdfEncodings.IDENTITY_H);
            document.setFont(font);
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.add(new Paragraph(date + ", orders"));
        Table table = new Table(4);
        table.addCell(new Cell().add(new Paragraph("Order ID")));
        table.addCell(new Cell().add(new Paragraph("Time")));
        table.addCell(new Cell().add(new Paragraph("Content")));
        table.addCell(new Cell().add(new Paragraph("Total Price")));
        for (OrderEntity order : orderEntities) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(order.getId()))));
            List<OrderContentEntity> contents = order.getOrderContents();
            com.itextpdf.layout.element.List names = new com.itextpdf.layout.element.List();
            for (OrderContentEntity content : contents) {
                names.add(new ListItem(String.format("%s : %d шт.", content.getDishItem().getName(), content.getQuantity())));
            }
            table.addCell(new Cell().add(new Paragraph(order.getDateOfCreation().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")))));
            table.addCell(new Cell().add(names));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(order.getFullPrice()))));
        }
        document.add(table);
        document.close();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=orders.pdf");
        ResponseEntity<byte[]> responseEntity = ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
        return responseEntity;
    }

    @Override
    public ResponseEntity<byte[]> getDishesReport(String startDate, String finishDate) {
        LocalDate localDateStart = LocalDate.parse(startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate localDateFinish = LocalDate.parse(finishDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDateTime start = localDateStart.atStartOfDay();
        LocalDateTime finish = localDateFinish.atTime(LocalTime.MAX);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(outputStream);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument);
        try {
            PdfFont font = PdfFontFactory.createFont("Times_New_Roman.ttf", PdfEncodings.IDENTITY_H);
            document.setFont(font);
        } catch (IOException e) {
            e.printStackTrace();
        }
        document.add(new Paragraph(String.format("%s - %s", startDate, finishDate)));
        Table table = new Table(3);
        table.addCell(new Cell().add(new Paragraph("Name")));
        table.addCell(new Cell().add(new Paragraph("Quantity sold")));
        table.addCell(new Cell().add(new Paragraph("Revenue")));
        List<OrderEntity> orderEntities = orderRepository.findAllByDateOfCreationBetween(start, finish);
        Set<DishItemEntity> dishItemEntities = new HashSet<>();
        for (OrderEntity order : orderEntities) {
            for (OrderContentEntity orderContentEntity : order.getOrderContents()) {
                dishItemEntities.add(orderContentEntity.getDishItem());
            }
        }
        double fullRevenue = 0;
        for (DishItemEntity dishItemEntity : dishItemEntities) {
            table.addCell(new Cell().add(new Paragraph(dishItemEntity.getName())));
            int number = orderContentRepository.findCount(dishItemEntity, orderEntities);
            table.addCell(new Cell().add(new Paragraph(String.valueOf(number))));
            double revenue = number * dishItemEntity.getPrice();
            table.addCell(new Cell().add(new Paragraph(String.valueOf(revenue))));
            fullRevenue += revenue;
        }
        document.add(table);
        document.add(new Paragraph(String.format("Total revenue for the specified period: %.2f", fullRevenue)));
        document.close();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=orders.pdf");
        ResponseEntity<byte[]> responseEntity = ResponseEntity.ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(outputStream.toByteArray());
        return responseEntity;
    }

}
