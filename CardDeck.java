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
    public synchronized Card drawingCard() {
        if (size == 0) {
            return null;
        }
        Card drawnCard = cards[0];
        for (int i = 1; i < size; i++) {
            cards[i - 1] = cards[i];
        }
        size--;
        cards[size] = null;
        return drawnCard;
    }
    public synchronized int getSize() {
        return size;
    }
    public synchronized String getContent() {
        String content = " ";
        for (int i = 0; i < size; i++) {
            content += cards[i].getValue();
            content += " ";
            
        }
        return content;

    }
        
    
    

}