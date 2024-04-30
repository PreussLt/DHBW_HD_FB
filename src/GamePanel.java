import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    private final GameController game;
    private final MainMenu menu;
    public boolean menuOpen = false;

    public GamePanel(GameController game, MainMenu menu) {
        this.game = game;
        this.menu = menu;
    }

    public void update() {
        if (!game.started && !menuOpen) {
            if(menu.getStatMenu(game.highscore)) game.started=true;
        }

        if (game.gameover && !menuOpen) {
            if(menu.getRestartMenu(game.highscore,game.score)){
                game.restart();
                game.started = true;
            }
        }
        game.update();
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g;
        for (Render r : game.getRenders())
            if (r.transform != null)
                g2D.drawImage(r.image, r.transform, null);
            else
                g.drawImage(r.image, r.x, r.y, null);


        g2D.setColor(Color.BLACK);


        if (game.started) {
            g2D.setFont(new Font("TimesRoman", Font.PLAIN, 24));

            g2D.drawString("Score:", 10, 726);
            g2D.drawString(Integer.toString(game.score), 30, 750);

            g2D.drawString("Highscore:", 100, 726);
            g2D.drawString(Integer.toString(game.highscore), 150, 750);

            g2D.drawString("Fähigkeit", 240, 726);
            g2D.drawString(Integer.toString(game.wormCounter)+"/"+game.wormsToAbility, 270,750 );

            if (game.abillityAktiv >=0 && game.ability!=null){
                Render r = game.ability.getRender();
                if (r.transform != null)
                    g2D.drawImage(r.image, r.transform, null);
                else
                    g.drawImage(r.image, r.x, r.y, null);
            }
        }



    }

    public void run() {
        try {
            while (true) {
                update();
                Thread.sleep(25);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
