package com.thunderTECH.Painter8Bit.panels.painter;

import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;


public class InstrumentPane extends GridPane {
    private final PaintPane paintPane;
    private final ArrayList<Color> colors;

    public InstrumentPane(PaintPane paintPane) {
        this.paintPane = paintPane;

        int rectWidth = 20;
        int rectHeight = 20;

        colors = getColorsList();

        loadPalletOnPane(getRectanglesList(colors.size(), rectWidth, rectHeight));


        this.setPadding(new Insets(20,20,20,20));
    }

    private ArrayList<Color> getColorsList() {
        ArrayList<Color> colorsList = new ArrayList<>();

        int[] colorModifier = {255, 192, 128, 64, 0};

        for (int r : colorModifier) {
            for (int g : colorModifier) {
                for (int b : colorModifier) {
                    colorsList.add(Color.rgb(r, g, b));
                }
            }
        }

        return colorsList;
    }

    private ArrayList<Rectangle> getRectanglesList(int colorsSize, int rectWidth, int rectHeight) {
        ArrayList<Rectangle> rectanglesList = new ArrayList<>(colorsSize);

        for (int i = 0; i < colorsSize; i++)
            rectanglesList.add(createRect(rectWidth, rectHeight, colors.get(i)));

        return rectanglesList;
    }

    private Rectangle createRect(int width, int height, Color fillColor) {
        Rectangle rect = new Rectangle(width, height);
        rect.setFill(fillColor);
        rect.setStroke(Color.LIGHTGRAY);

        rect.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                paintPane.setCurrentRectColor((Color) rect.getFill());
            }
        });

        return rect;
    }

    private void loadPalletOnPane(ArrayList<Rectangle> rectanglesList) {
        int colSize = 25;
        int rowSize = 25;

        for(int i = 0; i < rectanglesList.size(); i++) {
            int row = (i < colSize ? i : i % colSize);
            int col = i / rowSize;
            this.add(rectanglesList.get(i), col, row);
        }
    }

}
