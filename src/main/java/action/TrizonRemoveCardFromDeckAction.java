package action;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;

public class TrizonRemoveCardFromDeckAction extends AbstractTrizonAction {
    AbstractCard card;
    
    public TrizonRemoveCardFromDeckAction(AbstractCard card) {
        this.card = card;
    }

    @Override
    public void update() {
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.uuid.equals(card.uuid)) {
                AbstractDungeon.player.masterDeck.removeCard(c);
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, Settings.WIDTH / 3.0F * 2.0F, Settings.HEIGHT / 2.0F));
                break;
            }
        }

        this.isDone = true;
    }
}
