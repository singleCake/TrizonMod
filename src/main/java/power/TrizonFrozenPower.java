package power;

import java.lang.reflect.Field;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.evacipated.cardcrawl.modthespire.lib.SpireInstrumentPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class TrizonFrozenPower extends AbstractPower {
    public static final String POWER_ID = TrizonFrozenPower.class.getSimpleName();
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private byte moveByte;
    private AbstractMonster.Intent moveIntent;
    private EnemyMoveInfo move;

    public boolean isFrozen = false;

    public TrizonFrozenPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;

        String path128 = "TrizonResources/img/powers/TrizonFrozenPower84.png";
        String path48 = "TrizonResources/img/powers/TrizonFrozenPower32.png";
        this.region128 = new AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0]);
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                if (TrizonFrozenPower.this.owner instanceof AbstractMonster) {
                    AbstractMonster mo = (AbstractMonster) TrizonFrozenPower.this.owner;
                    if (mo.getIntentBaseDmg() >= 0) {
                        TrizonFrozenPower.this.moveByte = mo.nextMove;
                        TrizonFrozenPower.this.moveIntent = mo.intent;
                        try {
                            Field f = AbstractMonster.class.getDeclaredField("move");
                            f.setAccessible(true);
                            TrizonFrozenPower.this.move = (EnemyMoveInfo) f.get(mo);
                            EnemyMoveInfo stunMove = new EnemyMoveInfo(TrizonFrozenPower.this.moveByte,
                                    AbstractMonster.Intent.STUN, -1, 0, false);
                            f.set(mo, stunMove);
                            mo.createIntent();
                            TrizonFrozenPower.this.isFrozen = true;
                        } catch (IllegalAccessException | NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }
                }
                this.isDone = true;
            }
        });
    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
    }

    @Override
    public void onRemove() {
        if (this.owner instanceof AbstractMonster && this.isFrozen) {
            AbstractMonster m = (AbstractMonster) this.owner;
            if (this.move != null) {
                m.setMove(this.moveByte, this.moveIntent, this.move.baseDamage, this.move.multiplier,
                        this.move.isMultiDamage);
            } else {
                m.setMove(this.moveByte, this.moveIntent);
            }
            m.createIntent();
            m.applyPowers();
        }
    }

    @SpirePatch(clz = GameActionManager.class, method = "getNextAction")
    public static class GetNextAction {
        @SpireInstrumentPatch
        public static ExprEditor Instrument() {
            return new ExprEditor() {
                public void edit(MethodCall m) throws CannotCompileException {
                    if (m.getClassName().equals(AbstractMonster.class.getName()) && m
                            .getMethodName().equals("takeTurn"))
                        m.replace(
                                "if ($0.hasPower(" + power.TrizonFrozenPower.class.getName() + ".POWER_ID)) {" +
                                    "if (!((" + power.TrizonFrozenPower.class.getName() + ")$0.getPower(" + power.TrizonFrozenPower.class.getName() + ".POWER_ID)).isFrozen) {" +
                                        "$proceed($$);" +
                                    "}" +
                                "} else {" +
                                    "$proceed($$);" +
                                "}");
                }
            };
        }
    }

    @SpirePatch(clz = AbstractMonster.class, method = "rollMove")
    public static class RollMovePatch {
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(AbstractMonster __instance) {
            if (__instance.hasPower(POWER_ID)) {
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
