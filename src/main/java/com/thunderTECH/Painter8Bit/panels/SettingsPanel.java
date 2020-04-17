package com.thunderTECH.Painter8Bit.panels;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class SettingsPanel extends GridPane {


    public SettingsPanel(PaintPane paintPane) {
        this.add(getSaveImageButton(paintPane),0,0);
        this.add(getLoadImageButton(paintPane),1,0);
        this.add(getClearPaintPaneButton(paintPane),2,0);
        this.add(getGridStrokesShowCheckBox(paintPane), 3, 0);
        this.add(getChangePaintPaneRectSizePane(paintPane),4,0);
        this.add(getDefaultPaintPanePosition(paintPane),5,0);
        this.add(getShowHelpButton(), 6, 0);

        this.setPadding(new Insets(10,10,10,10));
    }

    private Button getSaveImageButton(PaintPane paintPane) {
        Button saveImageButton = new Button("Save image as...");

        saveImageButton.setOnAction(event -> {
            if(paintPane.isGridLinesVisible()) {
                paintPane.setGridLinesVisible(false);
                paintPane.saveImageFromPaintPane();
                paintPane.setGridLinesVisible(true);
            } else
                paintPane.saveImageFromPaintPane();
        });

        return saveImageButton;
    }

    private Button getLoadImageButton(PaintPane paintPane) {
        Button loadImageButton = new Button("Load image from...");
        loadImageButton.setOnAction(event -> paintPane.loadImageToPaintPane());

        return loadImageButton;
    }

    private Button getClearPaintPaneButton(PaintPane paintPane) {
        Button clearPaintPaneButton = new Button("Clear panel");
        clearPaintPaneButton.setOnAction(event -> paintPane.clearPaintPane());

        return clearPaintPaneButton;
    }

    private CheckBox getGridStrokesShowCheckBox(PaintPane paintPane) {
        CheckBox gridStrokesShowCheckBox = new CheckBox("Show paint grid");
        gridStrokesShowCheckBox.setSelected(paintPane.isGridLinesVisible());
        gridStrokesShowCheckBox.setOnAction(event ->
                paintPane.setGridLinesVisible(gridStrokesShowCheckBox.isSelected()));

        return gridStrokesShowCheckBox;
    }

    private Button getDefaultPaintPanePosition(PaintPane paintPane) {
        Button defaultSizePaintPaneButton = new Button("paintPane default position ");

        defaultSizePaintPaneButton.setOnAction(action -> {
            paintPane.setPaintPaneDefaultPosition();
        });

        return defaultSizePaintPaneButton;
    }

    private Button getShowHelpButton() {
        Button showHelpButton = new Button("Help");

        showHelpButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Help");
            alert.setContentText("Right mouse button (click) -> draw rectangle; "
                    + "\n"
                    +"Right mouse button (pressed&moved) -> draw line of rectangles on the way of moving; "
                    + "\n"
                    + "Left mouse button (click) -> 'take' color from rectangle;"
                    + "\n"
                    + "Middle mouse button (pressed&moved) -> allow to move paintPane ;"
                    + "\n"
                    + "Middle mouse button (scrolling) -> allow to scrolling(zooming) paintPane ;");

            alert.showAndWait();
        });

        return showHelpButton;
    }

    private BorderPane getChangePaintPaneRectSizePane(PaintPane paintPane) {
        BorderPane changePaintPaneRectSize = new BorderPane();

        Label text = new Label("PaintPane rectangles size");
        Label value = new Label(String.valueOf(paintPane.getPaintPaneRectSize()));

        Button minusSizeValue = new Button("-");
        minusSizeValue.setOnAction
                (actionEvent -> {
                    paintPane.setPaintPaneRectSize(paintPane.getPaintPaneRectSize() - 1);
                    value.setText(String.valueOf(paintPane.getPaintPaneRectSize()));
                });

        Button plusSizeValue = new Button("+");
        plusSizeValue.setOnAction
                (actionEvent -> {
                    paintPane.setPaintPaneRectSize(paintPane.getPaintPaneRectSize() + 1);
                    value.setText(String.valueOf(paintPane.getPaintPaneRectSize()));
                });

        changePaintPaneRectSize.setTop(text);
        changePaintPaneRectSize.setLeft(minusSizeValue);
        changePaintPaneRectSize.setCenter(value);
        changePaintPaneRectSize.setRight(plusSizeValue);

        changePaintPaneRectSize.setPadding(new Insets(10,10,10,10));

        return changePaintPaneRectSize;
    }
}
