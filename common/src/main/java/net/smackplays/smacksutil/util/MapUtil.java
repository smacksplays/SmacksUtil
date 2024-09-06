package net.smackplays.smacksutil.util;

import net.smackplays.smacksutil.screens.AbstractTeleportationTabletScreen;

import java.util.*;

public class MapUtil {
    public static Map<String, AbstractTeleportationTabletScreen.TeleportationData> sortByValue(Map<String, AbstractTeleportationTabletScreen.TeleportationData> map) {
        List<Map.Entry<String, AbstractTeleportationTabletScreen.TeleportationData>> list = new ArrayList<>(map.entrySet().stream().toList());
        list.sort(Comparator.comparing(Map.Entry::getKey));

        Map<String, AbstractTeleportationTabletScreen.TeleportationData> result = new LinkedHashMap<>();
        for (Map.Entry<String, AbstractTeleportationTabletScreen.TeleportationData> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}