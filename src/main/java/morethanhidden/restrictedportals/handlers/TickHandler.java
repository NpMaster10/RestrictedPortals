package morethanhidden.restrictedportals.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import java.util.HashMap;
import java.util.UUID;
import morethanhidden.restrictedportals.RestrictedPortals;
import morethanhidden.restrictedportals.events.PlayerMoveEvent;
import morethanhidden.restrictedportals.object.PlayerPos;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class TickHandler
{

  private HashMap<UUID, PlayerPos> lastPlayerPosition = new HashMap<>();

  private void ensureBlockBelowPlayer(double posX, double posY, double posZ, WorldServer world)
  {

    int x = MathHelper.floor_double(posX);
    int y = MathHelper.floor_double(posY);
    int z = MathHelper.floor_double(posZ);

    // Place stone only when there is no block below the player's feet
    if (world.isAirBlock(x, y, z)) {
      world.setBlock(x, y, z, Blocks.stone, 0, 3);
    }
  }

  @SubscribeEvent public void onPlayerTickEvent(TickEvent.PlayerTickEvent event)
  {
    if (event.side != Side.SERVER || event.phase == TickEvent.Phase.START)
      return;

    EntityPlayerMP player = (EntityPlayerMP)event.player;

    PlayerPos before = lastPlayerPosition.get(player.getPersistentID());
    PlayerPos current = new PlayerPos(event.player);

    if (before != null && !player.isDead && player.worldObj != null && !before.equals(current)) {

      PlayerMoveEvent moveEvent = new PlayerMoveEvent(player, before, current);
      MinecraftForge.EVENT_BUS.post(moveEvent);

      if (moveEvent.isCanceled() && event.side == Side.SERVER) {

        if (current.dim == 1) {

          player.travelToDimension(1);

          ChunkCoordinates coordinates = player.getBedLocation(0);
          if (coordinates == null) {
            coordinates = player.worldObj.getSpawnPoint();
          }

          player.setPositionAndUpdate(coordinates.posX, coordinates.posY + 1, coordinates.posZ);

        } else if (RestrictedPortals.DeeperIDS.contains(current.dim)) {
          MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, before.getDim());
          player.playerNetServerHandler.setPlayerLocation(before.getX(), before.getY() + 1.6, before.getZ(), before.getYaw(), before.getPitch());
          ensureBlockBelowPlayer(before.getX(), before.getY(), before.getZ(), MinecraftServer.getServer().worldServerForDimension(before.getDim()));
        } else {
          MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(player, before.getDim());
          player.playerNetServerHandler.setPlayerLocation(before.getX(), before.getY(), before.getZ(), before.getYaw(), before.getPitch());
        }
      }
    }

    lastPlayerPosition.put(player.getPersistentID(), new PlayerPos(event.player));
  }

  @SubscribeEvent public void onRightClick(PlayerInteractEvent event)
  {

    if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK && event.entityPlayer.getHeldItem() != null) {
      Item item = event.entityPlayer.getHeldItem().getItem();
      if (RestrictedPortals.netherLock && event.world.getBlock(event.x, event.y, event.z) == Blocks.portal && item == RestrictedPortals.netherItem) {

        event.entityPlayer.addStat(RestrictedPortals.netherUnlock, 1);

      } else if (RestrictedPortals.endLock && event.world.getBlock(event.x, event.y, event.z) == Blocks.end_portal && item == RestrictedPortals.endItem) {

        event.entityPlayer.addStat(RestrictedPortals.endUnlock, 1);

      } else if (RestrictedPortals.abandonedCavesLock && item == RestrictedPortals.abandonedCavesItem) {

        event.entityPlayer.addStat(RestrictedPortals.abandonedCavesUnlock, 1);

      } else if (RestrictedPortals.bedrockPlainsLock && item == RestrictedPortals.bedrockPlainsItem) {

        event.entityPlayer.addStat(RestrictedPortals.bedrockPlainsUnlock, 1);

      } else if (RestrictedPortals.compressedLock && item == RestrictedPortals.compressedItem) {

        event.entityPlayer.addStat(RestrictedPortals.compressedUnlock, 1);

      } else if (RestrictedPortals.crystalLock && item == RestrictedPortals.crystalItem) {

        event.entityPlayer.addStat(RestrictedPortals.crystalUnlock, 1);

      } else if (RestrictedPortals.darknessLock && item == RestrictedPortals.darknessItem) {

        event.entityPlayer.addStat(RestrictedPortals.darknessUnlock, 1);

      } else if (RestrictedPortals.deepWorldLock && item == RestrictedPortals.deepWorldItem) {

        event.entityPlayer.addStat(RestrictedPortals.deepWorldUnlock, 1);

      } else if (RestrictedPortals.dropLock && item == RestrictedPortals.dropItem) {

        event.entityPlayer.addStat(RestrictedPortals.dropUnlock, 1);

      } else if (RestrictedPortals.evilLock && item == RestrictedPortals.evilItem) {

        event.entityPlayer.addStat(RestrictedPortals.evilUnlock, 1);

      } else if (RestrictedPortals.farVoidLock && item == RestrictedPortals.farVoidItem) {

        event.entityPlayer.addStat(RestrictedPortals.farVoidUnlock, 1);

      } else if (RestrictedPortals.finalLabyrinthLock && item == RestrictedPortals.finalLabyrinthItem) {

        event.entityPlayer.addStat(RestrictedPortals.finalLabyrinthUnlock, 1);

      } else if (RestrictedPortals.forgottenLock && item == RestrictedPortals.forgottenItem) {

        event.entityPlayer.addStat(RestrictedPortals.forgottenUnlock, 1);

      } else if (RestrictedPortals.lavaLock && item == RestrictedPortals.lavaItem) {

        event.entityPlayer.addStat(RestrictedPortals.lavaUnlock, 1);

      } else if (RestrictedPortals.mazeLock && item == RestrictedPortals.mazeItem) {

        event.entityPlayer.addStat(RestrictedPortals.mazeUnlock, 1);

      } else if (RestrictedPortals.mutationLock && item == RestrictedPortals.mutationItem) {

        event.entityPlayer.addStat(RestrictedPortals.mutationUnlock, 1);

      } else if (RestrictedPortals.nearNetherLock && item == RestrictedPortals.nearNetherItem) {

        event.entityPlayer.addStat(RestrictedPortals.nearNetherUnlock, 1);

      } else if (RestrictedPortals.nearVoidLock && item == RestrictedPortals.nearVoidItem) {

        event.entityPlayer.addStat(RestrictedPortals.nearVoidUnlock, 1);

      } else {
        return;
      }

      if (RestrictedPortals.ConsumeItem) {
        event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);
      }
    }
  }

  @SubscribeEvent public void onPlayerMoveEvent(PlayerMoveEvent e)
  {
    EntityPlayerMP player = (EntityPlayerMP)e.entityPlayer;

    if (e.before.dim != e.entityPlayer.dimension) {
      int dimension = e.entityPlayer.dimension;

      if (RestrictedPortals.netherLock && dimension == -1 && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.netherUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.netherItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }

      if (RestrictedPortals.endLock && dimension == 1 && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.endUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.endItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }

      if (RestrictedPortals.abandonedCavesLock && dimension == RestrictedPortals.abandonedCavesId && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.abandonedCavesUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.abandonedCavesItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }

      if (RestrictedPortals.bedrockPlainsLock && dimension == RestrictedPortals.bedrockPlainsId && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.bedrockPlainsUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.bedrockPlainsItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }

      if (RestrictedPortals.compressedLock && dimension == RestrictedPortals.compressedId && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.compressedUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.compressedItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }

      if (RestrictedPortals.crystalLock && dimension == RestrictedPortals.crystalId && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.crystalUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.crystalItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }

      if (RestrictedPortals.darknessLock && dimension == RestrictedPortals.darknessId && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.darknessUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.darknessItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }

      if (RestrictedPortals.deepWorldLock && dimension == RestrictedPortals.deepWorldId && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.deepWorldUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.deepWorldItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }

      if (RestrictedPortals.dropLock && dimension == RestrictedPortals.dropId && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.dropUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.dropItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }

      if (RestrictedPortals.evilLock && dimension == RestrictedPortals.evilId && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.evilUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.evilItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }

      if (RestrictedPortals.farVoidLock && dimension == RestrictedPortals.farVoidId && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.farVoidUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.farVoidItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }

      if (RestrictedPortals.finalLabyrinthLock && dimension == RestrictedPortals.finalLabyrinthId && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.finalLabyrinthUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.finalLabyrinthItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }

      if (RestrictedPortals.forgottenLock && dimension == RestrictedPortals.forgottenId && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.forgottenUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.forgottenItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }

      if (RestrictedPortals.lavaLock && dimension == RestrictedPortals.lavaId && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.lavaUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.lavaItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }

      if (RestrictedPortals.mazeLock && dimension == RestrictedPortals.mazeId && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.mazeUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.mazeItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }

      if (RestrictedPortals.mutationLock && dimension == RestrictedPortals.mutationId && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.mutationUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.mutationItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }

      if (RestrictedPortals.nearNetherLock && dimension == RestrictedPortals.nearNetherId && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.nearNetherUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.nearNetherItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }

      if (RestrictedPortals.nearVoidLock && dimension == RestrictedPortals.nearVoidId && !player.func_147099_x().hasAchievementUnlocked(RestrictedPortals.nearVoidUnlock)) {
        player.addChatComponentMessage(new ChatComponentTranslation("Sorry, You need to make a " + StatCollector.translateToLocal(RestrictedPortals.nearVoidItem.getUnlocalizedName() + ".name") + " first"));
        e.setCanceled(true);
      }
    }
  }
}
