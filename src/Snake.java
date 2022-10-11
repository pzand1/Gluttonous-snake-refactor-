import java.util.LinkedList;

public class Snake extends Entity {
    private LinkedList<Entity> body = new LinkedList<>();
    protected boolean ismove = true;
    protected double speed = config.speed;
    protected int angle = 0;

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
        body.addFirst(new Entity(dx, dy));
        body.removeLast();
    }
}
