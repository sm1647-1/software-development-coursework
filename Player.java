import java.util.List;
import java.util.ArrayList;
import java.util.Random;



public class Player implements Runnable { //variables for player
    private int playerNum;
    private List<Card> hand = new ArrayList<>(); //araylist to store hand
    private boolean hasWon = false;
    private CardDeck left, right;
    private CardGame.GameState gameState; //to access the shared gamespace nested class that is created in CardGame
    private final Random random = new Random();

    public  void player(int playerNum, CardDeck left, CardDeck Right, boolean hasWon, CardGame.GameState gamestate) {
        this.playerNum = playerNum;
        this.left = left;
        this.right = right;
        this.hasWon = hasWon;
        this.gameState = gameState;
    }

    private void setInitialCards(Card c) { //method to set initial class
        if(hand.size() >= 4) {
            return;  //if the hand size is already 4 it doesnt need new initial cards
        }
        this.hand.add(c); //adds the card passed from the game into the hand
    }

    public int getPlayerNum(){ //method to get the player number
        int x = this.playerNum;
        return x;
    }

    public List<Card> getHand() { //method to return the hand of player
        List<Card> x = this.hand;
        return x;
    }

    public String finalHandString(){  //method to write the contents of the final hand after the game has been won to a string so it can be added to the output.txt file
        String contents = ""; 
        for (int i = 0; i < hand.size(); i++) {
            contents+= Integer.toString(hand.get(i).getValue()); //converts the integer value specified by the card i to a string and adds it to the contents
            contents += " ";
        }
        return contents; //return the string of the winning hand

    }
    
    public void discard(){ //method to discard card and add it to the pile on the right
        boolean discardComplete = false; //boolean condition so it will run until it gets a card value that isnt the same as the playernum
        while(!discardComplete) {
            int randint = random.nextInt(0,hand.size()); //creates a random int beteen 0 and the hand size
            if (hand.get(randint).getValue() == playerNum){
                continue; //if the card value is the same as the player number it will skip and roll a random int again
            }
            right.addingCard(hand.get(randint));  //if not the same as player num it will add it to the right pile before removing it
            hand.remove(randint);
            discardComplete = true;
        }
    }

    



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


    @Override 
    public void run() {

    }

}


