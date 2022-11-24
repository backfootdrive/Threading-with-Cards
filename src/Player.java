

import java.util.ArrayList;

import java.io.File;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

/**
 * A Runnable class that when run, draws and discards cards from the associated decks until their hand
 * has 4 cards of the same value.
 * 
 * @author Kamran Haque, Tyler Allen
 * @version 1.0
 * 
 */
public class Player implements Runnable{
	
	// instance attributes
    private int id;
    private CardGame game;
    private CardDeck leftDeck;
    private CardDeck rightDeck;
    private volatile ArrayList<Card> hand;
    private FileWriter writer;
    
    /**
     * Gets the Id of player.
     * 
     * @return The Id of the player.
     */
    public int getId() {return id;}
    
    /**
     * Class constructor for Player class
     * 
     * @param id An integer value.
     * @param left The CardDeck to draw cards from.
     * @param hand An ArrayList of Card objects.
     * @param right The CardDeck to discard cards to.
     * @param game an instance of CardGame.
     */
    public Player (int id, CardDeck left, ArrayList<Card> hand, CardDeck right, CardGame game) {
        this.id = id;
        this.leftDeck = left;
        this.rightDeck = right;
        this.hand = hand;
        this.game = game;
        
        // creates a string of the starting hand
        String initialHand = "player " + id + " initial hand";
        for (Card card:hand) {
            initialHand += " " + card.getValue();
        }

        try {
        	// creates a new file if one does not already exist
            File newFile = new File("./gameOutputs/player"+id+"_output.txt");
            if (newFile.createNewFile()) {
                System.out.println("File created: " + newFile.getName());
            } else {
                // System.out.println("Ouptut file already exists.");
            }
            
            // outputs the initial hand to file and console
            System.out.println(initialHand);
            writer = new FileWriter("./gameOutputs/player"+id+"_output.txt");
            writer.write(initialHand+"\n");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    /**
     * A method that discards a card to the right deck and draws a new one from the left deck.
     * 
     * @return True if the method succeeded.
     */
    public synchronized boolean drawNewCard() {
    	// checks if the left deck is empty
        while (leftDeck.isEmpty()){
        	// interrupts the current thread if another has finished
            if (Thread.currentThread().isInterrupted()){return false;}
        }
        try {
        	for (int i=0; i < hand.size(); i++) {
            	if (hand.get(i).getValue() != id) {
            		// discards a card from hand
                	Card oldCard = hand.get(i);
                	rightDeck.addToDeck(hand.remove(i));
                	// draws a card to hand
                	Card newCard = leftDeck.removeFromDeck();
                	hand.add(newCard);
                	
                	// output the card discarded to file and console
                	writer.write("player "+id+" discards a "+oldCard.getValue()+" to deck "+rightDeck.getId()+"\n");
                	System.out.println("player "+id+" discards a "+oldCard.getValue()+" to deck "+rightDeck.getId());
                	// outputs the card drawn to file and console
                	writer.write("player "+id+" draws "+newCard.getValue()+" from deck "+leftDeck.getId()+"\n");
                	System.out.println("player "+id+" draws "+newCard.getValue()+" from deck "+leftDeck.getId());
                	
                	// outputs the current hand
                	String currentHand = "player " + id + " current hand";
                	for (Card card:hand) {currentHand += " " + card.getValue();}
                	writer.write(currentHand+"\n");
                	break;
            	}
        	}
    	} catch (IOException e) {
    		System.out.println("write failed!");
    		e.printStackTrace();
    	}
        return true;
    }
    
    /**
     * Checks if the player has won.
     * 
     * @return True if hand has 4 cards of the same value.
     */
    public boolean checkWin() {
    	int firstValue = hand.get(0).getValue();
    	// checks if all Cards in hand match the value of first card
    	if (hand.stream().allMatch(x -> x.getValue() == firstValue)) {
    		// notifies the game instance a player has won
            game.notifyAllFinished(id);
            return true;
    	}
    	return false;
    }
    
    /**
     * The run method, checks if player has won, draws new card if not.
     */
    public void run () {
    	// checks if thread has been interrupted
        while (!Thread.currentThread().isInterrupted()){
        	if (checkWin()) {
        		// if thread has won, interrupt itself
                Thread.currentThread().interrupt();
        	} else {
        		// draw a new card
        		drawNewCard();
        	}
        }
    }
    
    /**
     * Prints the players final hand to file and console and exits.
     * 
     * @param idWinner The Id of the winning player.
     */
    public synchronized void notifyFinished(int idWinner){
        try{
            if (idWinner != id) {
                writer.write("player "+idWinner+" has informed player "+id+" that player "+idWinner+" has won\n");
            } else {
                writer.write("player "+id+" wins\n");
                System.out.println("player "+id+" wins");
            }
            // outputs final hand to file and console
            String finalHand = "player " + id + " final hand";
            for (Card card:hand) {
                finalHand += " " + card.getValue();
            }
            System.out.println(finalHand);
            writer.write("player "+id+" exits\n");
            writer.write(finalHand+"\n");
            writer.close();
        } catch (IOException e){
            System.out.println("write failed!");
            e.printStackTrace();
        }
    }
}