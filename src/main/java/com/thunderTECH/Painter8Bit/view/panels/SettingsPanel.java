package com.thunderTECH.Painter8Bit.view.panels;

import com.thunderTECH.Painter8Bit.Painter;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class SettingsPanel extends GridPane {


    public SettingsPanel(PainterCanvas paintPane) {
        this.getStyleClass().add("settings-pane");
        this.getStylesheets().add("css-styles/settings-panel-style.css");

        this.add(getSaveProjectButton(paintPane),0,0);
        this.add(getLoadProjectButton(paintPane),1,0);
        this.add(getSaveImageButton(paintPane),2,0);
        this.add(getClearPainterCanvasButton(paintPane),3,0);
        this.add(getCanvasGridLineVisibleCheckBox(paintPane), 4, 0);
        this.add(getChangePainterCanvasSizePane(paintPane),5,0);
        this.add(getDefaultPaintPanePosition(paintPane),6,0);
    }

    private Button getSaveImageButton(PainterCanvas painterCanvas) {
        Button saveImageButton = new Button("Save project as png");

        saveImageButton.setOnAction(event -> Painter.SAVE_PAINTER_CANVAS_IMAGE_AS_PNG(painterCanvas));

        return saveImageButton;
    }

    private Button getSaveProjectButton(PainterCanvas painterCanvas) {
        Button saveImageButton = new Button("Save project");

        saveImageButton.setOnAction(event -> Painter.SAVE_PROJECT(painterCanvas));

        return saveImageButton;
    }

    private Button getLoadProjectButton(PainterCanvas painterCanvas) {
        Button saveImageButton = new Button("Load project");

        saveImageButton.setOnAction(event -> Painter.LOAD_PROJECT(painterCanvas));

        return saveImageButton;
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
