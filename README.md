# TileOwner

Associates a player with a placed TileEntity (blocks that do stuff like furnaces, brewing stands, etc). The API allows plugins to know which player "owns" so logic can credit the actions to the player.

By using Spigot's PersistenceData API this information is tracked without the need for bulky external storage like files or databases.

[Discord](https://discord.gg/Q6sHDfnMAc)

Created by viveleroi for the `play.darkhelmet.network` Minecraft server.

# API Overview

Your plugin can use this api to check, get, set, or clear the owner for a block.

To get the owner a block, use `TileOwner.getInstance().getOwner(block)`.

[Check Here](https://github.com/darkhelmet-gaming/TileOwner/blob/main/src/main/java/darkhelmet/network/tileowner/ITileOwner.java) For a full list of the API methods.

# Commands

- `check` - Check who owns the blocks you're looking at. (Permission: `tileowner.check`)
- `set (player)` - Set the owner of the block you're looking at. (Permission: `tileowner.set`)
- `clear` - Clear the owner of the block you're looking at. (Permission: `tileowner.clear`)
