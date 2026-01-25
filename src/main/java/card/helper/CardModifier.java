package card.helper;

public class CardModifier {
    public CardModifier() {
    }

    public int damage = 0;

    public void addDamage(int amount) {
        this.damage += amount;
    }
}
