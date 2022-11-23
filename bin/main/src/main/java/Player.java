package main.java;


import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

public class Player implements Runnable{

    private int id;
    private CardGame game;
    private CardDeck leftDeck;
    private CardDeck rightDeck;
    private volatile ArrayList<Card> hand;
    private FileWriter writer;
    private static int idCounter = 1;

    public Player (CardDeck left, ArrayList<Card> hand, CardDeck right, CardGame game) {
        this.id = idCounter++;
        this.leftDeck = left;
        this.rightDeck = right;
        this.hand = hand;
        this.game = game;

        String initialHand = "player " + id + " initial hand";
        for (Card card:hand) {
            initialHand += " " + card.getValue();
        }

        try {
            File newFile = new File("./gameOutputs/player"+id+"_output.txt");
            if (newFile.createNewFile()) {
                System.out.println("File created: " + newFile.getName());
            } else {
                // System.out.println("Ouptut file already exists.");
            }
            writer = new FileWriter("./gameOutputs/player"+id+"_output.txt");
            writer.write(initialHand+"\n");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public synchronized void drawNewCard() throws IOException {
        while (leftDeck.isEmpty()){
            if (Thread.currentThread().isInterrupted()){return;}
        }
        
        for (int i=0; i < hand.size(); i++) {
            if (hand.get(i).getValue() != id) {
                Card oldCard = hand.get(i);
                rightDeck.addToDeck(hand.remove(i));
                Card newCard = leftDeck.removeFromDeck();
                hand.add(newCard);

                writer.write("player "+id+" discards a "+oldCard.getValue()+" to deck "+rightDeck.getId()+"\n");
                System.out.println("player "+id+" discards a "+oldCard.getValue()+" to deck "+rightDeck.getId());
                writer.write("player "+id+" draws "+newCard.getValue()+" from deck "+leftDeck.getId()+"\n");
                System.out.println("player "+id+" draws "+newCard.getValue()+" from deck "+leftDeck.getId());

                String currentHand = "player " + id + " current hand";
                for (Card card:hand) {currentHand += " " + card.getValue();}
                writer.write(currentHand+"\n");
                break;
            }
        }
    }

    public void run () {
        while (!Thread.currentThread().isInterrupted()){
            try {
                int firstValue = hand.get(0).getValue();
                if (hand.stream().allMatch(x -> x.getValue() == firstValue)) {
                    game.notifyAllFinished(id);
                } else {
                    drawNewCard();
                }
            } catch (IOException e) {
                System.out.println("write failed!");
                e.printStackTrace();
            }
        }
    }

    public synchronized void notifyFinished(int idWinner){
        try{
            if (idWinner != id) {
                writer.write("player "+idWinner+" has informed player "+id+" that player "+idWinner+" has won\n");
            } else {
                writer.write("player "+id+" wins\n");
                System.out.println("player "+id+" wins");
            }
            String finalHand = "player " + id + " final hand";
            for (Card card:hand) {
                finalHand += " " + card.getValue();
            }
            writer.write("player "+id+" exits\n");
            writer.write(finalHand+"\n");
            writer.close();
        } catch (IOException e){
            System.out.println("write failed!");
            e.printStackTrace();
        }
    }
}
