package com.thunderTECH.Painter8Bit;

import com.thunderTECH.Painter8Bit.panels.instruments.InstrumentPane;
import com.thunderTECH.Painter8Bit.panels.PaintPane;
import com.thunderTECH.Painter8Bit.panels.SettingsPanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Painter extends Application {

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(getMainPane(),1280,720));
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
