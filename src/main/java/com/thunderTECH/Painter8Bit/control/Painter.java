package com.thunderTECH.Painter8Bit.control;

import com.thunderTECH.Painter8Bit.ActionBuffer;
import com.thunderTECH.Painter8Bit.control.events.CanvasKeyboardPressed;
import com.thunderTECH.Painter8Bit.control.events.CanvasMouseDragged;
import com.thunderTECH.Painter8Bit.control.events.CanvasMousePressed;
import com.thunderTECH.Painter8Bit.control.events.CanvasMouseScroll;
import com.thunderTECH.Painter8Bit.model.Pixel;
import com.thunderTECH.Painter8Bit.model.Rectangle;
import com.thunderTECH.Painter8Bit.view.Viewer;
import com.thunderTECH.Painter8Bit.view.panels.PainterCanvas;
import com.thunderTECH.Painter8Bit.view.panels.instruments.ColorsPalette;
import com.thunderTECH.Painter8Bit.view.panels.instruments.InstrumentPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class Painter extends Application {
    private static final Color GRID_LINES_COLOR = Color.LIGHTGREY;
    private static final Color DEFAULT_RECT_COLOR = Color.TRANSPARENT;
    private static final String PROJECTS_DIR = "c:/PixelPainter projects";
    private boolean newProject = true;

    private static Stage STAGE;
    private static final int RECT_SIZE = 10; //SIZExSIZE rect
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 600;

    private final PainterCanvas painterCanvas;


    public Painter() {
        painterCanvas = new PainterCanvas(CANVAS_WIDTH,CANVAS_HEIGHT);

        //Setup control events
        painterCanvas.getCanvas().setOnMousePressed(new CanvasMousePressed(this));
        painterCanvas.getCanvas().setOnMouseDragged(new CanvasMouseDragged(this));
        painterCanvas.getCanvas().setOnKeyPressed(new CanvasKeyboardPressed(this));
        painterCanvas.getCanvas().setOnScroll(new CanvasMouseScroll(this));

        //Setup app style
        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
    }

    @Override
    public void start(Stage stage) {
        STAGE = stage;

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
        try {
            File file = new File(PROJECTS_DIR + "/" + painterCanvas.getFileName() + ".png");
            ImageIO.write(painterCanvas.getSnapshotImage(), "png", file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /** Save project(canvas) to folder **/
    public void saveProject() {
        // Create folder(if not exist)
        File projectsFolder = new File(PROJECTS_DIR);
        projectsFolder.mkdir();

        String canvasFileName = painterCanvas.getFileName();

        //Showing dialog for name input if new project
        if (newProject) {
            // Create dialog with properties
            TextInputDialog dialog = new TextInputDialog("File name");
            dialog.setHeaderText("File name chooser");
            dialog.setContentText("Please enter file name:");
            // Showing dialog
            Optional<String> result = dialog.showAndWait();
            // Check user input and name existence
            if (result.isPresent()) {
                canvasFileName = result.get();

                if(new File(PROJECTS_DIR + "/" + canvasFileName).exists()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING,"Name already exists!", ButtonType.OK);
                    alert.showAndWait();
                    return;
                }
            } else {
                dialog.close();
                try {
                    throw new FileNotFoundException();
                } catch (FileNotFoundException e) {
                    return;
                }
            }
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

        if(selectedFile == null) {
            return;
        } else {
            //Filter for showing only .ser files
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SER files (*.ser)", "*.ser");
            fileChooser.getExtensionFilters().add(extFilter);
            //Get file path
            String pathToFile = selectedFile.getPath();
            // Send path to canvas
            painterCanvas.loadCanvas(pathToFile);

            STAGE.setTitle("[8BitPainter]" + " - Project: " + painterCanvas.getFileName());

            newProject = false;
        }
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
        ActionBuffer.ADD_TO_BUFFER(rect);
        InstrumentPane.ADD_LAST_USED_COLOR(color);
        painterCanvas.paint(rect, color);
    }
    /**'Unpaint' last painted rectangle**/
    public void undoPaint() {
        Rectangle rect = ActionBuffer.GET_LAST_ACTIONED_RECTANGLE();
        if(rect != null) {
            painterCanvas.paint(rect, rect.getColor());
        }
    }

    public void repaint() {
        painterCanvas.paint();
    }

    public void clear() {
        newProject = true;
        ActionBuffer.CLEAR_BUFFER();
        painterCanvas.clear();
    }

    public void setCurrentColor(Color color) {
        painterCanvas.setCurrentRectColor(color);
        ColorsPalette.SHOW_CURRENT_COLOR_ON_PALETTE(color);
    }

    public Color getCurrentColor() {
        return painterCanvas.getCurrentRectColor();
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
        painterCanvas.setDefaultPosition();
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

    public Pixel getCanvasPixel(int x, int y) {
        return painterCanvas.getPixel(x,y);
    }

    public PainterCanvas getPainterCanvas() {
        return painterCanvas;
    }
}
