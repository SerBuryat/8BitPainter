package com.thunderTECH.Painter8Bit;

import com.thunderTECH.Painter8Bit.view.Viewer;
import com.thunderTECH.Painter8Bit.view.panels.PainterCanvas;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

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

    public static void SAVE_PAINTER_CANVAS_IMAGE(PainterCanvas painterCanvas) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            try {
                ImageIO.write(painterCanvas.getSnapshotImage(), "png", file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
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
