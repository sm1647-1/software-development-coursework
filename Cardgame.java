
public class CardGame {
    private boolean isfinished = false;
    private int playercount; 


    public void AskforPlayercount(){ //method for asking how many players are playing
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
}


