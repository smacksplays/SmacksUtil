package net.smackplays.smacksutil;

import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.veinminer.VeinMiner;

public class CommonClass {
    public static VeinMiner veinMiner;
    public static void init() {
        if (Services.CONFIG != null) {
            Services.CONFIG.init();
        }
        veinMiner = new VeinMiner();
    }
}