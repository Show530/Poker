import java.util.*;

public class Poker {

    private final String[] SUITS = { "C", "D", "H", "S" };
    private final String[] RANKS = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K" };

    private final Player player;
    private List<Card> deck;
    private final Scanner in;

    public Poker() {
        this.player = new Player();
        this.in = new Scanner(System.in);
    }

    public void play() {
        // play the game
        System.out.println("How many chips would you like to wager?");
        int chipWager = in.nextInt();
        in.nextLine();
        while (chipWager < 1 || chipWager > 25) {
            System.out.println("How many chips would you like to wager?");
            chipWager = in.nextInt();
            // in.nextLine();
        }
        shuffleAndDeal();
        showHand();

        int answer;

        System.out.println("How many cards would you like to trade?");
        answer = in.nextInt();
        while(answer < -1 || answer > 3) {
            System.out.println("How many cards would you like to trade?");
            answer = in.nextInt();
        }

        tradeCards(answer);
       /* Card card1;
        Card card2;
        Card card3;
        if(answer == 3) {
            System.out.println("Card 1 to trade: ");
            String rank1 = in.nextLine().trim().toUpperCase();
            card1 = Card.getCardByRank(rank1);
            in.nextLine();
            System.out.println("Card 2 to trade: ");
            String rank2 = in.nextLine().trim().toUpperCase();
            card2 = Card.getCardByRank(rank2);
            System.out.println("Card 3 to trade: ");
            String rank3 = in.nextLine().trim().toUpperCase();
            card3 = Card.getCardByRank(rank3);
            tradeCards(card1, card2, card3);
        }
        else if(answer == 2) {
            System.out.println("Card 1 to trade: ");
            String rank1 = in.nextLine().trim().toUpperCase();
            card1 = Card.getCardByRank(rank1);
            in.nextLine();
            System.out.println("Card 2 to trade: ");
            String rank2 = in.nextLine().trim().toUpperCase();
            card2 = Card.getCardByRank(rank2);
            tradeCards(card1, card2);
        }
        else if(answer == 1) {
            System.out.println("Card 1 to trade: ");
            String rank1 = in.nextLine().trim().toUpperCase();
            card1 = Card.getCardByRank(rank1);
            in.nextLine();
            tradeCards(card1);
        }

        */
        String chipMessage= "You have a total of " ;
        showHand();
        if(straight() && fiveSameSuit() && player.maxCard() == 14) { //royal flush
            player.addChips(chipWager, 100);
            System.out.println("You got a royal flush! " + chipMessage + player.getChips() +" chips.");
        }
        else if(straight() && fiveSameSuit()) { //straight flush
            player.addChips(chipWager, 50);
            System.out.println("You got a straight flush! " + player.getChips() + " chips.");
        }
        else if (player.fourOfAKind()) {
            player.addChips(chipWager, 25);
            System.out.println("You got a four of a kind! "+ chipMessage + player.getChips() + " chips.");
        }
        else if (player.fullHouse()) {
            player.addChips(chipWager, 15);
            System.out.println("You got a full house! " + chipMessage + player.getChips() + " chips.");
        }
        else if (fiveSameSuit()) { //flush
            player.addChips(chipWager, 10);
            System.out.println("You got a flush! " + chipMessage + player.getChips() + " chips.");
        }
        else if (straight()) {
            player.addChips(chipWager, 5);
            System.out.println("You got a straight! " + chipMessage + player.getChips() + " chips.");
        }
        else if (player.threeOfAKind()) {
            player.addChips(chipWager, 3);
            System.out.println("You got three of a kind!" + chipMessage + player.getChips() + " chips.");
        }

        else if (player.twoPairs()) {
            player.addChips(chipWager, 2);
            System.out.println("You got two pairs!" + chipMessage + player.getChips() +" chips.");
        }

        else if (player.onePair()) {
            player.addChips(chipWager, 1);
            System.out.println("You got a pair of jacks or higher! " + chipMessage + + player.getChips() + " chips.");
        }
        else {
            System.out.println("Sorry, you have a losing hand. " + chipMessage + player.getChips() + " chips.");
        }

    }

   public void tradeCards(int answer) {
        player.removeCards(answer);
        while (player.getHand().size() < 5) {
            player.takeCard(deck.remove(0));    // deals extra cards to the player
        }
    }
    /*
    public void tradeCards(Card card1, Card card2, Card card3) {
        player.removeCards(card1);
        player.removeCards(card2);
        player.removeCards(card3);
        while (player.getHand().size() < 5) {
            player.takeCard(deck.remove(0));    // deals extra cards to the player
        }
    }

    public void tradeCards(Card card1, Card card2) {
        player.removeCards(card1);
        player.removeCards(card2);
        while (player.getHand().size() < 5) {
            player.takeCard(deck.remove(0));    // deals extra cards to the player
        }
    }

    public void tradeCards(Card card1) {
        player.removeCards(card1);
        while (player.getHand().size() < 5) {
            player.takeCard(deck.remove(0));    // deals extra cards to the player
        }
    }

     */

    public void shuffleAndDeal() {
        if (deck == null) {
            initializeDeck();
        }
        Collections.shuffle(deck);  // shuffles the deck

        while (player.getHand().size() < 5) {
            player.takeCard(deck.remove(0));    // deal 5 cards to the player
        }
    }

    public boolean straight() {
        int min = player.minCard();
        int max = player.maxCard();
        int average = player.average();
        if (fiveSameSuit()) {
            return average == max - 2 && max - 5 == min;
        }
        else {
            return false;
        }
    }

    public boolean fiveSameSuit() {
        int hearts = player.suitValue("H");
        int clubs = player.suitValue("C");
        int diamonds = player.suitValue("D");
        int spades = player.suitValue("S");
        return hearts == 5 || clubs == 5 || diamonds == 5 || spades == 5;
    }

    ////////// PRIVATE METHODS /////////////////////////////////////////////////////

    private void initializeDeck() {
        deck = new ArrayList<>(52);

        for (String suit : SUITS) {
            for (String rank : RANKS) {
                deck.add(new Card(rank, suit));     // adds 52 cards to the deck (13 ranks, 4 suits)
            }
        }
    }

    private void showHand() {
            System.out.println("\nPLAYER hand: " + player.getHand());   // only show player's hand
    }


    ////////// MAIN METHOD /////////////////////////////////////////////////////////

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("#########################################################");
        System.out.println("#                                                       #");
        System.out.println("#       A human solo game of Poker! Have fun. :)        #");
        System.out.println("#                                                       #");
        System.out.println("#########################################################");

        new Poker().play();
        String playerContinue;
        do {
            System.out.println("Would you like to play again? y/n");
            playerContinue = in.nextLine().trim().toLowerCase();
            if (playerContinue.equals("n")) {
                break;
            }
            else {
                new Poker().play();
            }
        } while (playerContinue.equals("y"));
    }
}