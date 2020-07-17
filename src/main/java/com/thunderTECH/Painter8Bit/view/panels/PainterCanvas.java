package com.thunderTECH.Painter8Bit.view.panels;

import com.thunderTECH.Painter8Bit.Painter;
import com.thunderTECH.Painter8Bit.control.*;
import com.thunderTECH.Painter8Bit.model.Pixel;
import com.thunderTECH.Painter8Bit.model.Rectangle;
import com.thunderTECH.Painter8Bit.view.panels.instruments.ColorsPalette;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.awt.image.RenderedImage;
import java.io.*;

public class PainterCanvas implements Serializable {
    private final Canvas canvas;
    private PixelWriter pixelGraphicWriter;
    private Rectangle[][] rectangles;
    private Pixel[][] pixels;
    private boolean isGridLineVisible;
    private Color gridLinesColor;
    private Color currentRectangleColor;

    // canvas mouse dragging params
    private double canvasDragX;
    private double canvasDragY;
    private double canvasTranslateX;
    private double canvasTranslateY;


    public PainterCanvas(int width, int height) {
        canvas = createCanvas(width,height);
        rectangles = createRectangles();
        pixels = createPixels();

        pixelGraphicWriter = canvas.getGraphicsContext2D().getPixelWriter();

        isGridLineVisible = true;
        gridLinesColor = Painter.GET_GRID_LINES_COLOR();
        currentRectangleColor = Color.BLACK;

        canvas.setOnMousePressed(new CanvasMousePressed(this));
        canvas.setOnMouseDragged(new CanvasMouseDragged(this));
        canvas.setOnScroll(new CanvasMouseScroll(this));
        canvas.setOnMouseMoved(new CanvasMouseMoved(this));

        canvas.setOnKeyPressed(new CanvasKeyboardPressed(this));

        paint();

        canvas.requestFocus();
    }

    /** Paint single rectangle in given color **/
    public void paint(Rectangle rectangle, Color color) {
        rectangle.paint(pixelGraphicWriter, color);

        if (isGridLineVisible)
            rectangle.paintBorders(pixelGraphicWriter, gridLinesColor);
    }
    /** Paint or repaint all canvas **/
    public void paint() {
        for(Rectangle[] rectangles : this.rectangles) {
            for(Rectangle rectangle : rectangles)
                paint(rectangle, rectangle.getColor());
        }
    }
    /** Clear all canvas **/
    public void clear() {
        for(Rectangle[] rectangles : this.rectangles) {
            for (Rectangle rectangle : rectangles) {
                paint(rectangle, Color.TRANSPARENT);
            }
        }
    }


    public void paintGridLines() {
        setGridLineVisible(true);
        paint();
    }

    public void clearGridLines() {
        setGridLineVisible(false);
        paint();
    }

    /** Sets default canvas position(remove scale and scroll params) **/
    public void setCanvasSize(double width, double height) {
        if(width > 800)
            width = 800;
        if(width < 100)
            width = 100;

        if(height > 600)
            height = 600;
        if(height < 100)
            height = 100;

        canvas.setWidth(width);
        canvas.setHeight(height);
        canvasSizeCorrection(canvas);

        rectangles = createRectangles();
        pixels = createPixels();

        paint();
    }

    public void setPainterCanvasDefaultPosition() {
        canvas.getTransforms().clear();
        canvas.setTranslateX(0.0);
        canvas.setTranslateY(0.0);
    }


