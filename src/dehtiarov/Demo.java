package dehtiarov;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Created by Dmytro_Dehtiarov on 3/2/2017.
 */
public class Demo {

    public static void main(String[] args) throws IOException {
        compareFiles(new File("image1.png"), new File("image2.png"));
    }

    private static void compareFiles(File file1, File file2) throws IOException {
        BufferedImage bufferedImage1 = ImageIO.read(file1);
        BufferedImage bufferedImage2 = ImageIO.read(file2);
        List<Rectangle> rectangles = getRectangles(bufferedImage1, bufferedImage2);
        drawResult(bufferedImage2, rectangles, "result.jpg");
    }

    private static void drawResult(BufferedImage bufferedImage1, List<Rectangle> rectangles, String filename) throws IOException {
        Graphics2D graph = bufferedImage1.createGraphics();
        graph.setColor(Color.RED);
        for (Rectangle rectangle : rectangles) {
            graph.drawRect(rectangle.getTopLeft().getX(), rectangle.getTopLeft().getY(), rectangle.getWidth(), rectangle.getHeigth());
        }
        graph.dispose();
        ImageIO.write(bufferedImage1, "jpg", new File(filename));
    }

    private static List<Rectangle> getRectangles(BufferedImage img1, BufferedImage img2) {
        List<ArrayList<Point>> arrayLists = new ArrayList();
        if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
            for (int y = 0; y < img1.getHeight(); y++) {
                arrayLists.add(new ArrayList<>());
                for (int x = 0; x < img1.getWidth(); x++) {
                    if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                        arrayLists.get(y).add(new Point(x, y));
                    }
                }
            }
            return getRectanglesFromPointArray(arrayLists);
        }
        throw new RuntimeException("images has different sizes");
    }

    private static List<Rectangle> getRectanglesFromPointArray(List<ArrayList<Point>> arrayLists) {
        List<Rectangle> rectangles = new ArrayList<>();
        Point topLeft = new Point();
        Point bottomRight = new Point();

        for (List<Point> pointsRaw : arrayLists) {
            if (pointsRaw.size() != 0) {
                findCorrectPoints(topLeft, bottomRight, pointsRaw);
            } else {
                if (isCorrectBottomPoint(bottomRight)) {
                    rectangles.add(new Rectangle(topLeft, bottomRight));
                    topLeft = new Point();
                    bottomRight = new Point();
                }
            }
        }
        if (isCorrectBottomPoint(bottomRight)) {
            rectangles.add(new Rectangle(topLeft, bottomRight));
        }
        return rectangles;
    }

    private static boolean isCorrectBottomPoint(Point bottomRight) {
        return bottomRight.getX() != 0 && bottomRight.getY() != 0;
    }

    private static void findCorrectPoints(Point topLeft, Point bottomRight, List<Point> list) {
        int xtl = list.get(0).getX();
        int ytl = list.get(0).getY();
        int xbr = list.get(list.size() - 1).getX();
        int ybr = list.get(list.size() - 1).getY();

        topLeft.setX((topLeft.getX() > xtl || topLeft.getX() == 0) ? xtl : topLeft.getX());
        topLeft.setY((topLeft.getY() > ytl || topLeft.getY() == 0) ? ytl : topLeft.getY());
        bottomRight.setX((bottomRight.getX() < xbr) ? xbr : bottomRight.getX());
        bottomRight.setY((bottomRight.getY() < ybr) ? ybr : bottomRight.getY());
    }
}