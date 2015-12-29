package pl.gebickionline.services.pricelist;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import pl.gebickionline.exception.PdfPriceListException;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by Łukasz on 2015-12-29.
 */
public class PdfPriceList {
    private final List<Group> groups;
    private final Document document;
    private final ByteArrayOutputStream os;
    private final PdfWriter writer;

    public PdfPriceList(List<Group> groups) {
        this.groups = groups;
        document = new Document();
        os = new ByteArrayOutputStream();

        try {
            writer = PdfWriter.getInstance(document, os);
            document.open();

            Paragraph title = new Paragraph("e-Firma - Cennik", titleFont());
            title.setSpacingAfter(15.0f);
            document.add(title);
            LineSeparator ls = new LineSeparator();
            document.add(ls);

            Paragraph creationTimestamp = new Paragraph(String.format("Stan aktualny na dzień %s o godzinie %s", currentDate(), currentTime()), subtitleFont());
            creationTimestamp.setSpacingAfter(15.0f);
            document.add(creationTimestamp);
            if (groups.isEmpty())
                document.add(new Paragraph("Cennik jest pusty", groupNameFont()));
            else
                groups.forEach(this::addGroupToDocument);

            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new PdfPriceListException(e);
        }

    }

    private void addGroupToDocument(Group group) {
        String groupName = group.groupName();

        PdfPTable serviceGroupTable = new PdfPTable(2);

        serviceGroupTable.setHeaderRows(1);
        serviceGroupTable.setSpacingBefore(10.f);
        serviceGroupTable.setSpacingAfter(10.f);
        serviceGroupTable.setWidthPercentage(100.0f);

        PdfPCell headerCell = new PdfPCell(new Paragraph(groupName, groupNameFont()));
        headerCell.setColspan(2);
        headerCell.setBorder(0);
        headerCell.setBorderWidthBottom(1);
        serviceGroupTable.addCell(headerCell);

        group.services().forEach(service -> addServiceToTable(serviceGroupTable, service));

        try {
            document.add(serviceGroupTable);
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new PdfPriceListException(e);
        }
    }

    private void addServiceToTable(PdfPTable serviceGroupTable, Service service) {

        PdfPCell serviceNameCell = new PdfPCell(new Paragraph(service.serviceName(), serviceRowFont()));
        serviceNameCell.setBorder(0);
        serviceGroupTable.addCell(serviceNameCell);

        String price = service.price() != null
                ? divideBy100(service.price()) + " zł"
                : String.format("od %s do %s zł", divideBy100(service.minPrice()), divideBy100(service.maxPrice()));

        PdfPCell priceCell = new PdfPCell(new Paragraph(price, serviceRowFont()));
        priceCell.setBorder(0);
        serviceGroupTable.addCell(priceCell);

    }

    private Font serviceRowFont() {
        return FontFactory.getFont(FontFactory.HELVETICA, "cp1250", 12, Font.NORMAL, new CMYKColor(255, 255, 255, 0));
    }

    private String divideBy100(Long price1) {
        return String.format("%.2f", price1 / 100.0);
    }

    private Font groupNameFont() {
        return FontFactory.getFont(FontFactory.HELVETICA, "cp1250", 20, Font.BOLD, new CMYKColor(255, 255, 255, 0));
    }

    private Font titleFont() {
        return FontFactory.getFont(FontFactory.COURIER_BOLD, "cp1250", 34, Font.BOLD, new CMYKColor(255, 255, 255, 0));
    }

    private Font subtitleFont() {
        return FontFactory.getFont(FontFactory.HELVETICA, "cp1250", 8, Font.ITALIC, new CMYKColor(255, 255, 255, 0));
    }

    private String currentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return sdf.format(new Date());
    }

    private String currentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(new Date());
    }

    public byte[] toByteArray() {
        return os.toByteArray();
    }
}
