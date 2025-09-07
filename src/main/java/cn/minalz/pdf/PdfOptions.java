package cn.minalz.pdf;

import lombok.Data;

@Data
public class PdfOptions {
    private String format = "A4";
    private boolean displayHeaderFooter = false;
    private String headerTemplate;
    private String footerTemplate;
}