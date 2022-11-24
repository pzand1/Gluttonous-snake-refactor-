package com.base;

import java.io.Serializable;

import static java.lang.Math.pow;

public class Entity implements Serializable {
    protected String picture;
    public double x;
    public double y;
    protected int radius = config.radius;
    public Entity(){}

    public Entity(double x, double y) {
        this.x = x;
        this.y = y;
    }
    private boolean isHit = config.isHit;
    public boolean isHit(){
        return isHit;
    }
    //判断重叠 true
    public boolean circular(Entity e){
        return Math.sqrt(pow(this.x - e.x, 2) + pow(this.y - e.y, 2)) <= this.radius + e.radius;
    }
}
