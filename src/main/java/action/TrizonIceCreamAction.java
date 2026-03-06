package action;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import patch.FreezeCardPatch.FrozenField;

public class TrizonIceCreamAction extends AbstractTrizonAction {
    public TrizonIceCreamAction(int times) {
        this.times = times;
    }

    @Override
    public void actionRepeat() {
        int draw = 0;

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (FrozenField.frozen.get(c)) {
                draw++;
            }
        }

        this.addToTop(new DrawCardAction(draw));
    }
}
