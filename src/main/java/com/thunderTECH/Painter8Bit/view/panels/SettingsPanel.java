package com.thunderTECH.Painter8Bit.view.panels;

import com.thunderTECH.Painter8Bit.control.Painter;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class SettingsPanel extends GridPane {
    private final Painter painter;

    public SettingsPanel(Painter painter) {
        this.painter = painter;

        this.getStyleClass().add("settings-pane");
        this.getStylesheets().add("css-styles/settings-panel-style.css");

        MenuBar menuBar = new MenuBar();
        menuBar.getStyleClass().add("settings-pane");
        menuBar.getStylesheets().add("css-styles/settings-panel-style.css");

        this.add(menuBar, 0, 0);
        this.add(getCanvasGridLineVisibleCheckBox(), 1, 0);
        this.add(getDefaultPaintPanePosition(), 2, 0);

        Menu menuFile = new Menu("File");
        menuFile.getItems().add(getSaveProjectItem());
        menuFile.getItems().add(getSaveImageItem());
        menuFile.getItems().add(getLoadProjectItem());

        Menu menuOptions = new Menu("Options");
        menuOptions.getItems().add(getChangePainterCanvasSizeItem());
        menuOptions.getItems().add(getClearPainterCanvasItem());

        Menu menuHelp = new Menu("Help");
        menuHelp.getItems().add(getShowTipsItem());

        menuBar.getMenus().addAll(menuFile, menuOptions, menuHelp);
    }

    private MenuItem getSaveImageItem() {
        MenuItem saveImageItem = new MenuItem("Save project as png");

        saveImageItem.setOnAction(event -> painter.savePainterCanvasImageAsPng());

        return saveImageItem;
    }

    private MenuItem getSaveProjectItem() {
        MenuItem saveImageMenu = new MenuItem("Save project");

        saveImageMenu.setOnAction(event -> painter.saveProject());

        return saveImageMenu;
    }

    private MenuItem getLoadProjectItem() {
        MenuItem saveImageItem = new MenuItem("Load project");

        saveImageItem.setOnAction(event -> painter.loadProject());

        return saveImageItem;
    }

    private MenuItem getClearPainterCanvasItem() {
        MenuItem clearPaintPaneItem = new MenuItem("Clear panel");

        clearPaintPaneItem.setOnAction(event -> painter.clear());

        return clearPaintPaneItem;
    }

    private CheckBox getCanvasGridLineVisibleCheckBox() {
        CheckBox gridStrokesShowCheckBox = new CheckBox("Show paint grid");
        gridStrokesShowCheckBox.setSelected(painter.isGridLineVisible());
        gridStrokesShowCheckBox.setOnAction(event -> {
                painter.setGridLineVisible(gridStrokesShowCheckBox.isSelected());
                painter.repaint();
        });

        return gridStrokesShowCheckBox;
    }

    private Button getDefaultPaintPanePosition() {
        Button defaultSizePaintPaneButton = new Button("Canvas default position ");

        defaultSizePaintPaneButton.setOnAction(action -> painter.setPainterCanvasDefaultPosition());

        return defaultSizePaintPaneButton;
    }

    private GridPane getChangePainterCanvasSizePane(Dialog<DialogPane> dialog) {
        GridPane changePaintPaneSizePane = new GridPane();

        Label widthText = new Label("Width:");
        Label heightText = new Label("Height:");

        TextField widthField = new TextField(String.valueOf((painter.getCanvasWidth())));
        TextField heightField = new TextField(String.valueOf((painter.getCanvasHeight())));

        Button changeSizeButton = new Button("Change size");
        changeSizeButton.setOnAction(actionEvent -> {
                    painter.setCanvasSize
                            (Integer.parseInt(widthField.getText()),Integer.parseInt(heightField.getText()));
                    dialog.close();
                });

        changePaintPaneSizePane.add(widthText,0,0);
        changePaintPaneSizePane.add(heightText,0,1);
        changePaintPaneSizePane.add(widthField,1,0);
        changePaintPaneSizePane.add(heightField,1,1);
        changePaintPaneSizePane.add(changeSizeButton,2,0);

        changePaintPaneSizePane.setPadding(new Insets(10,10,10,10));

        return changePaintPaneSizePane;
    }

    private MenuItem getChangePainterCanvasSizeItem() {
        MenuItem changeSizeCanvasItem = new MenuItem("Change canvas size");

        Dialog<DialogPane> dialog = new Dialog<>();
        DialogPane dialogPane = new DialogPane();
        dialog.setDialogPane(dialogPane);
        dialogPane.getButtonTypes().add(ButtonType.CANCEL);
        dialogPane.setContent(getChangePainterCanvasSizePane(dialog));
        changeSizeCanvasItem.setOnAction(actionEvent -> dialog.showAndWait());

        return  changeSizeCanvasItem;
    }

    private MenuItem getShowTipsItem() {
        MenuItem showTipsItem = new MenuItem("Show tips");

        Dialog<DialogPane> dialog = new Dialog<>();
        DialogPane dialogPane = new DialogPane();
        dialog.setDialogPane(dialogPane);
        dialog.setHeaderText("PixelPainter tips");
        dialogPane.getButtonTypes().add(ButtonType.CANCEL);

        Label tipsLabel = new Label(
                "RIGHT MOUSE BUTTON - paint with current color" + "\n"
                        + "LEFT MOUSE BUTTON - set current color from canvas rectangle" + "\n"
                        + "CTRL+Z - 'unpaint' last colored rectangle" + "\n"
                        + "Projects folder - 'c://PixelPainter projects'" + "\n");
        tipsLabel.setAlignment(Pos.CENTER);

        dialogPane.setContent(tipsLabel);
        showTipsItem.setOnAction(actionEvent -> dialog.showAndWait());

        return showTipsItem;
    }
}
