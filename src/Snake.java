import java.util.LinkedList;

public class Snake extends Entity {
    private LinkedList<Entity> body = new LinkedList<>();
    protected boolean ismove = true;
    protected double speed = config.speed;
    protected int angle = 200;

    public Snake(double x, double y) {
        super(x, y);
        this.body.addFirst(new Entity(x, y));
        this.body.addFirst(new Entity(x, y));
        this.body.addFirst(new Entity(x, y));
        this.body.addFirst(new Entity(x, y));
    }

    protected void eatFood(Food[] f) {
        for (Food food:f) {
            if(body.getLast().circular(food)){

                body.addLast(new Entity(body.getLast().x, body.getLast().y));
            }
        }
    }
    public LinkedList<Entity> getBody(){
        return body;
    }
    public void setAngle(){

    }
    protected void move(){
        double distance = speed * config.dT;
        double dx = distance * Math.sin(angle);
        double dy = -distance * Math.cos(angle);
        Entity b = body.getFirst();
        body.addFirst(new Entity(b.x + dx, b.y + dy));
        body.removeLast();
        System.out.println(body.size());
    }

    public void draw(){
        for(Entity e : getBody()){
            StdDraw.picture(e.x, e.y, "1.png");
        }
    }
}
