package net.TelepathicGrunt.UltraAmplified.LootTable;

public class JungleTempleLootUA {
	
	/**broke in 1.14 due to vanilla changing how loot tables internals work. Need to wait until forge implements a patch or workaround**/
//	@SubscribeEvent
//	public static void lootLoad(LootTableLoadEvent event)
//	{
//		UltraAmplified.Logger.log(Level.DEBUG, "CHECKING CHEST");
//		
//		if (event.getName().toString().equals("minecraft:chests/jungle_temple")){
//		
//			LootEntry diamondHorseArmorEntry = new ParentedLootEntry(Items.DIAMOND_HORSE_ARMOR, 10, 80, new LootFunction[0], new ILootCondition[0], "ultraamplified:diamond_horse_armor");
//			LootEntry goldHorseArmorEntry = new LootEntry(Items.GOLDEN_HORSE_ARMOR, 20, 40, new LootFunction[0], new ILootCondition[0], "ultraamplified:gold_horse_armor");
//			LootEntry cobwebEntry = new LootEntry(Blocks.COBWEB.asItem(), 50, -20, new LootFunction[0], new ILootCondition[0], "ultraamplified:cobweb");
//			LootEntry goldenCarrotEntry = new LootEntry(Items.GOLDEN_CARROT, 10, 80, new LootFunction[0], new ILootCondition[0], "ultraamplified:golden_carrot");
//			LootEntry poisonousPotatoEntry = new LootEntry(Items.POISONOUS_POTATO, 30, -10, new LootFunction[0], newILootCondition[0], "ultraamplified:poisonous_potato");
//		
//			LootPool newPool1 = new LootPool(new LootEntry[]{diamondHorseArmorEntry, goldHorseArmorEntry, cobwebEntry}, new ILootCondition[0], new ILootFunction[0],  new RandomValueRange(1), new RandomValueRange(0,1), "ultraamplified_pool_inject1");
//			LootPool newPool2 = new LootPool(new LootEntry[]{goldenCarrotEntry, poisonousPotatoEntry, cobwebEntry}, new ILootCondition[0], new ILootFunction[0],  new RandomValueRange(2), new RandomValueRange(1,3), "ultraamplified_pool_inject2");
//		
//			event.getTable().addPool(newPool1);
//			event.getTable().addPool(newPool2);
//	
//		}
//	}
}
