package com.base;

import com.library.StdDraw;

import java.util.HashMap;
import java.util.LinkedList;

public class bigFood extends Food{
    protected int radius = config.radiusFood * 3;

    public bigFood(HashMap<Integer, Snake> snakes) {
        super(snakes);
    }

    @Override
    public void takeFood(Snake s, HashMap<Integer, Snake> snakes) {
        s.getBody().addFirst(new Entity(s.getBody().getFirst().x, s.getBody().getFirst().y));
        s.getBody().addFirst(new Entity(s.getBody().getFirst().x, s.getBody().getFirst().y));
        s.getBody().addFirst(new Entity(s.getBody().getFirst().x, s.getBody().getFirst().y));
        s.getBody().addFirst(new Entity(s.getBody().getFirst().x, s.getBody().getFirst().y));
        this.createFood(snakes);
    }

    @Override
    public void draw() {
            StdDraw.setPenColor(config.color_Food);
            StdDraw.filledCircle(this.x, this.y, this.radius);
    }
}
