package com.thunderTECH.Painter8Bit.panels.instruments;

import com.thunderTECH.Painter8Bit.panels.painter.PaintPane;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;


public class InstrumentPane extends BorderPane {

    public InstrumentPane(PaintPane paintPane) {
        this.setCenter(new ColorsPalette(paintPane));

        this.setPadding(new Insets(20,20,20,20));
    }

}
