package net.smackplays.smacksutil.util;


import net.smackplays.smacksutil.screens.AbstractTeleportationTabletScreen;

import java.util.Comparator;
import java.util.Map;

public class StringComparator implements Comparator<Map.Entry<String, AbstractTeleportationTabletScreen.TeleportationData>> {

    public StringComparator() {
    }
    @Override
    public int compare(Map.Entry<String, AbstractTeleportationTabletScreen.TeleportationData> o1, Map.Entry<String, AbstractTeleportationTabletScreen.TeleportationData> o2) {
        return o1.getKey().compareTo(o2.getKey());
    }
}
