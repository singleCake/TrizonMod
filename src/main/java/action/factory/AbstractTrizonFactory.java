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

    protected static String getDescription(Class<?> factoryClass) {
        return TrizonFactoryStrings.getDescription(factoryClass);
    }

    protected static String[] getExtendedDescription(Class<?> factoryClass) {
        return TrizonFactoryStrings.getExtendedDescription(factoryClass);
    }


    // 用于在调用接口中接收参数
    private void receiveThisCard(TrizonCard card) {
        this.this_card = card;  // 这个目前作为私有接口，在创建卡牌与融合卡牌时分配
    }
    public void receiveTarget(AbstractCreature target) {
        this.target = target;
    }
    public void receiveDamageInfo(DamageInfo info) {}
    public void receiveCard(AbstractCard card) {}

    // 融合工厂列表，用于融合行为
    public static ArrayList<AbstractTrizonFactory> fuseFactories(ArrayList<AbstractTrizonFactory> factories1, ArrayList<AbstractTrizonFactory> factories2) {
        ArrayList<AbstractTrizonFactory> fusedFactories = new ArrayList<>();
        for (AbstractTrizonFactory factory1 : factories1) {
            fusedFactories.add(factory1.clone());
        }

        // 相同的工厂进行融合，不同的工厂直接加入
        for (AbstractTrizonFactory factory1 : fusedFactories) {
            for (AbstractTrizonFactory factory2 : factories2) {
                if (factory1.getClass().equals(factory2.getClass())) {
                    if (!factory1.fuse(factory2)) {
                        fusedFactories.add(factory2.clone());
                    }
                    break;
                }
            }
        }

        for (AbstractTrizonFactory factory2 : factories2) {
            boolean foundMatch = false;
            for (AbstractTrizonFactory factory1 : factories1) {
                if (factory2.getClass().equals(factory1.getClass())) {
                    foundMatch = true;
                    break;
                }
            }
            if (!foundMatch) {
                fusedFactories.add(factory2.clone());
            }
        }

        return fusedFactories;
    }

    public static void factoriesReceiveThisCard(ArrayList<AbstractTrizonFactory> factories, TrizonCard card) {
        for (AbstractTrizonFactory factory : factories) {
            factory.receiveThisCard(card);
        }
    }

    @Override
    public boolean fuse(AbstractTrizonFactory other) {
        return true;
    }
}
