package morethanhidden.restrictedportals;

import morethanhidden.restrictedportals.handlers.TickHandler;
import morethanhidden.restrictedportals.items.WorldKey;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import java.util.ArrayList;
import morethanhidden.restrictedportals.util.Util;

@Mod(modid="RestrictedPortals", name="RestrictedPortals", version="0.3.4")
public class RestrictedPortals {

	@Instance(value = "RestrictedPortals")
	public static RestrictedPortals instance;
	
	public static Logger logger = LogManager.getLogger("RestrictedPortals");

	public static Achievement netherUnlock;
	public static Achievement endUnlock;

	public static final Item netherKey = new WorldKey("netherKey");
	public static final Item endKey = new WorldKey("endKey");

	public static Item netherItem;
	public static Item endItem;
	public static boolean netherLock;
	public static boolean endLock;
	public static boolean ConsumeItem;
	public boolean useKeys;

    //Deeper Caves Var
	public static Achievement abandonedCavesUnlock;
	public static Achievement bedrockPlainsUnlock;
	public static Achievement compressedUnlock;
	public static Achievement crystalUnlock;
	public static Achievement darknessUnlock;
	public static Achievement deepWorldUnlock;
	public static Achievement dropUnlock;
	public static Achievement evilUnlock;
	public static Achievement farVoidUnlock;
	public static Achievement finalLabyrinthUnlock;
	public static Achievement forgottenUnlock;
	public static Achievement lavaUnlock;
	public static Achievement mazeUnlock;
	public static Achievement mutationUnlock;
	public static Achievement nearNetherUnlock;
	public static Achievement nearVoidUnlock;

	public static final Item abandonedCavesKey = new WorldKey("abandonedCavesKey");
	public static final Item bedrockPlainsKey = new WorldKey("bedrockPlainsKey");
	public static final Item compressedKey = new WorldKey("compressedKey");
	public static final Item crystalKey = new WorldKey("crystalKey");
	public static final Item darknessKey = new WorldKey("darknessKey");
	public static final Item deepWorldKey = new WorldKey("deepWorldKey");
	public static final Item dropKey = new WorldKey("dropKey");
	public static final Item evilKey = new WorldKey("evilKey");
	public static final Item farVoidKey = new WorldKey("farVoidKey");
	public static final Item finalLabyrinthKey = new WorldKey("finalLabyrinthKey");
	public static final Item forgottenKey = new WorldKey("forgottenKey");
	public static final Item lavaKey = new WorldKey("lavaKey");
	public static final Item mazeKey = new WorldKey("mazeKey");
	public static final Item mutationKey = new WorldKey("mutationKey");
	public static final Item nearNetherKey = new WorldKey("nearNetherKey");
	public static final Item nearVoidKey = new WorldKey("nearVoidKey");

	public static Item abandonedCavesItem;
	public static Item bedrockPlainsItem;
	public static Item compressedItem;
	public static Item crystalItem;
	public static Item darknessItem;
	public static Item deepWorldItem;
	public static Item dropItem;
	public static Item evilItem;
	public static Item farVoidItem;
	public static Item finalLabyrinthItem;
	public static Item forgottenItem;
	public static Item lavaItem;
	public static Item mazeItem;
	public static Item mutationItem;
	public static Item nearNetherItem;
	public static Item nearVoidItem;

	public static boolean abandonedCavesLock;
	public static boolean bedrockPlainsLock;
	public static boolean compressedLock;
	public static boolean crystalLock;
	public static boolean darknessLock;
	public static boolean deepWorldLock;
	public static boolean dropLock;
	public static boolean evilLock;
	public static boolean farVoidLock;
	public static boolean finalLabyrinthLock;
	public static boolean forgottenLock;
	public static boolean lavaLock;
	public static boolean mazeLock;
	public static boolean mutationLock;
	public static boolean nearNetherLock;
	public static boolean nearVoidLock;

