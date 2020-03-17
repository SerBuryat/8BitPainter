package com.thunderTECH.Painter8Bit.panels.painter;

import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class InstrumentPane extends GridPane {
    private final PaintPane paintPane;
    private final Color[] colors = {Color.WHITE, Color.LIGHTGRAY, Color.YELLOW, Color.GREENYELLOW, Color.GREEN,
                              Color.BROWN, Color.RED, Color.PURPLE, Color.BLUE, Color.INDIGO, Color.BLACK};

    public InstrumentPane(PaintPane paintPane) {
        this.paintPane = paintPane;

        loadNodesOnPane(getRectanglesArray(colors.length));


        this.setPadding(new Insets(20,20,20,20));
    }

    private Rectangle[] getRectanglesArray(int length) {
        Rectangle[] rectangles = new Rectangle[length];
        for(int i = 0; i < length; i++) {
            rectangles[i] = createRect(40, 40, colors[i], Color.GRAY);
        }
        return rectangles;
    }

    private void loadNodesOnPane(Rectangle[] rectangles) {

        for(int i = 0; i < rectangles.length; i++)
            this.add(rectangles[i], 0, i);

    }

    private Rectangle createRect(int width, int height, Color fillColor, Color strokeColor) {
        Rectangle rect = new Rectangle(width, height);
        rect.setFill(fillColor);
        rect.setStroke(strokeColor);

        rect.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                paintPane.setCurrentRectColor((Color) rect.getFill());
            }
        });

        return rect;
    }

}
