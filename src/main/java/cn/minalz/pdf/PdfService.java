package cn.minalz.pdf;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.Margin;
import com.microsoft.playwright.options.WaitUntilState;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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
}