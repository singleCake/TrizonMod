package patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;

import relics.FuseRelic;
import ui.campfire.FuseCampfireUI;

public class CampfireFusePatch {

    @SpirePatch(clz = RestRoom.class, method = "onPlayerEntry")
    public static class RestRoomOnPlayerEntryPatch {
        public static void Postfix(RestRoom __instance) {
            if (AbstractDungeon.player.hasRelic(FuseRelic.ID)) {
                FuseCampfireUI.initFuseUI(__instance.campfireUI);
            }
        }
    }

    @SpirePatch(clz = CancelButton.class, method = "update")
    public static class CancelButtonUpdatePatch {
        @SpireInsertPatch(rloc = 63)
        public static void Insert(CancelButton __instance) {
            if (AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() instanceof RestRoom) {
                if (AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
                    FuseCampfireUI.getFuseUI(((RestRoom) AbstractDungeon.getCurrRoom())
                    .campfireUI).selectingCard = false;
                } else {
                    FuseCampfireUI.switchMode(false);
                }
            }
        }
    }
}
