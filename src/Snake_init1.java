import java.util.LinkedList;

import static java.lang.Math.pow;

public class Snake_init1 implements Snake {
    private LinkedList<entity_Init> body = new LinkedList<>();

    @Override
    private void eatFood(Food[] f) {
        for (Food food:f) {
            if(body.getLast().circular(food)){
                body.addFirst(new entity_Init(body.getFirst().getX(),body.getFirst().getY()));

            }
        }
    }
}
