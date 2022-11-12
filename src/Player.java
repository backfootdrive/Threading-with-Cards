import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class Player extends Thread{
    private static ArrayList<Player> allPlayers = new ArrayList<Player>();

    private int id;
    private CardDeck leftDeck;
    private CardDeck rightDeck;
    private volatile ArrayList<Card> hand;
    private volatile boolean done = false;
    private static int idCounter = 1;
    private FileWriter writer;

    public static ArrayList<Player> getAllPlayers () {return allPlayers;}

    public Player (CardDeck left, ArrayList<Card> hand, CardDeck right) {
        this.id = idCounter++;
        this.leftDeck = left;
        this.rightDeck = right;
        this.hand = hand;
        allPlayers.add(this);

        try {
            File myObj = new File("player"+id+"_output.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("Ouptut file already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        String initialHand = "player " + id + " initial hand ";
        for (Card card:hand) {
            initialHand += card.getValue() + " ";
        }

        try {
            writer = new FileWriter("player"+id+"_output.txt");
            writer.write(initialHand+"\n");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public synchronized void drawNewCard() {
        if (leftDeck.isEmpty()) {
            return;
        }

        for (int i=0; i < hand.size(); i++) {
            if (hand.get(i).getValue() != id) {
                try {
                    writer.write("Player "+id+" discards a "+hand.get(i).getValue()+" to deck "+rightDeck.getId()+"\n");
                    System.out.println("Player "+id+" discards a "+hand.get(i).getValue()+" to deck "+rightDeck.getId());
                } catch (IOException e) {
                    System.out.println("write failed");
                    e.printStackTrace();
                }
                rightDeck.addToDeck(hand.remove(i));
                break;
            }
        }

        hand.add(leftDeck.removeFromDeck());
        try {
            writer.write("Player "+id+" draws "+hand.get((hand.size())-1).getValue()+" from deck "+leftDeck.getId()+"\n");
            System.out.println("Player "+id+" draws "+hand.get((hand.size())-1).getValue()+" from deck "+leftDeck.getId());
        } catch (IOException e) {
            System.out.println("write failed");
            e.printStackTrace();
        }
    }

    public void run () {
        while (!done){
            int firstValue = hand.get(0).getValue();
            if (hand.stream().allMatch(x -> x.getValue() == firstValue)) {
                allPlayers.forEach((player) -> player.notifyFinished(id));
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (done == false) {
                drawNewCard();
            }
        }
        String finalHand = "player " + id + " final hand ";
        for (Card card:hand) {
            finalHand += card.getValue() + " ";
        }
        try {
            writer.write(finalHand);
            writer.close();
        } catch (IOException e) {
            System.out.println("failed to close file");
        }
    }

    public void notifyFinished(int idWinner) {
        done = true;
        try {
            writer.write("Player "+idWinner+" has informed player "+id+" that player "+idWinner+" has won\n");
        } catch (IOException e) {
            System.out.println("write failed");
            e.printStackTrace();
        }
    }
}
