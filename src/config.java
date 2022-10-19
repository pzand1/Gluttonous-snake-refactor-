import java.awt.*;

public final class config {
    //地图x方向大小 * 2(正负方向和)
    public static final int x_Size = 10000;
    //地图y方向大小 * 2(正负方向和)
    public static final int y_Size = 10000;
    //游戏时
    public static final int dT = 10;
    //默认初始速度
    public static final double speed = 5;
    //实体半径
    public static final int radius = 190;
    public static final int radiusFood = 100;
    //每帧转动最大幅度
    public static final int MaxAngle = 10;
    //每次改变的幅度
    public static final double dAngle = 0.5;


    public static final Color color_Snake = Color.BLACK;
    public static final Color color_Food = Color.GREEN;
    //碰撞生效
    public static final boolean isHit = true;
    //窗口大小
    public static final int canvasWidth = 1000;
    public static final int canvasHeight = 820;
}
