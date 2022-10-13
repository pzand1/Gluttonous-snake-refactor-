import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class control {
    public static int n = 0;

    public static void ThreadTest(LinkedList<Snake> snakes) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (StdDraw.hasNextKeyTyped()) {
                    n++;
                    char key = StdDraw.nextKeyTyped();
                    if (key == 'a' && snakes.getFirst().hasdAgree <= config.MaxAngle) {
                        snakes.getFirst().hasdAgree += config.dAngle;

                    } else if (key == 'd' && snakes.getFirst().hasdAgree >= -config.MaxAngle) {
                        snakes.getFirst().hasdAgree -= config.dAngle;
                    }
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1);//0代表一开始就执行没有延迟
    }

    public static void mouse_control(LinkedList<Snake> snakes) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Snake snake = snakes.getFirst();
                Entity head = snake.getBody().getLast();
                double degree_snake = degree(snake.angle);
                double mouseX = StdDraw.mouseX();
                double mouseY = StdDraw.mouseY();
                double degree_mouse = degree(Math.toDegrees(Math.atan2(mouseY - head.y, mouseX - head.x)));
                if(Trun(degree_mouse, degree_snake) == 'l' && !(snake.hasdAgree == config.MaxAngle)){
                    snake.hasdAgree += config.dAngle;
                }else if(Trun(degree_mouse, degree_snake) == 'r'&& !(snake.hasdAgree == -config.MaxAngle)){
                    snake.hasdAgree -= config.dAngle;
                }
//                System.out.println(degree_mouse);
//                System.out.println(degree_snake);
//                System.out.println(Trun(degree_mouse, degree_snake));

            }

        };
        timer.scheduleAtFixedRate(timerTask, 0, 1);//0代表一开始就执行没有延迟
    }

    public static double degree(double angle) {
        double a = angle % 360;
        return a < 0 ? a + 360 : a;
    }
    public static char Trun(double mouse, double snake){
        double sub = snake - mouse;
        if(Math.abs(sub) <= 0.05) return 'x';
        if(sub > 0){
            if(sub <= 180)
                return 'r';
            else return 'l';
        }else if(sub < 0){
            if(sub <= -180) return 'r';
            else return 'l';
        }
        return 'x';
    }
}