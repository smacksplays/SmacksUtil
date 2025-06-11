package net.smackplays.smacksutil.platform;

import net.smackplays.smacksutil.Constants;
import net.smackplays.smacksutil.platform.services.*;

import java.util.ServiceLoader;

/**
 * Class Services */
public class Services {
    /** Constructor*/
    public Services() {

    }
    /** Service PLATFORM*/
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    /** Service CONFIG*/
    public static final IModConfig CONFIG = load_1(IModConfig.class);
    /** Service KEY_HANDLER*/
    public static final IKeyHandler KEY_HANDLER = load_1(IKeyHandler.class);
    /** Service C2S_PACKET_SENDER*/
    public static final IClientPacketSender C2S_PACKET_SENDER = load_1(IClientPacketSender.class);
    /** Service S2C_PACKET_SENDER*/
    public static final IServerPacketSender S2C_PACKET_SENDER = load(IServerPacketSender.class);

    /** Load Service
     * @param clazz clazz
     * @return Service
     * @param <T> T*/
    public static <T> T load(Class<T> clazz) {

        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }

    /** Load Service
     * @param clazz class
     * @return Service
     * @param <T> T*/
    public static <T> T load_1(Class<T> clazz) {
        if (PLATFORM.isClient()){
            return load(clazz);
        }
        return null;
    }

}