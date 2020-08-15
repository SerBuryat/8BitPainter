package com.thunderTECH.Painter8Bit.view;

import com.thunderTECH.Painter8Bit.view.panels.PainterCanvas;
import com.thunderTECH.Painter8Bit.view.panels.SettingsPanel;
import com.thunderTECH.Painter8Bit.view.panels.instruments.InstrumentPane;
import javafx.scene.layout.BorderPane;

public class Viewer {
    private final PainterCanvas painterCanvas;
    private final BorderPane viewPane;


    public Viewer (PainterCanvas painterCanvas) {
        this.painterCanvas = painterCanvas;

        this.viewPane = new BorderPane();
        InstrumentPane instrumentPane = new InstrumentPane(this.painterCanvas);
        SettingsPanel settingsPanel = new SettingsPanel(this.painterCanvas);

        viewPane.setCenter(this.painterCanvas.getCanvas());
        viewPane.setRight(instrumentPane);
        viewPane.setTop(settingsPanel);
    }

    public BorderPane getPane() {
        return this.viewPane;
    }
}
