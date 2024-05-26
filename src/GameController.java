
import java.util.ArrayList;


public class GameController {

    public static final int PIPE_DELAY = 100;

    private Boolean paused;

    private int objektDelay;

    private Bird bird;
    private ArrayList<Pipe> pipes;
    private ArrayList<Worm> worms;
    private Keyboard keyboard;

    public int score;
    public int highscore = 0;
    public int wormCounter;

    public Ability ability;
    public int abillityAktiv=-1;
    public int wormsToAbility=7;
    public Boolean gameover;
    public Boolean started;

    public GameController() {
        keyboard = Keyboard.getInstance();
        restart();
    }

    public void restart() {
        paused = false;
        started = false;
        gameover = false;

        score = 0;
        objektDelay = 0;
        wormCounter = 0;
        abillityAktiv=-1;

        bird = new Bird();
        pipes = new ArrayList<Pipe>();
        worms = new ArrayList<Worm>();
    }

    public void update() {
        if (!started){
            return;
        }

        if (paused)
            return;

        bird.update();

        if (gameover)
            return;

        moveObjekts();
        checkForCollisions();
    }

    public ArrayList<Render> getRenders() {
        ArrayList<Render> renders = new ArrayList<Render>();
        renders.add(new Render(0, 0, "lib/background.png"));
        for (Pipe pipe : pipes)
            renders.add(pipe.getRender());
        for (Worm worm : worms)
            renders.add(worm.getRender());
        renders.add(new Render(0, 0, "lib/foreground.png"));
        renders.add(bird.getRender());
        return renders;
    }



    private void moveObjekts() {
        objektDelay--;
        Worm wormNext = null;
        if (objektDelay < 0) {
            objektDelay = PIPE_DELAY;
            Pipe northPipe = null;
            Pipe southPipe = null;

            // Look for Objekts off the screen
            for (Pipe pipe : pipes) {
                if (pipe.x - pipe.width < 0) {
                    if (northPipe == null) {
                        northPipe = pipe;
                    } else if (southPipe == null) {
                        southPipe = pipe;
                        break;
                    }
                }
            }

            for (Worm worm : worms){
                if (worm.x - worm.height < 0 && worms.size() < pipes.size()/2) {
                    wormNext = worm;
                }
            }

            if (northPipe == null) {
                Pipe pipe = new Pipe("north");
                pipes.add(pipe);
                northPipe = pipe;
            } else {
                northPipe.reset();
            }

            if (southPipe == null) {
                Pipe pipe = new Pipe("south");
                pipes.add(pipe);
                southPipe = pipe;
            } else {
                southPipe.reset();
            }


            Worm worm;
            if (wormNext == null) {
                worm = new Worm(southPipe);
                worms.add(worm);
            } else if (wormNext.collectet){
                worm = new Worm(southPipe);
                worms.add(worm);
                wormNext.reset();

            } else { // Worm was missed
                worm = new Worm(southPipe);
                worms.add(worm);
                wormNext.reset();
            }
            wormNext = worm;

            northPipe.y = southPipe.y + southPipe.height + 250;
        }

        for (Pipe pipe : pipes) {
            pipe.update();
        }

        for (Worm worm : worms)
            worm.update();
    }

    private void checkForCollisions() {

        for (Pipe pipe : pipes) {
            if (pipe.collides(bird.x, bird.y, bird.width, bird.height) ) {
                if (ability != null && ability.abilitynum == 1 && abillityAktiv >-1){
                    abillityAktiv=0;
                }else {
                    gameover = true;
                    bird.dead = true;
                }
            } else if (pipe.x == bird.x && pipe.orientation.equalsIgnoreCase("south")) {
                score++;
                abillityAktiv--;
                if (abillityAktiv>=0){
                    ability.getAbility(this,bird);
                }else if (abillityAktiv==-1){
                    ability.removeAbility(bird);
                }
            }
        }

        for (Worm worm : worms){
            if (worm.collides(bird.x, bird.y, bird.width, bird.height)) {
                worm.collectet = true;
                wormCounter++;
                worm.y = -200;
                if (wormCounter==wormsToAbility){
                    wormCounter=0;
                    abillityAktiv=5;
                    ability = new Ability();
                }
            }
        }

        // Ground + Bird collision
        if (bird.y + bird.height > Controller.HEIGHT - 80) {
            gameover = true;
            bird.y = Controller.HEIGHT - 80 - bird.height;
        }

        if (score>highscore)highscore = score;
    }
}
