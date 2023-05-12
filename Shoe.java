import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shoe extends CardCollection {

    public Shoe(int decks) {
        super();
        if (decks != 6 && decks != 8){
            throw new CardException(null);
        }
        // Create the shoe by adding the specified number of decks
        for (int i = 0; i < decks; i++) {
            for (Card.Suit suit : Card.Suit.values()) {
                for (Card.Rank rank : Card.Rank.values()) {
                    Card card = new BaccaratCard(rank, suit);
                    cards.add(card);
                }
            }
        }
    }

    public int size() {
        return super.size();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card deal() {
        if (cards.isEmpty()) {
            throw new CardException(null);
        }
        return cards.remove(0);
    }
}