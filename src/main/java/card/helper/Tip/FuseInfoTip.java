package card.helper.Tip;

import com.megacrit.cardcrawl.core.CardCrawlGame;

public class FuseInfoTip {
    public static final String TITLE = CardCrawlGame.languagePack.getUIString("Trizon:FuseInfoTip").TEXT[0];
    public String description;

    public FuseInfoTip(String description) {
        this.description = description;
    }
}
