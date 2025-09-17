package cn.minalz.invoice;

import com.fasterxml.jackson.databind.JsonNode;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StampSeal {
    public static void addSeal(PdfDocument pdf, Document doc, JsonNode sealCfg) throws IOException {
        String imgPath = sealCfg.get("imagePath").asText();
        InputStream in = StampSeal.class.getResourceAsStream("/" + imgPath);
        if (in == null) {
            throw new IOException("seal image not found: " + imgPath);
        }
        ByteArrayOutputStream tmp = new ByteArrayOutputStream();
        byte[] buf = new byte[4096];
        int n;
        while ((n = in.read(buf)) != -1) {
            tmp.write(buf, 0, n);
        }
        in.close();
        byte[] imgBytes = tmp.toByteArray();
        ImageData data = ImageDataFactory.create(imgBytes);

        float w = sealCfg.get("width").floatValue();
        float h = sealCfg.get("height").floatValue();
        float x = sealCfg.get("pageX").floatValue();
        float y = sealCfg.get("pageY").floatValue();
        boolean everyPage = sealCfg.get("onEveryPage").asBoolean();

        int pages = pdf.getNumberOfPages();
        for (int i = 1; i <= pages; i++) {
            PdfPage page = pdf.getPage(i);
            PdfCanvas canvas = new PdfCanvas(page.newContentStreamAfter(), page.getResources(), pdf);
            canvas.saveState();
            PdfExtGState gs = new PdfExtGState().setFillOpacity(0.6f); // 透明度
            canvas.setExtGState(gs);
            canvas.addImageWithTransformationMatrix(data, w, 0, 0, h, x, y);
            canvas.restoreState();
            if (!everyPage) break;
        }
    }
}