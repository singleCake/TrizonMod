package patch;

import static card.helper.CardTargeting.CARD;
import static card.helper.SnowballTargeting.CARD_OR_ENEMY;

import com.evacipated.cardcrawl.mod.stslib.cards.targeting.TargetingHandler;
import com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

// 跳过对鼠标y坐标的检测，便于指向手牌
@SpirePatch(clz = CustomTargeting.class, method = "customTargeting")
public class HoverPatch {
    @SpireInsertPatch(rloc = 31)
    public static void Insert1(AbstractPlayer p, TargetingHandler<?> targeting) {
        if (p.hoveredCard.target == CARD || p.hoveredCard.target == CARD_OR_ENEMY) {
            InputHelper.mY += 100.0F * Settings.scale;
        }
    }

    @SpireInsertPatch(rloc = 32)
    public static void Insert2(AbstractPlayer p, TargetingHandler<?> targeting) {
        if (p.hoveredCard.target == CARD || p.hoveredCard.target == CARD_OR_ENEMY) {
            InputHelper.mY -= 100.0F * Settings.scale;
        }
    }
}
