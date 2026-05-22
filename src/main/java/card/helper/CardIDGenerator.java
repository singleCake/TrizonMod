package card.helper;

import java.io.IOException;
import java.util.Properties;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import basemod.BaseMod;
import basemod.interfaces.ISubscriber;
import card.TrizonFusedCard;

public class CardIDGenerator implements ISubscriber {
    public static CardIDGenerator Inst = new CardIDGenerator();

    private int nextID = 0;

    private CardIDGenerator() {
        BaseMod.subscribe(this);
        loadFromConfig();
    }

    public String generateID() {
        String id = String.valueOf(nextID);
        nextID++;
        saveToConfig();
        return id;
    }

    private void loadFromConfig() {
        try {
            Properties defaults = new Properties();
            defaults.setProperty("nextID", String.valueOf(nextID));

            SpireConfig config = new SpireConfig("TrizonMod", "CardIDGeneratorConfig", defaults);
            nextID = config.getInt("nextID");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToConfig() {
        try {
            SpireConfig config = new SpireConfig("TrizonMod", "CardIDGeneratorConfig");
            config.setInt("nextID", nextID);
            config.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SpirePatch(clz = CardLibrary.class, method  = "getCard", paramtypez = {String.class})
    public static class GetCardPatch {
        @SpirePrefixPatch
        public static SpireReturn<AbstractCard> Prefix(String cardID) {
            if (cardID.startsWith(TrizonFusedCard.ID)) {
                return SpireReturn.Return(new TrizonFusedCard());
            }
            return SpireReturn.Continue();
        }
    }

    @SpirePatch(clz = CardLibrary.class, method  = "getCard", paramtypez = {AbstractPlayer.PlayerClass.class, String.class})
    public static class GetCardPatch2 {
        @SpirePrefixPatch
        public static SpireReturn<AbstractCard> Prefix(AbstractPlayer.PlayerClass plyrClass, String cardID) {
            if (cardID.startsWith(TrizonFusedCard.ID)) {
                return SpireReturn.Return(new TrizonFusedCard());
            }
            return SpireReturn.Continue();
        }
    }
}
