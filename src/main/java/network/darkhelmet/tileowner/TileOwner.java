package network.darkhelmet.tileowner;

import co.aikar.commands.BukkitCommandManager;

import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import network.darkhelmet.tileowner.commands.TileOwnerCommand;
import network.darkhelmet.tileowner.listeners.BlockPlaceListener;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.java.JavaPlugin;

public class TileOwner extends JavaPlugin implements ITileOwner {
    /**
     * Cache static instance.
     */
    private static ITileOwner instance;

    /**
     * Define the plugin name for prefixing/logs.
     */
    private static final String PLUGIN_NAME = "TileOwner";

    /**
     * The logger.
     */
    private static final Logger log = Logger.getLogger("Minecraft");

    /**
     * Cache an instance of the UUID data type.
     */
    private final UUIDDataType uuidDataType = new UUIDDataType();

    /**
     * The namespace key we use.
     */
    private final NamespacedKey tileOwnerKey;

    /**
     * Get this instance.
     *
     * @return The plugin instance
     */
    public static ITileOwner getInstance() {
        return instance;
    }

    /**
     * Constructor.
     */
    public TileOwner() {
        instance = this;
        tileOwnerKey = new NamespacedKey(this, "tileowner");
    }

    /**
     * On Enable.
     */
    @Override
    public void onEnable() {
        log("Initializing " + PLUGIN_NAME + " " + this.getDescription().getVersion() + ". by viveleroi.");

        if (isEnabled()) {
            // Initialize and configure the command system
            BukkitCommandManager manager = new BukkitCommandManager(this);

            manager.registerCommand(new TileOwnerCommand());

            getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        }
    }

    @Override
    public void clearOwner(Block block) {
        if (!isValidBlock(block)) {
            return;
        }

        if (block.getState() instanceof TileState tileState) {
            PersistentDataContainer container = tileState.getPersistentDataContainer();

            if (container.has(tileOwnerKey, uuidDataType)) {
                container.remove(tileOwnerKey);

                tileState.update();
            }
        }
    }

    @Override
    public Optional<OfflinePlayer> getOwner(Block block) {
        if (block.getState() instanceof TileState tileState) {
            PersistentDataContainer container = tileState.getPersistentDataContainer();

            if (container.has(tileOwnerKey, uuidDataType)) {
                UUID uuid = container.get(tileOwnerKey, uuidDataType);

                return Optional.of(Bukkit.getOfflinePlayer(uuid));
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean hasOwner(Block block) {
        if (block.getState() instanceof TileState tileState) {
            PersistentDataContainer container = tileState.getPersistentDataContainer();

            return container.has(tileOwnerKey, uuidDataType);
        }

        return false;
    }

    @Override
    public boolean isValidBlock(Block block) {
        return switch (block.getType()) {
            case BLAST_FURNACE, BREWING_STAND, FURNACE, SMOKER -> true;
            default -> false;
        };
    }

    @Override
    public void setOwner(Block block, UUID playerUUID) {
        if (!isValidBlock(block)) {
            return;
        }

        if (block.getState() instanceof TileState tileState) {
            PersistentDataContainer container = tileState.getPersistentDataContainer();
            container.set(tileOwnerKey, uuidDataType, playerUUID);

            tileState.update();
        }
    }

    /**
     * Log a message to console.
     *
     * @param message String
     */
    public static void log(String message) {
        log.info("[" + PLUGIN_NAME + "]: " + message);
    }
}
