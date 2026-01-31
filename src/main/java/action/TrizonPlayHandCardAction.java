package action;

import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrizonPlayHandCardAction extends AbstractTrizonAction {
    private AbstractCard card;

    public TrizonPlayHandCardAction(AbstractCard card, AbstractCreature target) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.source = (AbstractCreature)AbstractDungeon.player;
        this.card = card;
        this.target = target;
    }

    public TrizonPlayHandCardAction(AbstractCard card) {
        this(card, null); 
    }
  
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (!AbstractDungeon.player.hand.contains(card)) {
                this.isDone = true;
                return;
            }
            AbstractDungeon.player.hand.group.remove(card);
            (AbstractDungeon.getCurrRoom()).souls.remove(card);
            AbstractDungeon.player.limbo.group.add(card);
            card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
            card.target_y = Settings.HEIGHT / 2.0F;
            card.targetAngle = -10.0F;
            card.lighten(false);
            card.drawScale = 0.12F;
            card.targetDrawScale = 0.75F;
            card.applyPowers();
            if (this.target != null)
                addToTop(new NewQueueCardAction(card, this.target, false, true));
            else
                addToTop(new NewQueueCardAction(card, true, false, true));
            addToTop(new UnlimboAction(card));
            if (!Settings.FAST_MODE) {
                addToTop(new WaitAction(Settings.ACTION_DUR_MED));
            } else {
                addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
            } 
        } 
        this.isDone = true;
    }
}
