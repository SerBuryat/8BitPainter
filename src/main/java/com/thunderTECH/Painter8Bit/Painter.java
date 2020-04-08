package com.thunderTECH.Painter8Bit;

import com.thunderTECH.Painter8Bit.panels.instruments.InstrumentPane;
import com.thunderTECH.Painter8Bit.panels.painter.PaintPane;
import com.thunderTECH.Painter8Bit.panels.painter.SettingsPanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Painter extends Application {

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(getMainPane()));
        stage.setTitle("[8BitPainter]");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private BorderPane getMainPane() {
        BorderPane mainPane = new BorderPane();

        PaintPane paintPane = new PaintPane();
        InstrumentPane instrumentPane = new InstrumentPane(paintPane);
        SettingsPanel settingsPanel = new SettingsPanel(paintPane);

        mainPane.setCenter(paintPane);

        mainPane.setRight(instrumentPane);
        mainPane.setTop(settingsPanel);

        return mainPane;
    }
}
