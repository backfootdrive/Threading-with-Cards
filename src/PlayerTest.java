import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	int id;
	CardGame game;
	CardDeck deck;
	ArrayList<Card> hand;
	
	@Before
	public void playerSetup() {
		id = 1;
		game = new CardGame();
		deck = new CardDeck(1);
		for (int i=0; i < 4; i++) {
			for (int j=1; j < 11; j++) {
				deck.addToDeck(new Card(j));
			}
		}
		hand = new ArrayList<>();
		for (int i=1; i < 5; i++) {
			hand.add(new Card(i));
		}
	}
	
	@Test
	public void test() {
		Player player = new Player(id++, deck, hand, deck, game);
		assertEquals(1, player.getId());
	}
	
	@Test
	public void test2() {
		Player player = new Player(id++, deck, hand, deck, game);
		assertTrue(player.drawNewCard());
	}
	
	@Test
	public void test3() {
		Player player = new Player(id++, deck, hand, deck, game);
		player.run();
	}
	
	@Test
	public void test4() {
		Player player = new Player(id++, deck, hand, deck, game);
		player.notifyFinished(id);
	}
	
	@Test
	public void test5() {
		hand = new ArrayList<>();
		for (int i=1; i < 5; i++) {
			hand.add(new Card(1));
		}
		Player player = new Player(id++, deck, hand, deck, game);
		assertTrue(player.checkWin());
	}

}
