package cn.minalz.pdf;

import com.esotericsoftware.minlog.Log;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.Margin;
import com.microsoft.playwright.options.WaitUntilState;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PdfService {

    public byte[] generate(String html, PdfOptions opts) {
        // 1. 启动 Playwright（自动下载无头 Chrome）
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(true)
                            .setArgs(Arrays.asList("--no-sandbox", "--disable-setuid-sandbox"))
            );
            Page page = browser.newPage();

            html = inlineImg(html);

            // 2. 塞 HTML
            page.setContent(html, new Page.SetContentOptions().setWaitUntil(WaitUntilState.NETWORKIDLE));

            // 3. 构造 PDF 参数
            Page.PdfOptions pdfOpts = new Page.PdfOptions()
                    .setFormat(opts.getFormat())
                    .setPrintBackground(true)
                    .setMargin(new Margin()
                            .setTop("20mm")
                            .setBottom("20mm")
                            .setLeft("10mm")
                            .setRight("10mm"));

            if (opts.isDisplayHeaderFooter()) {
                pdfOpts.setDisplayHeaderFooter(true)
                       .setHeaderTemplate(opts.getHeaderTemplate() != null ? opts.getHeaderTemplate().replace("{{title}}", "2025 销售报表")
                               .replace("{{createTime}}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))) : "")
                       .setFooterTemplate(opts.getFooterTemplate() != null ? opts.getFooterTemplate().replace("{{createTime}}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))) : "");
            }

            // 4. 生成 PDF
            byte[] pdf = page.pdf(pdfOpts);
            browser.close();
            return pdf;
        }

    }

    private String inlineImg(String html) {
        // 正则找 <img src="/images/xxx.ext">
        try {
            Pattern p = Pattern.compile("(<img\\s+[^>]*?)src\\s*=\\s*\"([^\"]*\\.(jpg|jpeg|png))\"", Pattern.CASE_INSENSITIVE);

            Matcher m = p.matcher(html);
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                String prefix  = m.group(1);          // <img ... 到 src 前
                String fileName= m.group(2);          // 原路径
                String ext     = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
                String mime    = ext.equals("png") ? "png" : "jpeg";
                byte[] bytes   = Files.readAllBytes(Paths.get("src/main/resources/static/" + fileName));
                String dataUri = "data:image/" + mime + ";base64," + Base64.getEncoder().encodeToString(bytes);

                // 只替换 src 值，其余属性保持原样
                m.appendReplacement(sb, prefix + "src=\"" + dataUri + "\"");
            }
            m.appendTail(sb);
            return sb.toString();
        } catch (IOException e) {
            Log.error("图片转base64错误");
        }
        return html;
    }

}