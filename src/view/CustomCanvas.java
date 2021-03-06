package view;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class CustomCanvas extends Canvas {

    AffineTransform origin;
    Graphics2D g2d;

    // x-axis coord constants
    public static final int X_AXIS_MARGIN_FROM_LEFT = 20;
    public static final int X_AXIS_MARGIN_FROM_TOP = 420;
    public static final int X_AXIS_WIDTH = 700;

    // y-axis coord constants
    public static final int Y_AXIS_X_MARGIN_FROM_LEFT = 20;
    public static final int Y_AXIS_MARGIN_FROM_TOP = 0;
    public static final int Y_AXIS_HEIGHT = 420;

    //arrows of axis are represented with "hipotenuse" of
    //triangle
    // now we are define length of cathetas of that triangle
    public static final int FIRST_LENGHT = 5;
    public static final int SECOND_LENGHT = 4;

    // size of start coordinate lenght
    public static final int ORIGIN_COORDINATE_LENGHT = 10;

    // distance of coordinate strings from axis
    public static final int AXIS_STRING_DISTANCE = 20;

    public void drawPixel(int x, int y) {
        g2d.drawLine(x, y, x, y);
    }

    public void prepare() {
        g2d = (Graphics2D) getGraphics();
        origin = g2d.getTransform();
        g2d.translate(0, getHeight() - 1);
        g2d.scale(1, -1);
    }

    public void changeOrigin() {
        g2d.setTransform(origin);
    }

    public void drawCartesianPlane() {
        g2d = (Graphics2D) getGraphics();

        // x-axis
        g2d.drawLine(X_AXIS_MARGIN_FROM_LEFT, X_AXIS_MARGIN_FROM_TOP,
                X_AXIS_WIDTH, X_AXIS_MARGIN_FROM_TOP);
        // y-axis
        g2d.drawLine(Y_AXIS_X_MARGIN_FROM_LEFT, Y_AXIS_MARGIN_FROM_TOP,
                Y_AXIS_X_MARGIN_FROM_LEFT, Y_AXIS_HEIGHT);

        // x-axis arrow
        g2d.drawLine(X_AXIS_WIDTH - FIRST_LENGHT,
                X_AXIS_MARGIN_FROM_TOP - SECOND_LENGHT,
                X_AXIS_WIDTH, X_AXIS_MARGIN_FROM_TOP);
        g2d.drawLine(X_AXIS_WIDTH - FIRST_LENGHT,
                X_AXIS_MARGIN_FROM_TOP + SECOND_LENGHT,
                X_AXIS_WIDTH, X_AXIS_MARGIN_FROM_TOP);

        // y-axis arrow
        g2d.drawLine(Y_AXIS_X_MARGIN_FROM_LEFT - SECOND_LENGHT,
                Y_AXIS_MARGIN_FROM_TOP + FIRST_LENGHT,
                Y_AXIS_X_MARGIN_FROM_LEFT, Y_AXIS_MARGIN_FROM_TOP);
        g2d.drawLine(Y_AXIS_X_MARGIN_FROM_LEFT + SECOND_LENGHT,
                Y_AXIS_MARGIN_FROM_TOP + FIRST_LENGHT,
                Y_AXIS_X_MARGIN_FROM_LEFT, Y_AXIS_MARGIN_FROM_TOP);

        // draw origin Point
        g2d.fillOval(
                X_AXIS_MARGIN_FROM_LEFT - (ORIGIN_COORDINATE_LENGHT / 2),
                Y_AXIS_HEIGHT - (ORIGIN_COORDINATE_LENGHT / 2),
                ORIGIN_COORDINATE_LENGHT, ORIGIN_COORDINATE_LENGHT);

        // draw text "X" and draw text "Y"
        g2d.drawString("X", X_AXIS_WIDTH - AXIS_STRING_DISTANCE / 2,
                X_AXIS_MARGIN_FROM_TOP + AXIS_STRING_DISTANCE);
        g2d.drawString("Y", Y_AXIS_X_MARGIN_FROM_LEFT - AXIS_STRING_DISTANCE,
                Y_AXIS_MARGIN_FROM_TOP + AXIS_STRING_DISTANCE / 2);
        g2d.drawString("(0, 0)", X_AXIS_MARGIN_FROM_LEFT - AXIS_STRING_DISTANCE,
                Y_AXIS_HEIGHT + AXIS_STRING_DISTANCE);

        // numerate axis
        int xCoordNumbers = 21;
        int yCoordNumbers = 13;
        int xLength = (X_AXIS_WIDTH - X_AXIS_MARGIN_FROM_LEFT)
                / xCoordNumbers;
        int yLength = (Y_AXIS_HEIGHT - Y_AXIS_MARGIN_FROM_TOP)
                / yCoordNumbers;

        // draw x-axis numbers
        for (int i = 1; i < xCoordNumbers; i++) {
            g2d.drawLine(X_AXIS_MARGIN_FROM_LEFT + (i * xLength),
                    X_AXIS_MARGIN_FROM_TOP - SECOND_LENGHT,
                    X_AXIS_MARGIN_FROM_LEFT + (i * xLength),
                    X_AXIS_MARGIN_FROM_TOP + SECOND_LENGHT);
            g2d.drawString(Integer.toString(i),
                    X_AXIS_MARGIN_FROM_LEFT + (i * xLength) - 3,
                    X_AXIS_MARGIN_FROM_TOP + AXIS_STRING_DISTANCE);
        }

        //draw y-axis numbers
        for (int i = 1; i < yCoordNumbers; i++) {
            g2d.drawLine(Y_AXIS_X_MARGIN_FROM_LEFT - SECOND_LENGHT,
                    Y_AXIS_HEIGHT - (i * yLength),
                    Y_AXIS_X_MARGIN_FROM_LEFT + SECOND_LENGHT,
                    Y_AXIS_HEIGHT - (i * yLength));
            g2d.drawString(Integer.toString(i),
                    Y_AXIS_X_MARGIN_FROM_LEFT - AXIS_STRING_DISTANCE,
                    Y_AXIS_HEIGHT - (i * yLength));
        }
    }

    public void drawText(int xCord, int yCord, String text) {
        g2d.drawString(text, xCord, yCord);
    }
}


