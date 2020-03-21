package com.thunderTECH.Painter8Bit.panels.painter;

import com.thunderTECH.Painter8Bit.ColorsGenerator;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;


public class InstrumentPane extends GridPane {
    private final PaintPane paintPane;
    private final List<Color> colors;

    public InstrumentPane(PaintPane paintPane) {
        this.paintPane = paintPane;

        int rectWidth = 20;
        int rectHeight = 20;

        colors = getColorsList();

        loadPalletOnPane(getRectanglesList(colors.size(), rectWidth, rectHeight));


        this.setPadding(new Insets(20,20,20,20));
    }

    private List<Color> getColorsList() {
        List<Color> colorsList = new ArrayList<>();

        int brightnessLevel = 4;

        for (int i = brightnessLevel; i > 0; i--) {
            List<Color> tmpColors = new ArrayList<>(ColorsGenerator.GET_24_COLOR_PALLET());
            List<Color> brighterColors = ColorsGenerator.BRIGHTER_COLORS_LIST(tmpColors, i);
            colorsList.addAll(brighterColors);
        }

        colorsList.addAll(ColorsGenerator.GET_24_COLOR_PALLET());

        int darknessLevel = 4;

        for (int i = 0; i < darknessLevel; i++) {
            List<Color> tmpColors = new ArrayList<>(ColorsGenerator.GET_24_COLOR_PALLET());
            List<Color> darkerColors = ColorsGenerator.DARKER_COLORS_LIST(tmpColors, i);
            colorsList.addAll(darkerColors);
        }

        return colorsList;
    }

    private List<Rectangle> getRectanglesList(int colorsSize, int rectWidth, int rectHeight) {
        List<Rectangle> rectanglesList = new ArrayList<>(colorsSize);

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

    private void loadPalletOnPane(List<Rectangle> rectanglesList) {
        // 24 main colors
        int colSize = 24;
        int rowSize = 24;

        for(int i = 0; i < rectanglesList.size(); i++) {
            int row = (i < rowSize ? i : i % rowSize);
            int col = i / colSize;
            this.add(rectanglesList.get(i), col, row);
        }
    }

}
