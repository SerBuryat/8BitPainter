package com.thunderTECH.Painter8Bit.panels;

import com.thunderTECH.Painter8Bit.panels.instruments.InstrumentPane;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class PaintPane extends Canvas {
    private Rectangle[][] rectanglesGrid;

    private final GraphicsContext graphic;

    private int paintPaneWidth = 800;
    private int paintPaneHeight = 600;

    private int paintPaneRectSize = 10;
    private int paintPaneRectWidth = paintPaneRectSize;
    private int paintPaneRectHeight = paintPaneRectSize;

    private int gridWidth;
    private int gridHeight;

    private Color currentRectColor;

    private boolean isGridLinesVisible;
    private final Color paintPaneGridStrokeColor;

    // vars for move&drag paintPane
    private double paneDragX;
    private double paneDragY;

    private double paneTranslateX;
    private double paneTranslateY;

    //vars for scaling paintPane
    private Scale paintPaneScale;


    //constructors
    public PaintPane() {
        graphic = this.getGraphicsContext2D();
        graphic.setImageSmoothing(false);

        this.setWidth(paintPaneWidth);
        this.setHeight(paintPaneHeight);

        this.gridWidth = paintPaneWidth / paintPaneRectWidth;
        this.gridHeight = paintPaneHeight / paintPaneRectHeight;

        rectanglesGrid = getRectanglesGridArray(gridWidth, gridHeight);

        currentRectColor = Color.BLACK;

        this.loadMouseActionsPaintPaneActionGroup(this);
        this.loadScrollingPaintPaneActionGroup(this);

        paintPaneGridStrokeColor = Color.GRAY;
        this.setGridLinesVisible(true);

        this.setCursor(Cursor.CROSSHAIR);
    }


    //public
    public void setPaintPaneDefaultPosition() {
        this.getTransforms().clear();
        this.setTranslateX(0.0);
        this.setTranslateY(0.0);
    }

    public void setGridLinesVisible(boolean paintPaneGridVisible) {
        isGridLinesVisible = paintPaneGridVisible;

        if(isGridLinesVisible) {
            this.drawPaintPaneGrid();
        } else {
            this.repaintPaintPane();
        }

    }

    public boolean isGridLinesVisible() {
        return isGridLinesVisible;
    }

    public void setCurrentRectColor(Color rectColor) {
        currentRectColor = rectColor;
    }

    public void saveImageFromPaintPane() {
        this.setPaintPaneDefaultPosition();

        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

        //Prompt user to select a file
        File file = fileChooser.showSaveDialog(null);

        if(file != null){
            try {
                //Pad the capture area
                WritableImage writableImage = new WritableImage((int)this.getWidth(), (int)this.getHeight());

                // parameters for remove background
                SnapshotParameters sp = new SnapshotParameters();
                //minX-6, minY-67 -> without this Viewport of snapshot will be incorrect(i don't know why)
                sp.setViewport(new Rectangle2D(6,67,this.getWidth(),this.getHeight()));
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
            PixelReader pixelReader = loadedImage.getPixelReader();
            drawPixelsFromImageToPaintPane(pixelReader);
        }
    }

    public void clearPaintPane() {
        if(isGridLinesVisible) {
            for(int x = 0; x < gridWidth; x++) {
                for(int y = 0; y < gridHeight; y++) {
                    Rectangle rect = rectanglesGrid[x][y];

                    paintRect(rect,Color.TRANSPARENT);

                    graphic.setStroke(paintPaneGridStrokeColor);
                    graphic.strokeRect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
                }
            }
        } else {
            for(int x = 0; x < gridWidth; x++) {
                for(int y = 0; y < gridHeight; y++) {
                    Rectangle rect = rectanglesGrid[x][y];
                    rect.setFill(Color.TRANSPARENT);
                    graphic.clearRect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
                }
            }
        }
    }

    public void setPaintPaneRectSize(int value) {
        if(value>=5 && value<=30)
            paintPaneRectSize = value;

        this.recalculatePaintPane();
    }

    public int getPaintPaneRectSize() {
        return paintPaneRectSize;
    }

    //private
    private void drawPaintPaneGrid() {
        for(int x = 0; x < gridWidth; x++) {
            for(int y = 0; y < gridHeight; y++) {
                Rectangle rect = rectanglesGrid[x][y];
                graphic.setStroke(paintPaneGridStrokeColor);
                graphic.strokeRect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
            }
        }
    }

    private void repaintPaintPane() {
        for(int x = 0; x < gridWidth; x++) { // first paint all TRANSPARENT color rectangles
            for(int y = 0; y < gridHeight; y++) {
                Rectangle rect = rectanglesGrid[x][y];
                if(rect.getFill().equals(Color.TRANSPARENT))
                    this.paintRect(rect,(Color)rect.getFill());
            }
        }
        for(int x = 0; x < gridWidth; x++) { // then paint all !TRANSPARENT color rectangles
            for(int y = 0; y < gridHeight; y++) {
                Rectangle rect = rectanglesGrid[x][y];
                if(!rect.getFill().equals(Color.TRANSPARENT))
                    this.paintRect(rect,(Color)rect.getFill());
            }
        }
    }

    private void drawPixelsFromImageToPaintPane(PixelReader pixelReader) {
        for(int x = 0; x < gridWidth; x++) {
            for(int y = 0; y < gridHeight; y++) {
                Rectangle rect = rectanglesGrid[x][y];
                Color color = pixelReader.getColor((int)rect.getX(),(int)rect.getY());
                this.paintRect(rect, color);
            }
        }
    }

    private void recalculatePaintPane() {
        //change paintPane size if can't divide size completely
        if(this.getWidth() % paintPaneRectSize != 0 || this.getHeight() % paintPaneRectSize != 0) {
            this.setWidth((paintPaneWidth - (paintPaneWidth % paintPaneRectSize)));
            this.setHeight((paintPaneHeight - (paintPaneHeight % paintPaneRectSize)));
        }

        this.paintPaneRectWidth = paintPaneRectSize;
        this.paintPaneRectHeight = paintPaneRectSize;
        this.gridWidth = paintPaneWidth / paintPaneRectWidth;
        this.gridHeight = paintPaneHeight / paintPaneRectHeight;

        rectanglesGrid = getRectanglesGridArray(gridWidth, gridHeight);
    }

    private Rectangle[][] getRectanglesGridArray(int gridWidth, int gridHeight) {
        Rectangle[][] rectangles = new Rectangle[gridWidth][gridWidth];
        for(int x = 0; x < gridWidth; x++) {
            for(int y = 0; y < gridHeight; y++) {
                Rectangle rect = createRect
                        (x*paintPaneRectWidth, y*paintPaneRectHeight, paintPaneRectWidth, paintPaneRectHeight);

                rectangles[x][y] = rect;

                this.paintRect(rect,(Color) rect.getFill());
            }
        }
        return rectangles;
    }

    private Rectangle createRect(int x, int y, int rectWidth, int rectHeight) {
        Color defaultRectColor = Color.TRANSPARENT;

        Rectangle rect = new Rectangle(x, y, rectWidth, rectHeight);

        rect.setFill(defaultRectColor);
        rect.setStroke(defaultRectColor);

        return rect;
    }

    private void paintRect(Rectangle rect, Color color) {
        rect.setFill(color);
        if(color.equals(Color.TRANSPARENT))
            this.paintTransparentRect(rect);
        else
            this.paintColoredRect(rect, color);
    }

    private void paintTransparentRect(Rectangle rect) {
        if(isGridLinesVisible) {
            graphic.clearRect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
            graphic.setStroke(paintPaneGridStrokeColor);
            graphic.strokeRect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
        } else {
            graphic.clearRect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
        }
    }

    private void paintColoredRect(Rectangle rect, Color color) {
        if(isGridLinesVisible) {
            graphic.setFill(color);
            graphic.fillRect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
            graphic.setStroke(paintPaneGridStrokeColor);
            graphic.strokeRect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
        } else {
            graphic.setFill(color);
            graphic.fillRect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
        }
    }

    private void loadMouseActionsPaintPaneActionGroup(PaintPane paintPane) {
        paintPane.setOnMousePressed(event -> {
            int rectX = (int) (event.getX()/ paintPaneRectWidth);
            int rectY = (int) (event.getY() / paintPaneRectHeight);

            Rectangle rect = rectanglesGrid[rectX][rectY];

            if(event.getButton() == MouseButton.PRIMARY) {
                paintRect(rect, currentRectColor);
                InstrumentPane.ADD_LAST_USED_COLOR(currentRectColor);
            }
            if(event.getButton() == MouseButton.SECONDARY)
                this.setCurrentRectColor((Color) rect.getFill());

            if(event.getButton() == MouseButton.MIDDLE) {
                paneDragX = event.getSceneX();
                paneDragY = event.getSceneY();

                paneTranslateX = getTranslateX();
                paneTranslateY = getTranslateY();

                setCursor(Cursor.CLOSED_HAND);
            }
        });

        paintPane.setOnMouseReleased(event -> {
            if(event.getButton() == MouseButton.MIDDLE)
                setCursor(Cursor.CROSSHAIR);
        });

        paintPane.setOnMouseDragged(event -> {
            int rectX = (int) (event.getX()/ paintPaneRectWidth);
            int rectY = (int) (event.getY() / paintPaneRectHeight);
            Rectangle rect = rectanglesGrid[rectX][rectY];

            if(event.getButton() == MouseButton.PRIMARY)
                this.paintRect(rect,currentRectColor);

            if(event.getButton() == MouseButton.MIDDLE) {
                double offsetX = event.getSceneX() - paneDragX;
                double offsetY = event.getSceneY() - paneDragY;

                setTranslateX(paneTranslateX + offsetX);
                setTranslateY(paneTranslateY + offsetY);
            }
        });
    }

    private void loadScrollingPaintPaneActionGroup(PaintPane paintPane) {
        final double scaleDelta = 1.1;

        paintPane.setOnScroll(event -> {
            event.consume();

            if (event.getDeltaY() == 0)
                return;

            paintPaneScale = new Scale();

            double scaleFactor = (event.getDeltaY() > 0) ? scaleDelta : 1/scaleDelta;

            paintPaneScale.setPivotX(event.getX());
            paintPaneScale.setPivotY(event.getY());
            paintPaneScale.setX(getScaleX() * scaleFactor);
            paintPaneScale.setY(getScaleY() * scaleFactor);

            getTransforms().addAll(paintPaneScale);
        });
    }

}