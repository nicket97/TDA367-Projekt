package model;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
/**
 * Adds a chosen text to the picture
 *
 */
public class TextFilter implements Layerable{

    private String text = "Your text";
    private String font = "Helvetica";
    private int size = 40;
    private int yPosition = 100;
    private int r = 255;
    private int g = 255;
    private int b = 255;

    public TextFilter(String text, int size, int yPosition, int r, int g, int b) {
        this.text = text;
        this.size = size;
        this.yPosition = yPosition;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public TextFilter() {}

    @Override
    public LoadedImage transform(LoadedImage img) {
        BufferedImage BImg = img.getBufferedImg();

        Graphics2D g2 = BImg.createGraphics();
        FontMetrics metrics = g2.getFontMetrics(new Font(font, Font.BOLD, size));
        g2.setColor(new java.awt.Color(r, g, b));
        g2.setFont(new Font(font, Font.BOLD, size));
        int xPosition = (img.getWidth() - metrics.stringWidth(text)) / 2;
        g2.drawString(text, xPosition, yPosition);

        return new LoadedImage(BImg);
    }

    @Override
    public String saveLayer() {
        String output = "TextFilter?" + text + "?" + size + "?" + yPosition + "?" + r + "?" + g + "?" + b + "?";
        return output;
    }

    @Override
    public String getName() {
        return "Textfilter";
    }

    @Override
    public List<Slider> getSliders() {
        List<Slider> emptyList = new ArrayList();
        return emptyList;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getPosition() {
        return yPosition;
    }

    public void setPosition(String position) {
        this.yPosition = yPosition;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }
}
