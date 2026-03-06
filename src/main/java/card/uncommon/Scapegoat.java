package card.uncommon;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;

import action.factory.TrizonGainBlockActionFactory;
import card.TrizonCard;

public class Scapegoat extends TrizonCard {
    public static final String ID = card.helper.CardHelper.makeID(Scapegoat.class);
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "TrizonResources/img/cards/Scapegoat.png";
    private static final int COST = -2;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Scapegoat() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, RARITY, TARGET);
        this.trizonBooleans.scapegoat = true;
        this.block = this.baseBlock = 8;
        this.selfRetain = true;

        reInitBehavior();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();

            this.reInitBehavior();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

    @Override
    public void setBehavior() {
        if (this.upgraded) {
            this.behavior.addToDrawnBehavior(new TrizonGainBlockActionFactory(baseBlock));
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "damage")
    public static class DamagePatch {
        @SpireInsertPatch(rloc = 52, localvars = { "damageAmount" })
        public static void Insert(AbstractPlayer __instance, DamageInfo info, @ByRef int[] damageAmount) {
            if (damageAmount[0] > 0) {
                for (AbstractCard c : __instance.hand.group) {
                    if (c instanceof TrizonCard) {
                        if (((TrizonCard) c).isScapegoat()) {
                            damageAmount[0] = 0;
                            AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(c, __instance.hand));
                            break;
                        }
                    }
                }
            }
        }
    }
}
