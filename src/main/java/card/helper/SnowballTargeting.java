package card.helper;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.cards.targeting.TargetingHandler;
import com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SnowballTargeting extends TargetingHandler<card.helper.SnowballTargeting.SnowballTarget> {
    @SpireEnum
    public static AbstractCard.CardTarget CARD_OR_ENEMY;

    private static SnowballTarget getTarget(AbstractCard card) {
        return CustomTargeting.getCardTarget(card);
    }

    public static AbstractCard getTargetCard(AbstractCard card) {
        return getTarget(card).card;
    }

    public static AbstractCreature getTargetCreature(AbstractCard card) {
        return getTarget(card).creature;
    }

    private AbstractCard hoveredCard = null;
    private AbstractCreature hoveredCreature = null;

    @Override
    public boolean hasTarget() {
        return hoveredCard != null || hoveredCreature != null;
    }

    @Override
    public void updateHovered() {
        hoveredCard = null;
        hoveredCreature = null;

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.hb.hovered) {
                hoveredCard = c;
                return;
            }
        }

        for (AbstractCreature m : AbstractDungeon.getMonsters().monsters) {
            if (m.hb.hovered) {
                hoveredCreature = m;
                return;
            }
        }
    }

    @Override
    public SnowballTarget getHovered() {
        return new SnowballTarget(hoveredCard, hoveredCreature);
    }

    @Override
    public void clearHovered() {
        hoveredCard = null;
        hoveredCreature = null;
    }

    @Override
    public void renderReticle(SpriteBatch sb) {
        if (hoveredCard != null) {
            
        }

        if (hoveredCreature != null) {
            hoveredCreature.renderReticle(sb);
        }
    }

    @Override
    public void setDefaultTarget() {
        hoveredCreature = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
    }
    @Override
    public int getDefaultTargetX() {
        return (int) AbstractDungeon.getMonsters().monsters.get(0).hb.cX;
    }
    @Override
    public int getDefaultTargetY() {
        return (int) AbstractDungeon.getMonsters().monsters.get(0).hb.cY;
    }

    public class SnowballTarget {
        public AbstractCard card;
        public AbstractCreature creature;

        public SnowballTarget(AbstractCard card, AbstractCreature creature) {
            this.card = card;
            this.creature = creature;
        }
    }
}
