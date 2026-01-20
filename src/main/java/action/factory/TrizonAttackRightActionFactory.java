package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

import action.TrizonAttackRightAction;
import card.TrizonCard;

public class TrizonAttackRightActionFactory extends AbstractTrizonFactory {
    private int damage;
    private int times;

    public TrizonAttackRightActionFactory(TrizonCard cardPlayed, int damage, int times) {
        this.cardPlayed = cardPlayed;
        this.damage = damage;
        this.times = times;
    }

    @Override
    public AbstractGameAction create() {
        return new TrizonAttackRightAction(cardPlayed, damage, times);
    }
    
    @Override
    public void fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonAttackRightActionFactory) {
            TrizonAttackRightActionFactory o = (TrizonAttackRightActionFactory) other;
            this.damage += o.damage;
            this.times = Math.max(this.times, o.times);
        }
    }
}
