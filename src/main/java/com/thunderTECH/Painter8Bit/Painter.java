package com.thunderTECH.Painter8Bit;

import com.thunderTECH.Painter8Bit.view.Viewer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Painter extends Application {
    private static int PIXEL_SIZE = 10; // 10x10 rectangle
    private static int CANVAS_WIDTH = 800;
    private static int CANVAS_HEIGHT = 600;
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(Viewer.GET_VIEW_PANE(),1280,720);
        stage.setScene(scene);
        stage.setTitle("[8BitPainter]");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static int GET_PIXEL_SIZE() {
        return PIXEL_SIZE;
    }

    public static int GET_CANVAS_WIDTH() {
        return CANVAS_WIDTH;
    }

    public static int GET_CANVAS_HEIGHT() {
        return CANVAS_HEIGHT;
    }
}
