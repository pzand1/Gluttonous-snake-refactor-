import java.util.LinkedList;

public class Snake extends Entity {

    private LinkedList<Entity> body = new LinkedList<>();
    protected double hasdAgree = 0;

    protected boolean ismove = true;
    protected double speed = config.speed;
    protected double angle = 0;

    public Snake(double x, double y, String picture) {
        super(x, y);
        this.picture = picture;
        for(int i = 0;i < 10;i++) {
            this.body.addFirst(new Entity(x, y + 1));
        }
    }

    protected void eatFood(LinkedList<Snake> snakes, LinkedList<Food> foods) {
        for (Food food : foods) {
            if(body.getLast().circular(food)){
                food.takefood(this, snakes);
            }
        }
    }

    public LinkedList<Entity> getBody(){
        return body;
    }
    private void setAngle(){
        this.angle = this.angle + this.hasdAgree;
        this.hasdAgree = 0;
    }
    protected void move(){
        if(!ismove){return;}
        setAngle();
        double distance = speed * config.dT;
        double agree = Math.toRadians(this.angle);
        double dx = distance * Math.cos(agree);
        double dy = distance * Math.sin(agree);
        Entity b = body.getLast();
        body.addLast(new Entity(b.x + dx, b.y + dy));
        body.removeFirst();
        StdDraw.line(b.x, b.y, b.x + dx * 50, b.y + dy * 50);

        System.out.println(this.hasdAgree);
    }
    protected boolean isdead(LinkedList<Snake> snakes){
        Entity head = body.getLast();
        for(Snake s : snakes){
            for (Entity e : s.body){
                if(head.circular(e) && !head.equals(e)){
                    return true;
                }
            }
        }
        return false;
    }

    public void draw(){
        for(Entity e : getBody()){
            StdDraw.setPenColor(config.color_Snake);
            StdDraw.filledCircle(e.x, e.y, this.radius);
        }
    }
}
