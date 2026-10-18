import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;





public class CardGame {
    private static int playercount; 
    public static class GameState {
        private final AtomicInteger winnerId = new AtomicInteger(0);
        public volatile boolean isOver = false;//added volatile 
        
        public void setWinner(int id) { //method to set winnerId
        winnerId.compareAndSet(0, id); //uses compare nand set with the default value of zero to make sure only one player is able to change it and no others can after
            isOver = true;
        }

        public int getWinnerId() { //method to get winnerID as the other threads will need to know the winning playhers ID
            int x = winnerId.get();
            return x;
        }
    
        public void declareWinner() {  //Method that declares the winner
            System.out.println("the winner is player " +  winnerId);
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

        }}

    private static void dealCards(List<Card> pack, List<Player> players, List<CardDeck> decks) {
        int nextCard = 0;
        for (int roundRobin = 0; roundRobin < 4; roundRobin++) {
            for (int i = 0; i < players.size(); i++) {
                players.get(i).setInitialCards(pack.get(nextCard));
                nextCard++;
            }
        }
        for (int roundRobin = 0; roundRobin < 4; roundRobin++) {
            for (int i = 0; i < decks.size(); i++) { 
                decks.get(i).addingCard(pack.get(nextCard));
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

    private static List<Player> createPlayers(int n, List<CardDeck> decks, GameState gamestate, int playercount) { //method for creating players based on the playercount
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
            Player player = new Player(i, left, right, gamestate, playercount);
            players.add(player);
        }
        return players;
    }

    private static void writeDeckOutputstOfFile(List<CardDeck> decks) throws IOException {//writing the end of games decks to a file
        for (CardDeck deck : decks) {//loop through all decks
            String contents = "Deck" + deck.getDeckId() + "Contents, " + deck.getContent().trim();//add content to the file
            String filename = "Deck" + deck.getDeckId() + "_final_decks_output_file.txt";//creating the filename

            Files.writeString(Path.of(filename), contents);//created the file and added the contents
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        CardGame game = new CardGame();//creating the game object
        game.AskforPlayercount()//calling method to find out how many players
        try (Scanner input = new Scanner(System.in)) {//creating scanner for file path
            List<Card> pack = readingPack(input, game.playercount);//reading file and store cards
            GameState gamestate = new GameState();//creating the shared game state
            List<CardDeck> decks = createDecks(game.playercount);//creating empty decks
            List<Player> players = createPlayers(game.playercount, decks, gamestate);//creating players and their decks
            dealCards(pack, players, decks);//call the dealing cards method to deal the cards
            for (int i = 0; i < players.size(); i++) {//checking if a player has won with their starting hand
                if (players.get(i).haswinninghand()){
                    break;
                }
            }
            List<Thread> threads = new Arraylist<>();//storing the player threads in a list
            for (int i = 0; i < players.size(); i++) {//one thread for each player
                Thread thread = new Thread(players.get(i));
                threads.add(thread);//storing the thread to start it and so we can come back to it later.
            }
            for (int i = 0; i < threads.size(); i++) {//starting the player threads
                threads.get(i).start();
            }
            for (int i = 0; i < threads.size(); i++) {//making the main thread wait until every player thread has finished
                threads.get(i).join();
            }
            writeDeckOutputsOfFile(decks);//write out the final contents to the output file



    }
}






