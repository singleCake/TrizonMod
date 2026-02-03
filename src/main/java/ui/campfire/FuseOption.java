package ui.campfire;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;

public class FuseOption extends AbstractCampfireOption {
    public static final String ID = "Trizon:FuseOption";

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);

    private static final String[] TEXT = uiStrings.TEXT;

    public FuseOption() {
        this.label = TEXT[0];
        updateUsability(true);
    }

    public void updateUsability(boolean canUse) {
        this.description = TEXT[1];
        this.img = ImageMaster.loadImage("TrizonResources/img/ui/campfire/fuse_option.png");
    }

    public void useOption() {
        FuseCampfireUI.switchMode(true);
    }
}
