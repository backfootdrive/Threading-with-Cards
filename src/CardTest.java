

import org.junit.Test;
import static org.junit.Assert.*;

public class CardTest {
    private int num = 1;

    @Test
    public void testValue() {
        Card card = new Card(num);
        assertTrue(card.getValue() == num);
    }
}
