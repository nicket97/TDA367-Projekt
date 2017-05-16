package model;

import javafx.scene.control.Slider;
import javafx.scene.paint.Color;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class TextFilter implements Layerable{

    private String text = "Your text";
    private int size = 18;
    private String position = "up";
    private Color color = Color.WHITE;

    public TextFilter(String text, int size, Color color) {
        this.text = text;
        this.size = size;
        this.position = position;
        this.color = color;
    }

    public TextFilter() {}

    @Override
    public LoadedImage transform(LoadedImage img) {

        BufferedImage BImg = img.getBufferedImg();


        Graphics2D g = BImg.createGraphics();
        //g.setColor(color);
        g.setFont(new Font( "SansSerif", Font.BOLD, 12 ));
        g.drawString(text, 10, 10);
        System.err.println(this.text+10+10);
        return new LoadedImage(BImg);

    }

    @Override
    public String saveLayer() {
        String output = "TextFilter?" + text + "?" + size + "?" + position + "?" + color.getRed() + "?" + color.getGreen() + "?" + color.getBlue() + "?";
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
