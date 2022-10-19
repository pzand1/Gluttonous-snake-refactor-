import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class control {
    //向指定方向转dAgree
    private static void trun_To_SpeDir(Snake snake, char key, double dAngle){
        if (key == 'l' && snake.hasdAgree <= config.MaxAngle) {
            snake.hasdAgree += dAngle;
        } else if (key == 'r' && snake.hasdAgree >= -config.MaxAngle) {
            snake.hasdAgree -= dAngle;
        }
    }
    //向指定坐标转dAgree
    private static void trun_To_SpeCoor(Snake snake, double X, double Y){
        Entity head = snake.getBody().getLast();
        double degree_snake = degree(snake.angle);
        double now_degree = degree(Math.toDegrees(Math.atan2(Y - head.y, X - head.x)));
        trun_To_SpeDir(snake, Trun(now_degree, degree_snake));
    }
    public static void ThreadTest(LinkedList<Snake> snakes) {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (StdDraw.hasNextKeyTyped()) {
                    char key = StdDraw.nextKeyTyped();
                    if(key == config.turnToLeft) {
                        trun_To_SpeDir(snakes.getFirst(), 'l', config.dAngle_Key);
                    }else if(key == config.turnToRight){
                        trun_To_SpeDir(snakes.getFirst(), 'r', config.dAngle_Key);
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
                double mouseX = StdDraw.mouseX();
                double mouseY = StdDraw.mouseY();
                trun_To_SpeCoor(snake, mouseX, mouseY);
            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1);//0代表一开始就执行没有延迟
    }

    private static double degree(double angle) {
        double a = angle % 360;
        return a < 0 ? a + 360 : a;
    }
    private static char Trun(double mouse, double snake){
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

    private static void trun_To_SpeDir(Snake snake, char key){
        if (key == 'l' && snake.hasdAgree <= config.MaxAngle) {
            snake.hasdAgree += config.dAngle;
        } else if (key == 'r' && snake.hasdAgree >= -config.MaxAngle) {
            snake.hasdAgree -= config.dAngle;
        }
    }
}