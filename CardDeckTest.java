import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CardDeckTest {
    @Test
    void correctOrderWhenDrawing() {//testing that cards are drawn in the correct FIFO order, this also means we are checking if they are added in the right order.
        CardDeck deck = new CardDeck(4);//creating deck with space for 4 cards in

        Card first = new Card(1);//making the card objs
        Card sec = new Card(2);
        Card third = new Card(3);
        Card fourth = new Card(4);
        
        deck.addingCard(first);//adding the cards to the deck 
        deck.addingCard(sec);
        deck.addingCard(third);
        deck.addingCard(fourth);

        assertSame(first, deck.drawingCard());//checking if they came out in the right order by calling the drawing card method 
        assertSame(sec, deck.drawingCard());
        assertSame(third, deck.drawingCard());
        assertSame(fourth, deck.drawingCard());
    }
    @Test
    void DeckSizeCheckerDrawing() {//checking the size updates when the cards are drawn out
        CardDeck deck = new CardDeck(4);//creating deck with space for 4 cards in

        Card first = new Card(1);//making the card objs
        Card sec = new Card(2);
        Card third = new Card(3);
        Card fourth = new Card(4);
        
        deck.addingCard(first);//adding the cards to the deck 
        deck.addingCard(sec);
        deck.addingCard(third);
        deck.addingCard(fourth);

        deck.drawingCard();//taking a card out then checking the size of the deck
        assertEquals(3, deck.getSize());
        deck.drawingCard();
        assertEquals(2, deck.getSize());
    }
    @Test
    void DeckSizeCheckerAdding() {//checking if the size updates when the cards are added
        CardDeck deck = new CardDeck(2);
        assertEquals(0, deck.getSize());
        deck.addingCard(new Card(3));
        assertEquals(1, deck.getSize());
        deck.addingCard(new Card(4));
        assertEquals(2, deck.getSize());
    }
    @Test 
    void invalidCapacity() {//checking if the deck accepts invalid capacities.
        for (int i = -1; i <= 0; i++) {
            try {
                new CardDeck(i);
                fail("This is not a valid capacity, should be greater than 0");
            } catch (IllegalArgumentException e) {
            
            }
        }
    }
    @Test
    void correctDeckId() {//checking if the setter method stores the correct id
        CardDeck deck = new CardDeck(2);

        deck.setDeckId(1);
        assertSame(1, deck.getDeckId());

    }

    @Test
    void acceptingNullCard() {//testing if the deck will take in null values
        CardDeck deck = new CardDeck(4);
        Card one = new Card(3);
        deck.addingCard(one);
        Card two = null;

        try {
            deck.addingCard(two);//trying to add null to the deck
            fail("card is rejected as it has no value");//if its accepted then it fails
        } catch (IllegalStateException e) {}

        assertSame(one, deck.drawingCard());//check the other card is sitll ok and if the deck size was affected
        assertEquals(0, deck.getSize());



    }

}
