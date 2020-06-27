package com.thunderTECH.Painter8Bit.view.panels;

import com.thunderTECH.Painter8Bit.Painter;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class SettingsPanel extends GridPane {


    public SettingsPanel(PainterCanvas paintPane) {
        this.getStyleClass().add("settings-pane");
        this.getStylesheets().add("css-styles/settings-panel-style.css");

        this.add(getSaveImageButton(paintPane),0,0);
        this.add(getLoadImageButton(paintPane),1,0);
        this.add(getClearPainterCanvasButton(paintPane),2,0);
        this.add(getCanvasGridLineVisibleCheckBox(paintPane), 3, 0);
        this.add(getChangePainterCanvasSizePane(paintPane),4,0);
        this.add(getDefaultPaintPanePosition(paintPane),5,0);
    }

    private Button getSaveImageButton(PainterCanvas painterCanvas) {
        Button saveImageButton = new Button("Save image as...");

        saveImageButton.setOnAction(event -> Painter.SAVE_PAINTER_CANVAS_IMAGE(painterCanvas));

        return saveImageButton;
    }

    private Button getLoadImageButton(PainterCanvas painterCanvas) {
        Button loadImageButton = new Button("Load image from...");
        loadImageButton.setOnAction(event -> Painter.LOAD_PAINTER_CANVAS_IMAGE(painterCanvas));

        return loadImageButton;
    }

    private Button getClearPainterCanvasButton(PainterCanvas painterCanvas) {
        Button clearPaintPaneButton = new Button("Clear panel");
        clearPaintPaneButton.setOnAction(event -> painterCanvas.clear());

        return clearPaintPaneButton;
    }

    private CheckBox getCanvasGridLineVisibleCheckBox(PainterCanvas painterCanvas) {
        CheckBox gridStrokesShowCheckBox = new CheckBox("Show paint grid");
        gridStrokesShowCheckBox.setSelected(painterCanvas.isGridLineVisible());
        gridStrokesShowCheckBox.setOnAction(event -> {
                painterCanvas.setGridLineVisible(gridStrokesShowCheckBox.isSelected());
                painterCanvas.paint();
        });

        return gridStrokesShowCheckBox;
    }

    private Button getDefaultPaintPanePosition(PainterCanvas painterCanvas) {
        Button defaultSizePaintPaneButton = new Button("painterCanvas default position ");

        defaultSizePaintPaneButton.setOnAction(action -> painterCanvas.setPainterCanvasDefaultPosition());

        return defaultSizePaintPaneButton;
    }

    private GridPane getChangePainterCanvasSizePane(PainterCanvas painterCanvas) {
        GridPane changePaintPaneSizePane = new GridPane();

        Label widthText = new Label("Width:");
        Label heightText = new Label("Height:");

        TextField widthField = new TextField(String.valueOf((int)painterCanvas.getCanvas().getWidth()));
        TextField heightField = new TextField(String.valueOf((int)painterCanvas.getCanvas().getHeight()));

        Button changeSizeButton = new Button("Change size");
        changeSizeButton.setOnAction(actionEvent ->
                painterCanvas.setCanvasSize
                        (Integer.parseInt(widthField.getText()),Integer.parseInt(heightField.getText())));

        changePaintPaneSizePane.add(widthText,0,0);
        changePaintPaneSizePane.add(heightText,0,1);
        changePaintPaneSizePane.add(widthField,1,0);
        changePaintPaneSizePane.add(heightField,1,1);
        changePaintPaneSizePane.add(changeSizeButton,2,0);

        changePaintPaneSizePane.setPadding(new Insets(10,10,10,10));

        return changePaintPaneSizePane;
    }
}
