package patch;

import java.util.ArrayList;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.StaticSpireField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;

import card.TrizonCard;
import card.TrizonFusedCard;
import card.helper.Tip.FuseInfoTip;
import card.helper.Tip.TimingTip;

public class TipHelperPatch {
    @SpirePatch(clz = TipHelper.class, method = SpirePatch.CLASS)
    public static class TimingTipsField {
        public static StaticSpireField<ArrayList<TimingTip>> timingTips = new StaticSpireField<>(() -> null);
    }

    @SpirePatch(clz = TipHelper.class, method = "renderTipForCard")
    public static class RenderTipForCardPatch {
        @SpireInsertPatch(rloc = 1)
        public static void Insert(AbstractCard c, SpriteBatch sb, ArrayList<String> keywords) {
            if (c instanceof TrizonFusedCard) {
                TimingTipsField.timingTips.set(((TrizonFusedCard) c).getTimingTips());
            } else {
                TimingTipsField.timingTips.set(null);
            }
        }
    }

    @SpirePatch(clz = TipHelper.class, method = "renderKeywords")
    public static class RenderKeywordsPatch {
        @SpireInsertPatch(rloc = 4)
        public static void Insert(float x, float y, SpriteBatch sb, ArrayList<String> keywords) {
            if (TimingTipsField.timingTips.get() != null) {
                y += (TimingTipsField.timingTips.get().size() - 1) * 62.0F * Settings.scale;
            }
        }

        @SpirePostfixPatch
        public static void Postfix(float x, float y, SpriteBatch sb, ArrayList<String> keywords) {
            if (TimingTipsField.timingTips.get() != null) {
                for (TimingTip tip : TimingTipsField.timingTips.get()) {
                    float textHeight = -FontHelper.getSmartHeight(FontHelper.tipBodyFont, tip.description,
                            280.0F * Settings.scale, 26.0F * Settings.scale) - 7.0F * Settings.scale;
                    try {
                        TIP_TEXT_HEIGHT_FIELD.setFloat(null, textHeight);
                        RENDER_TIP_BOX_METHOD.invoke(null, x, y, sb, tip.title, tip.description);
                    } catch (ReflectiveOperationException e) {
                        throw new RuntimeException("Failed to render timing tip", e);
                    }
                    y -= textHeight + 32.0F * Settings.scale * 3.15F;
                }
            }
        }

        private static final Field TIP_TEXT_HEIGHT_FIELD;
        private static final Method RENDER_TIP_BOX_METHOD;

        static {
            try {
                TIP_TEXT_HEIGHT_FIELD = TipHelper.class.getDeclaredField("textHeight");
                TIP_TEXT_HEIGHT_FIELD.setAccessible(true);
                RENDER_TIP_BOX_METHOD = TipHelper.class.getDeclaredMethod("renderTipBox", float.class, float.class,
                        SpriteBatch.class, String.class, String.class);
                RENDER_TIP_BOX_METHOD.setAccessible(true);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException("Failed to access TipHelper static members", e);
            }
        }
    }

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderTips")
    public static class SingleCardViewPopupRenderTipsPatch {
        private static final String GOLD_CARD_TITLE = CardCrawlGame.languagePack
                .getUIString("Trizon:GoldenCardTip").TEXT[0];
        private static final String GOLD_CARD_DESC = CardCrawlGame.languagePack
                .getUIString("Trizon:GoldenCardTip").TEXT[1];

        @SpireInsertPatch(rloc = 13, localvars = { "t" })
        public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb, @ByRef ArrayList<PowerTip>[] t)
                throws NoSuchFieldException, IllegalAccessException {
            Field cardField = SingleCardViewPopup.class.getDeclaredField("card");
            cardField.setAccessible(true);
            AbstractCard card = (AbstractCard) cardField.get(__instance);
            if (card instanceof TrizonFusedCard) {
                for (TimingTip tip : ((TrizonFusedCard) card).getTimingTips()) {
                    t[0].add(new PowerTip(tip.title, tip.description));
                }
            } else if (card instanceof TrizonCard) {
                FuseInfoTip fuseInfoTip = ((TrizonCard) card).getFuseInfoTip();
                if (fuseInfoTip != null) {
                    t[0].add(0, new PowerTip(FuseInfoTip.TITLE, fuseInfoTip.description));
                }
                if (card instanceof TrizonCard && ((TrizonCard) card).trizonBooleans.gold) {
                    t[0].add(0, new PowerTip(GOLD_CARD_TITLE, GOLD_CARD_DESC));
                }
            }
        }
    }
}
