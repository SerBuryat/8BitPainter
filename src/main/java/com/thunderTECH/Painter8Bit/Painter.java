package com.thunderTECH.Painter8Bit;

import com.thunderTECH.Painter8Bit.view.Viewer;
import com.thunderTECH.Painter8Bit.view.panels.PainterCanvas;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Painter extends Application {
    private static int RECT_SIZE = 10; //SIZExSIZE rect
    private static int CANVAS_WIDTH = 800;
    private static int CANVAS_HEIGHT = 600;
    private static Color GRID_LINES_COLOR = Color.LIGHTGRAY;
    private static Color RECT_COLOR = Color.TRANSPARENT;

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

    public static void LOAD_PAINTER_CANVAS_IMAGE(PainterCanvas painterCanvas) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if(file != null){
            Image loadedImage = new Image(file.toURI().toString());
            painterCanvas.loadImage(loadedImage);
        }
    }

    public static int GET_CANVAS_WIDTH() {
        return CANVAS_WIDTH;
    }

    public static int GET_CANVAS_HEIGHT() {
        return CANVAS_HEIGHT;
    }

    public static int GET_RECT_SIZE() {
        return RECT_SIZE;
    }

    public static Color GET_GRID_LINES_COLOR() {
        return GRID_LINES_COLOR;
    }

    public static Color GET_RECT_COLOR() {
        return RECT_COLOR;
    }
}
