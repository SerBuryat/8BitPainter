package com.thunderTECH.Painter8Bit.view.panels;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class SettingsPanel extends GridPane {


    public SettingsPanel(PainterCanvas paintPane) {
        this.getStyleClass().add("settings-pane");
        this.getStylesheets().add("css-styles/settings-panel-style.css");

        //this.add(getSaveImageButton(paintPane),0,0);
        //this.add(getLoadImageButton(paintPane),1,0);
        this.add(getClearPainterCanvasButton(paintPane),2,0);
        this.add(getCanvasGridLineVisibleCheckBox(paintPane), 3, 0);
        //this.add(getChangePaintPaneSizePane(paintPane),4,0);
        //this.add(getDefaultPaintPanePosition(paintPane),5,0);
        //this.add(getShowHelpButton(), 6, 0);
    }

    /*private Button getSaveImageButton(PainterCanvas painterCanvas) {
        Button saveImageButton = new Button("Save image as...");

        saveImageButton.setOnAction(event -> {
            if(painterCanvas.getPixelGrid().isGridLineVisible()) {
                painterCanvas.getPixelGrid().setGridLineVisible(false);
                painterCanvas.saveImageFromPaintPane();
                painterCanvas.getPixelGrid().setGridLineVisible(true);
            } else
                painterCanvas.saveImageFromPaintPane();
        });

        return saveImageButton;
    }*/

    /*private Button getLoadImageButton(PainterCanvas painterCanvas) {
        Button loadImageButton = new Button("Load image from...");
        loadImageButton.setOnAction(event -> painterCanvas.loadImageToPaintPane());

        return loadImageButton;
    }*/

    private Button getClearPainterCanvasButton(PainterCanvas painterCanvas) {
        Button clearPaintPaneButton = new Button("Clear panel");
        clearPaintPaneButton.setOnAction(event -> painterCanvas.clear());

        return clearPaintPaneButton;
    }

    private CheckBox getCanvasGridLineVisibleCheckBox(PainterCanvas painterCanvas) {
        CheckBox gridStrokesShowCheckBox = new CheckBox("Show paint grid");
        gridStrokesShowCheckBox.setSelected(painterCanvas.getPixelGrid().isGridLineVisible());
        gridStrokesShowCheckBox.setOnAction(event -> {
                painterCanvas.getPixelGrid().setGridLineVisible(gridStrokesShowCheckBox.isSelected());
                painterCanvas.paint();
        });

        return gridStrokesShowCheckBox;
    }

    /*private Button getDefaultPaintPanePosition(PainterCanvas paintPane) {
        Button defaultSizePaintPaneButton = new Button("paintPane default position ");

        defaultSizePaintPaneButton.setOnAction(action -> paintPane.setPaintPaneDefaultPosition());

        return defaultSizePaintPaneButton;
    }*/

    /*private Button getShowHelpButton() {
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
    }*/

    /*private GridPane getChangePaintPaneSizePane(PainterCanvas paintPane) {
        GridPane changePaintPaneSizePane = new GridPane();

        Label widthText = new Label("Width:");
        Label heightText = new Label("Height:");

        TextField widthField = new TextField(String.valueOf((int)paintPane.getWidth()));
        TextField heightField = new TextField(String.valueOf((int)paintPane.getHeight()));

        Button changeSizeButton = new Button("Change size");
        changeSizeButton.setOnAction(actionEvent ->
                paintPane.setPaintPaneSize
                        (Integer.parseInt(widthField.getText()),Integer.parseInt(heightField.getText())));

        changePaintPaneSizePane.add(widthText,0,0);
        changePaintPaneSizePane.add(heightText,0,1);
        changePaintPaneSizePane.add(widthField,1,0);
        changePaintPaneSizePane.add(heightField,1,1);
        changePaintPaneSizePane.add(changeSizeButton,2,0);

        changePaintPaneSizePane.setPadding(new Insets(10,10,10,10));

        return changePaintPaneSizePane;
    }*/
}
