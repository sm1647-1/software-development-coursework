package Tests;

public class PlayerTest {
    @Test
    public void testSetInitialCards(){ //testing that the setinitialhand method sets 4 cards correctly

        CardDeck left = new CardDeck(10); 
        CardDeck right = new CardDeck(10);
        CardGame.GameState gameState = new CardGame.GameState();

        Player player = new player(1, left, right, gammeState, 1); //initializing a player to test

        player.setInitialCards(new Card(1));
        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(3));
        player.setInitialCards(new Card(4));

        asserEquals(4, player.getHand().size());

    }

    @Test
    public void testSetInitialCardslimit(){ //testing that the setinitialhand method sets 4 cards correctly and doesnt accept more than 4

        CardDeck left = new CardDeck(10); 
        CardDeck right = new CardDeck(10);
        CardGame.GameState gameState = new CardGame.GameState();

        Player player = new player(1, left, right, gammeState, 1); //initializing a player to test

        player.setInitialCards(new Card(1));
        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(3));
        player.setInitialCards(new Card(4));
        player.setInitialCards(new Card(5));

        asserEquals(4, player.getHand().size()); //the size should stay 4 
    }

    @Test
    public void testWinningHand() { //testing if the method correctly identifies a winning hand

        CardDeck left = new CardDeck(10); 
        CardDeck right = new CardDeck(10);
        CardGame.GameState gameState = new CardGame.GameState();

        Player player = new player(1, left, right, gammeState, 1); //initializing a player to test

        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(2));

        assertTrue(player.hasWinningHand());
        sserEquals(1, gameState.getWinnerId()); //checks that it actually sets player 1 to the winner in the shared gameState space and the method returns true for player 1 winning
    }

    @Test
    public void testLosingHand() { //testing if the method correctly identifies a losing hand

        CardDeck left = new CardDeck(10); 
        CardDeck right = new CardDeck(10);
        CardGame.GameState gameState = new CardGame.GameState();

        Player player = new player(1, left, right, gammeState, 1); //initializing a player to test

        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(2));
        player.setInitialCards(new Card(3));
        player.setInitialCards(new Card(2));

        assertFalse(player.hasWinningHand()); //check wether it returns false since its a losing hand the winnerId in gamestate remains the default value
        sserEquals(0, gameState.getWinnerId()); 
    }
}
