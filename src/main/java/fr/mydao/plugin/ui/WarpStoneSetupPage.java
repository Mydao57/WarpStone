package fr.mydao.plugin.ui;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.protocol.packets.interface_.CustomPageLifetime;
import com.hypixel.hytale.protocol.packets.interface_.CustomUIEventBindingType;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.EventData;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public final class WarpStoneSetupPage extends InteractiveCustomUIPage<WarpStoneSetupPage.WarpStoneSetupData> {

    private static final String KEY_SAVE = "Save";   // string "true"/"false"
    private static final String KEY_NAME = "Name";   // string

    public static final class WarpStoneSetupData {
        public String save; // "true"
        public String name; // input text
        public WarpStoneSetupData() {}
    }

    // ✅ Codec qui mappe sur save/name (qui existent)
    private static final BuilderCodec<WarpStoneSetupData> CODEC =
            BuilderCodec.builder(WarpStoneSetupData.class, WarpStoneSetupData::new)
                    .append(new KeyedCodec<>(KEY_SAVE, Codec.STRING),
                            (d, v) -> d.save = v,
                            d -> d.save)
                    .add()
                    .append(new KeyedCodec<>(KEY_NAME, Codec.STRING),
                            (d, v) -> d.name = v,
                            d -> d.name)
                    .add()
                    .build();

    private final Vector3i warpStonePos;

    public WarpStoneSetupPage(
            @Nonnull PlayerRef playerRef,
            @Nonnull CustomPageLifetime lifetime,
            @Nonnull Vector3i warpStonePos
    ) {
        super(playerRef, lifetime, CODEC);
        this.warpStonePos = warpStonePos;
    }

    @Override
    public void build(
            @Nonnull Ref<EntityStore> ref,
            @Nonnull UICommandBuilder ui,
            @Nonnull UIEventBuilder events,
            @Nonnull Store<EntityStore> store
    ) {
        // ⚠️ Le fichier doit être Common/Pages/WarpStoneSetup.ui
        ui.append("Pages/WarpStoneSetup.ui");

        // ✅ ta build veut des String => "true" et pas true
        events.addEventBinding(
                CustomUIEventBindingType.Activating,
                "#SaveButton",
                new EventData()
                        .append(KEY_SAVE, "true")
                        .append(KEY_NAME, "#NameInput.Value"),
                false
        );
    }

    @Override
    public void handleDataEvent(
            @Nonnull Ref<EntityStore> ref,
            @Nonnull Store<EntityStore> store,
            @Nonnull WarpStoneSetupData data
    ) {
        Player player = store.getComponent(ref, Player.getComponentType());
        if (player == null) return;

        boolean isSave = "true".equalsIgnoreCase(data.save);
        if (!isSave) return;

        String name = (data.name == null || data.name.isBlank()) ? "WarpStone" : data.name.trim();

        // Debug visible
        player.sendMessage(Message.raw(
                "WarpStone saved: " + name + " @ " +
                        warpStonePos.getX() + "," + warpStonePos.getY() + "," + warpStonePos.getZ()
        ));

        // ✅ ferme proprement (existe sur InteractiveCustomUIPage)
        this.close();
    }
}
