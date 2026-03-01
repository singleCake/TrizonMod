package card.helper.FactoryList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import action.factory.AbstractTrizonFactory;
import card.helper.CardTargeting;
import card.helper.SnowballTargeting;

import static card.helper.SnowballTargeting.CARD_OR_ENEMY;
import static card.helper.CardTargeting.CARD;

public class UseActionFactoryList extends AbstractFactoryList {
    public UseActionFactoryList() {
    }

    public void behave(AbstractPlayer p, AbstractMonster m) {
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
        } else {
            for (AbstractTrizonFactory factory : factorys) {
                factory.receiveTarget(m);
                this.addToBot(factory.create());
            }
        }
    }

    @Override
    public String generateRawDescription() {
        String useDescription = "";
        for (AbstractTrizonFactory factory : factorys) {
            useDescription += factory.rawDescription() + " NL ";
        }
        return useDescription;
    }
}
