package morethanhidden.restrictedportals.util;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class Util {
    public static Item findConfiguredItem(String itemRaw) {
	    if (itemRaw == null || itemRaw.trim().isEmpty())
	    	return null;
    
	    String[] itemSplit = itemRaw.split(":", 2);
    
	    if (itemSplit.length != 2)
	    	return null;
    
	    return GameRegistry.findItem(itemSplit[0], itemSplit[1]);
    }
}

