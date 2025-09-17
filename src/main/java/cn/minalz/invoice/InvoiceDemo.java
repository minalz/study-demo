package cn.minalz.invoice;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InvoiceDemo {
    public static void main(String[] args) throws Exception {
        List<Invoice> list = IntStream.rangeClosed(1, 20)
                .mapToObj(i -> {
                    Invoice inv = new Invoice();
                    inv.setInvoiceCode("1234567890");
                    inv.setInvoiceNumber("98765432" + (i % 10));
                    inv.setInvoiceDate(LocalDate.now().minusDays(i));
                    inv.setBuyerName("购买方" + i);
                    inv.setTotalAmount(BigDecimal.valueOf(10000 + i * 100, 2));
                    inv.setTotalTax(BigDecimal.valueOf(1300 + i * 13, 2));
                    return inv;
                }).collect(Collectors.toList());

        try (FileOutputStream out = new FileOutputStream("发票明细.pdf")) {
            new PdfExporter().export(list, out);
        }
        System.out.println("PDF 已生成，含透明章");
    }
}