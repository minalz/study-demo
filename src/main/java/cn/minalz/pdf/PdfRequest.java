package cn.minalz.pdf;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PdfRequest {
    @NotBlank
    private String html;
    private PdfOptions options = new PdfOptions();
}