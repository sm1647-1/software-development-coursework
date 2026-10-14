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
    public synchronized Card drawingCard() {//created the method for drawing cards from a deck
        if (size == 0) {
            return null;//if there are no cards to draw, return null
        }
        Card drawnCard = cards[0];//saves the first card as drawnCard
        for (int i = 1; i < size; i++) {//moves every card in the deck one index to the left(move it forward)
            cards[i - 1] = cards[i];
        }
        size--;//decrease size of array by 1 
        cards[size] = null;// make last index of array null as is not in use.
        return drawnCard;//returns the card the player has picked up.
    }
    public synchronized int getSize() {//method that returns the number of cards in the deck
        return size;
    }
    public synchronized String getContent() {//method that returns the cards in the deck
        String content = " ";//created an empty string to add to
        for (int i = 0; i < size; i++) {//loop through the indexes that have cards in and add it to the string
            content += cards[i].getValue();
            content += " ";// add a space in between cards
            
        }
        return content;//return the string with the deck in. 

    }
        
    
    

}