public class game {
    public static void main(String[] args) {
        Snake s = new Snake(0, 0);
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-100, 100);
        StdDraw.clear();
        s.draw();
        StdDraw.show();
        while(true){
            StdDraw.clear();
            s.draw();
            s.move();
            StdDraw.show();
        }
    }
}
