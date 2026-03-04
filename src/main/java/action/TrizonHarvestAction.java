package action;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import card.TrizonCard;

public class TrizonHarvestAction extends AbstractTrizonAction {
    TrizonCard cardPlayed;

    public TrizonHarvestAction(TrizonCard cardPlayed, int amount) {
        this.cardPlayed = cardPlayed;
        this.amount = amount;
    }

    @Override
    public void update() {
        for (int i = AbstractDungeon.getCurrRoom().monsters.monsters.size() - 1; i >= 0; i--) {
            if (!((AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).isDying &&
                    ((AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).currentHealth > 0 &&
                    !((AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).isEscaping) {
                this.addToTop(new TrizonSpellAction(cardPlayed, (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i), amount, 1, AttackEffect.FIRE));
            }
        }

        this.addToTop(new TrizonSpellAction(cardPlayed, AbstractDungeon.player, amount, 1, AttackEffect.FIRE));
        this.isDone = true;
    }
}
