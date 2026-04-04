package patch;

import static modcore.TrizonMod.PlayerColorEnum.Trizon;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.custom.CustomModeScreen;

public class EndlessTipPatch {
    private static final String[] TEXT = CardCrawlGame.languagePack.getUIString("Trizon:EndlessTip").TEXT;
    
    @SpirePatch(clz = CustomModeScreen.class, method = "renderScreen")
    public static class EndlessTipRenderPatch {
        @SpirePostfixPatch
        public static void Postfix(CustomModeScreen __instance, SpriteBatch sb, float ___scrollY) {
            if (CardCrawlGame.chosenCharacter == Trizon) {
                FontHelper.renderSmartText(sb, FontHelper.panelNameFont, TEXT[0],
                        CustomModeScreen.screenX + 500.0F * Settings.scale,
                        ___scrollY + 780.0F * Settings.scale, Settings.GOLD_COLOR);
            }
        }
    }
}
