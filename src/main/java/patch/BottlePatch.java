package patch;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.relics.BottledFlame;
import com.megacrit.cardcrawl.relics.BottledLightning;
import com.megacrit.cardcrawl.relics.BottledTornado;

import card.TrizonFusedCard;

public class BottlePatch {
    @SpirePatch(clz = BottledFlame.class, method = "onEquip")
    public static class BottledFlameOnEquip {
        @SpireInsertPatch(rloc = 8)
        public static SpireReturn<Void> Insert(BottledFlame __instance) {
            CardGroup toBottle = AbstractDungeon.player.masterDeck.getPurgeableCards().getAttacks();
            ArrayList<AbstractCard> toRemove = new ArrayList<>();
            for (AbstractCard c : toBottle.group) {
                if (c instanceof TrizonFusedCard) {
                    toRemove.add(c);
                }
            }
            toBottle.group.removeAll(toRemove);

            AbstractDungeon.gridSelectScreen.open(toBottle, 1, __instance.DESCRIPTIONS[1] + __instance.name + LocalizedStrings.PERIOD, false, false, false, false);
            return SpireReturn.Return();
        }
    }

    @SpirePatch(clz = BottledLightning.class, method = "onEquip")
    public static class BottledLightningOnEquip {
        @SpireInsertPatch(rloc = 8)
        public static SpireReturn<Void> Insert(BottledLightning __instance) {
            CardGroup toBottle = AbstractDungeon.player.masterDeck.getPurgeableCards().getSkills();
            ArrayList<AbstractCard> toRemove = new ArrayList<>();
            for (AbstractCard c : toBottle.group) {
                if (c instanceof TrizonFusedCard) {
                    toRemove.add(c);
                }
            }
            toBottle.group.removeAll(toRemove);

            AbstractDungeon.gridSelectScreen.open(toBottle, 1, __instance.DESCRIPTIONS[1] + __instance.name + LocalizedStrings.PERIOD, false, false, false, false);
            return SpireReturn.Return();
        }
    }

    @SpirePatch(clz = BottledTornado.class, method = "onEquip")
    public static class BottledTornadoOnEquip {
        @SpireInsertPatch(rloc = 8)
        public static SpireReturn<Void> Insert(BottledTornado __instance) {
            CardGroup toBottle = AbstractDungeon.player.masterDeck.getPurgeableCards().getPowers();
            ArrayList<AbstractCard> toRemove = new ArrayList<>();
            for (AbstractCard c : toBottle.group) {
                if (c instanceof TrizonFusedCard) {
                    toRemove.add(c);
                }
            }
            toBottle.group.removeAll(toRemove);

            AbstractDungeon.gridSelectScreen.open(toBottle, 1, __instance.DESCRIPTIONS[1] + __instance.name + LocalizedStrings.PERIOD, false, false, false, false);
            return SpireReturn.Return();
        }
    }
}
