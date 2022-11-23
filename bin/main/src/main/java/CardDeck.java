package main.java;


import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CardDeck {

    private int id;
    private volatile ArrayList<Card> deck = new ArrayList<Card>();
    private static int idCounter = 1;
    
    public int getId() {return id;}
    public ArrayList<Card> getDeck() {return deck;}

    public void setDeck (ArrayList<Card> deck) {this.deck = deck;}

    public CardDeck(){
        this.id = idCounter++;
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

    public synchronized void outputContents() {
        try {
            File newFile = new File("./gameOutputs/deck"+id+"_output.txt");
            if (newFile.createNewFile()) {
                System.out.println("File created: " + newFile.getName());
            } else {
                // System.out.println("Ouptut file already exists.");
            }
            FileWriter writer = new FileWriter("./gameOutputs/deck"+id+"_output.txt");
            writer.write(this.toString());
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String toString () {
        String contents = "deck " + id + "contents";
        for (Card card: deck) {contents += " " + card.getValue();}
        return contents;
        //return deck.toArray().toString();
    }

}
