package cn.minalz.invoice;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class Invoice {
    private String invoiceCode;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private String buyerName;
    private BigDecimal totalAmount;
    private BigDecimal totalTax;

}