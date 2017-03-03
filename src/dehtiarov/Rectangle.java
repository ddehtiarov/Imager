package dehtiarov;

/**
 * Created by Dmytro_Dehtiarov on 3/2/2017.
 */
public class Rectangle {

    Point topLeft;
    Point bottomRight;

    public Rectangle() {
    }

    public Rectangle(Point topLeft, Point bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    public Point getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public Point getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Point bottomRight) {
        this.bottomRight = bottomRight;
    }

    public int getWidth(){
        return bottomRight.getX() - topLeft.getX();
    }

    public int getHeigth(){
        return bottomRight.getY() - topLeft.getY();
    }
}
