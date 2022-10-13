import java.util.LinkedList;

public class game {
    static int n =0 ;
    public static void main(String[] args) throws InterruptedException {

        System.out.println(Math.toDegrees(Math.atan2(0, -1)));
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

//        control.ThreadTest(snakess);
        control.mouse_control(snakess);
//        while (!snakes.isdead(snakess)) {
        while (true) {
            control.n = 0;
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
//            System.out.println(control.n);
        }

    }
}
