import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    private GameController gameController;

    @BeforeEach
    public void setUp() {
    	
        gameController = new GameController();
    }

    @Test
    public void testInitialGameState() {
    	
        assertFalse(gameController.started);
        assertFalse(gameController.getPaused());
        assertFalse(gameController.gameover);
        assertEquals(0, gameController.score);
        assertEquals(0, gameController.wormCounter);
        assertEquals(-1, gameController.abillityAktiv);
    }

    @Test
    public void testRestart() {
    	
        gameController.restart();
        assertFalse(gameController.started);
        assertFalse(gameController.getPaused());
        assertFalse(gameController.gameover);
        assertEquals(0, gameController.score);
        assertEquals(0, gameController.wormCounter);
        assertEquals(-1, gameController.abillityAktiv);
    }

    @Test
    public void testUpdateWhenNotStarted() {
        
    	gameController.update();
    	
        assertFalse(gameController.started);
        assertFalse(gameController.getPaused());
        assertFalse(gameController.gameover);
    }

    @Test
    public void testUpdateWhenPaused() {
        
    	gameController.started = true;
        gameController.setPaused(true);
        gameController.update();
   
        assertTrue(gameController.getPaused());
    }

    @Test
    public void testUpdateWhenGameover() {
    	
        gameController.started = true;
        gameController.gameover = true;
        gameController.update();
        assertTrue(gameController.gameover);
    }

    @Test
    public void testMoveObjectsAndCheckCollisions() {
    	
        gameController.started = true;
        gameController.update();
    }

    @Test
    public void testGetRenders() {
        ArrayList<Render> renders = gameController.getRenders();
        assertNotNull(renders);
        assertFalse(renders.isEmpty());
    }

    @Test
    public void testCollisionWithPipe() {
        gameController.started = true;
        gameController.getPipes().add(new Pipe("north")); 
        gameController.getPipes().get(0).x = gameController.getBird().x;
        gameController.getPipes().get(0).y = gameController.getBird().y;

        gameController.update();
        assertTrue(gameController.gameover);
        assertTrue(gameController.getBird().dead);
    }

    @Test
    public void testCollisionWithWorm() {
        gameController.started = true;
        Worm worm = new Worm(new Pipe("south"));
        worm.x = gameController.getBird().x;
        worm.y = gameController.getBird().y;
        gameController.getWorms().add(worm);

        gameController.update();
        assertTrue(worm.collectet);
        assertEquals(1, gameController.wormCounter);
    }

   
}
