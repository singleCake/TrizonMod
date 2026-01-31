package patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import action.TrizonYandereAction;

public class YanderePatch {
    @SpirePatch(clz = AbstractPlayer.class, method = "damage") 
    public static class LoseHpPatch {
        @SpireInsertPatch(rloc = 100)
        public static void Insert(AbstractPlayer __instance, com.megacrit.cardcrawl.cards.DamageInfo info) {
            AbstractDungeon.actionManager.addToTop(new TrizonYandereAction());
        }
    }

    @SpirePatch(clz = AbstractCreature.class, method = "heal", paramtypez = {int.class, boolean.class})
    public static class HealPatch {
        @SpireInsertPatch(rloc = 9)
        public static void Insert(AbstractCreature __instance, int healAmount, boolean showEffect) {
            if (__instance.isPlayer) {
                AbstractDungeon.actionManager.addToTop(new TrizonYandereAction());
            }
        }
    }
}
