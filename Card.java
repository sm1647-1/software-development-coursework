public class Card { //defining the class for the cards
    
    private final int value; //creating the field to store the cards number
    public Card(int value) {
        if (value < 0){ // checking if the number on the card is valid 
            throw new IllegalArgumentException(
                "Value must be greater or equals to 1");//tells us when the card number is not valid
        }
        this.value = value; // storing the number
    }
    public int getValue() { //getter method that returns the number of the card
        return value;
    }
}
