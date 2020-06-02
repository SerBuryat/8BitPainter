package com.thunderTECH.Painter8Bit.view;

import com.thunderTECH.Painter8Bit.Painter;
import com.thunderTECH.Painter8Bit.view.panels.PainterCanvas;
import com.thunderTECH.Painter8Bit.view.panels.SettingsPanel;
import com.thunderTECH.Painter8Bit.view.panels.instruments.InstrumentPane;
import javafx.scene.layout.BorderPane;

public class Viewer {

    public static BorderPane GET_VIEW_PANE() {
        BorderPane mainPane = new BorderPane();

        PainterCanvas painterCanvas = new PainterCanvas(Painter.GET_CANVAS_WIDTH(),Painter.GET_CANVAS_HEIGHT());
        InstrumentPane instrumentPane = new InstrumentPane(painterCanvas);
        SettingsPanel settingsPanel = new SettingsPanel(painterCanvas);

        mainPane.setCenter(painterCanvas.getCanvas());
        mainPane.setRight(instrumentPane);
        mainPane.setTop(settingsPanel);

        return mainPane;
    }
}
