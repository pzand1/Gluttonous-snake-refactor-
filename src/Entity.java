import static java.lang.Math.pow;

public class Entity {
    protected String picture;
    protected double x;
    protected double y;
    protected int radius = config.radius;
    public Entity(){};

    public Entity(double x, double y) {
        this.x = x;
        this.y = y;
    }
    private boolean isHit = config.isHit;
    public boolean isHit(){
        return isHit;
    }

    public boolean circular(Entity e){
        return pow(this.x - e.x, 2) + pow(this.y - e.y, 2) <= pow(this.radius + e.radius, 2);
    }
}
