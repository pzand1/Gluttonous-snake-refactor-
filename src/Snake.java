import java.util.LinkedList;

public interface Snake extends Entity{
    LinkedList<Entity> getBody();

    boolean isDead();
    void move(int e);
}
