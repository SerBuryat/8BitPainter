package com.thunderTECH.Painter8Bit;

import com.thunderTECH.Painter8Bit.panels.painter.PaintPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Painter extends Application {
    public static final int WIDTH = 800;
    public final static int HEIGHT = 600;

    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(new PaintPane(WIDTH,HEIGHT)));
        stage.setTitle("[8BitPainter]");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
