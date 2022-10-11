import java.util.Random;

public class Food extends Entity{
    protected void createFood(Snake[] snakes){
        Random random = new Random();
        boolean temp = true;
        while(temp){
            temp = false;
            this.x = random.nextInt();
            this.y = random.nextInt();
            for (Snake snake:snakes) {
                for (Entity body : snake.getBody()) {
                    if(!this.circular(body)){
                        temp = true;
                        break;
                    }
                }
                if(temp){
                    break;
                }
            }
        }
    }
}
