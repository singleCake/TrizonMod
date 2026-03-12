package card.helper.FactoryList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import action.factory.AbstractTrizonFactory;
import card.helper.Targeting.CardTargeting;
import card.helper.Targeting.SnowballTargeting;
import card.helper.Tip.TimingTip;

import com.evacipated.cardcrawl.mod.stslib.cards.targeting.SelfOrEnemyTargeting;

import static card.helper.Targeting.CardTargeting.CARD;
import static card.helper.Targeting.SnowballTargeting.CARD_OR_ENEMY;
import static com.evacipated.cardcrawl.mod.stslib.cards.targeting.SelfOrEnemyTargeting.SELF_OR_ENEMY;


public class UseActionFactoryList extends AbstractFactoryList {
    public UseActionFactoryList() {
    }

    public void behave(AbstractPlayer p, AbstractMonster m) {
        if (this_card == null) {
            System.out.println("Error: this_card is null in UseActionFactoryList");
            return;
        }
        // 根据卡牌目标结算行为
        if (this_card.target == CARD) {
            AbstractCard card = CardTargeting.getTarget(this_card);
            for (AbstractTrizonFactory factory : factorys) {
                factory.receiveCard(card);
                this.addToBot(factory.create());
            }
        }
        else if (this_card.target == CARD_OR_ENEMY) {
            AbstractCard card = SnowballTargeting.getTargetCard(this_card);
            AbstractCreature creature = SnowballTargeting.getTargetCreature(this_card);
            for (AbstractTrizonFactory factory : factorys) {
                factory.receiveCard(card);
                factory.receiveTarget(creature);
                this.addToBot(factory.create());
            }
        }
        else if (this_card.target == SELF_OR_ENEMY) {
            AbstractCreature target = SelfOrEnemyTargeting.getTarget(this_card);
            for (AbstractTrizonFactory factory : factorys) {
                factory.receiveTarget(target);
                this.addToBot(factory.create());
            }
        } else {
            for (AbstractTrizonFactory factory : factorys) {
                if (m != null) {
                    factory.receiveTarget(m);
                }
                this.addToBot(factory.create());
            }
        }
    }

    @Override
    public String generateRawDescription() {
        return buildFactoriesDescription() + " NL ";
    }

    @Override
    public TimingTip generateTimingTip() {
        String description = buildFactoriesDescription();
        if (description.equals(""))
            return null;
        return new TimingTip(FUSED_CARD_TIMING[USE], description);
    }
}
