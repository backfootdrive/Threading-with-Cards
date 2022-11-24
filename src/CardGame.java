

import java.util.ArrayList;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * A card game that utilises threads, where each player (thread) draws a card from the deck
 * to their left and discards one to the deck on their right, whoever has four cards of the same
 * numerical value in their hand wins.
 * 
 * @author Kamran Haque, Tyler Allen
 * @version 1.0
 *
 */
public class CardGame {
	
	// instance attributes
    private int winner;
    private int deckIdCounter = 1;
    private int playerIdCounter = 1;
    private ArrayList<Card> cards;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Thread> threads = new ArrayList<Thread>();
    private ArrayList<CardDeck> decks = new ArrayList<CardDeck>();

    /**
     * An executable main method that gets the number of players and the path of the pack
     * via the command-line.
     * 
     * @param args args
     */
    public static void main (String[] args) {
        String path;
        int numPlayers;
        CardGame game = new CardGame();

        // Declare Scanner object
        Scanner in = new Scanner(System.in);
        // Gets the number of players
        try {
            // Get the Int input
            System.out.print("Enter number of players: ");
            numPlayers = Integer.parseInt(in.nextLine());
            System.out.print("Enter filename of pack: ");
            path = in.nextLine();
            System.out.println(path);

            game.setUpGame(path, numPlayers);
            game.startGame();

        } catch (NumberFormatException e) {
            System.out.println("Not a number");
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            in.close();
        }
    }

    /**
     * A method which takes the path of the pack, then reads the lines of the file as integers
     * to create new Card objects.
     * 
     * @param path The location of the text file as a String.
     * @param numPlayers The number of players.
     * 
     * @return True if pack is valid, false if pack is invalid.
     */
    public boolean readPack (String path, int numPlayers) {
    	// creates an array of Card objects
        cards = new ArrayList<Card>();
        Scanner fileReader = null;

        try {
        	// Declare Scanner object
            File myObj = new File(path);
            fileReader = new Scanner(myObj);
            while (fileReader.hasNextLine()) {
            	// reads number from file
                int data = fileReader.nextInt();
                if (data <= 0) {
                    throw new Exception("Pack contains non-positive integer");
                }
                // creates a new Card object and adds it to the list of cards
                cards.add(new Card(data));
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File does not exist");
            // e.printStackTrace();
            return false;
        } catch (InputMismatchException e) {
            System.out.println("File contains non-integer");
            // e.printStackTrace();
            return false;
        } catch (NoSuchElementException e) {
            // pack contains empty new line on the end
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
        	// ensure the scanner is closed
            if (fileReader != null) {
                fileReader.close();
            }
        }
        
        // Checks to see if pack is the right length
        if (cards.size() == (8 * numPlayers)) {
            return true;
        } else if (cards.size() > (8 * numPlayers)) {
            System.out.println("Input pack invalid: Too long");
        } else {
            System.out.println("Input pack invalid: Too short");
        }
        return false;
    }
    
    /**
     * A method to distribute the cards among the hands and decks.
     * 
     * @param numPlayers The number of players.
     * 
     * @return ArrayList of player hands.
     */
    public ArrayList<ArrayList<Card>> distributeCards(int numPlayers) {
    	
    	ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();
    	
    	for (int i=0; i<numPlayers; i++) {
            hands.add(new ArrayList<Card>());
            decks.add(new CardDeck(deckIdCounter++));
        }
    	
    	// first distributes cards to the hands in a round robin fashion
        for (int i=0; i<4; i++) {
            for (ArrayList<Card> hand:hands) {
                hand.add(0, cards.remove(0));
            }
        }
        
        // distributes remaining cards to the decks in a round robin fashion
        for (int i=0; i<4; i++) {
            for (CardDeck deck:decks) {
                deck.getDeck().add(0, cards.remove(0));
            }
        }
        
        return hands;
    }

    /**
     * A method which sets up the card game, reads the pack, creates the hands and decks, 
     * distributes the cards and initialises the player threads.
     * 
     * @param path The location of the text file as a String.
     * @param numPlayers The number of players.
     * 
     * @return True if pack is valid, false if pack is invalid.
     */
    public boolean setUpGame (String path, int numPlayers) {
    	// cannot have negative number of players
        if (numPlayers <= 0) {
            return false;
        }
        // returns false if input pack is invalid
        if (readPack(path, numPlayers) == false){
            return false;
        }
        
        ArrayList<ArrayList<Card>> hands;
        hands = distributeCards(numPlayers);
        
        // creates a new directory for the output files if it does not exist
        new File("./gameOutputs").mkdir();
        for (int i = 0; i < numPlayers; i++) {
            try {
                players.add(new Player(playerIdCounter, decks.get(i), hands.get(i), decks.get(i+1), this));
            } catch (IndexOutOfBoundsException e) {
            	// for the last player wraps to the first deck
                players.add(new Player(playerIdCounter, decks.get(i), hands.get(i), decks.get(0), this));
            }
            playerIdCounter++;
        }
        
        // checks if a player has a winning hand before the game has started
        for (Player player: players) {
        	if (player.checkWin()) {return true;}}
        
        // creates Thread objects using Player objects (Runnables)
        for (Player player: players) {threads.add(new Thread(player));}
        
        return true;
    }
    
    /**
     * Method to interrupt all threads once a thread has won and output deck contents.
     * 
     * @param id Id of winning player.
     */
    public void notifyAllFinished (int id) {
    	// interrupts all running threads
        threads.forEach((thread) -> thread.interrupt());
        // tells every player object that another player has won
        for (Player player: players) {player.notifyFinished(id);}
        // tells decks to output their contents
        for (CardDeck deck: decks) {deck.outputContents();}
        // sets the id of the winning thread
        winner = id;
    }

    /**
     * Starts the player threads.
     */
    public void startGame () {
        threads.forEach((thread) -> thread.start());
    }
    
    /**
     * Get the Id of the winning player.
     * 
     * @return Id of winning player.
     */
    public int getWinner() {return winner;}
    
    /**
     * Get the array of Thread objects.
     * 
     * @return ArrayList of Thread objects.
     */
    public ArrayList<Thread> getAllThreads() {return threads;}
    
    /**
     * Get the array of Player objects.
     * 
     * @return ArrayList of Player objects.
     */
    public ArrayList<Player> getAllPlayers() {return players;}
}

// n players
// n decks
// 4 card hand
// rest of cards split between deck
// 8n pack