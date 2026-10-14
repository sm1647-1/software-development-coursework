import java.util.ArrayList;
import java.util.List;
public class CardGame {
    private boolean isfinished = false;
    private int playercount; 
    public String gamestate;


    private void AskforPlayercount(){ //method for asking how many players are playing
        boolean valid = false; //boolean for while loop so it will continue asking for a valid number of players until it gets it
        while(!valid){
            try {
                playercount = Integer.parseInt(System.console().readLine("Please enter the amount of players in the game"));
                if (playercount < 1) { //game cannot have less than one player
                    System.out.print("please enter a valid number of players");
                }
                if (playercount > 0) {valid = true;}
            } catch(NumberFormatException e) {  //try catch is for if they do not enter a valid integer
                System.out.print("please enter a valid integer");
            } 
        }
    }

    private static List<CardDeck> createDecks(int n){ //method for creating decks based on n(playercount)
        List<CardDeck> decks = new ArrayList<>(); //creates an arraylist to store the card decks
        for (int i = 1; i <= n; i++) {  //for loop which will add a new deck to the list for each player in the game 
            decks.add(new CardDeck(i));
        }
        return decks; //return the empty list 
    }

    private static List<Player> createPlayers(int n, List<CardDeck> decks, String gamestate){ //method for creating players based on the playercount
        List<Player> players = new ArrayList<>(); 
        for (int i = 1; i <= n; i++) {
  
        }
    }
}



