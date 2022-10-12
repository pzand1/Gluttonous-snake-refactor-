import java.util.LinkedList;

public class game {
    public static void main(String[] args) {
        LinkedList<Snake> snakess = new LinkedList<>();
        snakess.add(new Snake(0, 0,"???"));

        LinkedList<Food> foods = new LinkedList<>();
        foods.add(new Food());

        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(config.canvasWidth, config.canvasHeight);
        StdDraw.setXscale(-config.x_Size, config.x_Size);
        StdDraw.setYscale(-config.y_Size, config.y_Size);

        Snake snakes = snakess.getLast();
        snakess.getLast().draw();
        StdDraw.show();

        control.ThreadTest(snakess);
//        while (!snakes.isdead(snakess)) {
        while (true) {
            StdDraw.clear();
            for (Snake s : snakess) {
                s.draw();
                s.move();
            }

            StdDraw.show();
//            StdDraw.pause(config.dT);
            StdDraw.pause(100);

        }

    }
}
