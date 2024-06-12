
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
        setPaused(false);
        started = false;
        gameover = false;

        score = 0;
        objektDelay = 0;
        wormCounter = 0;
        abillityAktiv=-1;

        setBird(new Bird());
        setPipes(new ArrayList<Pipe>());
        setWorms(new ArrayList<Worm>());
    }

    public void update() {
        if (!started){
            return;
        }

        if (getPaused())
            return;

        getBird().update();

        if (gameover)
            return;

        moveObjekts();
        checkForCollisions();
    }

    public ArrayList<Render> getRenders() {
        ArrayList<Render> renders = new ArrayList<Render>();
        renders.add(new Render(0, 0, "img/background.png"));
        for (Pipe pipe : getPipes())
            renders.add(pipe.getRender());
        for (Worm worm : getWorms())
            renders.add(worm.getRender());
        renders.add(new Render(0, 0, "img/foreground.png"));
        renders.add(getBird().getRender());
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
            for (Pipe pipe : getPipes()) {
                if (pipe.x - pipe.width < 0) {
                    if (northPipe == null) {
                        northPipe = pipe;
                    } else if (southPipe == null) {
                        southPipe = pipe;
                        break;
                    }
                }
            }

            for (Worm worm : getWorms()){
                if (worm.x - worm.height < 0 && getWorms().size() < getPipes().size()/2) {
                    wormNext = worm;
                }
            }

            if (northPipe == null) {
                Pipe pipe = new Pipe("north");
                getPipes().add(pipe);
                northPipe = pipe;
            } else {
                northPipe.reset();
            }

            if (southPipe == null) {
                Pipe pipe = new Pipe("south");
                getPipes().add(pipe);
                southPipe = pipe;
            } else {
                southPipe.reset();
            }


            Worm worm;
            if (wormNext == null) {
                worm = new Worm(southPipe);
                getWorms().add(worm);
            } else if (wormNext.collectet){
                worm = new Worm(southPipe);
                getWorms().add(worm);
                wormNext.reset();

            } else { // Worm was missed
                worm = new Worm(southPipe);
                getWorms().add(worm);
                wormNext.reset();
            }
            wormNext = worm;

            northPipe.y = southPipe.y + southPipe.height + 250;
        }

        for (Pipe pipe : getPipes()) {
            pipe.update();
        }

        for (Worm worm : getWorms())
            worm.update();
    }

    private void checkForCollisions() {

        for (Pipe pipe : getPipes()) {
            if (pipe.collides(getBird().x, getBird().y, getBird().width, getBird().height) ) {
                if (ability != null && ability.abilitynum == 1 && abillityAktiv >-1){
                    abillityAktiv=0;
                }else {
                    gameover = true;
                    getBird().dead = true;
                }
            } else if (pipe.x == getBird().x && pipe.orientation.equalsIgnoreCase("south")) {
                score++;
                abillityAktiv--;
                if (abillityAktiv>=0){
                    ability.getAbility(this,getBird());
                }else if (abillityAktiv==-1){
                    ability.removeAbility(getBird());
                }
            }
        }

        for (Worm worm : getWorms()){
            if (worm.collides(getBird().x, getBird().y, getBird().width, getBird().height)) {
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
        if (getBird().y + getBird().height > Controller.HEIGHT - 80) {
            gameover = true;
            getBird().y = Controller.HEIGHT - 80 - getBird().height;
        }

        if (score>highscore)highscore = score;
    }

	public Boolean getPaused() {
		return paused;
	}

	public void setPaused(Boolean paused) {
		this.paused = paused;
	}

	public ArrayList<Pipe> getPipes() {
		return pipes;
	}

	public void setPipes(ArrayList<Pipe> pipes) {
		this.pipes = pipes;
	}

	public Bird getBird() {
		return bird;
	}

	public void setBird(Bird bird) {
		this.bird = bird;
	}

	public ArrayList<Worm> getWorms() {
		return worms;
	}

	public void setWorms(ArrayList<Worm> worms) {
		this.worms = worms;
	}
}
