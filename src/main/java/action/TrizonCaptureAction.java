package action;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

public class TrizonCaptureAction extends AbstractTrizonAction {
    AbstractCard card;

    public TrizonCaptureAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        AbstractDungeon.topLevelEffects.add(new ShowCardAndObtainEffect(card.makeStatEquivalentCopy(), Settings.WIDTH / 3.0F * 2.0F, Settings.HEIGHT / 2.0F));
        this.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
        this.isDone = true;
    }
}
