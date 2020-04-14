package com.thunderTECH.Painter8Bit.panels.painter;

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
    private final Rectangle[][] rectanglesGrid;

    private final GraphicsContext graphic;

    private final int paintPaneWidth = 1280;
    private final int paintPaneHeight = 720;

    private final int paintPaneRectWidth = 10;
    private final int paintPaneRectHeight = 10;

    private final int gridWidth;
    private final int gridHeight;

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

    public PaintPane() {
        graphic = this.getGraphicsContext2D();

        this.setWidth(paintPaneWidth);
        this.setHeight(paintPaneHeight);

        this.gridWidth = paintPaneWidth / paintPaneRectWidth;
        this.gridHeight = paintPaneHeight / paintPaneRectHeight;

        rectanglesGrid = getRectanglesGridArray(gridWidth, gridHeight);

        currentRectColor = Color.BLACK;

        this.loadMouseActionsPaintPaneActionGroup(this);
        this.loadScrollingPaintPaneActionGroup(this);

        isGridLinesVisible = false;
        paintPaneGridStrokeColor = Color.GRAY;

        this.setCursor(Cursor.CROSSHAIR);
    }

    public void setPaintPaneDefaultSize() {
        this.getTransforms().clear();
        this.setTranslateX(0.0);
        this.setTranslateY(0.0);
    }

    public void setGridLinesVisible(boolean paintPaneGridVisible) {
        isGridLinesVisible = paintPaneGridVisible;

        if(isGridLinesVisible) {
            for(int x = 0; x < gridWidth; x++) {
                for(int y = 0; y < gridHeight; y++) {
                    Rectangle rect = rectanglesGrid[x][y];
                    graphic.setStroke(paintPaneGridStrokeColor);
                    graphic.strokeRect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
                }
            }
        } else {
            for(int x = 0; x < gridWidth; x++) {
                for(int y = 0; y < gridHeight; y++) {
                    Rectangle rect = rectanglesGrid[x][y];
                    this.paintRect(rect,(Color)rect.getFill());
                }
            }
        }
    }

    public boolean isGridLinesVisible() {
        return isGridLinesVisible;
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
            //graphic.drawImage(loadedImage,0,0,this.getWidth(),this.getHeight());

            PixelReader pixelReader = loadedImage.getPixelReader();

            for(int x = 0; x < gridWidth; x++) {
                for(int y = 0; y < gridHeight; y++) {
                    Rectangle rect = rectanglesGrid[x][y];
                    Color color = pixelReader.getColor((int)rect.getX(),(int)rect.getY());
                    this.paintRect(rect, color);
                }
            }
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
        if(isGridLinesVisible) {
            if(color == Color.TRANSPARENT) {
                rect.setFill(color);

                graphic.clearRect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
                graphic.setStroke(paintPaneGridStrokeColor);
                graphic.strokeRect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
            } else {
                rect.setFill(color);

                graphic.setFill(color);
                graphic.fillRect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
                graphic.setStroke(paintPaneGridStrokeColor);
                graphic.strokeRect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
            }
        } else {
            if(color.equals(Color.TRANSPARENT)) {
                rect.setFill(color);
                //-1 -> bcs in clearRect() bounds bigger then in other drawing methods(fillRect(),strokeRect() etc.)
                //it's a java man)))
                graphic.clearRect(rect.getX()+1,rect.getY()+1,rect.getWidth()-1,rect.getHeight()-1);
            } else {
                rect.setFill(color);

                graphic.setFill(color);
                graphic.fillRect(rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
            }

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
