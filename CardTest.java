import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class CardTest {
    @Test
    void storeCorrectValue() {
        for ( int value = 0; value <= 1; value++) {
            boolean reject = false;
            Card card = new Card (value);
            assertEquals(value, card.getValue(), "value not stored correctly");
        }
    }
    @Test
    void valueBelowZero() {
        boolean rej = false;
        try {new Card(-1);

        } catch (IllegalArgumentException e) {rej = true;

        }
        assertTrue(rej, "Cards below zero are rejected. ");
    }
}

