package character;

import basemod.abstracts.CustomPlayer;
import card.basic.Bear;
import card.basic.Defend;
import card.basic.Meat;
import card.basic.Strike;
import modcore.TrizonMod;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;

import static modcore.TrizonMod.PlayerColorEnum.Trizon_COLOR;
import static modcore.TrizonMod.PlayerColorEnum.Trizon;

public class Marry extends CustomPlayer  {
    private static final String MY_CHARACTER_SHOULDER_1 = "TrizonResources/img/char/Marry/shoulder1.png";
    private static final String MY_CHARACTER_SHOULDER_2 = "TrizonResources/img/char/Marry/shoulder2.png";
    private static final String CORPSE_IMAGE = "TrizonResources/img/char/Marry/corpse.png";
    private static final String[] ORB_TEXTURES = new String[]{
            "TrizonResources/img/UI/orb/layer5.png",
            "TrizonResources/img/UI/orb/layer4.png",
            "TrizonResources/img/UI/orb/layer3.png",
            "TrizonResources/img/UI/orb/layer2.png",
            "TrizonResources/img/UI/orb/layer1.png",
            "TrizonResources/img/UI/orb/layer6.png",
            "TrizonResources/img/UI/orb/layer5d.png",
            "TrizonResources/img/UI/orb/layer4d.png",
            "TrizonResources/img/UI/orb/layer3d.png",
            "TrizonResources/img/UI/orb/layer2d.png",
            "TrizonResources/img/UI/orb/layer1d.png"
    };

    private static final float[] LAYER_SPEED = new float[]{-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};

    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("KGPRmod:Marry");

    public Marry(String name)  {
        super(name, Trizon, ORB_TEXTURES,"TrizonResources/img/UI/orb/vfx.png", LAYER_SPEED, null, null);

        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 150.0F * Settings.scale);

        this.initializeClass(
                "TrizonResources/img/char/Marry/character.png",
                MY_CHARACTER_SHOULDER_2, MY_CHARACTER_SHOULDER_1,
                CORPSE_IMAGE,
                this.getLoadout(),
                0.0F, 0.0F,
                200.0F, 320.0F,
                new EnergyManager(3)
        );

    }
    
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        for (int x = 0; x < 4; x++) {
            retVal.add(Strike.ID);
        }
        for (int x = 0; x < 3; x++) {
            retVal.add(Defend.ID);
        }
        retVal.add(Meat.ID);
        retVal.add(Bear.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        return retVal;
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(
                characterStrings.NAMES[0],
                characterStrings.TEXT[0],
                70,
                70,
                0,
                99,
                5,
                this,
                this.getStartingRelics(),
                this.getStartingDeck(),
                false
        );
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Trizon_COLOR;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Meat();
    }

    @Override
    public Color getCardTrailColor() {
        return TrizonMod.TrizonColor;
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 5;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public ArrayList<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel("TrizonResources/img/char/Victory1.png", "ATTACK_MAGIC_FAST_1"));
        panels.add(new CutscenePanel("TrizonResources/img/char/Victory2.png"));
        panels.add(new CutscenePanel("TrizonResources/img/char/Victory3.png"));
        return panels;
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_HEAVY";
    }

    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Marry(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    @Override
    public Color getSlashAttackColor() {
        return TrizonMod.TrizonColor;
    }

    @Override
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[1];
    }

    @Override
    public Color getCardRenderColor() {
        return TrizonMod.TrizonColor;
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }
}
