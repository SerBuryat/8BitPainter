package com.thunderTECH.Painter8Bit.panels.painter;

import com.thunderTECH.Painter8Bit.panels.instruments.InstrumentPane;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class PaintPane extends GridPane {
    private final Rectangle[][] rectanglesGrid;

    private final int paintPaneWidth = 1280;
    private final int paintPaneHeight = 720;

    private final int paintPaneRectWidth = 10;
    private final int paintPaneRectHeight = 10;

    final int gridWidth;
    final int gridHeight;

    private Color currentRectColor;

    // vars for move&drag paintPane
    private double paneDragX;
    private double paneDragY;

    private double paneTranslateX;
    private double paneTranslateY;


    public PaintPane() {
        this.setWidth(paintPaneWidth);
        this.setHeight(paintPaneHeight);

        this.gridWidth = paintPaneWidth / paintPaneRectWidth;
        this.gridHeight = paintPaneHeight / paintPaneRectHeight;

        rectanglesGrid = getRectanglesGridArray(gridWidth, gridHeight);

        currentRectColor = Color.BLACK;

        this.setGridLinesVisible(true);

        this.loadScrollingPaintPaneActionGroup(this);
        this.loadDraggingPaintPaneActionGroup(this);

    }

    public void setCurrentRectColor(Color rectColor) {
        currentRectColor = rectColor;
    }

    public void saveImageFromPaintPane() {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

        //Prompt user to select a file
        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            try {
                //Pad the capture area
                WritableImage writableImage = new WritableImage((int)getWidth(),
                        (int)getHeight());

                // parameters for remove background
                SnapshotParameters sp = new SnapshotParameters();
                sp.setFill(Color.TRANSPARENT);

                RenderedImage renderedImage = SwingFXUtils.fromFXImage(snapshot(sp, writableImage), null);

                //Write the snapshot to the chosen file
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) { ex.printStackTrace(); }
        }
    }

    public void loadImageToPaintPane() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);

        if(file != null){
            Image loadedImage = new Image(file.toURI().toString());

            BackgroundSize backgroundSize = new BackgroundSize(this.getWidth(), this.getHeight(), true, true, true, true);
            BackgroundImage backgroundImage = new BackgroundImage(loadedImage, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);

            this.setBackground(new Background(backgroundImage));
        }
    }

    public void clearPaintPane() {
        for(int x = 0; x < gridWidth; x++) {
            for(int y = 0; y < gridHeight; y++) {
                repaintRect(rectanglesGrid[x][y], Color.TRANSPARENT);
            }
        }
        this.setBackground(null);
    }

    private Rectangle[][] getRectanglesGridArray(int gridWidth, int gridHeight) {
        Rectangle[][] rectangles = new Rectangle[gridWidth][gridWidth];
        for(int x = 0; x < gridWidth; x++) {
            for(int y = 0; y < gridHeight; y++) {
                Rectangle rect = createRect(x, y, paintPaneRectWidth, paintPaneRectHeight);

                addMouseActionForRect(rect);

                rectangles[x][y] = rect;
                this.add(rect, x, y);
            }
        }
        return rectangles;
    }

    private Rectangle createRect(int x, int y, int rectWidth, int rectHeight) {
        Color defaultRectColor = Color.TRANSPARENT;

        Rectangle rect =
                new Rectangle(x * rectWidth, y * rectHeight,
                        rectWidth, rectHeight);
        rect.setFill(defaultRectColor);
        rect.setStroke(defaultRectColor);

        return rect;
    }

    private void addMouseActionForRect(Rectangle rect) {
        rect.setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                repaintRect(rect, currentRectColor);
                InstrumentPane.ADD_LAST_USED_COLOR(currentRectColor);
            }
            if(event.getButton() == MouseButton.SECONDARY)
                this.setCurrentRectColor((Color) rect.getFill());
        });
    }

    private void repaintRect(Rectangle rect, Color color) {
        rect.setFill(color);
        rect.setStroke(color);
    }

    private void loadScrollingPaintPaneActionGroup(PaintPane paintPane) {
        final double SCALE_DELTA = 1.1;

        paintPane.setOnScroll(event -> {
            event.consume();

            if (event.getDeltaY() == 0) {
                return;
            }

            Scale scale = new Scale();

            double scaleFactor =
                    (event.getDeltaY() > 0)
                            ? SCALE_DELTA
                            : 1/SCALE_DELTA;

            scale.setPivotX(event.getX());
            scale.setPivotY(event.getY());
            scale.setX(getScaleX() * scaleFactor);
            scale.setY(getScaleY() * scaleFactor);

            getTransforms().addAll(scale);
        });
    }

    private void loadDraggingPaintPaneActionGroup(PaintPane paintPane) {
        paintPane.setOnMousePressed(event -> {
            if(event.getButton() == MouseButton.MIDDLE) {
                paneDragX = event.getSceneX();
                paneDragY = event.getSceneY();

                paneTranslateX = paintPane.getTranslateX();
                paneTranslateY = paintPane.getTranslateY();

                paintPane.setCursor(Cursor.CLOSED_HAND);
            }
        });

        paintPane.setOnMouseReleased(event -> {
            if(event.getButton() == MouseButton.MIDDLE)
                paintPane.setCursor(Cursor.DEFAULT);
        });

        int correction = 1; // correction accuracy
        final int rectWidth = paintPaneWidth / gridWidth + correction;
        final int rectHeight = paintPaneHeight / gridHeight + correction;

        this.setOnMouseDragged(event -> {

            int rectX = (int) (event.getX()/ rectWidth);
            int rectY = (int) (event.getY() / rectHeight);

            if(event.getButton() == MouseButton.PRIMARY ) {
                rectanglesGrid[rectX][rectY].setFill(currentRectColor);
                rectanglesGrid[rectX][rectY].setStroke(currentRectColor);
            }

            if(event.getButton() == MouseButton.MIDDLE) {
                double offsetX = event.getSceneX() - paneDragX;
                double offsetY = event.getSceneY() - paneDragY;

                paintPane.setTranslateX(paneTranslateX + offsetX);
                paintPane.setTranslateY(paneTranslateY + offsetY);
            }
        });
    }

}
