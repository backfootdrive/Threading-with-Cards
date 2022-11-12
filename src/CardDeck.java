import java.util.ArrayList;

public class CardDeck {
    private static int idCounter = 1;
    private static ArrayList<CardDeck> allDecks = new ArrayList<CardDeck>();

    private int id;
    private volatile ArrayList<Card> deck = new ArrayList<Card>();

    public int getId() {return id;}
    public ArrayList<Card> getDeck() {return deck;}
    public static ArrayList<CardDeck> getAllDecks() {return allDecks;}

    public void setDeck (ArrayList<Card> deck) {this.deck = deck;}

    public CardDeck(){
        this.id = idCounter++;
        allDecks.add(this);
    }

    public synchronized void addToDeck(Card card) {
        deck.add(card);
    }

    public synchronized Card removeFromDeck() {
        return deck.remove(0);
    }

    public synchronized boolean isEmpty() {
        return deck.isEmpty();
    }

    public String toString () {
        return deck.toArray().toString();
    }

}
