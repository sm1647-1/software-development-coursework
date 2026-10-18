import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;





public class CardGame {
    private int playercount; 
    public static class GameState {
        private boolean isFinished = false;
        private final AtomicInteger winnerId = new AtomicInteger(0);
        public volatile boolean isOver = false;//added volatile 
        

        public void setWinner(int id) {
        winnerId.compareAndSet(0, id);
            isOver = true;
        }

        public int getWinnerId() {
            int x = winnerId.get();
            return x;
        }
    
        public synchronized void declareWinner() {
            if (winnerId.get() != 0 && !isFinished) {
                System.out.println( winnerId.get() + "is the winner");
        
        isFinished = true;
        }
    }
}


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

    private static List<Card> readingPack(Scanner input, int numberOfPlayers) {
        int requiredCards = 8 * numberOfPlayers;
        while (true) {
            System.out.println(
                "Enter where the pack file is stored");
                String filename = input.nextLine().trim();
                List<Card> pack = new ArrayList<>();
                try (BufferedReader reader = Files.newBufferedReader(Path.of(filename))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        int value = Integer.parseInt(line.trim());
                        if (value < 1) {
                            throw new IllegalArgumentException("Card has to be a positive integer");
                        }
                        if (pack.size() == requiredCards) {
                            throw new IllegalArgumentException("Too many cards");
                        }
                        pack.add(new Card(value));
                    }
                    if (pack.size() != requiredCards) {
                        throw new IllegalArgumentException("The pack needs" + requiredCards + "cards");
                    }
                    return pack;
                
                } catch (IOException e) {
                    System.out.println("Please check file path again.");
                    
                }

        }}}

    private static void dealCards(List<Card> pack, List<Player> players, List<CardDeck> decks) {
        int nextCard = 0;
        for (int roundRobin = 0; roundRobin < 4; roundRobin++) {
            for (Player player : players) {
                player.setInitialCards(pack.get(nextCard));
                nextCard++;
            }
        }
        for (int roundRobin = 0; roundRobin < 4; roundRobin++) {
            for (CardDeck deck : decks) { 
                deck.addingCard(pack.get(nextCard));
                nextCard++;
            }
        }
    }



        
    

    private static List<CardDeck> createDecks(int n){ //method for creating decks based on n(playercount)
        List<CardDeck> decks = new ArrayList<>(); //creates an arraylist to store the card decks
        for (int i = 1; i <= n; i++) {  //for loop which will add a new deck to the list for each player in the game 
            CardDeck deck = new CardDeck(4 * n);
            deck.setDeckId(i);
            decks.add(deck);
        }
        return decks; //return the empty list 
    }

    private static List<Player> createPlayers(int n, List<CardDeck> decks, GameState gamestate) { //method for creating players based on the playercount
        List<Player> players = new ArrayList<>(); 
        for (int i = 1; i <= n; i++) {
            CardDeck left = decks.get(i - 1);
            CardDeck right;
            if (i == n) {
                right = decks.get(0);
            }
            else {
                right = decks.get(i);
            }
            Player player = new Player(i, left, right, gamestate);
            players.add(player);
        }
        return players;
    }

    private static void writeDeckOutputstofile(List<CardDeck> decks) throws IOException {//writing the end of games decks to a file
        for (CardDeck deck : decks) {//loop through all decks
            String contents = "Deck" + deck.getDeckId() + "Contents, " + deck.getContent().trim();//add content to the file
            String filename = "Deck" + deck.getDeckId() + "_final_decks_output_file.txt";//creating the filename

            Files.writeString(Path.of(filename), contents);//created the file and added the contents
        }
    }







