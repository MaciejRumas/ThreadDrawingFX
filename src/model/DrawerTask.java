package model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class DrawerTask extends Task<DrawerTaskValue>{

    private static final int MIN = -8;
    private static final int MAX = 8;
    private int pointsAmount;
    private Random random = new Random();
    private double value = 0;
    private int hit_i = 0;

    public DrawerTask(int pointsAmount) {
        this.pointsAmount = pointsAmount;
    }

    @Override
    protected DrawerTaskValue call()  {

            Canvas canvas = (Canvas)Main.getMainScene().lookup("#mainCanvas");
            GraphicsContext gc = canvas.getGraphicsContext2D();

            gc.fillRect(gc.getCanvas().getLayoutX(), gc.getCanvas().getLayoutY(), gc.getCanvas().getWidth(), gc.getCanvas().getHeight());

            BufferedImage bi = new BufferedImage((int)gc.getCanvas().getWidth(), (int)gc.getCanvas().getHeight(), BufferedImage.TYPE_INT_RGB);

            Graphics2D graphics2D = bi.createGraphics();
            graphics2D.setPaint(Color.YELLOW);
            graphics2D.fillRect(0,0,bi.getWidth(),bi.getHeight());

            double x, y, x_1, y_1;

            for(int i = 0;i < pointsAmount;i++) {

                x = MIN + (MAX - MIN) * random.nextDouble();
                y = MIN + (MAX - MIN) * random.nextDouble();

                if (Equation.calc(x, y)) {
                    x_1 = canvas.getHeight() + (canvas.getHeight() - canvas.getWidth()) * (x - MIN) / (MAX - MIN) + canvas.getWidth()/16;
                    y_1 = canvas.getHeight() + (canvas.getHeight() - canvas.getWidth()) * (y - MIN) / (MAX - MIN) - canvas.getWidth()/8;
                    bi.setRGB((int) x_1, (int) y_1, 0); //16776960
                    hit_i++;
                }


                if (i % 1000 == 0) {
                    gc.drawImage(SwingFXUtils.toFXImage(bi, null), 0, 0);
                    updateProgress(i, pointsAmount);
                }

                if (isCancelled())
                {
                    gc.drawImage(SwingFXUtils.toFXImage(bi, null), 0, 0);
                    updateProgress(i, pointsAmount);
                    break;
                }
            }

            value += hit_i;
            //value *= canvas.getHeight() * canvas.getWidth() / pointsAmount;
            value *= 16.0 * 16.0 / pointsAmount;

        return new DrawerTaskValue(value);
    }


}
