package net.smackplays.smacksutil.util;

import net.smackplays.smacksutil.screens.AbstractTeleportationTabletScreen;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapUtil {
    public static Map<String, AbstractTeleportationTabletScreen.TeleportationData> sortByValue(Map<String, AbstractTeleportationTabletScreen.TeleportationData> map) {
        List<Map.Entry<String, AbstractTeleportationTabletScreen.TeleportationData>> list = new ArrayList<>(map.entrySet().stream().toList());
        list.sort(Map.Entry.comparingByKey());

        Map<String, AbstractTeleportationTabletScreen.TeleportationData> result = new LinkedHashMap<>();
        for (Map.Entry<String, AbstractTeleportationTabletScreen.TeleportationData> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}