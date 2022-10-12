import java.util.LinkedList;
import java.util.Random;

public class Food extends Entity{
    public Food() {

    }

    protected void createFood(LinkedList<Snake> snakes){
        Random random = new Random();
        boolean temp = true;
        while(temp){
            temp = false;
            this.x = random.nextInt();
            this.y = random.nextInt();
            for (Snake snake : snakes) {
                for (Entity body : snake.getBody()) {
                    if(!this.circular(body)){
                        temp = true;
                        break;
                    }
                }

                if(temp){ break;}

            }
        }
    }
    public void takefood(Snake s){
        s.getBody().addLast(new Entity(s.getBody().getLast().x, s.getBody().getLast().y));
    }
}
