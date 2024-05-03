import java.awt.*;
import java.util.Random;

public class Worm {

    public int x;
    public int y = 400;
    public int speed;

    public int width;
    public int height = 10;

    public int ability;
    public boolean collectet;
    private Image image;

    public Worm(Pipe pipe){
        Random random = new Random();
        this.x = pipe.x;
        this.y += pipe.y + random.nextInt(100);
        this.speed = pipe.speed;
        this.width = pipe.width;

    }

    public void reset() {
        width = 66;
        height = 50;
        x = Controller.WIDTH + 2;


    }

    public void update() {
        x -= speed;
    }

    public boolean collides(int _x, int _y, int _width, int _height) {

        int margin = 2;

        if (_x + _width - margin > x && _x + margin < x + width && _y + _height/2 > y && _y - height < y + height) {
            return true;
        }

        return false;
    }
    public Render getRender() {
        Render r = new Render();
        r.x = x;
        r.y = y;

        if (image == null) {
            image = Util.loadImage("lib/worm.png");
        }
        r.image = image;

        return r;
    }
}
