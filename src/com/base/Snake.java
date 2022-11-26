package com.base;

import com.library.StdDraw;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class Snake extends Entity implements Draw , Serializable {

    private LinkedList<Entity> body = new LinkedList<>();
    private int number;
    public double hasdAgree = 0;

//    public double dtime = 0;

    protected boolean ismove = true;
    protected double speed = config.speed;
    protected double angle = 0;


    public Snake(int number){
        this.number = number;
        for(int i = 0;i < 10;i++) {
            this.body.addFirst(new Entity(i, i + 1));
        }
    }

    public Snake(double x, double y, String picture) {
        super(x, y);
        this.picture = picture;
        for(int i = 0;i < 10;i++) {
            this.body.addFirst(new Entity(x, y + 1));
        }
    }

    public double getAngle() {
        return hasdAgree;
    }

    public void eatFood(HashMap<Integer, Snake> snakes, LinkedList<Food> foods) {
        for (Food food : foods) {
            if(body.getLast().circular(food)){
                food.takeFood(this, snakes);
            }
        }
    }

    public LinkedList<Entity> getBody(){
        return body;
    }
    private void resetAngle(){
        this.angle = this.angle + this.hasdAgree;
        this.hasdAgree = 0;
    }
    public void move(){
        if(!ismove){return;}
        resetAngle();
        double distance = speed * config.dT;
//        double distance = speed * dtime;
        double agree = Math.toRadians(this.angle);
        double dx = distance * Math.cos(agree);
        double dy = distance * Math.sin(agree);
        Entity b = body.getLast();
        body.addLast(new Entity(b.x + dx, b.y + dy));
        body.removeFirst();

//        dtime = 0;
        //Library.StdDraw.line(b.x, b.y, b.x + dx * 50, b.y + dy * 50);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Snake snake = (Snake) o;
        return Objects.equals(body, snake.body) && Objects.equals(number, snake.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
