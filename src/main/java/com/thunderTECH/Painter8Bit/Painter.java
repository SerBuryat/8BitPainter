package com.thunderTECH.Painter8Bit;

import com.thunderTECH.Painter8Bit.view.Viewer;
import com.thunderTECH.Painter8Bit.view.panels.PainterCanvas;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Painter extends Application {
    private static Stage STAGE;
    private static int RECT_SIZE = 10; //SIZExSIZE rect
    private static int CANVAS_WIDTH = 800;
    private static int CANVAS_HEIGHT = 600;
    private static final Color GRID_LINES_COLOR = Color.LIGHTGREY;
    private static final Color DEFAULT_RECT_COLOR = Color.TRANSPARENT;
    private static final String PROJECTS_DIR = "c:/PixelPainter projects";

    @Override
    public void start(Stage stage) {
        STAGE = stage;

        PainterCanvas painterCanvas = new PainterCanvas(CANVAS_WIDTH,CANVAS_HEIGHT);

        Scene scene = new Scene(new Viewer(painterCanvas).getPane(),1280,720);
        stage.setScene(scene);
        stage.setTitle("[8BitPainter]");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void SAVE_PAINTER_CANVAS_IMAGE_AS_PNG(PainterCanvas painterCanvas) {
        SAVE_PROJECT(painterCanvas);

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

    public static void SAVE_PROJECT(PainterCanvas painterCanvas) {
        /*FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showSaveDialog(STAGE);
        //Filter for saving in .ser file format
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SER files (*.ser)", "*.ser");
        fileChooser.getExtensionFilters().add(extFilter);

        String pathToFile = selectedFile.getPath();*/

        File projectsFolder = new File(PROJECTS_DIR);

        projectsFolder.mkdir();

        String canvasFileName = painterCanvas.getFileName();

        if (canvasFileName.equals("")) {
            TextInputDialog dialog = new TextInputDialog("File name");
            dialog.setHeaderText("File name chooser");
            dialog.setContentText("Please enter file name:");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                canvasFileName = result.get();
            } else
                dialog.close();
        }

        painterCanvas.saveCanvas(projectsFolder.getPath(), canvasFileName);

    }

    public static void LOAD_PROJECT(PainterCanvas painterCanvas) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(PROJECTS_DIR));
        File selectedFile = fileChooser.showOpenDialog(STAGE);
        //Filter for showing only .ser files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SER files (*.ser)", "*.ser");
        fileChooser.getExtensionFilters().add(extFilter);

        String pathToFile = selectedFile.getPath();

        painterCanvas.loadCanvas(pathToFile);
    }

    public static int GET_RECT_SIZE() {
        return RECT_SIZE;
    }

    public static Color GET_GRID_LINES_COLOR() {
        return GRID_LINES_COLOR;
    }

    public static Color GET_RECT_COLOR() {
        return DEFAULT_RECT_COLOR;
    }
}
