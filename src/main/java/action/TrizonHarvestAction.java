package action;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import card.AbstractTrizonCard;

public class TrizonHarvestAction extends AbstractTrizonAction {
    public TrizonHarvestAction(AbstractTrizonCard<?> cardPlayed, int amount) {
        this.this_card = cardPlayed;
        this.amount = amount;
    }

    @Override
    public void update() {
        for (int i = AbstractDungeon.getCurrRoom().monsters.monsters.size() - 1; i >= 0; i--) {
            if (!((AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).isDying &&
                    ((AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).currentHealth > 0 &&
                    !((AbstractDungeon.getCurrRoom()).monsters.monsters.get(i)).isEscaping) {
                this.addToTop(new TrizonSpellAction(this.this_card, (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i), amount, 1, AttackEffect.FIRE));
            }
        }

        this.addToTop(new TrizonSpellAction(this.this_card, AbstractDungeon.player, amount, 1, AttackEffect.FIRE));
        this.isDone = true;
    }
}
