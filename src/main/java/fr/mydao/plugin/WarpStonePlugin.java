package fr.mydao.plugin;

import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.protocol.InteractionType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.event.events.player.PlayerInteractEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;

public class WarpStonePlugin extends JavaPlugin {

    private static final HytaleLogger LOGGER = HytaleLogger.forEnclosingClass();

    public WarpStonePlugin(@Nonnull JavaPluginInit init) {
        super(init);
        LOGGER.atInfo().log("Loaded " + getName() + " v" + getManifest().getVersion());
    }

    @Override
    protected void setup() {
        LOGGER.atInfo().log("Setting up WarpStone plugin");

        getEventRegistry().registerGlobal(PlayerInteractEvent.class, this::onPlayerInteract);
    }

    private void onPlayerInteract(PlayerInteractEvent event) {
        try {
            // clic droit / interaction
            if (event.getActionType() != InteractionType.Primary) return;

            if (event.getTargetBlock() == null) return;

            var player = event.getPlayer();
            var pos = event.getTargetBlock();

            // DEBUG: log pour voir l’ID réel
            LOGGER.atInfo().log(
                    "Player " + player.getDisplayName() + " interacted with block at " + pos
            );

            // ⚠️ TODO (prochaine étape):
            // - récupérer le BlockState
            // - vérifier que c’est une WarpStone
            // - ouvrir la vraie UI

            event.setCancelled(true);

            player.sendMessage(Message.parse("WarpStone cliquée ! (UI arrive)"));
        } catch (Throwable t) {
            LOGGER.atSevere().withCause(t).log("Error");
        }
        }
}
