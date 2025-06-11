package net.smackplays.smacksutil;

import net.smackplays.smacksutil.platform.Services;
import net.smackplays.smacksutil.veinminer.VeinMiner;

/**
 * CommonClass */
public class CommonClass {
    /** VeinMiner Service*/
    public static VeinMiner veinMiner;
    /** Constructor*/
    public CommonClass(){

    }

    /** init common services*/
    public static void init() {
        if (Services.CONFIG != null) {
            Services.CONFIG.init();
        }
        veinMiner = new VeinMiner();
    }
}