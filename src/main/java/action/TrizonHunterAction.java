package action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class TrizonHunterAction extends AbstractTrizonAction {
    private AbstractCreature target;
    private AbstractCard cardToModify;

    public TrizonHunterAction(AbstractCreature target, AbstractCard card) {
        this.target = target;
        this.cardToModify = card;
    }

    @Override
    public void update() {
        if (this.target == null || !(this.target instanceof AbstractMonster)) {
            this.isDone = true;
            return;
        }
        if (((AbstractMonster) this.target).isDying || this.target.currentHealth <= 0) {
            this.cardToModify.freeToPlayOnce = true;
            this.addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if (AbstractDungeon.player.discardPile.contains(cardToModify)
                            && AbstractDungeon.player.hand.size() < 10) {
                        AbstractDungeon.player.hand.addToHand(cardToModify);
                        cardToModify.unhover();
                        cardToModify.setAngle(0.0F, true);
                        cardToModify.lighten(false);
                        cardToModify.drawScale = 0.12F;
                        cardToModify.targetDrawScale = 0.75F;
                        cardToModify.applyPowers();
                        AbstractDungeon.player.discardPile.removeCard(cardToModify);
                    }
                    
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.glowCheck();
                    this.isDone = true;
                }
            });
        }
        this.isDone = true;
    }
}
