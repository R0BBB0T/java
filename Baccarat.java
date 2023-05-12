import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.LinkedList;


public class Baccarat {
  
  public static void main(String[] args) {
    int[] conditionCollection = new int[4]; // An array that keeps count of player wins, banker wins, ties and rounds played
    int games = 0;
    int wins = 0;
    int lose = 0;
    int tie = 0;
    Shoe baccaratShoe = new Shoe(6); // Initialises the shoe object
    baccaratShoe.shuffle(); // Shuffles
    if (args.length == 1 && (args[0].equals("-i") || args[0].equals("--interactive"))) { // If the command line argument is --interactive...
      conditionCollection = interactiveMode(baccaratShoe);
    } else if (args.length == 0) {
      conditionCollection = noninteractiveMode(baccaratShoe);
    } else {
      System.err.println("Invalid Command Line Arguments"); // If arguments are invalid
    }
    wins = conditionCollection[0];
    lose = conditionCollection[1];
    tie = conditionCollection[2];
    games = conditionCollection[3];

    System.out.print("\n" + games + " rounds played");
    System.out.print("\n" + wins + " player wins");
    System.out.print("\n" + lose + " banker wins");
    System.out.print("\n" + tie + " ties");

    
  }
  private static int[] interactiveMode(Shoe baccaratShoe){ // Plays the game in interactive mode (asks the user if they want to continue)
    String mode = "interactive";
    int[] conditionCollection = playRound(baccaratShoe, mode);
    return conditionCollection;

  }

  private static int[] noninteractiveMode(Shoe baccaratShoe){ // Plays the game by itself (until there are less than 6 cards left)
    int[] conditionCollection = playRound(baccaratShoe, "non-interactive");
    return conditionCollection;
  }
  
  private static int[] playRound(Shoe baccaratShoe, String mode) {
    int[] conditionCollection = new int[4]; // Keeps count of how the round ends and number played
  
    boolean play = true;
    while (play == true){ // While there are 6 or more cards and player chooses to continue
      int round = conditionCollection[3] + 1;
      System.out.print("\nRound " + round);

      BaccaratHand playerHand = new BaccaratHand(); // Creates 2 hands and deals each 2 cards
      BaccaratHand bankerHand = new BaccaratHand();
      playerHand.add(baccaratShoe.deal());
      bankerHand.add(baccaratShoe.deal());  
      playerHand.add(baccaratShoe.deal());
      bankerHand.add(baccaratShoe.deal()); 
      int condition = printResults(playerHand, bankerHand); // Prints the toString() representatino of the hands and decides the winner
      if (condition == 3){ // If the player does not get another card
        bankerHand.add(baccaratShoe.deal());
        condition = printResults(playerHand, bankerHand); 
      }

      if (condition == 2){ // If the player gets another card
        Card card = baccaratShoe.deal();
        playerHand.add(card);
        boolean newDraw = shouldDrawBanker(bankerHand, card); // Sends the banker hand and the new player card so that it can decide if the banker draws
        if (newDraw == true){
          System.out.print("\nDealing third card to banker...");
          bankerHand.add(baccaratShoe.deal());
        }
        condition = printResults(playerHand, bankerHand); 
      }

      if (condition == 1){ // Updates wins/losses
        conditionCollection[0] += 1;
      } 
      if (condition == -1){
        conditionCollection[1] += 1;
      }
      if (condition == 0){
        conditionCollection[2] += 1;
      }
      conditionCollection[3] += 1;

      if (baccaratShoe.size() <6){
        play = false;
      }

      if (mode == "interactive"){ // Asks the user to play in interactive mode
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nAnother Round? (y/n): ");
        char choice = scanner.next().charAt(0);
        choice = Character.toUpperCase(choice);
  
        if (choice != 'Y'){
          play = false;
        }
      }
    }
    return conditionCollection;
  }
  
  private static int printResults(BaccaratHand playerHand, BaccaratHand bankerHand) {
    int condition; // Tells the other function what to do and gets around only being able to return one output
    System.out.print("\nPlayer: " + playerHand.toString() + " = " + calculateScore(playerHand));
    System.out.print("\nBanker: " + bankerHand.toString() + " = " + calculateScore(bankerHand));

    // Ends the round if any hands are naturals
    if (calculateScore(playerHand)  > calculateScore(bankerHand) && (isNatural(playerHand) == true || isNatural(bankerHand) == true)){
      System.out.print("\nPlayer Win!\n");
      condition = 1;
    }
    else if (calculateScore(playerHand) < calculateScore(bankerHand) && (isNatural(playerHand) == true || isNatural(bankerHand) == true)){
      System.out.print("\nBanker Win!\n");
      condition = -1;
    } 
    else if ((calculateScore(playerHand) == calculateScore(bankerHand) && (isNatural(playerHand) == true || isNatural(bankerHand) == true))){
      System.out.print("\nTie\n");
      condition = 0;
    }

    // Checks if the player needs a card
    else if (shouldDrawThirdCard(playerHand) == true){
      System.out.print("\nDealing third card to player...");
      return 2;
    }

    // If the player does not need a card and the banker does
    else if (shouldDrawThirdCard(bankerHand) == true && shouldDrawThirdCard(playerHand) == false && playerHand.size() == 2){
      System.out.print("\nDealing third card to banker...");
      return 3;
    }

    // Tells the playRound function who won
    else if (calculateScore(playerHand) > calculateScore(bankerHand)){
      System.out.print("\nPlayer Win!\n");
      condition = 1;
    }
    else if (calculateScore(playerHand) < calculateScore(bankerHand)){
      System.out.print("\nBanker Win!\n");
      condition = -1;
    } else {
      System.out.print("\nTie\n");
      condition = 0;
    }
    return condition;
  }

  
  private static int calculateScore(BaccaratHand hand) { // Returns the value of a hand
    int score = hand.value(); 
    return score;
  }
  
  private static boolean isNatural(BaccaratHand hand) { // Returns if the hand is natural
    boolean natural = hand.isNatural();
    return natural;
  }
  
  private static boolean shouldDrawThirdCard(BaccaratHand hand) { // Uses the basic drawing rules
    boolean choice = false;
    if (hand.value() < 6 && hand.size() <= 2){
      choice = true;
    }
    return choice;
  }

  private static boolean shouldDrawBanker(BaccaratHand hand, Card card){ // Uses the more complex banker drawing rules
    boolean choice = false;
    if (card.value() == 0 && hand.value() < 4){
      choice = true;
      return choice;
    }
    else if (card.value() == 1 && hand.value() < 4){
      choice = true;
      return choice;
    }
    else if (card.value() == 2 && hand.value() < 5){
      choice = true;
      return choice;
    }
    else if (card.value() == 3 && hand.value() < 5){
      choice = true;
      return choice;
    }
    else if (card.value() == 4 && hand.value() < 6){
      choice = true;
      return choice;
    }
    else if (card.value() == 5 && hand.value() < 6){
      choice = true;
      return choice;
    }
    else if (card.value() == 6 && hand.value() < 7){
      choice = true;
      return choice;
    }
    else if (card.value() == 7 && hand.value() < 7){
      choice = true;
      return choice;
    }
    else if (card.value() == 8 && hand.value() < 3){
      choice = true;
      return choice;
    }
    else if (card.value() == 9 && hand.value() < 4){
      choice = true;
      return choice;
    }
    return choice;
  }


}