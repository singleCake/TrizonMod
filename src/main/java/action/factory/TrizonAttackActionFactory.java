package action.factory;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import action.TrizonAttackAction;
import card.TrizonCard;

public class TrizonAttackActionFactory extends AbstractTrizonFactory {
    private int damage;
    private int times;

    public TrizonAttackActionFactory(TrizonCard cardPlayed, AbstractCreature target, int damage, int times) {
        this.cardPlayed = cardPlayed;
        this.target = target;
        this.damage = damage;
        this.times = times;
    }
    
    @Override
    public AbstractGameAction create() {
        return new TrizonAttackAction(cardPlayed, target, damage, times);
    }

    @Override
    public void fuse(AbstractTrizonFactory other) {
        if (other instanceof TrizonAttackActionFactory) {
            TrizonAttackActionFactory o = (TrizonAttackActionFactory) other;
            this.damage += o.damage;
            this.times = Math.max(this.times,  o.times);
        }
    }
}
