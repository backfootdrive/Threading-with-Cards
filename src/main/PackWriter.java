package main;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PackWriter {
    private int number;
    public static void main (String[] args) throws InterruptedException {
        PackWriter packWriter = new PackWriter();
        packWriter.createPack();
    }

    public void getNumber() {
        // Declare Scanner object
        Scanner in = new Scanner(System.in);
 
        // Gets the number of players
        while (true) {
            try {
                // Get the Int input
                System.out.print("Enter your number: ");
                number = in.nextInt();
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        in.close();
    }

    public void createPack() {
        getNumber();
        try {
            File myObj = new File(number+".txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
                FileWriter writer = new FileWriter(number+".txt");
                for (int i=0; i<3; i++){writer.write((number-1)+"\n");}
                for (int i=1; i<=((number*7)+(number-3)); i++) {
                    if (i == ((number*7)+(number-3))){
                        writer.write(Integer.toString(i));
                    } else {
                        writer.write(i+"\n");
                    }
                }
            writer.close();
            } else {
                System.out.println("Ouptut file already exists.");
            }
        } catch (IOException e){}

    }
}
