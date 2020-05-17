package com.thunderTECH.Painter8Bit.panels;

import com.thunderTECH.Painter8Bit.panels.instruments.ColorsPalette;
import com.thunderTECH.Painter8Bit.panels.instruments.InstrumentPane;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.SceneAntialiasing;
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

    private final int defaultPaintPaneWidth = 800;
    private final int defaultPaintPaneHeight = 600;
    private int paintPaneWidth = defaultPaintPaneWidth;
    private int paintPaneHeight = defaultPaintPaneHeight;

    private final int paintPaneRectSize = 10;
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

        this.setWidth(paintPaneWidth);
        this.setHeight(paintPaneHeight);

        this.gridWidth = (int) (paintPaneWidth / paintPaneRectWidth);
        this.gridHeight = (int) (paintPaneHeight / paintPaneRectHeight);

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
        ColorsPalette.showCurrentColorOnPalette(currentRectColor);
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

            paintPaneWidth = (int) loadedImage.getWidth();
            paintPaneHeight = (int) loadedImage.getHeight();

            this.setWidth(paintPaneWidth);
            this.setHeight(paintPaneHeight);

            recalculatePaintPaneSizes();

            drawPixelsFromImageToPaintPane(loadedImage.getPixelReader());
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


    public void setPaintPaneSize(int width, int height) {
        if(width > 800)
            width = 800;
        if(width < 100)
            width = 100;

        if(height > 600)
            height = 600;
        if(height < 100)
            height = 100;

        paintPaneWidth = width;
        paintPaneHeight = height;
        this.setWidth(width);
        this.setHeight(height);

        this.recalculatePaintPaneSizes();
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
        // corr is for avoiding native FX anti-aliasing
        int corr = paintPaneRectSize / 2;
        for(int x = 0; x < gridWidth; x++) {
            for(int y = 0; y < gridHeight; y++) {
                Rectangle rect = rectanglesGrid[x][y];
                Color color = pixelReader.getColor((int)rect.getX()+corr,(int)rect.getY()+corr);
                this.paintRect(rect, color);
            }
        }
    }

    private void recalculatePaintPaneSizes() {
        //change paintPane size if can't divide size completely
        if(this.getWidth() % paintPaneRectSize != 0 || this.getHeight() % paintPaneRectSize != 0) {
            this.setWidth((paintPaneWidth - (paintPaneWidth % paintPaneRectSize)));
            this.setHeight((paintPaneHeight - (paintPaneHeight % paintPaneRectSize)));
        }

        this.paintPaneRectWidth = paintPaneRectSize;
        this.paintPaneRectHeight = paintPaneRectSize;
        this.gridWidth = (int) (paintPaneWidth / paintPaneRectWidth);
        this.gridHeight = (int) (paintPaneHeight / paintPaneRectHeight);

        Rectangle[][] previousRectGrid = rectanglesGrid;

        rectanglesGrid = getRectanglesGridArray(gridWidth, gridHeight);

        copyPreviousGridToNewRectGrid(previousRectGrid, rectanglesGrid);

        repaintPaintPane();
    }

    private void copyPreviousGridToNewRectGrid(Rectangle[][] previousRectGrid, Rectangle[][] rectanglesGrid) {
        int previousGridWidth = previousRectGrid.length;
        int previousGridHeight = previousRectGrid[0].length;

        for(int x = 0; x < gridWidth; x++) {
            for(int y = 0; y < gridHeight; y++) {
                if(x < previousGridWidth && y < previousGridHeight) {
                    if(!previousRectGrid[x][y].getFill().equals(Color.TRANSPARENT)) {
                        rectanglesGrid[x][y].setFill(previousRectGrid[x][y].getFill());
                    }
                }
            }
        }
    }


    private Rectangle[][] getRectanglesGridArray(int gridWidth, int gridHeight) {
        Rectangle[][] rectangles = new Rectangle[gridWidth][gridHeight];

        for(int x = 0; x < gridWidth; x++) {
            for(int y = 0; y < gridHeight; y++) {
                Rectangle rect = createRect
                        (x*paintPaneRectWidth, y*paintPaneRectHeight,
                                paintPaneRectWidth, paintPaneRectHeight);

                rectangles[x][y] = rect;

                this.paintRect(rect,(Color) rect.getFill());
            }
        }
        return rectangles;
    }

    private Rectangle createRect(double x, double y, double rectWidth, double rectHeight) {
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
        } else
            graphic.clearRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
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
            if(event.getButton() == MouseButton.SECONDARY) {
                setCurrentRectColor((Color) rect.getFill());
            }

            if(event.getButton() == MouseButton.MIDDLE) {
                paneDragX = event.getSceneX();
                paneDragY = event.getSceneY();

                paneTranslateX = getTranslateX();
                paneTranslateY = getTranslateY();

                setCursor(Cursor.CLOSED_HAND);
            }
        });

        paintPane.setOnMouseReleased(event -> {
            //fixing clearRect() method bug (when it clears borders nearest rectangles)
            if(event.getButton() == MouseButton.PRIMARY)
                repaintPaintPane();

            if(event.getButton() == MouseButton.MIDDLE)
                setCursor(Cursor.CROSSHAIR);
        });

        paintPane.setOnMouseDragged(event -> {
            int rectX = (int) (event.getX()/ paintPaneRectWidth);
            int rectY = (int) (event.getY() / paintPaneRectHeight);

            Rectangle rect = rectanglesGrid[rectX][rectY];

            if(event.getButton() == MouseButton.PRIMARY)
                paintRect(rect, currentRectColor);

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