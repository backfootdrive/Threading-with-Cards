package test.java;

import main.java.CardGame;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.File;

public class CardGameTest {


    @Test 
    public void testPack() {
        CardGame game = new CardGame();
        assertTrue(game.readPack("./src/test/resources/valid4.txt", 4));
    }

    @Test 
    public void testPack2() {
        CardGame game = new CardGame();
        assertTrue(game.readPack("./src/test/resources/valid16.txt", 16));
    }

    @Test 
    public void testPack3() {
        CardGame game = new CardGame();
        assertTrue(game.readPack("./src/test/resources/valid30.txt", 30));
    }

    @Test 
    public void testPack4() {
        CardGame game = new CardGame();
        assertFalse(game.readPack("./src/test/resources/invalid16-1.txt", 16));
    }

    @Test 
    public void testPack5() {
        CardGame game = new CardGame();
        assertFalse(game.readPack("./src/test/resources/invalid16-2.txt", 16));
    }

    @Test 
    public void testPack6() {
        CardGame game = new CardGame();
        assertFalse(game.readPack("./src/test/resources/invalid16-3.txt", 16));
    }

    @Test 
    public void testPack7() {
        CardGame game = new CardGame();
        assertFalse(game.readPack("./src/test/resources/invalid16-4.txt", 16));
    }

    @Test
    public void testGame() {
        CardGame game = new CardGame();
        game.setUpGame("./src/test/resources/valid4.txt", 4);
        game.startGame();
    }

    @Test
    public void testGame2() {
        CardGame game = new CardGame();
        game.setUpGame("./src/test/resources/valid16.txt", 16);
        game.startGame();
    }

    @Test
    public void testGame3() {
        CardGame game = new CardGame();
        game.setUpGame("./src/test/resources/valid30.txt", 30);
        game.startGame();
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
 