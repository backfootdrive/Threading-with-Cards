
import org.junit.Test;   
import static org.junit.Assert.*;
import java.io.File;

public class CardGameTest {

    @Test 
    public void testPack() {
        CardGame game = new CardGame();
        assertTrue(game.readPack("valid4.txt", 4));
    }

    @Test 
    public void testPack2() {
        CardGame game = new CardGame();
        assertTrue(game.readPack("valid16.txt", 16));
    }

    @Test 
    public void testPack3() {
        CardGame game = new CardGame();
        assertTrue(game.readPack("valid30.txt", 30));
    }

    @Test 
    public void testPack4() {
        CardGame game = new CardGame();
        assertFalse(game.readPack("invalid16-1.txt", 16));
    }

    @Test 
    public void testPack5() {
        CardGame game = new CardGame();
        assertFalse(game.readPack("invalid16-2.txt", 16));
    }

    @Test 
    public void testPack6() {
        CardGame game = new CardGame();
        assertFalse(game.readPack("invalid16-3.txt", 16));
    }

    @Test 
    public void testPack7() {
        CardGame game = new CardGame();
        assertFalse(game.readPack("invalid16-4.txt", 16));
    }

    @Test
    public void testGame() {
        CardGame game = new CardGame();
        game.setUpGame("valid4.txt", 4);
        game.startGame();
        try {
        	for (Thread thread: game.getAllThreads()) {
        		thread.join();
        	}
        } catch (InterruptedException e) {
        	e.printStackTrace();
        }
    }

    @Test
    public void testGame2() {
        CardGame game = new CardGame();
        game.setUpGame("valid16.txt", 16);
        game.startGame();
        try {
        	for (Thread thread: game.getAllThreads()) {
        		thread.join();
        	}
        } catch (InterruptedException e) {
        	e.printStackTrace();
        }
    }

    @Test
    public void testGame3() {
        CardGame game = new CardGame();
        game.setUpGame("valid30.txt", 30);
        game.startGame();
        try {
        	for (Thread thread: game.getAllThreads()) {
        		thread.join();
        	}
        } catch (InterruptedException e) {
        	e.printStackTrace();
        }
    }

    @Test
    public void getPath() {
        try {
            String currentPath = new File(".").getCanonicalPath();
            System.out.println("Current dir:" + currentPath);
        
            String currentDir = System.getProperty("user.dir");
            System.out.println("Current dir using System:" + currentDir);
        } catch (Exception e) {}
    }
}
 