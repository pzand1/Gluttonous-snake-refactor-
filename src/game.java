import java.util.LinkedList;

public class game {
    static int n = 0;

    public static void main(String[] args) throws InterruptedException {

        LinkedList<Snake> snakess = new LinkedList<>();
        snakess.add(new Snake(0, 0, "???"));

        LinkedList<Food> foods = new LinkedList<>();
        foods.add(new Food());
        foods.add(new bigFood());

        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(config.canvasWidth, config.canvasHeight);
        StdDraw.setXscale(-config.x_Size, config.x_Size);
        StdDraw.setYscale(-config.y_Size, config.y_Size);


        Snake snakes = snakess.getLast();
        snakess.getLast().draw();
        StdDraw.show();
        if (config.mouse_control)
            control.mouse_control(snakess);
        else if(config.Key_control)
            control.ThreadTest(snakess);

//        while (!snakes.isdead(snakess)) {
        while (true) {
            StdDraw.clear();
            for (Snake s : snakess) {
                s.draw();
                s.move();
                s.eatFood(snakess, foods);
            }
            for (Food f : foods) {
                f.draw();
            }
            StdDraw.show();
            StdDraw.pause(config.dT);
        }

    }
}
