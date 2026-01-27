package power;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import power.helper.PowerHelper;

public class TrizonShieldPower extends AbstractPower {
    public static final String POWER_ID = PowerHelper.makeID(TrizonShieldPower.class);
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TrizonShieldPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        
        String path128 = "TrizonResources/img/powers/TrizonShieldPower84.png";
        String path48 = "TrizonResources/img/powers/TrizonShieldPower32.png";
        this.region128 = new AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0]);
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "damage")
    public static class ShieldPowerPatch {
        @SpireInsertPatch(rloc = 1, localvars = {"damageAmount"})
        public static void Insert(AbstractPlayer __instance, DamageInfo info, @ByRef int[] damageAmount) {
            if (__instance.hasPower(POWER_ID)) {
                __instance.getPower(POWER_ID).flash();
                damageAmount[0] = 0;
                AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(__instance, __instance, POWER_ID));
            }
        }
    }
}
