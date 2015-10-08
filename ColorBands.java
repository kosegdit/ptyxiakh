/*
 * /*
 * *
 * * This is a class file for the program AviNet
 * *
 * * Copyright (c) Segditsas Konstantinos, 2015
 * * Email: kosegdit@gmail.com
 * *
 * * All rights reserved
 * *
 */

package ptyxiakh;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author kostas
 */
public class ColorBands {
    
    
    public static List<Color> getColorBands(Color color, int bands) {

        List<Color> colorBands = new ArrayList<>(bands);
        for (int index = 0; index < bands; index++) {
            colorBands.add(darken(color, (double) index / (double) bands));
        }
        return colorBands;

    }

    public static Color getRandomColor() {
        
        Random rand = new Random();

        return new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    public static Color darken(Color color, double fraction) {

        int red = (int) Math.round(Math.max(0, color.getRed() - 255 * fraction));
        int green = (int) Math.round(Math.max(0, color.getGreen() - 255 * fraction));
        int blue = (int) Math.round(Math.max(0, color.getBlue() - 255 * fraction));

        int alpha = color.getAlpha();

        return new Color(red, green, blue, alpha);

    }
}
