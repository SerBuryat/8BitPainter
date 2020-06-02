package com.thunderTECH.Painter8Bit.view.panels.instruments;

import com.thunderTECH.Painter8Bit.view.panels.PainterCanvas;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;


public class InstrumentPane extends BorderPane {

    private static List<Rectangle> lastColorsRectanglesList;

    public InstrumentPane(PainterCanvas painterCanvas) {

        this.setTop(getColorPicker(painterCanvas));

        this.setCenter(new ColorsPalette(painterCanvas));

        this.setBottom(getLastUsedColorsPane(painterCanvas));

        this.setPadding(new Insets(20,20,20,20));
    }

    public static void ADD_LAST_USED_COLOR(Color color) {
        Color lastColor = (Color) lastColorsRectanglesList.get(0).getFill();
        if(lastColor != color && !IS_COLOR_IN_LAST_COLORS_LIST(color, lastColorsRectanglesList)) {
            for(int i = lastColorsRectanglesList.size()-1; i > 0; i--) {
                lastColorsRectanglesList.get(i).setFill(lastColorsRectanglesList.get(i-1).getFill());
            }
            lastColorsRectanglesList.get(0).setFill(color);
        }
    }

    private static boolean IS_COLOR_IN_LAST_COLORS_LIST(Color color, List<Rectangle> lastUsedColorsList) {

        return lastUsedColorsList.stream()
                .anyMatch(rectangle -> rectangle.getFill() == color);
    }

    private BorderPane getLastUsedColorsPane(PainterCanvas painterCanvas) {
        BorderPane lastUsedColorsPane = new BorderPane(); // lastUsedColorsPane

        lastUsedColorsPane.setTop(new Label("Last used colors: "));

        GridPane colorsPane = new GridPane();
        lastUsedColorsPane.setCenter(colorsPane);

        lastColorsRectanglesList = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            lastColorsRectanglesList.add(new Rectangle(15,15));
            colorsPane.add(lastColorsRectanglesList.get(i), i , 0);
        }

        lastColorsRectanglesList.forEach(rect -> {
            rect.setFill(Color.WHITE);
            rect.setStroke(Color.BLACK);
            rect.setStrokeWidth(0.5);
            rect.setOnMouseClicked(event -> {
                if(event.getButton() == MouseButton.PRIMARY) {
                    painterCanvas.setCurrentPixelColor((Color) rect.getFill());
                }
            });
        });

        return lastUsedColorsPane;
    }

    private ColorPicker getColorPicker(PainterCanvas painterCanvas) {
        ColorPicker colorPicker = new ColorPicker();

        colorPicker.setOnAction(action -> painterCanvas.setCurrentPixelColor(colorPicker.getValue()));

        return colorPicker;
    }


}
