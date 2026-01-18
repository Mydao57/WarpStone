package fr.mydao.plugin;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.modules.interaction.interaction.config.Interaction;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import fr.mydao.plugin.interaction.WarpStoneOpenInteraction;

import javax.annotation.Nonnull;

public class WarpStonePlugin extends JavaPlugin {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public WarpStonePlugin(@Nonnull JavaPluginInit init) {
        super(init);
        LOGGER.atInfo().log("Loaded " + getName() + " v" + getManifest().getVersion());
    }

    @Override
    protected void setup() {
        LOGGER.atInfo().log("Setting up plugin " + this.getName());

        getCodecRegistry(Interaction.CODEC).register(
                "WarpStone_OpenInteraction",
                WarpStoneOpenInteraction.class,
                WarpStoneOpenInteraction.CODEC
        );
    }
}
