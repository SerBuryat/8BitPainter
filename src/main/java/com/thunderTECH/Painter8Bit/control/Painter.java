package com.thunderTECH.Painter8Bit.control;

import com.thunderTECH.Painter8Bit.ActionBuffer;
import com.thunderTECH.Painter8Bit.model.Rectangle;
import com.thunderTECH.Painter8Bit.view.Viewer;
import com.thunderTECH.Painter8Bit.view.panels.PainterCanvas;
import com.thunderTECH.Painter8Bit.view.panels.instruments.ColorsPalette;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class Painter extends Application {
    private static final Color GRID_LINES_COLOR = Color.LIGHTGREY;
    private static final Color DEFAULT_RECT_COLOR = Color.TRANSPARENT;
    private static final String PROJECTS_DIR = "c:/PixelPainter projects";

    private static Stage STAGE;
    private static int RECT_SIZE = 10; //SIZExSIZE rect
    private static int CANVAS_WIDTH = 800;
    private static int CANVAS_HEIGHT = 600;

    private PainterCanvas painterCanvas;


    public Painter() {
        //Set app style
        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
    }

    @Override
    public void start(Stage stage) {
        STAGE = stage;

        painterCanvas = new PainterCanvas(CANVAS_WIDTH,CANVAS_HEIGHT);

        Scene scene = new Scene(new Viewer(this).getPane(),1280,720);
        stage.setScene(scene);
        stage.setTitle("[8BitPainter]");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    /** Save canvas picture like .png file **/
    public void savePainterCanvasImageAsPng() {
        this.saveProject();

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
    /** Save project(canvas) to folder **/
    public void saveProject() {
        // Create folder(if not exist)
        File projectsFolder = new File(PROJECTS_DIR);
        projectsFolder.mkdir();
        // Get current canvas name(for saving file with this name)
        String canvasFileName = painterCanvas.getFileName();

        //If current canvas doesn't have name (equals(""))
        //Showing dialog for name input
        if (canvasFileName.equals("")) {
            // Create dialog with properties
            TextInputDialog dialog = new TextInputDialog("File name");
            dialog.setHeaderText("File name chooser");
            dialog.setContentText("Please enter file name:");
            // Showing dialog
            Optional<String> result = dialog.showAndWait();
            // Check user input
            if (result.isPresent()){
                canvasFileName = result.get();
            } else
                dialog.close();
        }
        //Saving canvas with folder path and file name
        painterCanvas.saveCanvas(projectsFolder.getPath(), canvasFileName);
    }
    /** Load project(canvas) from selected file **/
    public void loadProject() {
        // Open chooser
        FileChooser fileChooser = new FileChooser();
        // Set default directory
        fileChooser.setInitialDirectory(new File(PROJECTS_DIR));
        // Showing dialog and get chosen file
        File selectedFile = fileChooser.showOpenDialog(STAGE);
        //Filter for showing only .ser files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SER files (*.ser)", "*.ser");
        fileChooser.getExtensionFilters().add(extFilter);
        //Get file path
        String pathToFile = selectedFile.getPath();
        // Send path to canvas
        painterCanvas.loadCanvas(pathToFile);
    }
    /** Get canvas rectangle size **/
    public static int GET_RECT_SIZE() {
        return RECT_SIZE;
    }
    /** Get canvas grid color **/
    public static Color GET_GRID_LINES_COLOR() {
        return GRID_LINES_COLOR;
    }
    /** Get default painting color **/
    public static Color GET_DEFAULT_RECT_COLOR() {
        return DEFAULT_RECT_COLOR;
    }

    public void paint(Rectangle rect, Color color) {
        painterCanvas.paint(rect, color);
    }

    public void repaint() {
        painterCanvas.paint();
    }

    public void clear() {
        ActionBuffer.CLEAR_BUFFER();
        painterCanvas.clear();
    }

    public void setCurrentColor(Color color) {
        painterCanvas.setCurrentRectColor(color);
        ColorsPalette.SHOW_CURRENT_COLOR_ON_PALETTE(color);
    }

    public void putCanvasOnPane(BorderPane borderPane) {
        borderPane.setCenter(painterCanvas.getCanvas());
    }

    public void setGridLineVisible(boolean isGridLineVisible) {
        painterCanvas.setGridLineVisible(isGridLineVisible);
    }

    public boolean isGridLineVisible() {
        return painterCanvas.isGridLineVisible();
    }

    public void setPainterCanvasDefaultPosition() {
        painterCanvas.setPainterCanvasDefaultPosition();
    }

    public void setCanvasSize(int width, int height) {
        painterCanvas.setCanvasSize(width, height);
    }

    public int getCanvasWidth() {
        return (int)painterCanvas.getCanvas().getWidth();
    }

    public int getCanvasHeight() {
        return (int)painterCanvas.getCanvas().getHeight();
    }
}
