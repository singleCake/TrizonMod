package action.factory;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import card.TrizonCard;
import fusable.Fusable;

public abstract class AbstractTrizonFactory implements Fusable<AbstractTrizonFactory> {
    protected AbstractCreature target;
    protected TrizonCard cardPlayed;
    protected int amount;

    public abstract AbstractGameAction create();

    // 用于在调用接口中接收参数
    public void receiveTarget(AbstractCreature target) {
        this.target = target;
    }
    public void receiveCardPlayed(TrizonCard card) {
        this.cardPlayed = card;
    }
    public void receiveDamageInfo(DamageInfo info) {}
    public void receiveCard(AbstractCard card) {}

    // 融合工厂列表，用于融合行为
    public static ArrayList<AbstractTrizonFactory> fuseFactories(ArrayList<AbstractTrizonFactory> factories1, ArrayList<AbstractTrizonFactory> factories2) {
        ArrayList<AbstractTrizonFactory> fusedFactories = new ArrayList<>();
        // 相同的工厂进行融合，不同的工厂直接加入
        for (AbstractTrizonFactory factory1 : factories1) {
            boolean foundMatch = false;
            for (AbstractTrizonFactory factory2 : factories2) {
                if (factory1.getClass().equals(factory2.getClass())) {
                    factory1.fuse(factory2);
                    fusedFactories.add(factory1);
                    foundMatch = true;
                    break;
                }
            }
            if (!foundMatch) {
                fusedFactories.add(factory1);
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
                fusedFactories.add(factory2);
            }
        }

        return fusedFactories;
    }
}
