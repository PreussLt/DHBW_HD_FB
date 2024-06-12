import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PipeTest {

    private Pipe pipeSouth;
    private Pipe pipeNorth;

    @BeforeEach
    public void setUp() {
        pipeSouth = new Pipe("south");
        pipeNorth = new Pipe("north");
    }

    @Test
    public void testInitialState() {
        assertEquals("south", pipeSouth.orientation);
        assertEquals(66, pipeSouth.width);
        assertEquals(400, pipeSouth.height);
        assertTrue(pipeSouth.x > Controller.WIDTH); 
    }

    @Test
    public void testReset() {
        pipeSouth.reset();
        assertEquals(66, pipeSouth.width);
        assertEquals(400, pipeSouth.height);
        assertTrue(pipeSouth.x > Controller.WIDTH);
        assertTrue(pipeSouth.y < 0 && pipeSouth.y > -pipeSouth.height - 60);
    }

    @Test
    public void testUpdate() {
        int initialX = pipeSouth.x;
        pipeSouth.update();
        assertEquals(initialX - pipeSouth.speed, pipeSouth.x);
    }

    @Test
    public void testCollidesWithSouthPipe() {
        pipeSouth.y = 100; 
        int birdX = pipeSouth.x - 10;
        int birdY = pipeSouth.y + pipeSouth.height - 10;
        int birdWidth = 20;
        int birdHeight = 20;

        assertTrue(pipeSouth.collides(birdX, birdY, birdWidth, birdHeight));
    }

    @Test
    public void testCollidesWithNorthPipe() {
        pipeNorth.y = 100; 
        int birdX = pipeNorth.x - 10;
        int birdY = pipeNorth.y - 10;
        int birdWidth = 20;
        int birdHeight = 20;

        assertTrue(pipeNorth.collides(birdX, birdY, birdWidth, birdHeight));
    }

    @Test
    public void testNoCollision() {
        pipeSouth.y = 100; 
        int birdX = pipeSouth.x + pipeSouth.width + 10; 
        int birdY = pipeSouth.y + pipeSouth.height - 10;
        int birdWidth = 20;
        int birdHeight = 20;

        assertFalse(pipeSouth.collides(birdX, birdY, birdWidth, birdHeight));
    }

    @Test
    public void testGetRender() {
        Render render = pipeSouth.getRender();
        assertNotNull(render);
        assertEquals(pipeSouth.x, render.x);
        assertEquals(pipeSouth.y, render.y);
        assertNotNull(render.image);
    }
}
