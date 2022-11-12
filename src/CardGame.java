import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class CardGame {
    public static void main (String[] args) throws InterruptedException {
        // Declare Scanner object
        Scanner in = new Scanner(System.in);
        int numPlayers;
        ArrayList<Card> cards = new ArrayList<Card>();

        // Gets the number of players
        while (true) {
            try {
                // Get the Int input
                System.out.print("Enter number of players: ");
                numPlayers = in.nextInt();
                in.nextLine();
                break;
            } catch (Exception e) {
            }
        }

        while (true) {
            try {
                System.out.print("Enter filename of pack: ");
                String filename = in.nextLine();
                File myObj = new File(filename);
                Scanner fileReader = new Scanner(myObj);
                while (fileReader.hasNextLine()) {
                    int data = fileReader.nextInt();
                    if (data <= 0) {
                        fileReader.close();
                        throw new Exception("Pack contains non-positive integer");
                    }
                    cards.add(new Card(data));
                    System.out.println(data);
                }
                fileReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("File does not exist");
            } catch (InputMismatchException e) {
                System.out.println("Invalid pack: Contains invalid input");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if (cards.size() == (8 * numPlayers)) {
                break;
            } else if (cards.size() > (8 * numPlayers)) {
                System.out.println("Input pack invalid: Too long");
            } else {
                System.out.println("Input pack invalid: Too short");
            }
        }

        in.close();

        // Reads cards from pack file
        setUpGame(numPlayers, cards);
        Player.getAllPlayers().forEach((thread) -> thread.start());
    }

    public static ArrayList<Card> readPack (int numPlayers){
        Scanner in = new Scanner(System.in);
        ArrayList<Card> cards = new ArrayList<Card>();
        
        while (true) {
            try {
                System.out.print("Enter filename of pack: ");
                String filename = in.nextLine();
                File myObj = new File(filename);
                Scanner fileReader = new Scanner(myObj);
                while (fileReader.hasNextLine()) {
                    int data = fileReader.nextInt();
                    if (data <= 0) {
                        fileReader.close();
                        throw new Exception("Pack contains non-positive integer");
                    }
                    cards.add(new Card(data));
                    System.out.println(data);
                }
                fileReader.close();
            } catch (FileNotFoundException e) {
                System.out.println("File does not exist");
            } catch (InputMismatchException e) {
                System.out.println("Invalid pack: Contains invalid input");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            if (cards.size() == (8 * numPlayers)) {
                break;
            } else if (cards.size() > (8 * numPlayers)) {
                System.out.println("Input pack invalid: Too long");
            } else {
                System.out.println("Input pack invalid: Too short");
            }
        }
        in.close();
        return cards;
    }

    public static void setUpGame (int numPlayers, ArrayList<Card> cards) {
        ArrayList<ArrayList<Card>> hands = new ArrayList<ArrayList<Card>>();
        ArrayList<CardDeck> decks = new ArrayList<CardDeck>();

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
            for (CardDeck deck:CardDeck.getAllDecks()) {
                deck.getDeck().add(cards.remove(0));
            }
        }
        for (int i = 0; i < numPlayers; i++) {
            try {
                new Player(decks.get(i), hands.get(i), decks.get(i+1));
            } catch (IndexOutOfBoundsException e) {
                new Player(decks.get(i), hands.get(i), decks.get(0));
            }
        }
    }
}

// n players
// n decks
// 4 card hand
// rest of cards split between deck
// 8n pack
