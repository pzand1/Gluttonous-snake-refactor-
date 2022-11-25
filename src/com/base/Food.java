package com.base;

import com.library.StdDraw;

import java.io.Serializable;
import java.util.*;

public class Food extends Entity implements Draw, Serializable {
    protected int radius = config.radiusFood;
    public Food(HashMap<Integer, Snake> snakes) {
        this.createFood(snakes);
    }

    public void createFood(HashMap<Integer, Snake> snakes){
        Random random = new Random();
        boolean temp = true;
        while(temp){
            temp = false;
            this.x = (random.nextInt() % config.x_Size);
            this.y = (random.nextInt() % config.y_Size);
            for (Map.Entry<Integer,Snake> entry : snakes.entrySet()) {
                for (Entity body : entry.getValue().getBody()) {
                    if(this.circular(body)){
                        temp = true;
                        break;
                    }
                }
                if(temp){ break;}
            }
        }
    }
    public void takeFood(Snake s,HashMap<Integer, Snake> snakes){
        s.getBody().addFirst(new Entity(s.getBody().getFirst().x, s.getBody().getFirst().y));
        createFood(snakes);
    }
    public void draw(){
        StdDraw.setPenColor(config.color_Food);
        StdDraw.filledCircle(this.x, this.y, this.radius);
    }
}
