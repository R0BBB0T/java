import java.util.ArrayList;
import java.util.List;

public class BaccaratHand extends CardCollection{
    
    public BaccaratHand() {
        cards = new ArrayList<>();
    }
    
    public int size() {
        return cards.size();
    }
    
    public void add(BaccaratCard card) {
        cards.add(card);
    }
    
    public int value() {
        int totalValue = 0;
        for (Card card : cards) {
            totalValue += card.value();
        }
        return totalValue % 10;
    }
    
    public boolean isNatural() {
        return cards.size() == 2 && value() >= 8;
    }
    
    @Override
    public String toString() {
        int i = 0;
        String string = "";
        for (Card card : cards) {
            string = string + card.getRank().getSymbol() + card.getSuit().getSymbol();
            i++;
            if (i < cards.size()){
                string = string + " ";
            }
        }
        return string;
    }
}