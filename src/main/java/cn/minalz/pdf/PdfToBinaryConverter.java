package cn.minalz.pdf;

import java.io.*;

import org.apache.pdfbox.pdmodel.PDDocument;

public class PdfToBinaryConverter {

  public static void main(String[] args) {
    File inputFile = new File("D:/test/test.pdf");
    File outputFile = new File("D:/test/output.bin");

    try (PDDocument document = PDDocument.load(inputFile)) {
      OutputStream output = new FileOutputStream(outputFile);

      // 将PDF文件转换为2进制流文件
      document.save(output);

      output.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
