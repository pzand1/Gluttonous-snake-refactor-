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
        timer.scheduleAtFixedRate(timerTask, 0, 10);//0代表一开始就执行没有延迟
    }
}