package com.thunderTECH.Painter8Bit.panels.painter;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;

public class SettingsPanel extends GridPane {


    public SettingsPanel(PaintPane paintPane) {

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

        Button loadImageButton = new Button("Load image from...");
        loadImageButton.setPadding(new Insets(10,10,10,10));
        loadImageButton.setOnAction(event -> paintPane.loadImageToPaintPane());

        Button clearPaintPane = new Button("Clear panel");
        clearPaintPane.setPadding(new Insets(10,10,10,10));
        clearPaintPane.setOnAction(event -> paintPane.clearPaintPane());

        CheckBox isGridStrokesShowCheckBox = new CheckBox("Show paint grid");
        isGridStrokesShowCheckBox.setPadding(new Insets(10,10,10,10));
        isGridStrokesShowCheckBox.setSelected(paintPane.isGridLinesVisible());
        isGridStrokesShowCheckBox.setOnAction(event ->
                paintPane.setGridLinesVisible(isGridStrokesShowCheckBox.isSelected()));

        this.setPadding(new Insets(10,10,10,10));

        this.add(saveImageButton,0,0);
        this.add(loadImageButton,1,0);
        this.add(clearPaintPane,2,0);
        this.add(isGridStrokesShowCheckBox, 3, 0);
    }
}
