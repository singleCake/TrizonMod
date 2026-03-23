package patch;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CardRewardScreen;

import card.TrizonCard;

public class GoldenCardPatch {
    @SpirePatch(clz = CardRewardScreen.class, method = "open")
    public static class CardRewardScreenOpenPatch {
        @SpirePrefixPatch
        public static void Prefix(CardRewardScreen __instance, ArrayList<AbstractCard> cards, RewardItem rItem, String header) {
            for (AbstractCard card : cards) {
                if (card instanceof TrizonCard && card.rarity == AbstractCard.CardRarity.RARE) {
                    if (AbstractDungeon.cardRandomRng.randomBoolean(0.05f)) {
                        ((TrizonCard) card).trizonBooleans.gold = true;
                        ((TrizonCard) card).setBgTexture();
                    }
                }
            }
        }
    }
}
