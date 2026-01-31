package action;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import card.TrizonCard;
import power.TrizonFrozenPower;

public class TrizonFreezeEnemyAction extends AbstractTrizonAction {
    public TrizonFreezeEnemyAction(AbstractCreature target) {
        this.target = target;
    }

    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (c instanceof TrizonCard) {
                ((TrizonCard) c).triggerOnEnemyFrozenAfterExhausted();
            }
        }
        this.addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new TrizonFrozenPower(target)));
    }
}
