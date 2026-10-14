public class CardDeck {

    private final Card[] cards; // array that stores the card objects
    private int size; //variable that holds the number of cards in the deck

    public CardDeck(int capacity) {//constructor
        if (capacity <= 0) {//checking that the capacity of the deck is valid
            throw new IllegalArgumentException("Capacity has to be greater than 0");

        }
        cards = new Card[capacity];//creating the array with the amount of indexes as the capacity
        size = 0;//deck always starts with no cards

    }
    public synchronized void addingCard(Card card) {//method that add a card to a deck, only one thread can use this at a time
        if (size == cards.length) {//checking if the deck is already full
            throw new IllegalStateException("the deck is already full");

        }
        cards[size] = card;//places the new card after the last one before it
        size++; //increasing the deck size by 1 so that a new card can be added
    }
    

}