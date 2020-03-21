package com.thunderTECH.Painter8Bit;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ColorsGenerator {
    private final static int[] COLOR_MODIFIER = {255, 192, 128, 64, 0};

    public static List<Color> GET_24_COLOR_PALLET() {
        List<Color> colorsPalletList = new ArrayList<>();

        colorsPalletList.addAll(RED_GREEN_COLORS_LIST());
        colorsPalletList.addAll(GREEN_BLUE_COLORS_LIST());
        colorsPalletList.addAll(BLUE_RED_COLORS_LIST());

        return colorsPalletList;
    }

    public static List<Color> BRIGHTER_COLORS_LIST(List<Color> colorsList, int brightnessLevel) {
        List<Color> brighterColorsList = new ArrayList<>(colorsList);

        while (brightnessLevel > 0) {
            List<Color> tmpColorsList = new ArrayList<>();
            brighterColorsList.forEach(color -> tmpColorsList.add(color.desaturate()));

            brighterColorsList = new ArrayList<>(tmpColorsList);

            brightnessLevel--;
        }

        return brighterColorsList;
    }

    public static List<Color> DARKER_COLORS_LIST(List<Color> colorsList, int darknessLevel) {
        List<Color> darkerColorsList = new ArrayList<>(colorsList);

        while (darknessLevel > 0) {
            List<Color> tmpColorsList = new ArrayList<>();
            darkerColorsList.forEach(color -> tmpColorsList.add(color.darker()));

            darkerColorsList = new ArrayList<>(tmpColorsList);

            darknessLevel--;
        }

        return darkerColorsList;
    }

    private static List<Color> RED_GREEN_COLORS_LIST() {
        ArrayList<Color> redGreenColorsList = new ArrayList<>();

        redGreenColorsList.add(Color.rgb(255,0,0));
        redGreenColorsList.add(Color.rgb(255,64,0));
        redGreenColorsList.add(Color.rgb(255,128,0));
        redGreenColorsList.add(Color.rgb(255,192,0));
        redGreenColorsList.add(Color.rgb(255,255,0));
        redGreenColorsList.add(Color.rgb(192,255,0));
        redGreenColorsList.add(Color.rgb(128,255,0));
        redGreenColorsList.add(Color.rgb(64,255,0));


        return redGreenColorsList;
    }

    private static List<Color> GREEN_BLUE_COLORS_LIST() {
        ArrayList<Color> greenBlueColorsList = new ArrayList<>();

        greenBlueColorsList.add(Color.rgb(0,255,0));
        greenBlueColorsList.add(Color.rgb(0,255,64));
        greenBlueColorsList.add(Color.rgb(0,255,128));
        greenBlueColorsList.add(Color.rgb(0,255,192));
        greenBlueColorsList.add(Color.rgb(0,255,255));
        greenBlueColorsList.add(Color.rgb(0,192,255));
        greenBlueColorsList.add(Color.rgb(0,128,255));
        greenBlueColorsList.add(Color.rgb(0,64,255));

        return greenBlueColorsList;
    }

    private static List<Color> BLUE_RED_COLORS_LIST() {
        ArrayList<Color> blueRedColorsList = new ArrayList<>();

        blueRedColorsList.add(Color.rgb(0,0,255));
        blueRedColorsList.add(Color.rgb(64,0,255));
        blueRedColorsList.add(Color.rgb(128,0,255));
        blueRedColorsList.add(Color.rgb(192,0,255));
        blueRedColorsList.add(Color.rgb(255,0,255));
        blueRedColorsList.add(Color.rgb(255,0,192));
        blueRedColorsList.add(Color.rgb(255,0,128));
        blueRedColorsList.add(Color.rgb(255,0,64));

        return blueRedColorsList;
    }
}
