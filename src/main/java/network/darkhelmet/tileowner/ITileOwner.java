package network.darkhelmet.tileowner;

import java.util.Optional;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;

public interface ITileOwner {
    /**
     * Clear the owner of the given block.
     *
     * @param block The block
     */
    void clearOwner(Block block);

    /**
     * Get the owner of a block.
     *
     * @param block The block
     * @return The owning player, if any
     */
    Optional<OfflinePlayer> getOwner(Block block);

    /**
     * Check if a block has an owner.
     *
     * @param block The block
     * @return True if block is owned
     */
    boolean hasOwner(Block block);

    /**
     * Checks if a block is valid (is whitelisted).
     *
     * @param block The block
     * @return True if block is valid
     */
    boolean isValidBlock(Block block);

    /**
     * Set the owner of a block. Will override any existing value.
     *
     * @param block The block
     * @param playerUUID The owner's UUID
     */
    void setOwner(Block block, UUID playerUUID);
}
