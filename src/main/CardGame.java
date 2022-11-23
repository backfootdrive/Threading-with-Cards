package main;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class CardGame {
    private int numPlayers;
    private ArrayList<Card> cards;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Thread> threads = new ArrayList<Thread>();
    private ArrayList<CardDeck> decks = new ArrayList<CardDeck>();

    public static void main (String[] args) throws InterruptedException {
        // Reads cards from pack file
        CardGame game = new CardGame();
        game.setUpGame();
        game.startGame();

    }

    public void getNumPlayers() {
        // Declare Scanner object
        Scanner in = new Scanner(System.in);
 
        // Gets the number of players
        while (true) {
            try {
                // Get the Int input
                System.out.print("Enter number of players: ");
                numPlayers = in.nextInt();
                in.nextLine();
                if (readPack(in)){break;}
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        in.close();
    }

    public boolean readPack (Scanner in){
        // Declare Scanner object
        cards = new ArrayList<Card>();
        
        while (true) {
            try {
                System.out.print("Enter filename of pack: ");
                String filename = in.nextLine();
                File myObj = new File(filename);
                Scanner fileReader = new Scanner(myObj);
                while (fileReader.hasNextLine()) {
                    int data = fileReader.nextInt();
                    // System.out.println(data);
                    if (data <= 0) {throw new Exception("Pack contains non-positive integer");}
                    cards.add(new Card(data));
                }
                fileReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("File does not exist");
            } catch (InputMismatchException e) {
                System.out.println("Invalid pack: Contains invalid input");
            } catch (NoSuchElementException e) {
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            if (cards.size() == (8 * numPlayers)) {
                return true;
            } else if (cards.size() > (8 * numPlayers)) {
                System.out.println("Input pack invalid: Too long");
            } else {
                System.out.println("Input pack invalid: Too short");
            }

            return false;
        }
    }

    public void setUpGame () {
        ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();

        getNumPlayers();

        for (int i=0; i<numPlayers; i++) {
            hands.add(new ArrayList<Card>());
            decks.add(new CardDeck());
        }
        for (int i=0; i<4; i++) {
            for (ArrayList<Card> hand:hands) {
                hand.add(cards.remove(0));
            }
        }
        for (int i=0; i<4; i++) {
            for (CardDeck deck:decks) {
                deck.addToDeck(cards.remove(0));;
            }
        }
        new File("./outputs").mkdir();
        for (int i = 0; i < numPlayers; i++) {
            try {
                players.add(new Player(decks.get(i), hands.get(i), decks.get(i+1), this));
            } catch (IndexOutOfBoundsException e) {
                players.add(new Player(decks.get(i), hands.get(i), decks.get(0), this));
            }
        }
        for (Player player: players) {threads.add(new Thread(player));}
    }

    public void startGame () {
        threads.forEach((thread) -> thread.start());
    }

    public void notifyAllFinished (int id) {
        threads.forEach((thread) -> thread.interrupt());
        for (Player player: players) {player.notifyFinished(id);}
        for (CardDeck deck: decks) {deck.outputContents();}       
    }
}

// n players
// n decks
// 4 card hand
// rest of cards split between deck
// 8n pack
