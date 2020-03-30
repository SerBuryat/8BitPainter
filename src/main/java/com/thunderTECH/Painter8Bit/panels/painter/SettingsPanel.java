package com.thunderTECH.Painter8Bit.panels.painter;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

public class SettingsPanel extends GridPane {


    public SettingsPanel(PaintPane paintPane) {
        this.setPadding(new Insets(10,10,10,10));

        this.add(getSaveImageButton(paintPane),0,0);
        this.add(getLoadImageButton(paintPane),1,0);
        this.add(getClearPaintPaneButton(paintPane),2,0);
        this.add(getGridStrokesShowCheckBox(paintPane), 3, 0);
        this.add(getShowHelpButton(), 4, 0);
    }

    private Button getSaveImageButton(PaintPane paintPane) {
        Button saveImageButton = new Button("Save image as...");
        saveImageButton.setPadding(new Insets(10,10,10,10));
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
        loadImageButton.setPadding(new Insets(10,10,10,10));
        loadImageButton.setOnAction(event -> paintPane.loadImageToPaintPane());

        return loadImageButton;
    }

    private Button getClearPaintPaneButton(PaintPane paintPane) {
        Button clearPaintPaneButton = new Button("Clear panel");
        clearPaintPaneButton.setPadding(new Insets(10,10,10,10));
        clearPaintPaneButton.setOnAction(event -> paintPane.clearPaintPane());

        return clearPaintPaneButton;
    }

    private CheckBox getGridStrokesShowCheckBox(PaintPane paintPane) {
        CheckBox gridStrokesShowCheckBox = new CheckBox("Show paint grid");
        gridStrokesShowCheckBox.setPadding(new Insets(10,10,10,10));
        gridStrokesShowCheckBox.setSelected(paintPane.isGridLinesVisible());
        gridStrokesShowCheckBox.setOnAction(event ->
                paintPane.setGridLinesVisible(gridStrokesShowCheckBox.isSelected()));

        return gridStrokesShowCheckBox;
    }

    private Button getShowHelpButton() {
        Button showHelpButton = new Button("Help");

        showHelpButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Help");
            alert.setContentText("Right click -> draw rectangle; "
                    + "\n"
                    + "Left click -> 'take' color from rectangle;"
                    + "\n"
                    + "Middle click -> center paint pane (get back size and scale);");

            alert.showAndWait();
        });

        return showHelpButton;
    }
}
