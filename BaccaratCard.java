// TODO: Implement the BaccaratCard class in this file

public class BaccaratCard {
    private Card.Rank r;
    private Card.Suit s;

    public BaccaratCard(Card.Rank r, Card.Suit s){
        this.r = r;
        this.s = s;

    } 

    public Card.Rank getRank(){
        return r;
    }

    public Card.Suit getSuit(){
        return s;
    }

    // Converts to string
    @Override
    public String toString() {
        return "LMAO";
    }

    public boolean equals(BaccaratCard other){
        return true;
    }

    public int compareTo(Card other){
        return 0;
    }

    public int value(){
        return 0;
    }
}