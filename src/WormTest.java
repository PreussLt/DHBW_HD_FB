import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WormTest {

    private Worm worm;
    private Pipe pipe;

    @BeforeEach
    public void setUp() {
        pipe = new Pipe("south");
        worm = new Worm(pipe);
    }

    @Test
    public void testInitialState() {
        assertEquals(pipe.x, worm.x);
        assertTrue(worm.y >= pipe.y + 400 && worm.y <= pipe.y + 500); 
        assertEquals(pipe.speed, worm.speed);
        assertEquals(pipe.width, worm.width);
        assertEquals(10, worm.height);
        assertFalse(worm.collectet);
    }

    @Test
    public void testReset() {
        worm.reset();
        assertEquals(66, worm.width);
        assertEquals(50, worm.height);
        assertTrue(worm.x > Controller.WIDTH); 
    }

    @Test
    public void testUpdate() {
        int initialX = worm.x;
        worm.update();
        assertEquals(initialX - worm.speed, worm.x);
    }

    @Test
    public void testCollides() {
        int birdX = worm.x - 10;
        int birdY = worm.y;
        int birdWidth = 20;
        int birdHeight = 20;

        assertTrue(worm.collides(birdX, birdY, birdWidth, birdHeight));
    }

    @Test
    public void testNoCollision() {
        int birdX = worm.x + worm.width + 10; 
        int birdY = worm.y;
        int birdWidth = 20;
        int birdHeight = 20;

        assertFalse(worm.collides(birdX, birdY, birdWidth, birdHeight));
    }

    @Test
    public void testGetRender() {
        Render render = worm.getRender();
        assertNotNull(render);
        assertEquals(worm.x, render.x);
        assertEquals(worm.y, render.y);
        assertNotNull(render.image);
    }
}
