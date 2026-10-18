import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Player implements Runnable { //variables for player
    private int playerNum;
    private List<Card> hand = new ArrayList<>(); //araylist to store hand
    private CardDeck left, right;
    private CardGame.GameState gameState; //to access the shared gamespace nested class that is created in CardGame
    private final Random random = new Random();
    private int discardCardValue; //Variables to store the value of the card that was discarded and drawn
    private int drawnCardValue;
    private BufferedWriter writerfr; //variable for the writer
    public Player(int playerNum, CardDeck left, CardDeck right, CardGame.GameState gameState) {
        this.playerNum = playerNum;
        this.left = left;
        this.right = right;
        this.gameState = gameState;
    }

    public void setInitialCards(Card c) { //method to set initial hand for the main game
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

    public String handString(){  //method to write the contents of the hand 
        String contents = ""; 
        for (int i = 0; i < hand.size(); i++) {
            contents+= Integer.toString(hand.get(i).getValue()); //converts the integer value specified by the card i to a string and adds it to the contents
            contents += " ";
        }
        return contents; //return the string of the hand

    }
    
    public void discard(){ //method to discard card and add it to the pile on the right
        boolean discardComplete = false; //boolean condition so it will run until it gets a card value that isnt the same as the playernum
        while(!discardComplete) {
            int randint = random.nextInt(0,hand.size()); //creates a random int beteen 0 and the hand size
            if (hand.get(randint).getValue() == playerNum){
                continue; //if the card value is the same as the player number it will skip and roll a random int again
            }
            right.addingCard(hand.get(randint));  //if not the same as player num it will add it to the right pile before removing it
            discardCardValue = hand.get(randint).getValue();//stores the value of the card discarded
            hand.remove(randint);
            discardComplete = true;
        }
    }


    public void playTurn() throws IOException { //method for playing a turn, a card will be drawn and then added to the hand then a card will be picked from the hand to be discarded, deals with IOexeceptions for when it writes to the file the actions
        Card x = left.drawingCard(); //will draw a card from the deck on the left
        drawnCardValue = x.getValue(); //stores the value of the drawn card
        hand.add(x); //will add this card to the hand
        discard(); //will then run the discard method which will discard a random card (following the sorting algorithm) and move it othe right pile 
        hasWinningHand();  //and before the end of the turn it will check if the hand is a winning hand

        writeToOutput("player " + playerNum + " draws a " + drawnCardValue + " from deck " + left.getDeckId()); //log the actions in the output file
        writeToOutput("player " + playerNum + " discards a " + discardCardValue + " to deck " + right.getDeckId());
        writeToOutput("player " + playerNum + " current hand " + handString());
    }  //the locking for this has not been sorted yet will come back and implement locking so the threads dont interfere with eachother when playing turn





    public  boolean hasWinningHand(){ //method for checking if the player has a winning hand and then setting it in the gamestate class
        if (hand.size() != 4) {return false;} //if the hand size is not 4 then it will not execute the checking code
        int count = 0; //count to represent how many of the cards are the same
        int first = hand.get(0).getValue(); //first value which everything is compared against (if all cards are the first value that means they are all the same and a matching pair)
        for (Card c : hand) {  //iterate through each card in the deck, if its value is the same as the first increment the count
            if (c.getValue() == first) {
                count++;
            }
        }
        //sets the winner id in the gamestate class to the current player who just wons id
        if (count == 4) {
            gameState.setWinner(playerNum);
            gameState.declareWinner();
            return true;
        } //evaluates if the count is 4, if it is then it means we have a winning hand as all the cards have to have been the same and if not it will return false
        else {return false;} //if not it is not a winning hand
    }

    private void writeToOutput(String lineToWrite) throws IOException {
        writerfr.write(lineToWrite);
        writerfr.newLine();
    }

    @Override 
    public void run(){
        String filename = "player" + playerNum + "_output.txt"; 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) { //creates the outputtxt file for the specific player
            writerfr = writer;
            writeToOutput("player " + playerNum + " initial hand " + handString()); //writes the initial hand to the output file

            hasWinningHand(); //run the haswinninghand initially, it will check if its a winning and if so gamestate.isover will be set to true meaning the game will be finished and the main game loop wont run


            while (gameState.isOver != true) { //loop for while the game is still playing
                playTurn();
            }

            //after the game ends it will write to the output file depending on who won
            if (gameState.getWinnerId() == playerNum)  {
                writeToOutput("player " + playerNum + " wins");
                writeToOutput("player " + playerNum + " exits");
                writeToOutput("player " + playerNum + " final hand " + handString());
            }
            else {
                writeToOutput("player " + gameState.getWinnerId() + " has informed player " + playerNum + " that player " + gameState.getWinnerId() + " has won");
                writeToOutput("player " + playerNum + " exits");
                writeToOutput("player " + playerNum + " final hand " + handString()); 
            }

        } catch(IOException q) {
            System.err.println("player " + playerNum + " failed to write to outputfile");
        }


    }

}


