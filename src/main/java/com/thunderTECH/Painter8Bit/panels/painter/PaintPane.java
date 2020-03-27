package com.thunderTECH.Painter8Bit.panels.painter;

import com.thunderTECH.Painter8Bit.Painter;
import com.thunderTECH.Painter8Bit.panels.instruments.InstrumentPane;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class PaintPane extends GridPane {
    private final Rectangle[][] rectanglesGrid;
    private final Color gridStrokeColor;

    private Color currentRectColor;
    private Color clearRectColor;

    final int gridWidth;
    final int gridHeight;

    final int rectWidth;
    final int rectHeight;

    public PaintPane(int paneWidth, int paneHeight) {
        this.setWidth(paneWidth);
        this.setHeight(paneHeight);

        this.gridWidth = paneWidth / 20;// for test
        this.gridHeight = paneHeight / 20; // for test

        rectWidth = Painter.WIDTH / gridWidth;
        rectHeight = Painter.HEIGHT / gridHeight;

        rectanglesGrid = getRectanglesGridArray(gridWidth, gridHeight);

        currentRectColor = Color.BLACK;
        clearRectColor = Color.TRANSPARENT;

        gridStrokeColor = Color.BLACK;

        /*this.setOnMouseDragged(event -> {
            int rectX = (int) (event.getX() / rectWidth);
            int rectY = (int) (event.getY() / rectHeight);

            if(event.getButton() == MouseButton.PRIMARY )
                rectanglesGrid[rectX][rectY].setFill(currentRectColor);
            if(event.getButton() == MouseButton.SECONDARY )
                rectanglesGrid[rectX][rectY].setFill(clearRectColor);
        });*/
    }

    public void setCurrentRectColor(Color rectColor) {
        currentRectColor = rectColor;
    }

    public Color getCurrentRectColor() {
        return currentRectColor;
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
                repaintRect(rectanglesGrid[x][y], clearRectColor);
            }
        }
        this.setBackground(null);
    }

    private Rectangle[][] getRectanglesGridArray(int gridWidth, int gridHeight) {
        Rectangle[][] rectangles = new Rectangle[gridWidth][gridWidth];
        for(int x = 0; x < gridWidth; x++) {
            for(int y = 0; y < gridHeight; y++) {
                Rectangle rect = createRect(x, y, rectWidth, rectHeight);

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

    private void repaintRect(Rectangle rect, Color currentRectColor) {
        rect.setFill(currentRectColor);
        rect.setStroke(this.isGridLinesVisible() ? gridStrokeColor : currentRectColor);
    }

}
