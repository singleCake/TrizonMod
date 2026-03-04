package action;

import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TrizonPlayCardAction extends AbstractTrizonAction {
    private AbstractCard source_card;
    private CardGroup sourceGroup;

    public TrizonPlayCardAction(AbstractCard source_card, AbstractCreature target, CardGroup sourceGroup) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.source = (AbstractCreature)AbstractDungeon.player;
        this.source_card = source_card;
        this.target = target;
        this.sourceGroup = sourceGroup;
    }

    public TrizonPlayCardAction(AbstractCard source_card, CardGroup sourceGroup) {
        this(source_card, null, sourceGroup);
    }
  
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (!this.sourceGroup.contains(source_card)) {
                this.isDone = true;
                return;
            }
            this.sourceGroup.group.remove(source_card);
            (AbstractDungeon.getCurrRoom()).souls.remove(source_card);
            AbstractCard card = source_card.makeSameInstanceOf();
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
