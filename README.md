# software-development-coursework

Project overview:
This project implements a multi-threaded card game simulation in Java.

The game takes in a specified number of players by the user and represents them each with a separate thread. Each player will hold four cards and will continuously play a turn, which consists of drawing from the left deck, and discarding a card to the right deck (according to a game-playing strategy where it will prefer cards matching the player number) and checking whether the player has a winning hand.

The game will end once a player obtains a 4 cards of the same number, the application also records each players actions and the final contents of each deck in separate text files.

Requirements:
This project uses Junit5 for testing.


Project Files

Card.java           Represents a card and stores its value
Player.java         Implements the player behaviour such threading, drawing, discarding, checking for winning hands and logging
CardDeck.java       Represents a deck of cards which will take and store cards as well as providing methods to get its content, size etc
CardGame.java       Initializes the game as well as distributing cards, starting all the threads and it manages the shared gamestate
CardTest.java       Tests card values making sure they are valid
CardDeckTest.java   Tests deck operations and valid inputs
PlayerTest.java     Tests player behaviour and valid deck inputs and player outputs
CardGameTest.java   Tests behaviour for the main card game and shared game state 


Running the game:
First compile the game using the command: javac Card.java CardDeck.java Player.java CardGame.java
Then run the game using the command: java CardGame
You will then be asked how many players are in the game which you would answer with an integer
Then you will be prompted to enter where the pack is stored which you will input the directory of the path containing the pack files, the pack should contain exactly 8 x n cards with  n being players


Testing the game:
for testing if the file does not already contain the necessary compiled java classes run: javac -cp ".;junit-platform-console-standalone-1.13.4.jar" Card.java CardDeck.java Player.java CardGame.java CardTest.java CardDeckTest.java PlayerTest.java CardGameTest.java

then to run all Junit tests run: java -jar junit-platform-console-standalone-1.13.4.jar execute --class-path . --scan-class-path




Output files:
each player should produce an output file detailing the players actions, initial and final hand and if they won or another player informed them.
each deck will have a deck file recording the final deck contents



