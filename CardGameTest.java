import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CardGameTest {
    @Test
    void initialStateHasNoWinner() {//testing that a new game starts without a winner
        CardGame.GameState state = new CardGame.GameState();//making a new state just for this test
        
        assertEquals(0, state.getWinnerId());//checking there is no winner before the game starts
        assertFalse(state.isOver);//checking that the game isnt over
    }
    @Test
    void winnerEndsGame() {//making sure once there is a winner that the game ends
        CardGame.GameState state = new CardGame.GameState();

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
    void  competingThreadsDontChangeWinner() throws InterruptedException{
        CardGame.GameState state = new CardGame.GameState();
        int[] observedWinners = new int[2];

        Thread one = new Thread(new Runnable() {
            @Override
            public void run() {
                state.setWinner(1);
                observedWinners[0] = state.getWinnerId();
            }
        });

        Thread two = new Thread(new Runnable() {
            @Override
            public void run() {
                state.setWinner(2);
                observedWinners[1] = state.getWinnerId();
            }
        });

        one.start();
        two.start();

        one.join();
        two.join();

        int finalWinner = state.getWinnerId();


        assertTrue(finalWinner == 1 || finalWinner == 2);

        assertEquals(finalWinner, observedWinners[0]);
        assertEquals(finalWinner, observedWinners[1]);

        assertTrue(state.isOver);
    }
        