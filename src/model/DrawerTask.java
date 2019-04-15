package model;

import java.awt.image.BufferedImage;
import java.util.Random;

import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawerTask extends Task{

    private static final int MIN = -8;
    private static final int MAX = 8;
    private GraphicsContext gc;
    private Canvas canvas;

    public DrawerTask(GraphicsContext gc, Canvas canvas) {
        this.gc = gc;
        this.canvas = canvas;
    }

    @Override
    protected Object call() throws Exception {
        while(true){

            gc.setFill(Color.BLUEVIOLET);

            gc.fillRect(gc.getCanvas().getLayoutX(), gc.getCanvas().getLayoutY(), gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
            BufferedImage bi = new BufferedImage((int)gc.getCanvas().getWidth(), (int)gc.getCanvas().getHeight(), BufferedImage.TYPE_INT_RGB);

            Random random = new Random();

            for(int i = 0;i < 500000;i++){

                double x = MIN + (MAX - MIN) * random.nextDouble();
                double y = MIN + (MAX - MIN) * random.nextDouble();

                if(Equation.calc(x,y)) {
                    double x_1 = canvas.getHeight() + (canvas.getHeight() - canvas.getWidth()) * (x-MIN) / (MAX - MIN);
                    double y_1 = canvas.getHeight() + (canvas.getHeight() - canvas.getWidth()) * (y-MIN) / (MAX - MIN);
                    bi.setRGB((int) x_1, (int) y_1, 16776960);
                }

                if (i % 1000 == 0)
                    gc.drawImage(SwingFXUtils.toFXImage(bi, null), 0, 0);

                updateProgress(i,500000);

                }



            if(isCancelled()) break;
            }
        return null;
    }


}
