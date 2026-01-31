package card.helper;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.cards.targeting.TargetingHandler;
import com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CardTargeting extends TargetingHandler<AbstractCard> {
    @SpireEnum
    public static AbstractCard.CardTarget CARD;

    public static AbstractCard getTarget(AbstractCard card) {
        return CustomTargeting.getCardTarget(card);
    }

    private AbstractCard hovered = null;

    @Override
    public boolean hasTarget() {
        return hovered != null;
    }

    @Override
    public void updateHovered() {
        hovered = null;

        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c == AbstractDungeon.player.hoveredCard) {
                continue;
            }
            if (c.isHoveredInHand(1.0f)) {
                hovered = c;
                return;
            }
        }
    }

    @Override
    public AbstractCard getHovered() {
        return hovered;
    }

    @Override
    public void clearHovered() {
        hovered = null;
    }

    @Override
    public void renderReticle(SpriteBatch sb) {
        if (hovered != null) {
            AbstractCard preview = hovered.makeSameInstanceOf();
            preview.drawScale = hovered.drawScale;
            preview.current_x = hovered.current_x;
            preview.current_y = hovered.current_y + AbstractCard.IMG_HEIGHT;
            preview.render(sb);
        }
    }

    @Override
    public void setDefaultTarget() {
        hovered = AbstractDungeon.player.hand.getTopCard();
    }
    @Override
    public int getDefaultTargetX() {
        return (int) AbstractDungeon.player.hand.getTopCard().hb.cX;
    }
    @Override
    public int getDefaultTargetY() {
        return (int) AbstractDungeon.player.hand.getTopCard().hb.cY;
    }
}