    public static int abandonedCavesId = -127;
    public static int bedrockPlainsId = -65;
    public static int compressedId = -53;
    public static int crystalId = -43;
    public static int darknessId = -112;
    public static int deepWorldId = -102;
    public static int dropId = -223;
    public static int evilId = -16;
    public static int farVoidId = -14;
    public static int finalLabyrinthId = -17;
    public static int forgottenId = -15;
    public static int lavaId = -82;
    public static int mazeId = -3440;
    public static int mutationId = -132;
    public static int nearNetherId = -72;
    public static int nearVoidId = -93;

	@EventHandler
		public void preInit(FMLPreInitializationEvent event) {

			Configuration config = new Configuration(event.getSuggestedConfigurationFile());
			
			config.load();
			
        	// Configuration
        	String netherItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock the Nether", "minecraft:flint_and_steel").getString();
        	String endItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock the End", "minecraft:ender_eye").getString();
        	useKeys = config.get(Configuration.CATEGORY_GENERAL, "Use keys rather than Items specified above", false).getBoolean();
			netherLock = config.get(Configuration.CATEGORY_GENERAL, "Lock the nether", true).getBoolean();
			endLock = config.get(Configuration.CATEGORY_GENERAL, "Lock the end", true).getBoolean();
			ConsumeItem = config.get(Configuration.CATEGORY_GENERAL, "Consume item on Right Click", false).getBoolean();



            //Deeper Caves Lock Config
			String abandonedCavesItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock Abandoned Caves", "").getString();
			String bedrockPlainsItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock Bedrock Plains", "").getString();
			String compressedItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock Compressed", "").getString();
			String crystalItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock Crystal", "").getString();
			String darknessItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock Darkness", "").getString();
			String deepWorldItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock Deep World", "").getString();
			String dropItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock Drop", "").getString();
			String evilItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock Evil", "").getString();
			String farVoidItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock Far Void", "").getString();
			String finalLabyrinthItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock Final Labyrinth", "").getString();
			String forgottenItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock Forgotten", "").getString();
			String lavaItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock Lava", "").getString();
			String mazeItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock Maze", "").getString();
			String mutationItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock Mutation", "").getString();
			String nearNetherItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock Near Nether", "").getString();
			String nearVoidItemRaw = config.get(Configuration.CATEGORY_GENERAL, "Item to Unlock Near Void", "").getString();
			abandonedCavesLock = config.get(Configuration.CATEGORY_GENERAL, "Lock Abandoned Caves", true).getBoolean();
			bedrockPlainsLock = config.get(Configuration.CATEGORY_GENERAL, "Lock Bedrock Plains", true).getBoolean();
			compressedLock = config.get(Configuration.CATEGORY_GENERAL, "Lock Compressed", true).getBoolean();
			crystalLock = config.get(Configuration.CATEGORY_GENERAL, "Lock Crystal", true).getBoolean();
			darknessLock = config.get(Configuration.CATEGORY_GENERAL, "Lock Darkness", true).getBoolean();
			deepWorldLock = config.get(Configuration.CATEGORY_GENERAL, "Lock Deep World", true).getBoolean();
			dropLock = config.get(Configuration.CATEGORY_GENERAL, "Lock Drop", true).getBoolean();
			evilLock = config.get(Configuration.CATEGORY_GENERAL, "Lock Evil", true).getBoolean();
			farVoidLock = config.get(Configuration.CATEGORY_GENERAL, "Lock Far Void", true).getBoolean();
			finalLabyrinthLock = config.get(Configuration.CATEGORY_GENERAL, "Lock Final Labyrinth", true).getBoolean();
			forgottenLock = config.get(Configuration.CATEGORY_GENERAL, "Lock Forgotten", true).getBoolean();
			lavaLock = config.get(Configuration.CATEGORY_GENERAL, "Lock Lava", true).getBoolean();
			mazeLock = config.get(Configuration.CATEGORY_GENERAL, "Lock Maze", true).getBoolean();
			mutationLock = config.get(Configuration.CATEGORY_GENERAL, "Lock Mutation", true).getBoolean();
			nearNetherLock = config.get(Configuration.CATEGORY_GENERAL, "Lock Near Nether", true).getBoolean();
			nearVoidLock = config.get(Configuration.CATEGORY_GENERAL, "Lock Near Void", true).getBoolean();

			config.save();

			if (!useKeys) {
				endItem = Util.findConfiguredItem(endItemRaw);
				netherItem = Util.findConfiguredItem(netherItemRaw);

				abandonedCavesItem = Util.findConfiguredItem(abandonedCavesItemRaw);
				bedrockPlainsItem = Util.findConfiguredItem(bedrockPlainsItemRaw);
				compressedItem = Util.findConfiguredItem(compressedItemRaw);
				crystalItem = Util.findConfiguredItem(crystalItemRaw);
				darknessItem = Util.findConfiguredItem(darknessItemRaw);
				deepWorldItem = Util.findConfiguredItem(deepWorldItemRaw);
				dropItem = Util.findConfiguredItem(dropItemRaw);
				evilItem = Util.findConfiguredItem(evilItemRaw);
				farVoidItem = Util.findConfiguredItem(farVoidItemRaw);
				finalLabyrinthItem = Util.findConfiguredItem(finalLabyrinthItemRaw);
				forgottenItem = Util.findConfiguredItem(forgottenItemRaw);
				lavaItem = Util.findConfiguredItem(lavaItemRaw);
				mazeItem = Util.findConfiguredItem(mazeItemRaw);
				mutationItem = Util.findConfiguredItem(mutationItemRaw);
				nearNetherItem = Util.findConfiguredItem(nearNetherItemRaw);
				nearVoidItem = Util.findConfiguredItem(nearVoidItemRaw);
			} else {
				if (endLock)
					GameRegistry.registerItem(endKey, "endKey");
				if (netherLock)
					GameRegistry.registerItem(netherKey, "netherKey");
				if (abandonedCavesLock)
					GameRegistry.registerItem(abandonedCavesKey, "abandonedCavesKey");
				if (bedrockPlainsLock)
					GameRegistry.registerItem(bedrockPlainsKey, "bedrockPlainsKey");
				if (compressedLock)
					GameRegistry.registerItem(compressedKey, "compressedKey");
				if (crystalLock)
					GameRegistry.registerItem(crystalKey, "crystalKey");
				if (darknessLock)
					GameRegistry.registerItem(darknessKey, "darknessKey");
				if (deepWorldLock)
					GameRegistry.registerItem(deepWorldKey, "deepWorldKey");
				if (dropLock)
					GameRegistry.registerItem(dropKey, "dropKey");
				if (evilLock)
					GameRegistry.registerItem(evilKey, "evilKey");
				if (farVoidLock)
					GameRegistry.registerItem(farVoidKey, "farVoidKey");
				if (finalLabyrinthLock)
					GameRegistry.registerItem(finalLabyrinthKey, "finalLabyrinthKey");
				if (forgottenLock)
					GameRegistry.registerItem(forgottenKey, "forgottenKey");
				if (lavaLock)
					GameRegistry.registerItem(lavaKey, "lavaKey");
				if (mazeLock)
					GameRegistry.registerItem(mazeKey, "mazeKey");
				if (mutationLock)
					GameRegistry.registerItem(mutationKey, "mutationKey");
				if (nearNetherLock)
					GameRegistry.registerItem(nearNetherKey, "nearNetherKey");
				if (nearVoidLock)
					GameRegistry.registerItem(nearVoidKey, "nearVoidKey");

				endItem = RestrictedPortals.endKey;
				netherItem = RestrictedPortals.netherKey;
				abandonedCavesItem = RestrictedPortals.abandonedCavesKey;
				bedrockPlainsItem = RestrictedPortals.bedrockPlainsKey;
				compressedItem = RestrictedPortals.compressedKey;
				crystalItem = RestrictedPortals.crystalKey;
				darknessItem = RestrictedPortals.darknessKey;
				deepWorldItem = RestrictedPortals.deepWorldKey;
				dropItem = RestrictedPortals.dropKey;
				evilItem = RestrictedPortals.evilKey;
				farVoidItem = RestrictedPortals.farVoidKey;
				finalLabyrinthItem = RestrictedPortals.finalLabyrinthKey;
				forgottenItem = RestrictedPortals.forgottenKey;
				lavaItem = RestrictedPortals.lavaKey;
				mazeItem = RestrictedPortals.mazeKey;
				mutationItem = RestrictedPortals.mutationKey;
				nearNetherItem = RestrictedPortals.nearNetherKey;
				nearVoidItem = RestrictedPortals.nearVoidKey;
			}

			//If Configuration is invalid
			if (endLock && endItem == null){
				endItem = Items.ender_eye;
				logger.info("Please fix the End Item in the Config");
			}
			if (netherLock && netherItem == null){
				netherItem = Items.flint_and_steel;
				logger.info("Please fix the Nether Item in the Config");
			}
				
			//Register Tick Handler
			TickHandler tickHandler = new TickHandler();
			FMLCommonHandler.instance().bus().register(tickHandler);
			MinecraftForge.EVENT_BUS.register(tickHandler);
    
			//Achievements
			ArrayList<Achievement> achievements = new ArrayList<>();
			
			if (netherLock) {
				netherUnlock = new Achievement("achievement.netherUnlock", "netherUnlock", 0, 0, netherItem, null).initIndependentStat().registerStat();
				achievements.add(netherUnlock);
			}
			if (endLock) {
				endUnlock = new Achievement("achievement.endUnlock", "endUnlock", 1, 0, endItem, netherLock ? netherUnlock : null).initIndependentStat().registerStat();
				achievements.add(endUnlock);
			}
            //DeeperCaves Achievements
	        if (abandonedCavesLock) {
		        abandonedCavesUnlock = new Achievement("achievement.abandonedCavesUnlock", "abandonedCavesUnlock", 0, 0, abandonedCavesItem, null).initIndependentStat().registerStat();
		        achievements.add(abandonedCavesUnlock);
	        }
	        if (bedrockPlainsLock) {
		        bedrockPlainsUnlock = new Achievement("achievement.bedrockPlainsUnlock", "bedrockPlainsUnlock", 1, 0, bedrockPlainsItem, abandonedCavesLock ? abandonedCavesUnlock : null).initIndependentStat().registerStat();
	        	        achievements.add(bedrockPlainsUnlock);
        	}
        	if (compressedLock) {
        		compressedUnlock = new Achievement("achievement.compressedUnlock", "compressedUnlock", 2, 0, compressedItem, bedrockPlainsLock ? bedrockPlainsUnlock : null).initIndependentStat().registerStat();
        		achievements.add(compressedUnlock);
        	}
	        if (crystalLock) {
        		crystalUnlock = new Achievement("achievement.crystalUnlock", "crystalUnlock", 3, 0, crystalItem, compressedLock ? compressedUnlock : null).initIndependentStat().registerStat();
	        	achievements.add(crystalUnlock);
	        }
	        if (darknessLock) {
	        	darknessUnlock = new Achievement("achievement.darknessUnlock", "darknessUnlock", 4, 0, darknessItem, crystalLock ? crystalUnlock : null).initIndependentStat().registerStat();
	        	achievements.add(darknessUnlock);
	        }
	        if (deepWorldLock) {
	        	deepWorldUnlock = new Achievement("achievement.deepWorldUnlock", "deepWorldUnlock", 5, 0, deepWorldItem, darknessLock ? darknessUnlock : null).initIndependentStat().registerStat();
	        	achievements.add(deepWorldUnlock);
	        }
	        if (dropLock) {
	        	dropUnlock = new Achievement("achievement.dropUnlock", "dropUnlock", 6, 0, dropItem, deepWorldLock ? deepWorldUnlock : null).initIndependentStat().registerStat();
	        	achievements.add(dropUnlock);
	        }
	        if (evilLock) {
	        	evilUnlock = new Achievement("achievement.evilUnlock", "evilUnlock", 7, 0, evilItem, dropLock ? dropUnlock : null).initIndependentStat().registerStat();
	        	achievements.add(evilUnlock);
	        }
	        if (farVoidLock) {
	        	farVoidUnlock = new Achievement("achievement.farVoidUnlock", "farVoidUnlock", 8, 0, farVoidItem, evilLock ? evilUnlock : null).initIndependentStat().registerStat();
	        	achievements.add(farVoidUnlock);
	        }
        	if (finalLabyrinthLock) {
        		finalLabyrinthUnlock = new Achievement("achievement.finalLabyrinthUnlock", "finalLabyrinthUnlock", 9, 0, finalLabyrinthItem, farVoidLock ? farVoidUnlock : null).initIndependentStat().registerStat();
	        	achievements.add(finalLabyrinthUnlock);
        	}
	        if (forgottenLock) {
	        	forgottenUnlock = new Achievement("achievement.forgottenUnlock", "forgottenUnlock", 10, 0, forgottenItem, finalLabyrinthLock ? finalLabyrinthUnlock : null).initIndependentStat().registerStat();
	        	achievements.add(forgottenUnlock);
	        }
	        if (lavaLock) {
	        	lavaUnlock = new Achievement("achievement.lavaUnlock", "lavaUnlock", 11, 0, lavaItem, forgottenLock ? forgottenUnlock : null).initIndependentStat().registerStat();
	        	achievements.add(lavaUnlock);
	        }
	        if (mazeLock) {
	        	mazeUnlock = new Achievement("achievement.mazeUnlock", "mazeUnlock", 12, 0, mazeItem, lavaLock ? lavaUnlock : null).initIndependentStat().registerStat();
	        	achievements.add(mazeUnlock);
	        }
	        if (mutationLock) {
	        	mutationUnlock = new Achievement("achievement.mutationUnlock", "mutationUnlock", 13, 0, mutationItem, mazeLock ? mazeUnlock : null).initIndependentStat().registerStat();
	        	achievements.add(mutationUnlock);
	        }
	        if (nearNetherLock) {
        		nearNetherUnlock = new Achievement("achievement.nearNetherUnlock", "nearNetherUnlock", 14, 0, nearNetherItem, mutationLock ? mutationUnlock : null).initIndependentStat().registerStat();
        		achievements.add(nearNetherUnlock);
        	}
        	if (nearVoidLock) {
        		nearVoidUnlock = new Achievement("achievement.nearVoidUnlock", "nearVoidUnlock", 15, 0, nearVoidItem, nearNetherLock ? nearNetherUnlock : null).initIndependentStat().registerStat();
        		achievements.add(nearVoidUnlock);
        	}

			if (achievements.size() == 0) {
				logger.warn("No Dimension are locked. You are using this mod why again?");
			} else {
				AchievementPage.registerAchievementPage(new AchievementPage("Restricted Portals", achievements.toArray(new Achievement[achievements.size()])));
			}

		}


		@EventHandler
		public void load(FMLInitializationEvent event) {
			//Temporary Naming based on config
			if (netherLock)
				LanguageRegistry.instance().addStringLocalization("achievement.netherUnlock.desc", "en_US", "Craft a " + StatCollector.translateToLocal(netherItem.getUnlocalizedName() + ".name"));
			if (endLock)
				LanguageRegistry.instance().addStringLocalization("achievement.endUnlock.desc", "en_US", "Craft a " + StatCollector.translateToLocal(endItem.getUnlocalizedName() + ".name"));
		}

}


