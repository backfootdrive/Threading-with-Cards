
/**
 * A container for integer values.
 * 
 * @author Kamran Haque, Tyler Allen
 * @version 1.0
 *
 */
public class Card {
    private final int value;
    
    /**
     * Sets the value of the Card object.
     * 
     * @param value An integer
     */
    public Card (int value) {this.value = value;}
    
    /**
     * Gets the value of the Card object.
     * 
     * @return Integer value of Card object.
     */
    public synchronized int getValue () {return value;}
    
    /**
     * Returns the value of the Card object as a string.
     * 
     * @return Value of Card Object as string.
     */
    public String toString () {return Integer.toString(value);}
}