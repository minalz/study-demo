package cn.minalz.invoice;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.logging.LoggerFactory;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Slf4j
public class PdfExporter {

    private final ObjectMapper mapper = new ObjectMapper();

    public void export(List<Invoice> data, OutputStream out) throws Exception {
        JsonNode cfg = mapper.readTree(Objects.requireNonNull(
                getClass().getResourceAsStream("/invoice-pdf.json")));

        PdfDocument pdf = new PdfDocument(new PdfWriter(out));
        Document doc = new Document(pdf, PageSize.A4.rotate());
        doc.setMargins(
                cfg.get("marginTop").asInt(),
                cfg.get("marginRight").asInt(),
                cfg.get("marginBottom").asInt(),
                cfg.get("marginLeft").asInt());

        /* 字体 */
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        /* 表头 */
        Paragraph title = new Paragraph(cfg.get("headerTitle").asText())
                .setFont(bold)
                .setFontSize(cfg.get("headerFontSize").asInt())
                .setTextAlignment(TextAlignment.CENTER);
        doc.add(title);
        doc.add(new Paragraph("\n"));

        /* 表格 */
        Table table = new Table(cfg.get("columns").size());
        table.setWidth(UnitValue.createPercentValue(100));

        // 列头
        cfg.get("columns").forEach(col ->
                table.addHeaderCell(new Cell().add(new Paragraph(col.get("title").asText())
                        .setFont(bold).setFontSize(cfg.get("fontSize").asInt()))));

        // 数据行
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DecimalFormat df = new DecimalFormat("#,##0.00");
        for (Invoice inv : data) {
            for (JsonNode col : cfg.get("columns")) {
                String field = col.get("field").asText();
                String val;
                switch (field) {
                    case "invoiceDate":
                        val = inv.getInvoiceDate().format(fmt);
                        break;
                    case "totalAmount":
                    case "totalTax":
                        val = df.format(new BigDecimal(inv.getClass()
                                .getMethod("get" + capitalize(field)).invoke(inv).toString()));
                        break;
                    default:
                        val = Objects.toString(inv.getClass()
                                .getMethod("get" + capitalize(field)).invoke(inv), "");
                }
                table.addCell(new Cell().add(new Paragraph(val)
                        .setFont(font).setFontSize(cfg.get("fontSize").asInt())));
            }
        }
        doc.add(table);

        /* 盖章 */
        StampSeal.addSeal(pdf, doc, cfg.get("seal"));

        doc.close();
    }

    private String capitalize(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}