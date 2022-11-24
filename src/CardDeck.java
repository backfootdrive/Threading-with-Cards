
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A class to store card objects.
 * 
 * @author Kamran Haque, Tyler Allen
 * @version 1.0
 *
 */
public class CardDeck {
	
	// instance attributes
    private int id;
    private volatile ArrayList<Card> deck = new ArrayList<Card>();
    
    // getter method(s)
    /**
     * Get the Id of the CardDeck.
     * 
     * @return Id of CardDeck.
     */
    public int getId() {return id;}
    /**
     * Get the array of Card objects.
     * 
     * @return ArrayList of Card objects.
     */
    public ArrayList<Card> getDeck() {return deck;}
    /**
     * Get the number of cards in the deck.
     * 
     * @return Number of cards in the CardDeck.
     */
    public int getNumCards() {return deck.size();}
    
    // setter method(s)
    /**
     * Sets the Cards in the deck.
     * 
     * @param deck An array of Card Objects.
     */
    public void setDeck (ArrayList<Card> deck) {this.deck = deck;}

    /**
     * The constructor method for the class, sets the id of the CardDeck.
     * 
     * @param id Id of CardDeck.
     */
    public CardDeck(int id){
        this.id = id;
    }

    /**
     * A method to add a card to the deck.
     * 
     * @param card A Card object.
     */
    public synchronized void addToDeck(Card card) {
    	// adds card to deck arraylist
        deck.add(card);
    }
    
    /**
     * A method to remove the first card from the deck.
     * 
     * @return Card object removed from deck.
     */
    public synchronized Card removeFromDeck() {
    	if (isEmpty())
    		return null;
    	// returns removed card
        return deck.remove(0);
    }
    
    /**
     * A method to check if the deck is empty.
     *
     * @return True, if deck is empty.
     */
    public synchronized boolean isEmpty() {
        return deck.isEmpty();
    }
    
    /**
     * Outputs contents of the deck to a text file.
     */
    public synchronized void outputContents() {
        try {
        	// creates new file if one does not already exist
            File newFile = new File("./gameOutputs/deck"+id+"_output.txt");
            if (newFile.createNewFile()) {
                System.out.println("File created: " + newFile.getName());
            } else {
                // System.out.println("Output file already exists.");
            }
            FileWriter writer = new FileWriter("./gameOutputs/deck"+id+"_output.txt");
            writer.write(this.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Return the contents of the CardDeck as a string.
     * 
     * @return Contents of the CardDeck
     */
    public String toString () {
        String contents = "deck " + id + " contents";
        for (Card card: deck) {contents += " " + card.getValue();}
        return contents;
        //return deck.toArray().toString();
    }
}