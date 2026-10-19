import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedWriter;
import java.io.StringWriter;
import java.util.List;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Path;

public class PlayerTest {

    private Player player;
    private CardDeck left;
    private CardDeck right;
    private CardGame.GameState gameState;


    @BeforeEach 
    public void setUp() {
        left = new CardDeck(10); 
        right = new CardDeck(10);
        gameState = new CardGame.GameState();
        player = new Player(1, left, right, gameState, 2); //initializing a player to test
        left.setDeckId(1); //set id for the decks
        right.setDeckId(2);

        left.addingCard(new Card(1));//adding cards to the left deck 
        left.addingCard(new Card(1));
        left.addingCard(new Card(1));
        left.addingCard(new Card(1));

        right.addingCard(new Card(2));//adding cards to the right deck 
        right.addingCard(new Card(2));
        right.addingCard(new Card(2));
        right.addingCard(new Card(2));
    }

    @Test
    public void testSetInitialCards(){ //testing that the setinitialhand method sets 4 cards correctly

        player.setInitialCards(new Card(1));
        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(3));
        player.setInitialCards(new Card(4));

        assertEquals(4, player.getHand().size());

    }

    @Test
    public void testSetInitialCardslimit(){ //testing that the setinitialhand method sets 4 cards correctly and doesnt accept more than 4

        player.setInitialCards(new Card(1));
        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(3));
        player.setInitialCards(new Card(4));
        player.setInitialCards(new Card(5));

        assertEquals(4, player.getHand().size()); //the size should stay 4 
    }

    @Test
    public void testHasWinningHand() { //testing if the method correctly identifies a winning hand

        player.setInitialCards(new Card(2)); 
        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(2));

        assertTrue(player.hasWinningHand());
        assertEquals(1, gameState.getWinnerId()); //checks that it actually sets player 1 to the winner in the shared gameState space and the method returns true for player 1 winning
    }

    @Test
    public void testLosingHand() { //testing if the method correctly identifies a losing hand

        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(3));
        player.setInitialCards(new Card(2));
        
        assertFalse(player.hasWinningHand()); //check wether it returns false since its a losing hand the winnerId in gamestate remains the default value
        assertEquals(0, gameState.getWinnerId()); 
    }

    @Test
    public void testHasWinningHandfor3() { //testing if the method correctly identifies a hand of 3 cards though matching is still losing

        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(2));

        assertFalse(player.hasWinningHand()); //check wether it returns false since its a losing hand the winnerId in gamestate remains the default value
        assertEquals(0, gameState.getWinnerId()); 
    }

    @Test
    public void testPlayTurn() throws IOException { //testing if player correctly draws and discards from the correct decks and the hand changes (proving the move happened) and has 4 cards at the end, it will also test that playturnw ill try keep card value that is its card number

        StringWriter stringWriter = new StringWriter();
        player.writerfr = new BufferedWriter(stringWriter);
        int oneCount = 0;

        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(3));
        player.setInitialCards(new Card(1));
        player.setInitialCards(new Card(1));

        List<Card> handBeforeTurn = new ArrayList<>(player.getHand());

        player.playTurn();

        boolean leftDrawn = false; //booleans to be used to assert wether the cards have been properly drawn and discarded
        boolean rightDiscarded = false; 

        boolean isSame = false;
        if (handBeforeTurn.equals(player.getHand())) {
            isSame = true;
        }

        for (Card c : player.getHand()) {
            if(c.getValue() == 1) {
                oneCount++;  //this for loop will go through each card in the hand and if the card is 1(the players number) it will increment oneCount
            }
            
        }

        if (left.getSize() == 3) {leftDrawn = true;} //if the deck size after is 3 that means it drew from the left deck
        if (right.getSize() == 5) {rightDiscarded = true;} //if the deck size after is 5 that means it discarded to right deck

        assertFalse(isSame); //will check that they are not the same meaning the turn has properly played
        assertTrue(leftDrawn);
        assertTrue(rightDiscarded); //will check if the left was drawn from and the right was discarded
        assertEquals(4, player.getHand().size());
        assertEquals(3, oneCount);//will assert if oneCount is 3, the hand initially starts with 2 ones, then after the turn plays since it is player 1 the plyer should prefer 1's, left only has ones so it will draw a one and discard either 2 or 3 and the hand should have 3 ones if the preffered hand sorting works, so if oneCount is 3 that means there are 3 ones and it has worked correctly

        player.writerfr.close();
    }

    @Test
    public void testPlayerOutput() throws IOException{ //tests if the player output correct given a player has won straight away

        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(3));
        player.setInitialCards(new Card(1));
        player.setInitialCards(new Card(1));

        gameState.setWinner(2);

        player.run(); //run the player run method

        String outputFile = Files.readString(Path.of("player1_output.txt")); //read the output file of player 1

        assertTrue(outputFile.contains("player 1 initial hand 2 3 1 1 "));//check the output contains the correct initial hand 
        assertTrue(outputFile.contains("player 2 has informed player 1 that player 2 has won")); //check out contains that the winner has informed the playher they have won
        assertTrue(outputFile.contains("player 1 exits")); //check output contains declaration that player has exited
        assertTrue(outputFile.contains("player 1 final hand 2 3 1 1 ")); //check output contains correct final hand
    }

    @Test
    public void testPlayerOutputIfturnPlayed() throws IOException{ //tests the player output given the player has played a turn

        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(3));
        player.setInitialCards(new Card(1));
        player.setInitialCards(new Card(1));

        
        player.run(); //run the player run method

        String outputFile = Files.readString(Path.of("player1_output.txt")); //read the output file of player 1

        assertTrue(outputFile.contains("player 1 current hand 2 1 1 1 ") || outputFile.contains("player 1 current hand 1 3 1 1 "));//check the output contains the correct current hand (which can only be 1311 or 2111) in its turn
        assertTrue(outputFile.contains("player 1 draws a 1 from deck 1")); //check that it logs that it draws the correct card from the correct left deck
        assertTrue(outputFile.contains("player 1 discards a 2 to deck 2") || outputFile.contains("player 1 discards a 3 to deck 2")); //check that it correctly logs that it discards a card(can only be 2 or 3) to deck 2
    }


    



}
