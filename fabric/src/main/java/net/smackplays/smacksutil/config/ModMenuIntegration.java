package net.smackplays.smacksutil.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.smackplays.smacksutil.platform.FabricModConfig;

/**
 * Class ModMenuIntegration */
@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi {
    /** Constructor*/
    public ModMenuIntegration() {

    }
    /** Getter
     * @return ConfigScreenFactory*/
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(FabricModConfig.class, parent).get();
    }
}