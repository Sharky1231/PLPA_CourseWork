package view;

import java.awt.*;
import java.util.Random;

public class CustomCanvas extends Canvas{

        public void drawTest()
        {
            Random r = new Random();
            getGraphics().drawLine(r.nextInt(700), r.nextInt(272), r.nextInt(700), r.nextInt(272));
        }
}
