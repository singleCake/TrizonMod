package patch;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.cards.CardGroup;

import card.TrizonCard;

public class NewConnectorPatch {
    @SpirePatch(clz = AbstractPlayer.class, method = "damage")
    public static class DamagePatch {
        @SpireInsertPatch(rloc = 47, localvars = {"damageAmount"})
        public static void Insert(AbstractPlayer __instance, DamageInfo info, @ByRef int[] damageAmount) {
            for (AbstractCard c : __instance.hand.group) {
                if (c instanceof TrizonCard) {
                    ((TrizonCard) c).triggerOnAttacked(info, damageAmount[0]);
                }
            }
        }
    }

    @SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
    public static class ExhaustPatch {
        @SpirePrefixPatch
        public static void Prefix(CardGroup __instance, AbstractCard c) {
            for (AbstractCard card : AbstractDungeon.player.hand.group) {
                if (card instanceof TrizonCard && card.uuid != c.uuid) {
                    ((TrizonCard) card).triggerOnOtherCardExhausted(c);
                }
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfTurnCards")
    public static class StartOfTurnPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer __instance) {
            for (AbstractCard c : __instance.exhaustPile.group) {
                if (c instanceof TrizonCard) {
                    ((TrizonCard) c).triggerAtStartOfTurnAfterExhausted();
                }
            }
        }
    }

    @SpirePatch(clz = AbstractCreature.class, method = "applyEndOfTurnTriggers")
    public static class EndOfTurnPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractCreature __instance) {
            for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                if (c instanceof TrizonCard) {
                    ((TrizonCard) c).triggerAtEndOfTurnAfterExhausted();
                }
            }
        }
    }
}
