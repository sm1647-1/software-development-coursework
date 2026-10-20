import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CardGameTest {

    private CardGame.GameState state;
    private CardGame cardGame;

    @BeforeEach 
    public void setUp() {
        state = new CardGame.GameState(); //each test will have a state declared for it
        cardGame = new CardGame();
    }

    @Test
    void initialStateHasNoWinner() {//testing that a new game starts without a winner

        assertEquals(0, state.getWinnerId());//checking there is no winner before the game starts
        assertFalse(state.isOver);//checking that the game isnt over
    }
    @Test
    void winnerEndsGame() {//making sure once there is a winner that the game ends
        state.setWinner(1);//making player 1 be the winner

        assertEquals(1, state.getWinnerId());//make sure the winner is player 1
        assertTrue(state.isOver);//check that players can see the game is over
    }
    @Test
    void replacingTheWinner() {//checking if another thread wins after one already has before its announced it doesnt replace it
        state.setWinner(1);//setting the winner
        state.setWinner(2); // making another player claim the win

        assertEquals(1, state.getWinnerId());//checking if player 1 is still the winner
        assertTrue(state.isOver);// making sure the game is still over
    }
    @Test 
    void  competingThreadsDontChangeWinner() throws InterruptedException{//checking that two threads claiming they have winners are consistant
        int[] observedWinners = new int[2];//stores the winners

        Thread one = new Thread(new Runnable() {//creating the thread that says 1 is the winner
            @Override
            public void run() {
                state.setWinner(1);
                observedWinners[0] = state.getWinnerId();//record the winner seen by the first thread
            }
        });

        Thread two = new Thread(new Runnable() {//creating the thread that thinks 2 won
            @Override
            public void run() {
                state.setWinner(2);
                observedWinners[1] = state.getWinnerId();//records the winner seen by the second thread
            }
        });

        one.start();//starting both threads, so their claims can compete
        two.start();

        one.join();//allows both threads to finish before checking recorded results.
        two.join();

        int finalWinner = state.getWinnerId();//gets the winner that remains


        assertTrue(finalWinner == 1 || finalWinner == 2);//allows either player to win 

        assertEquals(finalWinner, observedWinners[0]);//checking the threads recorded the same winner as the final result
        assertEquals(finalWinner, observedWinners[1]);

        assertTrue(state.isOver);//checking the game is over
    }

    @Test 
    void correctNumberOfDecksCreated() { //method to check whether the correct number of decks are created when decks are created
        List<CardDeck> decks = cardGame.createDecks(3); //creates 3 decks and stores in decks

        assertEquals(3, decks.size()); //checking the size of decks is 3 
    }

    @Test 
    void correctPlayerCount() { //method to check the correct number of players is declared 
        List<CardDeck> decks = cardGame.createDecks(2);

        List<Player> players = cardGame.createPlayers(1, decks, state);

        assertEquals(1, players.size()); //here only 1 player is initialised so players size list should be 1

    }

    @Test 
    void testDealCards(){ //tests that the deal cards method correctly deals in a round robin style
        List<Card> pack = new ArrayList<>(); //create a pack of cards from 1-16

        for (int i = 1; i <=16; i++) {
            pack.add(new Card(i));
        }

        List<CardDeck> decks = cardGame.createDecks(2);  //create two decks and two players

        List<Player> players = cardGame.createPlayers(2, decks, state);

        cardGame.dealCards(pack, players, decks); //deals the cards to the decks and players

        assertEquals(3, players.get(0).getHand().get(1).getValue()); //this checks that the second element in player 1,s hand is 3, this would verify the round robin style as that means 1 was dealt to 1 then 2 to 2, then for the second card dealt to 1 it would be 3

    }
}
        