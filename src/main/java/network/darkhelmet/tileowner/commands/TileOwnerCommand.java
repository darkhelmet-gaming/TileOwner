package network.darkhelmet.tileowner.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;

import java.util.Optional;

import network.darkhelmet.tileowner.TileOwner;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@CommandAlias("tileowner")
public class TileOwnerCommand extends BaseCommand {
    /**
     * Check who owns a block.
     *
     * @param player The player
     */
    @Subcommand("check")
    @CommandPermission("tileowner.check")
    public void onCheck(Player player) {
        Block target = player.getTargetBlock(null, 5);

        Optional<OfflinePlayer> owner = TileOwner.getInstance().getOwner(target);

        if (owner.isPresent()) {
            String msg = owner.get().equals(player) ? "You own that block" :
                String.format("Block is owned by %s", owner.get().getName());

            player.sendMessage(msg);
        } else {
            player.sendMessage("No one owns that block.");
        }
    }

    /**
     * Clear the current owner of a block.
     *
     * @param player The player
     */
    @Subcommand("clear")
    public void onClear(Player player) {
        Block target = player.getTargetBlock(null, 5);

        Optional<OfflinePlayer> optionalOwner = TileOwner.getInstance().getOwner(target);
        if (optionalOwner.isEmpty()) {
            player.sendMessage(ChatColor.RED + "No one owns this block.");

            return;
        }

        // Clear any
        if (player.hasPermission("tileowner.clear")) {
            TileOwner.getInstance().clearOwner(target);

            player.sendMessage(ChatColor.GREEN + "Cleared the owner of that block.");

            return;
        }

        // Clear self
        if (optionalOwner.get().equals(player) && player.hasPermission("tileowner.clear.self")) {
            TileOwner.getInstance().clearOwner(target);

            player.sendMessage(ChatColor.GREEN + "You no longer own that block.");
        }
    }

    /**
     * Set yourself as the owner for a block.
     *
     * @param player The player
     */
    @Subcommand("set")
    @CommandPermission("tileowner.set.self")
    public void onSet(Player player) {
        Block target = player.getTargetBlock(null, 5);

        Optional<OfflinePlayer> optionalOwner = TileOwner.getInstance().getOwner(target);
        if (optionalOwner.isPresent() && !optionalOwner.get().equals(player)) {
            player.sendMessage(ChatColor.RED + "Someone else already owns that block.");

            return;
        }

        TileOwner.getInstance().setOwner(target, player.getUniqueId());
    }

    /**
     * Set an owner to the block.
     *
     * @param player The player
     * @param owner The player who will own this block
     */
    @Subcommand("set")
    @CommandCompletion("@players")
    public void onSet(Player player, @co.aikar.commands.annotation.Optional Player owner) {
        owner = owner == null ? player : owner;

        Block target = player.getTargetBlock(null, 5);

        Optional<OfflinePlayer> optionalOwner = TileOwner.getInstance().getOwner(target);
        if (optionalOwner.isPresent()) {
            String msg = optionalOwner.get().equals(owner) ? "You already own that block."
                : "Someone already owns that block.";

            player.sendMessage(ChatColor.RED + msg);

            return;
        }

        TileOwner.getInstance().setOwner(target, owner.getUniqueId());

        player.sendMessage(ChatColor.GREEN + "Registered " + owner.getName() + " as the owner of that block.");
    }
}
