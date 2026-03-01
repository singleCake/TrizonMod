package action.factory;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import card.TrizonCard;
import fusable.Fusable;
import localization.TrizonFactoryStrings;

public abstract class AbstractTrizonFactory implements Fusable<AbstractTrizonFactory> {
    protected TrizonCard this_card = null;  // 触发这个Action的卡牌
    protected AbstractCreature target = null;
    protected int times;
    protected int amount;

    public abstract AbstractGameAction create();

    public abstract String rawDescription();

    public abstract AbstractTrizonFactory clone();

    protected static String getDescription(Class<? extends AbstractTrizonFactory> factoryClass) {
        return TrizonFactoryStrings.getDescription(factoryClass);
    }

    protected static String[] getExtendedDescription(Class<? extends AbstractTrizonFactory> factoryClass) {
        return TrizonFactoryStrings.getExtendedDescription(factoryClass);
    }


    // 用于在调用接口中接收参数
    public void receiveThisCard(TrizonCard card) {
        this.this_card = card;  // 这个目前作为私有接口，在创建卡牌与融合卡牌时分配
    }
    public void receiveTarget(AbstractCreature target) {
        this.target = target;
    }
    public void receiveDamageInfo(DamageInfo info) {}
    public void receiveCard(AbstractCard card) {}

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        return true;
    }
}
