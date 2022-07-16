package com.example.ap;

import javafx.scene.paint.Color;

public enum ColorTypes {
    White(Color.WHITE), Pink(Color.PINK), Blue(Color.BLUE), Gray(Color.LIGHTGRAY);

    public Color color;

    ColorTypes(Color color) {
        this.color = color;
    }

    public static ColorTypes find(Color color) {
        for (ColorTypes colorTypes : ColorTypes.values()) {
            if (colorTypes.color == color) return colorTypes;
        }
        return null;
    }
}
