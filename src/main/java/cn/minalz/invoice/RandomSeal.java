package cn.minalz.invoice;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 一键生成随机圆形公章，透明背景，文件：seal.png
 */
public class RandomSeal {

    /* ========== 可玩参数 ========== */
    private static final int IMG_SIZE = 300;          // 图片宽高
    private static final int BORDER_THICK = 12;       // 圆环线宽
    private static final Color SEAL_COLOR = Color.RED;
    private static final String[] COMPANY_POOL = {
            "上海天下第一科技有限公司", "上海天下第二科技有限公司"
    };
    /* ================================= */

    public static void main(String[] args) throws IOException {
        String company = COMPANY_POOL[(int) (Math.random() * COMPANY_POOL.length)];

        BufferedImage img = new BufferedImage(IMG_SIZE, IMG_SIZE, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 透明底
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, IMG_SIZE, IMG_SIZE);
        g2.setComposite(AlphaComposite.SrcOver);

        int center = IMG_SIZE / 2;
        int radius = center - BORDER_THICK;

        /* 1. 红色圆环 */
        g2.setColor(SEAL_COLOR);
        g2.setStroke(new BasicStroke(BORDER_THICK));
        g2.draw(new Ellipse2D.Float(BORDER_THICK / 2f, BORDER_THICK / 2f,
                IMG_SIZE - BORDER_THICK, IMG_SIZE - BORDER_THICK));

        /* 2. 五角星 */
        drawStar(g2, center, center, radius / 3);

        /* 3. 圆弧文字 */
        drawCircleText(g2, company, center, radius - 20);

        g2.dispose();
        ImageIO.write(img, "PNG", new File("static/seal.png"));
        System.out.println("seal.png 已生成！");
    }

    /* ----------- 五角星 ------------- */
    private static void drawStar(Graphics2D g, int cx, int cy, int r) {
        GeneralPath star = new GeneralPath();
        double angle = -Math.PI / 2; // 从顶点开始
        double delta = Math.PI / 5;
        for (int i = 0; i < 10; i++) {
            double rr = (i & 1) == 0 ? r : r * 0.4;
            double x = cx + rr * Math.cos(angle);
            double y = cy + rr * Math.sin(angle);
            if (i == 0) star.moveTo(x, y);
            else star.lineTo(x, y);
            angle += delta;
        }
        star.closePath();
        g.setColor(SEAL_COLOR);
        g.fill(star);
    }

    /* ----------- 圆弧文字 ------------- */
    private static void drawCircleText(Graphics2D g, String text, int center, int radius) {
        Font font = new Font("宋体", Font.BOLD, 18);
        g.setFont(font);
        FontRenderContext frc = g.getFontRenderContext();

        // 计算总弧度
        double totalArc = 0;
        for (char c : text.toCharArray()) {
            totalArc += font.createGlyphVector(frc, Character.toString(c)).getVisualBounds().getWidth();
        }
        // 平均角度间隔
        double angleStep = Math.toRadians(360) / text.length();
        double startAngle = -totalArc / 2 / radius; // 居中

        double angle = startAngle;
        for (char c : text.toCharArray()) {
            String ch = Character.toString(c);
            double w = font.createGlyphVector(frc, ch).getVisualBounds().getWidth();
            AffineTransform at = new AffineTransform();
            at.translate(center, center);
            at.rotate(angle + w / 2 / radius);
            at.translate(0, -radius);
            Shape chShape = font.createGlyphVector(frc, ch).getOutline();
            g.setTransform(at);
            g.setColor(SEAL_COLOR);
            g.fill(chShape);
            angle += angleStep;
        }
        g.setTransform(new AffineTransform()); // 复位
    }
}