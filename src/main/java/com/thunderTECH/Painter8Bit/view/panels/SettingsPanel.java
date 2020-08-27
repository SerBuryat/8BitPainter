package com.thunderTECH.Painter8Bit.view.panels;

import com.thunderTECH.Painter8Bit.control.Painter;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class SettingsPanel extends GridPane {


    public SettingsPanel(Painter painter) {
        this.getStyleClass().add("settings-pane");
        this.getStylesheets().add("css-styles/settings-panel-style.css");

        this.add(getSaveProjectButton(painter),0,0);
        this.add(getLoadProjectButton(painter),1,0);
        this.add(getSaveImageButton(painter),2,0);
        this.add(getClearPainterCanvasButton(painter),3,0);
        this.add(getCanvasGridLineVisibleCheckBox(painter), 4, 0);
        this.add(getChangePainterCanvasSizePane(painter),5,0);
        this.add(getDefaultPaintPanePosition(painter),6,0);
    }

    private Button getSaveImageButton(Painter painter) {
        Button saveImageButton = new Button("Save project as png");

        saveImageButton.setOnAction(event -> painter.savePainterCanvasImageAsPng());

        return saveImageButton;
    }

    private Button getSaveProjectButton(Painter painter) {
        Button saveImageButton = new Button("Save project");

        saveImageButton.setOnAction(event -> painter.saveProject());

        return saveImageButton;
    }

    private Button getLoadProjectButton(Painter painter) {
        Button saveImageButton = new Button("Load project");

        saveImageButton.setOnAction(event -> painter.loadProject());

        return saveImageButton;
    }

    private Button getClearPainterCanvasButton(Painter painter) {
        Button clearPaintPaneButton = new Button("Clear panel");
        clearPaintPaneButton.setOnAction(event -> painter.clear());

        return clearPaintPaneButton;
    }

    private CheckBox getCanvasGridLineVisibleCheckBox(Painter painter) {
        CheckBox gridStrokesShowCheckBox = new CheckBox("Show paint grid");
        gridStrokesShowCheckBox.setSelected(painter.isGridLineVisible());
        gridStrokesShowCheckBox.setOnAction(event -> {
                painter.setGridLineVisible(gridStrokesShowCheckBox.isSelected());
                painter.repaint();
        });

        return gridStrokesShowCheckBox;
    }

    private Button getDefaultPaintPanePosition(Painter painter) {
        Button defaultSizePaintPaneButton = new Button("painterCanvas default position ");

        defaultSizePaintPaneButton.setOnAction(action -> painter.setPainterCanvasDefaultPosition());

        return defaultSizePaintPaneButton;
    }

    private GridPane getChangePainterCanvasSizePane(Painter painter) {
        GridPane changePaintPaneSizePane = new GridPane();

        Label widthText = new Label("Width:");
        Label heightText = new Label("Height:");

        TextField widthField = new TextField(String.valueOf((painter.getCanvasWidth())));
        TextField heightField = new TextField(String.valueOf((painter.getCanvasHeight())));

        Button changeSizeButton = new Button("Change size");
        changeSizeButton.setOnAction(actionEvent ->
                painter.setCanvasSize
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
