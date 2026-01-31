package action;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrizonRandomPlayAttackAction extends AbstractTrizonAction {
    public TrizonRandomPlayAttackAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> attackCards = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                attackCards.add(c);
            }
        }

        for (int i = 0; i < this.amount; i++) {
            if (attackCards.size() == 0) {
                break;
            }
            int randomIndex = AbstractDungeon.cardRandomRng.random(attackCards.size() - 1);
            AbstractCard randomAttackCard = attackCards.get(randomIndex);
            attackCards.remove(randomIndex);
            this.addToTop(new TrizonPlayHandCardAction(randomAttackCard));
        }
        this.isDone = true;
    }
}
