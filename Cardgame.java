
public class Cardgame {
    private boolean isfinished = false;
    private int playercount;


    public void AskforPlayercount(){
        boolean valid = false;
        while(!valid){
            try {
                playercount = Integer.parseInt(System.console().readLine("Please enter the amount of players in the game"));
                if (playercount < 1) {
                    System.out.print("please enter a valid number of players");
                }
                if (playercount > 0) {valid = true;}
            } catch(NumberFormatException e) {
                System.out.print("please enter a valid integer");
            } 
        }


    }
}


