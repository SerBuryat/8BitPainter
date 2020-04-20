package com.thunderTECH.Painter8Bit.panels.instruments;

import com.thunderTECH.Painter8Bit.ColorsGenerator;
import com.thunderTECH.Painter8Bit.panels.PaintPane;
import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class ColorsPalette extends GridPane {
    private final PaintPane paintPane;
    private final List<Color> colors;
    private static List<Rectangle> rectangles;

    public ColorsPalette(PaintPane paintPane) {
        this.paintPane = paintPane;

        int rectWidth = 15;
        int rectHeight = 15;

        colors = getColorsList();
        rectangles = getRectanglesList(colors.size(), rectWidth, rectHeight);

        loadPalletOnPane(rectangles);


        this.setPadding(new Insets(20, 20, 20, 20));
    }

    public static void showCurrentColorOnPalette(Color currentColor) {
        rectangles.forEach(rectangle -> rectangle.setStroke(Color.DARKGRAY));

        for (Rectangle rect : rectangles) {
            if(rect.getFill().equals(currentColor))
                rect.setStroke(Color.RED);
        }
    }

    private List<Color> getColorsList() {

        List<Color> colorsList = new ArrayList<>();

        int brightnessLevel = 4;

        for (int i = brightnessLevel; i > 0; i--) {
            List<Color> tmpColors = new ArrayList<>(ColorsGenerator.GET_24_COLOR_PALETTE());
            List<Color> brighterColors = ColorsGenerator.BRIGHTER_COLORS_LIST(tmpColors, i);
            colorsList.addAll(brighterColors);
        }

        int darknessLevel = 5; // 0 is original color level

        for (int i = 0; i < darknessLevel; i++) {
            List<Color> tmpColors = new ArrayList<>(ColorsGenerator.GET_24_COLOR_PALETTE());
            List<Color> darkerColors = ColorsGenerator.DARKER_COLORS_LIST(tmpColors, i);
            colorsList.addAll(darkerColors);
        }

        colorsList.addAll(ColorsGenerator.GET_WHITE_BLACK_COLOR_PALETTE());

        colorsList.add(Color.TRANSPARENT);// last color in palette is TRANSPARENT

        return colorsList;
    }

    private List<Rectangle> getRectanglesList(int colorsSize, int rectWidth, int rectHeight) {
        rectangles = new ArrayList<>();

        for (int i = 0; i < colorsSize; i++)
            rectangles.add(createRect(rectWidth, rectHeight, colors.get(i)));

        return rectangles;
    }

    private Rectangle createRect(int width, int height, Color fillColor) {
        Rectangle rect = new Rectangle(width, height);
        rect.setFill(fillColor);

        rect.setStroke(Color.DARKGRAY);

        rect.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                paintPane.setCurrentRectColor((Color) rect.getFill());
            }
        });

        return rect;
    }

    private void loadPalletOnPane(List<Rectangle> rectanglesList) {
        // 24 main colors
        int colSize = 24;
        int rowSize = 24;

        for (int i = 0; i < rectanglesList.size(); i++) {
            int row = (i < rowSize ? i : i % rowSize);
            int col = i / colSize;
            this.add(rectanglesList.get(i), col, row);
        }
    }
}
