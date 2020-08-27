package com.thunderTECH.Painter8Bit.view;

import com.thunderTECH.Painter8Bit.control.Painter;
import com.thunderTECH.Painter8Bit.view.panels.PainterCanvas;
import com.thunderTECH.Painter8Bit.view.panels.SettingsPanel;
import com.thunderTECH.Painter8Bit.view.panels.instruments.InstrumentPane;
import javafx.scene.layout.BorderPane;

public class Viewer {
    private final Painter painter;
    private final BorderPane viewPane;


    public Viewer (Painter painter) {
        this.painter = painter;

        this.viewPane = new BorderPane();
        InstrumentPane instrumentPane = new InstrumentPane(this.painter);
        SettingsPanel settingsPanel = new SettingsPanel(this.painter);

        painter.putCanvasOnPane(viewPane);
        viewPane.setRight(instrumentPane);
        viewPane.setTop(settingsPanel);
    }

    public BorderPane getPane() {
        return this.viewPane;
    }
}
