/** 实体*/
public interface Entity {
    double getX();
    void setX(double X);

    double getY();
    void setY(double Y);

    boolean isHit();
    boolean circular(Entity e);
    
}