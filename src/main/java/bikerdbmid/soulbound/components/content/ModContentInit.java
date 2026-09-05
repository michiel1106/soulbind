package bikerdbmid.soulbound.components.content;

import bikerdbmid.soulbound.components.content.buffs.*;
import bikerdbmid.soulbound.components.content.debuffs.*;
import bikerdbmid.soulbound.components.content.effect.*;
import bikerdbmid.soulbound.components.content.powers.*;

public class ModContentInit {

    public static void init() {
        ModBuffs.init();
        ModDebuffs.init();
        ModEffects.init();
        ModPowers.init();

    }

}
