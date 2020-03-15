package com.thunderTECH.Painter8Bit;

import com.thunderTECH.Painter8Bit.panels.painter.InstrumentPane;
import com.thunderTECH.Painter8Bit.panels.painter.PaintPane;
import com.thunderTECH.Painter8Bit.panels.painter.SettingsPanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Painter extends Application {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

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

        PaintPane paintPane = new PaintPane(WIDTH, HEIGHT);
        InstrumentPane instrumentPane = new InstrumentPane(paintPane);
        SettingsPanel settingsPanel = new SettingsPanel(paintPane);

        mainPane.setCenter(paintPane);
        mainPane.setRight(instrumentPane);
        mainPane.setTop(settingsPanel);

        return mainPane;
    }
}
