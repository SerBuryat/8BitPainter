package com.thunderTECH.Painter8Bit.panels.painter;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class InstrumentPane extends GridPane {

    public InstrumentPane() {
        Button button1 = new Button("btn1");
        Button button2 = new Button("btn2");
        Button button3 = new Button("btn3");

        this.add(button1,0,0);
        this.add(button2,0,1);
        this.add(button3,0,2);
    }
}
