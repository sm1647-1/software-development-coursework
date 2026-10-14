import java.util.List;
import java.util.ArrayList;

public class Player implements Runnable { //variables for player
    private int playerNum;
    private List<Card> hand = new ArrayList<>(); //araylist to store hand
    private boolean hasWon = false;
    private CardDeck left, right;

    public  boolean hasWinningHand(){ //method for checking if the player has a winning hand
        if (hand.size() != 4) {return false;} //if the hand size is not 4 then it will not execute the checking code
        int count = 0; //count to represent how many of the cards are the same
        int first = hand.get(0).getValue(); //first value which everything is compared against (if all cards are the first value that means they are all the same and a matching pair)
        for (Card c : hand) {  //iterate through each card in the deck, if its value is the same as the first increment the count
            if (c.getValue() == first) {
                count++;
            }
        }
        return count == 4; //evaluates if the count is 4, if it is then it means we have a winning hand as all the cards have to have been the same and if not it will return false
    }

}