    public void saveCanvas(String pathToFile) {
        try {
            FileOutputStream fileOut = new FileOutputStream(pathToFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            this.writeObject(out);
            out.close();
            fileOut.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void writeObject(ObjectOutputStream obj) throws IOException {
        obj.writeDouble(canvas.getWidth());
        obj.writeDouble(canvas.getHeight());

        obj.writeInt(rectangles.length);
        obj.writeInt(rectangles[0].length);

        for (int i = 0; i < rectangles.length; i++) {
            for (int j = 0; j < rectangles[0].length; j++) {
                try {
                    obj.writeObject(rectangles[i][j]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    public void loadCanvas(String pathToFile) {
        try {
            FileInputStream fileIn = new FileInputStream(pathToFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            this.readObject(in);
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
    }

    private void readObject(ObjectInputStream obj) throws IOException, ClassNotFoundException {
        setCanvasSize(obj.readDouble(), obj.readDouble());

        Rectangle [][] rectangles = new Rectangle[obj.readInt()][obj.readInt()];
        for (int i = 0; i < rectangles.length; i++) {
            for(int j = 0; j < rectangles[0].length; j++) {
                rectangles[i][j] = ((Rectangle) obj.readObject());
            }
        }

        this.rectangles = rectangles;
        pixels = createPixels();

        pixelGraphicWriter = canvas.getGraphicsContext2D().getPixelWriter();

        isGridLineVisible = true;
        gridLinesColor = Painter.GET_GRID_LINES_COLOR();
        currentRectangleColor = Color.BLACK;

        canvas.setOnMousePressed(new CanvasMousePressed(this));
        canvas.setOnMouseDragged(new CanvasMouseDragged(this));
        canvas.setOnScroll(new CanvasMouseScroll(this));

        paint();

    }


    public RenderedImage getSnapshotImage() {
        setPainterCanvasDefaultPosition();
        clearGridLines();

        //Pad the capture area
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());

        // parameters for remove background
        SnapshotParameters sp = new SnapshotParameters();
        sp.setFill(Color.TRANSPARENT);

        WritableImage snapshot = canvas.snapshot(sp, writableImage);
        removeAntialiasingFromSnapshot(snapshot);

        paintGridLines();

        return SwingFXUtils.fromFXImage(snapshot, null);
    }
    /** remove blur effect from rectangles borders **/
    private void removeAntialiasingFromSnapshot(WritableImage snapshot) {
        PixelWriter writer = snapshot.getPixelWriter();

        for(int x = 0; x < snapshot.getWidth(); x++) {
            for(int y = 0; y < snapshot.getHeight(); y++) {
                writer.setColor(x,y,pixels[x][y].getColor());
            }
        }
    }


    public void setCurrentRectangleColor(Color currentRectangleColor) {
        this.currentRectangleColor = currentRectangleColor;
        ColorsPalette.showCurrentColorOnPalette(currentRectangleColor);
    }

    public Color getCurrentRectangleColor() {
        return currentRectangleColor;
    }


    public void setGridLineVisible(boolean isGridLineVisible) {
        this.isGridLineVisible = isGridLineVisible;
    }

    public boolean isGridLineVisible() {
        return isGridLineVisible;
    }


    public Canvas getCanvas() {
        return canvas;
    }

    public Rectangle getRectangle(int x, int y) {
        int posX = x / Painter.GET_RECT_SIZE();
        int posY = y / Painter.GET_RECT_SIZE();

        if(isCorrectCanvasBounds(posX,posY))
            return rectangles[posX][posY];

        return null;
    }

    public Pixel getPixel(int x, int y) {
        if(isCorrectCanvasBounds(x,y))
            return pixels[x][y];
        return null;
    }

    private boolean isCorrectCanvasBounds(int x, int y) {
        return (x >= 0 && x < canvas.getWidth()) && (y >= 0 && y < canvas.getHeight());
    }

    public void setCanvasDragX(double canvasDragX) {
        this.canvasDragX = canvasDragX;
    }
    public void setCanvasDragY(double canvasDragY) {
        this.canvasDragY = canvasDragY;
    }
    public double getCanvasDragX() {
        return canvasDragX;
    }
    public double getCanvasDragY() {
        return canvasDragY;
    }

    public void setCanvasTranslateX(double canvasTranslateX) {
        this.canvasTranslateX = canvasTranslateX;
    }
    public void setCanvasTranslateY(double canvasTranslateY) {
        this.canvasTranslateY = canvasTranslateY;
    }
    public double getCanvasTranslateX() {
        return canvasTranslateX;
    }
    public double getCanvasTranslateY() {
        return canvasTranslateY;
    }

    private Canvas createCanvas(double width, double height) {
        Canvas canvas = new Canvas(width,height);
        canvasSizeCorrection(canvas);
        return canvas;
    }
    /** Change canvas size if it doesn't divide completely (with reminder) **/
    private void canvasSizeCorrection(Canvas canvas) {
        if(canvas.getWidth() % Painter.GET_RECT_SIZE() != 0 || canvas.getHeight() % Painter.GET_RECT_SIZE() != 0) {
            canvas.setWidth(canvas.getWidth() - (canvas.getWidth() % Painter.GET_RECT_SIZE()));
            canvas.setHeight(canvas.getHeight() - (canvas.getHeight() % Painter.GET_RECT_SIZE()));
        }
    }

    private Rectangle[][] createRectangles() {
        int width = (int) (canvas.getWidth() / Painter.GET_RECT_SIZE());
        int height = (int) (canvas.getHeight() / Painter.GET_RECT_SIZE());
        Rectangle[][] rectangles = new Rectangle[width][height];

        int rectWidth = Painter.GET_RECT_SIZE();
        int rectHeight = Painter.GET_RECT_SIZE();
        Color rectColor = Painter.GET_RECT_COLOR();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                rectangles[x][y] = new Rectangle(x,y,rectWidth,rectHeight,rectColor);
            }
        }

        return rectangles;
    }

    private Pixel[][] createPixels() {
        Pixel[][] pixels = new Pixel[(int) canvas.getWidth()][(int) canvas.getHeight()];

        for(int x = 0; x < canvas.getWidth(); x++) {
            for(int y = 0; y < canvas.getHeight(); y++) {
                pixels[x][y] = getRectangle(x,y).getPixel(x,y);
            }
        }

        return pixels;
    }
}
