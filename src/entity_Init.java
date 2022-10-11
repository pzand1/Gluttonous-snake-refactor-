import static java.lang.Math.pow;

public class entity_Init implements Entity{
    private double x;
    private double y;
    private boolean isHit = config.isHit;
    public boolean isHit(){
        return isHit;
    }

    public entity_Init(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }

    public boolean circular(Entity e){
        if(pow(this.getX() - e.getX(), 2) + pow(this.getY() - e.getY(), 2) <= config.range * config.range){
            return true;
        }
    }
}
