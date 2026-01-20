package modcore;

import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.helpers.CardLibrary;

import basemod.BaseMod;
import basemod.interfaces.EditCharactersSubscriber;

@SpireInitializer
public class TrizonMod implements EditCharactersSubscriber {
    public TrizonMod() {
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        new TrizonMod();
    }

    public void receiveEditCharacters() {}

    public static class PlayerColorEnum {
        @SpireEnum
        public static PlayerClass Trizon;

        @SpireEnum
        public static AbstractCard.CardColor Trizon_COLOR;
    }

    public static class PlayerLibraryEnum {
        @SpireEnum
        public static CardLibrary.LibraryType Trizon_COLOR;
    }
}