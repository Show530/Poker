import java.util.ArrayList;
import java.util.List;
public class Player {

    private final List<Card> hand;
    static public int chips;

    public Player() {
        this.hand = new ArrayList<>();
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getChips() {
        return chips;
    }

    public void addChips(int chipWager, int factor) {
        int increase = chipWager * factor;
        chips += increase;
    }

   public void removeCards(int answer) {
        for (int i = 0; i < answer; i++ ) {
            int bottomOfHand = hand.size() - 1;
            hand.remove(bottomOfHand);
        }
    }

    /*public void removeCards(Card cardS) {
        int index = -1; //= findCard(cardS);
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getRank().equals(cardS.getRank())) {     // find card by rank
                index = i;
            }
            hand.remove(index);
        }
    }

     */

    public void takeCard(Card card) {
        hand.add(card);
        sortHand();
    }

    public int minCard() {
        int min = Card.getOrderedRank(hand.get(0).getRank());
        for(int i = 1; i < hand.size(); i++) {
            int current = Card.getOrderedRank(hand.get(i).getRank());
            if (current < min) {
                min = current;
            }
        }
        return min;
    }

    public int maxCard() {
        int max = Card.getOrderedRank(hand.get(0).getRank());
        for(int i = 1; i < hand.size(); i++) {
            int current = Card.getOrderedRank(hand.get(i).getRank());
            if (current > max) {
                max = current;
            }
        }
        return max;
    }

    public int average() {
        int sum = 0;
        for(int i = 1; i < hand.size(); i++) {
            int current = Card.getOrderedRank(hand.get(i).getRank());
            sum += current;
        }
        return sum/5;
    }

    public int suitValue(String suit) {
        int frequency = 0;
        for (Card card : hand) {
            if (card.getSuit().equals(suit)) {
                frequency++;
            }
        }
        return frequency;
    }

    public boolean fullHouse() {
        sortHand();
        if (Card.getOrderedRank(hand.get(0).getRank()) == Card.getOrderedRank(hand.get(1).getRank())
                && Card.getOrderedRank(hand.get(1).getRank()) == Card.getOrderedRank(hand.get(2).getRank())) {
            if (Card.getOrderedRank(hand.get(3).getRank()) == Card.getOrderedRank(hand.get(4).getRank())) {
                return true;
            }
        }
        else if (Card.getOrderedRank(hand.get(2).getRank()) == Card.getOrderedRank(hand.get(3).getRank())
                && Card.getOrderedRank(hand.get(3).getRank()) == Card.getOrderedRank(hand.get(4).getRank())) {
            if (Card.getOrderedRank(hand.get(0).getRank()) == Card.getOrderedRank(hand.get(1).getRank())) {
                return true;
            }
        }
        return false;
    }

    public boolean fourOfAKind() {
        sortHand();
        if(Card.getOrderedRank(hand.get(0).getRank()) == Card.getOrderedRank(hand.get(1).getRank())
                && Card.getOrderedRank(hand.get(1).getRank()) == Card.getOrderedRank(hand.get(2).getRank())
                && Card.getOrderedRank(hand.get(2).getRank()) == Card.getOrderedRank(hand.get(3).getRank())) {
            return true;
        }
        else if (Card.getOrderedRank(hand.get(1).getRank()) == Card.getOrderedRank(hand.get(2).getRank())
                && Card.getOrderedRank(hand.get(2).getRank()) == Card.getOrderedRank(hand.get(3).getRank())
                && Card.getOrderedRank(hand.get(3).getRank()) == Card.getOrderedRank(hand.get(4).getRank())) {
                    return true;
        }
        else {
            return false;
        }
    }

    public boolean threeOfAKind() {
        sortHand();
        if(Card.getOrderedRank(hand.get(0).getRank()) == Card.getOrderedRank(hand.get(1).getRank())
                && Card.getOrderedRank(hand.get(1).getRank()) == Card.getOrderedRank(hand.get(2).getRank())) {
            return true;
        }
        else if(Card.getOrderedRank(hand.get(1).getRank()) == Card.getOrderedRank(hand.get(2).getRank())
            && Card.getOrderedRank(hand.get(2).getRank()) == Card.getOrderedRank(hand.get(3).getRank())) {
            return true;
        }
        else if(Card.getOrderedRank(hand.get(2).getRank()) == Card.getOrderedRank(hand.get(3).getRank())
                && Card.getOrderedRank(hand.get(3).getRank()) == Card.getOrderedRank(hand.get(4).getRank())) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean twoPairs() {
        sortHand();
        if(Card.getOrderedRank(hand.get(0).getRank()) == Card.getOrderedRank(hand.get(1).getRank())) {
            if(Card.getOrderedRank(hand.get(2).getRank()) == Card.getOrderedRank(hand.get(3).getRank()) ||
                    Card.getOrderedRank(hand.get(3).getRank()) == Card.getOrderedRank(hand.get(4).getRank())) {
                return true;
            }
        }
        else if(Card.getOrderedRank(hand.get(1).getRank()) == Card.getOrderedRank(hand.get(2).getRank())) {
            if(Card.getOrderedRank(hand.get(3).getRank()) == Card.getOrderedRank(hand.get(4).getRank())) {
                return true;
            }
        }
            return false;
    }

    public boolean onePair() {
        boolean isBigPair = false;
        for(int i = 0; i < hand.size() - 1; i++) {
            boolean isPair = hand.get(i).getRank().equals(hand.get(i + 1).getRank());
            if (isPair) {
                if (Card.getOrderedRank(hand.get(i).getRank()) >= 11) {
                    isBigPair = true;
                }
            }
        }
        return isBigPair;
    }

    public boolean hasCard(Card card) {
        for (Card c : hand) {
            if (c.getRank().equals(card.getRank())) {
                return true;    // yes, they have the card
            }
        }

        return false;   // no, they don't
    }
    public void relinquishCard(Player player, Card card) {
        int index = findCard(card);

        if (index != -1) {
            Card c = hand.remove(index);    // remove the card from this player
            player.getHand().add(c);        // add the card to another player

            sortHand();
            player.sortHand();
        }
    }

    public Card getCardByNeed() {
        int index = 0;
        int frequency = 1;

        for (int i = 0; i < hand.size() - 1; i++) {
            int count = 1;

            for (int j = i + 1; j < hand.size(); j++) {
                if (hand.get(i).getRank().equals(hand.get(j).getRank())) {  // tallies cards of the same rank
                    count++;
                }
            }

            if (count > frequency) {    // updates which card is the most frequently occurring
                index = i;
                frequency = count;
            }
        }

        return hand.get(index);
    }

    private int findCard(Card card) {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getRank().equals(card.getRank())) {     // find card by rank
                return i;
            }
        }

        return -1;
    }


    private void sortHand() {
        hand.sort((a, b) -> {
            if (Card.getOrderedRank(a.getRank()) == Card.getOrderedRank(b.getRank())) {
                return Card.getOrderedSuit(a.getSuit()) - Card.getOrderedSuit(b.getSuit());     // order by suit if
            }                                                                                   // ranks are the same

            return Card.getOrderedRank(a.getRank()) - Card.getOrderedRank(b.getRank());         // otherwise, by rank
        });
    }


}