import java.awt.*;
import java.util.Random;

public class Ability {
    public int x = 400;
    public int y = 705;
    public int abilitynum;
    // 0 = 2x; 1 = shield; 2 = slower
    private Image image;

    public Ability(){
        abilitynum =(int)(Math.random() * 3);
        System.out.println(abilitynum);
        switch (abilitynum) {
            case 0: image = Util.loadImage("lib/2x.png");
            break;
            case 1: image = Util.loadImage("lib/Schild.png");
            break;
            case 2: image = Util.loadImage("lib/Verlangsamung.png");
            break;
        }

    }

    public void getAbility(GameController game, Bird bird){
        switch (abilitynum) {
            case 0: image = Util.loadImage("lib/2x.png");
            game.score++;
                break;
            case 2:
                bird.gravity=0.3;
                break;
        }
    }

    public void removeAbility(Bird bird){
        bird.resetGravity();
    }

    public Render getRender() {
        Render r = new Render();
        r.x = x;
        r.y = y;

        r.image = image;

        return r;
    }
}
