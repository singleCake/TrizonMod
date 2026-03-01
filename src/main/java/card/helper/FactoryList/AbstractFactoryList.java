package card.helper.FactoryList;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import action.factory.AbstractTrizonFactory;
import card.TrizonCard;
import fusable.Fusable;

public abstract class AbstractFactoryList implements Fusable<AbstractFactoryList> {
    TrizonCard this_card;
    protected ArrayList<AbstractTrizonFactory> factorys = new ArrayList<>();

    public void clear() {
        factorys.clear();
    }

    public void addFactory(AbstractTrizonFactory factory) {
        factorys.add(factory);
    }

    // 默认行为：直接结算工厂生成的行为
    public void behave() {
        for (AbstractTrizonFactory factory : factorys) {
            this.addToBot(factory.create());
        }
    }

    public AbstractFactoryList clone() {
        AbstractFactoryList copy = null;
        try {
            copy = this.getClass().getDeclaredConstructor().newInstance();
            copy.factorys = new ArrayList<>();
            for (AbstractTrizonFactory factory : this.factorys) {
                copy.addFactory(factory.clone());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return copy;
    }

    public void receiveThisCard(TrizonCard card) {
        this.this_card = card;
        for (AbstractTrizonFactory factory : factorys) {
            factory.receiveThisCard(card);
        }
    }

    @Override
    public boolean fuse(AbstractFactoryList other) {
        // 相同的工厂进行融合，不同的工厂直接加入
        for (AbstractTrizonFactory factory1 : factorys) {
            for (AbstractTrizonFactory factory2 : other.factorys) {
                if (factory1.getClass().equals(factory2.getClass())) {
                    if (!factory1.fuse(factory2)) {
                        factorys.add(factory2.clone());
                    }
                    break;
                }
            }
        }

        for (AbstractTrizonFactory factory2 : other.factorys) {
            boolean foundMatch = false;
            for (AbstractTrizonFactory factory1 : factorys) {
                if (factory2.getClass().equals(factory1.getClass())) {
                    foundMatch = true;
                    break;
                }
            }
            if (!foundMatch) {
                factorys.add(factory2.clone());
            }
        }

        return true;
    }

    protected static final String[] FUSED_CARD_TIMING = CardCrawlGame.languagePack.getUIString("Trizon:FuseCardTiming").TEXT;
    protected static final int USE = 0, EXHAUST = 1, DRAWN = 2, IN_HAND = 3, ATTACK = 4, FROZEN = 5, IN_EXHAUST = 6; 
    public abstract String generateRawDescription();

    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }
}
