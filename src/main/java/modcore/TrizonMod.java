package modcore;

import static modcore.TrizonMod.PlayerColorEnum.Trizon;

import java.nio.charset.StandardCharsets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.Keyword;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import card.basic.Meat;
import card.common.Bird;
import character.Shan;
import card.helper.CardTargeting;
import card.helper.SnowballTargeting;
import static card.helper.SnowballTargeting.CARD_OR_ENEMY;
import static card.helper.CardTargeting.CARD;

@SpireInitializer
public class TrizonMod implements 
    PostInitializeSubscriber, 
    EditCardsSubscriber, 
    EditRelicsSubscriber,
    EditKeywordsSubscriber, 
    EditStringsSubscriber, 
    EditCharactersSubscriber {
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

    private static final String BG_ATTACK_512 = "TrizonResources/img/512/bg_attack_512.png";
    private static final String BG_POWER_512 = "TrizonResources/img/512/bg_power_512.png";
    private static final String BG_SKILL_512 = "TrizonResources/img/512/bg_skill_512.png";
    private static final String small_orb = "TrizonResources/img/char/small_orb.png";
    private static final String BG_ATTACK_1024 = "TrizonResources/img/1024/bg_attack.png";
    private static final String BG_POWER_1024 = "TrizonResources/img/1024/bg_power.png";
    private static final String BG_SKILL_1024 = "TrizonResources/img/1024/bg_skill.png";
    private static final String big_orb = "TrizonResources/img/char/card_orb.png";
    private static final String energy_orb = "TrizonResources/img/char/cost_orb.png";

    public static final Color TrizonColor = new Color(0.46f, 0.39f, 0.38f, 1.0f); // 766462

    public TrizonMod() {
        BaseMod.subscribe(this);

        BaseMod.addColor(PlayerColorEnum.Trizon_COLOR, TrizonColor, TrizonColor, TrizonColor,
            TrizonColor, TrizonColor, TrizonColor, TrizonColor, BG_ATTACK_512,
            BG_SKILL_512, BG_POWER_512, energy_orb, BG_ATTACK_1024,
            BG_SKILL_1024, BG_POWER_1024, big_orb, small_orb
        );
    }

    public static void initialize() {
        new TrizonMod();
    }

    @Override
    public void receivePostInitialize() {
        CustomTargeting.registerCustomTargeting(CARD_OR_ENEMY, new SnowballTargeting());
        CustomTargeting.registerCustomTargeting(CARD, new CardTargeting());
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new Shan(CardCrawlGame.playerName), 
        "TrizonResources/img/char/Character_Button.png", 
        "TrizonResources/img/char/Character_Portrait.png",
        Trizon);
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd("TrizonMod")
        .packageFilter(Meat.class) 
        .setDefaultSeen(true) 
        .cards();
        new AutoAdd("TrizonMod")
        .packageFilter(Bird.class) 
        .setDefaultSeen(true) 
        .cards();
    }

    @Override
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new relics.FuseRelic(), PlayerColorEnum.Trizon_COLOR);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "ZHS";

        String json = Gdx.files.internal("TrizonResources/localization/" + lang + "/keywords.json")
                .readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword("trizon", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditStrings() {
        String lang = "ZHS";
        BaseMod.loadCustomStringsFile(CardStrings.class, "TrizonResources/localization/" + lang + "/cards.json"); 
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "TrizonResources/localization/" + lang + "/characters.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "TrizonResources/localization/" + lang + "/powers.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "TrizonResources/localization/" + lang + "/relics.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, "TrizonResources/localization/" + lang + "/ui.json");
    }
}