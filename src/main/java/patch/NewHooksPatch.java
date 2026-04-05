package patch;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import card.AbstractTrizonCard;

import com.megacrit.cardcrawl.cards.CardGroup;


public class NewHooksPatch {
    @SpirePatch(clz = AbstractPlayer.class, method = "damage")
    public static class DamagePatch {
        @SpireInsertPatch(rloc = 47, localvars = { "damageAmount" })
        public static void Insert(AbstractPlayer __instance, DamageInfo info, @ByRef int[] damageAmount) {
            for (AbstractCard c : __instance.hand.group) {
                if (c instanceof AbstractTrizonCard) {
                    damageAmount[0] = ((AbstractTrizonCard<?>) c).triggerOnAttacked(info, damageAmount[0]);
                }
            }
        }
    }

    @SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
    public static class ExhaustPatch {
        @SpirePrefixPatch
        public static void Prefix(CardGroup __instance, AbstractCard c) {
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof AbstractTrizonCard && card.uuid != c.uuid) {
                    ((AbstractTrizonCard<?>) card).triggerOnOtherCardExhausted(c);
                }
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfTurnCards")
    public static class StartOfTurnPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer __instance) {
            System.out.println("start of turn, powers: " + AbstractDungeon.player.powers.size());
            for (AbstractCard c : __instance.exhaustPile.group) {
                if (c instanceof AbstractTrizonCard) {
                    ((AbstractTrizonCard<?>) c).triggerAtStartOfTurnAfterExhausted();
                }
            }
        }
    }

    @SpirePatch(clz = GameActionManager.class, method = "callEndOfTurnActions")
    public static class EndOfTurnPatch {
        @SpirePostfixPatch
        public static void Postfix(GameActionManager __instance) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c instanceof AbstractTrizonCard) {
                    ((AbstractTrizonCard<?>) c).triggerAtEndOfTurn();
                }
            }
            for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                if (c instanceof AbstractTrizonCard) {
                    ((AbstractTrizonCard<?>) c).triggerAtEndOfTurnAfterExhausted();
                }
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfCombatPreDrawLogic")
    public static class StartOfCombatPatch {
        @SpirePrefixPatch
        public static void Prefix(AbstractPlayer __instance) {
            for (AbstractCard c : __instance.drawPile.group) {
                if (c instanceof AbstractTrizonCard) {
                    ((AbstractTrizonCard<?>) c).triggerAtStartOfCombatPreDraw();
                }
            }
        }
    }
}
