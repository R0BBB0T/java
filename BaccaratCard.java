public class BaccaratCard extends Card {

  public BaccaratCard(Rank r, Suit s) {
    super(r, s);
  }

  public Rank getRank() {
    return super.getRank();
  }

  public Suit getSuit() {
    return super.getSuit();
  }

  @Override
  public String toString() {
    return super.toString();
  }

  public boolean equals(Object other) {
    if (other instanceof BaccaratCard) {
      BaccaratCard card = (BaccaratCard) other;
      return this.getRank() == card.getRank() && this.getSuit() == card.getSuit();
    }
    return false;
  }

  public int compareTo(Card other) {
    return super.compareTo(other);
  }

  public int value() {
    int number = super.value();
    if (number == 10){
        number = 0;
    }
    return number;
  }
}