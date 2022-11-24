import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class CardDeckTest {
	int id;
	ArrayList<Card> cards;
	
	@Before
	public void beforeTest() {
		id = 1;
		cards = new ArrayList<Card>();
		cards.add(new Card(45));
		cards.add(new Card(7));
		cards.add(new Card(13));
		cards.add(new Card(935));
		cards.add(new Card(267));
		cards.add(new Card(190));
		cards.add(new Card(3));
		cards.add(new Card(65));
		cards.add(new Card(519));
		cards.add(new Card(740));
	}

	@Test
	public void test() {
		CardDeck deck = new CardDeck(id);
		assertEquals(1, deck.getId());
	}
	
	@Test 
	public void test2() {
		CardDeck deck = new CardDeck(id);
		deck.setDeck(cards);
		assertEquals(cards, deck.getDeck());
	}
	
	@Test
	public void test3() {
		CardDeck deck = new CardDeck(id);
		deck.setDeck(cards);
		assertEquals(cards.size(), deck.getNumCards());
	}
	
	@Test
	public void test4() {
		CardDeck deck = new CardDeck(id);
		deck.addToDeck(cards.get(3));
		assertEquals(cards.get(3), deck.getDeck().get(deck.getNumCards()-1));
	}
	
	@Test
	public void test5() {
		CardDeck deck = new CardDeck(id);
		deck.setDeck(cards);
		Card removedCard = deck.removeFromDeck();
		assertEquals(45, removedCard.getValue());
	}
	
	@Test
	public void test6() {
		CardDeck deck = new CardDeck(id);
		assertTrue(deck.isEmpty());
		deck.setDeck(cards);
		assertFalse(deck.isEmpty());
	}
	
	@Test
	public void test7() {
		CardDeck deck = new CardDeck(id);
		deck.addToDeck(cards.get(1));
		deck.addToDeck(cards.get(3));
		deck.addToDeck(cards.get(5));
		deck.addToDeck(cards.get(7));
		// System.out.println(deck.getId());
		assertEquals("deck 1 contents 7 935 190 65", deck.toString());
	}
}
