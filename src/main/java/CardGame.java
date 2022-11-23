package main.java;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class CardGame {

    private int winner;
    private ArrayList<Card> cards;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Thread> threads = new ArrayList<Thread>();
    private ArrayList<CardDeck> decks = new ArrayList<CardDeck>();

    public static void main (String[] args) throws FileNotFoundException, Exception{
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

    public boolean readPack (String path, int numPlayers) {

        // Declare Scanner object
        cards = new ArrayList<Card>();
        Scanner fileReader = null;

        try {
            File myObj = new File(path);
            fileReader = new Scanner(myObj);
            while (fileReader.hasNextLine()) {
                int data = fileReader.nextInt();
                if (data <= 0) {
                    throw new Exception("Pack contains non-positive integer");
                }
                cards.add(new Card(data));
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            // e.printStackTrace();
        } catch (InputMismatchException e) {
            // e.printStackTrace();
        } catch (NoSuchElementException e) {
            // pack contains empty new line on the end
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
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

    public boolean setUpGame (String path, int numPlayers) {

        ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();

        if (numPlayers <= 0) {
            return false;
        }
        if (readPack(path, numPlayers) == false){
            return false;
        }
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
        new File("./gameOutputs").mkdir();
        for (int i = 0; i < numPlayers; i++) {
            try {
                players.add(new Player(decks.get(i), hands.get(i), decks.get(i+1), this));
            } catch (IndexOutOfBoundsException e) {
                players.add(new Player(decks.get(i), hands.get(i), decks.get(0), this));
            }
        }
        for (Player player: players) {threads.add(new Thread(player));}
        System.out.println("hello");
        return true;
    }

    public void startGame () {
        threads.forEach((thread) -> thread.start());
    }

    public void notifyAllFinished (int id) {
        threads.forEach((thread) -> thread.interrupt());
        for (Player player: players) {player.notifyFinished(id);}
        for (CardDeck deck: decks) {deck.outputContents();}  
        winner = id;     
    }

    public int getWinner() {return winner;}
}

// n players
// n decks
// 4 card hand
// rest of cards split between deck
// 8n pack
