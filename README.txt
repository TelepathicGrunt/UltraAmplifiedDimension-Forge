    Made for Minecraft v.1.14.4
    Created by TelepathicGrunt

Hello! If you are reading this, you must be on Github then! I strongly encourage you to only download and run the master branch as that should be the most stable version of my mod. 
If you try to use the latest Minecraft version branch, you may run into bugs and issues as I am experimenting and working on that latest version. Older version branches should be alright.

Highly recommend using a strong computer with this mod!
You can choose biome sizes, ore spawn rates, structure rarity, toggle biomes, and much more with this mod's config options!

Feel free to use this mod and any part of it for whatever you want as long as you do not profit off of my mod. 
Just be sure to have fun and make awesome mods! That's all. Enjoy! :) 


------------------------------------------------
       | Ultra Amplified mod changelog |
       
       
    (V.6.7 Changes) (1.14.4 Minecraft)
    
    Config: 
�Added config option to trigger heavy fog in the Ultra Amplified Dimension for spooky effect. Set to false by default.
�Added config options to control spawnrate of Sun Shrine and Stonehenge ministructures.
�Fixed bug so that spawnrates config options now correctly apply for: Mineshafts, Caves, Ravines, Waterfalls, Lavafalls, Glowstone patches, Pillager Outposts, Ores, Sunshrines, Stonehenge, and End Islands.
�Lowered maximum spawnrate option from 100 to 22 for Cave Caverns because anything 30 or above causes Minecraft to stop loading chunks due to how massive caverns are.
�Lowered maximum spawnrate of End Islands from 500 to 100 because 500 is ridiculous lol. The entire land was pretty much all Endstone at that point with no opening or ledges.
�Increased maximum range for glowstone variant patches.

	Dimension:
�All light sources now look brighter and their light visually reaches further to make glow-variant patches look nicer. The actual light levels itself has not change.

    Biomes:
�Added new cactus blocks to create better cactuses to spawn in desert biomes.
�Desert Hills biome has less Sand under its surface which makes Stone appear more often on the bottom of ledges to make this biome slightly more distinct from regular Desert biome.
�Foliage in End Barren Fields biome are now slightly more green. 
�Added extra Iron Ores that spawn around sealevel for all biomes with "Mountain" in its name except for Ice Mountain biome.

    Blocks:
�All blocks that this mod added is now available in the creative inventory.

            
    (V.6.6 Changes) (1.14.4 Minecraft)
    
    Dimension: 
·Futher improved portal mechanics so portal now remembers last position you were in Ultra Amplified Dimension when you last teleport out of it.
·The distance fog in the Ultra Amplified Dimension now becomes more dark the lower down in the world you go to help with immersion. Nothing breaks immersion like seeing a bright blue sky through an unloaded chunk when underground. Now it'll be dark blue!

       
    (V.6.5 Changes) (1.14.4 Minecraft)
    
    Dimension: 
·Patched bug that prevented the dimension from being registered and working on the latest forge build.
·Added floor around base of portal in the Ultra Amplified dimension.
·Improved portal mechanics so now the portal will teleport players to last dimension and position the player was at instead of always to the Overworld at world origin.

	Config:
·Added config option to let users select maximum height of terrain.
    
       
    (V.6.4 Changes) (1.14.4 Minecraft)
·Quick fix to patch bug that caused putting this mod into a server would make the server to crash.

       
    (V.6.3 Changes) (1.14.4 Minecraft)
       
    Major Changes:
·This mod is now its own dimension in any worldtype that isn't Ultra Amplified. To enter this dimension, make a portal with these blocks and then use Flint and Steel on Polished Diorite in center. Then right click on portal to enter.

PG = Polished Granite
BPAS = Bottom Polished Andesite Slab
TPAS = Top Polished Andesite Slab
PD =  Polished Diorite

    Bottom Layer:
PG   |  BPAS  |  PG
BPAS |  BPAS  |  BPAS
PG   |  BPAS  |  PG

    Middle Layer:
     |        |  
     |   PD   |  
     |        | 
  
     Top Layer:
PG   |  TPAS  |  PG
TPAS |  TPAS  |  TPAS
PG   |  TPAS  |  PG


Note: The Ultra Amplified Dimension will have a portal at 8, y, 8 always and is unabled to be removed in Survival. Any other portal can be removed by left clicking on it to turn it back into Polished Diorite.

     Config:
·Added option to modify the values used for the terrain perlin noise generator. Gives people the ability to make it more or less crazy.
    
     Biomes:
·Columns in Wooded Hills biomes now generates Dirt under its Grass Blocks correctly on the bottom half of the column.
·Fixed bug that prevent glowstone patches from fully generating.
·Greatly increased spawnrate of crosses in Swamp Hills biome.
     

    (V.6.2 Changes) (1.14.4 Minecraft)

     Terrain:
·Adjusted terrain so top layers islands are less frequent. Feedback on this change would be appreciated.

     Config:
·Made the M form config now correctly make an actual area M form instead of tiny chunk size patches of the M biomes.
·Config values for if Plains, Savanna, and Wooded Mountains biomes should generate now correctly applies when these biomes tries to generate as a border separation between certain biomes.
·Stronghold config values changed to reflect the new way Strongholds generate and the config values now applies correctly.

     Biomes:
·Snowy Beach biome now spawns less often on land and will now also spawn along edges of where snowy biomes meets oceans.
·Stone Beach biome now spawns less often on land and will now also spawn along edges of where Extreme Hills biomes meets oceans.
·Mushroom Biomes will no longer spawn in ocean biomes but will continue to generate on land like it has before. This is because the ocean biomes looks better when it is mainly ocean and not interrupted by the Mushroom biome.
·Glowstone variant patches are more tighter.
·Added roots and short vines that spawn on the under-side of floating land in Forest, Birch Forest, Dark Forest, Giant Tree Taiga, Wooded Badlands Plateau, Savanna, Shattered Savanna, Snowy Taiga, Swamp, Taiga and all their hills and M variants biomes. The hills variants has denser roots.

     Structures:
·Strongholds now generate like other structures instead of being limited to a certain number per world so it is easier to find Strongholds.
·Fixed bug that prevented Ender Eyes from pointing to closest Stronghold.
·Locate command now works and will find all main structures. (Cannot find Stonehenge or Sun Shrine)


    (V.6.1 Changes) (1.14.4 Minecraft)
    
    Major Changes:
·Now compatible with 1.14.4 as 1.14.3 wouldn't work in 1.14 due to some import changes and an accesstransformer issue.
       
             
    (V.6.0 Changes) (1.14.3 Minecraft)
    
     Major Changes:
·Mod updated from 1.13.2 to work on 1.14.3 now!

     Config:
·Added config option for Pillager Outposts.
·Lava ocean config option now applies to Ocean biomes.
·Changed how M biome config option now affects the spawnrate of M variant biomes. It should be more uniform and consistent now and should work better
·Added config option for Bamboo Jungle generation.
·Patched bug that prevented most Wooded Badlands Plateau biome from spawning when config is set to only Badlands biomes for generation.
·Added config option to turn on and off column/ramp generation.
·Combined the structure generation and spawnrate config options into one to reduce number of configs. Set the structure spawnrate to 101 to turn off generation for that structure.
·Added config option to affect the spawnrates of glowstone variant patches

     Biomes:
·Added Bamboo Jungle biome to generate in this mod along with some modification. Uses real Bamboo unlike the Sugar Cane that the 1.12 version of this mod used!
·Adjusted grass density in Jungle biomes so the grond isn't 100% covered in grass.
·Fixed Jungle Bushes in Jungle biome to generate under ledges now.
·M forms of Redwood Taiga biomes now generates slightly less trees to be less dense.
·Fixed bug preventing Mesa Plateau variant biomes from spawning.
·Patched bug that made Deep variants of oceans super rare when config biome options is set to oceans only. 
·Added Coarse Dirt, Clay, and Gravel patches to spawn at bottom of water in all biomes (except oceans, Nether, and End biomes. Desert biomes gets Sandstone, Sand, and Clay patches). These patches can be easily found between sea level and Y = 40 in all biomes or in the shallow lakes in Swamp, Desert lakes, Modified Jungle, Modified Jungle Hills, and Bamboo Jungle Hills biomes.
·Warm and Lukewarm Ocean biomes now has a sandy bottom below sea level and neutral Ocean biomes has Gravel bottom below sea level. 
·Badlands biome now generates Red Sand at all heights above sea level to better distinguish it from Badlands Plateau biome.
·Modified Badlands Plateau biome and Modified Wooded Badlands Plateau biome generates Deadbush, lavafalls, and Gold Ore more often with the wooden biome generating more trees as well.
·The End biome has been changed so that terrain above Y = 70 generates Grass Blocks, Dirt, and Stone like most other biomes. Reduced spawnrate of Chorus Plant. Generates patches of Endstone, Endstone Bricks, and Obsidian. Spawns Berry Bushes, small amount of Tallgrass/Ferns, and a hybrid tree with Birch Trunk, Dark Oak Leaves, and a patch of Blue Terracotta at base of trunk.
·Added Barren End Field biome to make a thin border around the End biome for better biome transition. This biome is slightly less purple than the End biome, has a small amount of patches of Obsidian and Endstone, no trees, and can spawn Berry Bushes.
·Nether biome now has a thin Savanna border for better biome transition.
·All biomes has patches of mainly Glowstone Ore blocks (and sometimes other Glowstone variants) between Y = 45 to 70 (some has Y = 60 as max) to provide light at top of the topmost Ravine maze and at top of Cave Caverns. 
·Most biomes now has patches of Glowstone variant blocks under their ledges to provide light (spawns at Y = 70 and higher). The biomes that does not have these patches includes all snowy biomes, all ocean biomes, and most Badlands biomes.
·Patched bug that made Tall Birch Forest trees leaves break because the leaves were too far from the trunk. 
·Patched bug that generated giant trees too close to build height limit and got them cut off in Dark Forest Hills biome.

     Structures:
·Crosses with very rare hidden chest in Swampland Hills biome has their loot nerfed.
·End Cities lowered by 8 blocks so they do not get cut off by height limit.
·Nether Fortresses that spawns underground in all biomes now generates as a stone variant. Aboveground Nether Fortresses in the Nether remains the same.
·Added Pillager Outposts to Plains, Snowy Taiga, Taiga, Dark Forest, Tundra, Savanna, Shattered Savanna, and Desert biomes.
·Made Marked Treasure Chests 2.5 times more common due to difficulty finding the Red Sand 'X' below sea level.
·Mineshaft blocks has been changed for most Mineshaft types to add variety and look better.
·Water filled caves has been added to all ocean biomes. You may find entrances to these new caves in Ravines, Cave Cavities, or on the ocean floor!
·Added pool in front of Ocean Monument entrance so it is easier to get inside as before, the small entrance could be easily blocked by terrain.
·Fixed Ocean Monument bug preventing entrances to the Elder Guardian rooms from being made.
·Fixed Ocean Monument bug that let terrain blocks be generated in the Monument. Coral and Icebergs can rarely still be generated inside.
·Ocean Monument can be at a random height between Y = 67 and Y = 85 as they were at a fixed height at Y = 67 before to minimize the occurrence of the terrain block bug.
·Added columns and ramps to most biomes to give a more cave-like appearance as well as make travel between layers of land easier. All ocean biomes does not have columns/ramps. 
·Jungle Temple chest loot has been significantly buffed and the Dispensers now shoot Arrows with long poison effect.

     Blocks:
·Coarse Glowdirt, Glowdirt, Glowgrass Block, Glowmycelium, Glowpodzol, Glowsand, Glowstone Ore, and Red Glowsand has been added. The blocks will give off light and when mined, will drop Glowstone dust and the base block. Fortune and Silk Touch works on these blocks.
     
      
    (V.5.0 Changes) (1.13.2 Minecraft)
       
     Major Changes:
·Mod updated from 1.12.2 to work on 1.13.2 now!

     Config:
·The Config GUI no longer works as 1.13 Forge does not have a working config UI. Instead, exit the Minecraft world you are in, look for the serverconfig folder in the save folder for that specific world, and edit the ultra_amplified_mod-server.toml file to change the config. Then enter the Minecraft world or restart server for the changes to take place. To make a fresh new world with a different config, create a new world and force close Minecraft before it starts showing the generation percentage. Then change the config and re-enter the world.
·Added config options for the new ocean biomes and ocean structures.
·Seperated scattered structures config to now let players control generation of every kind of scattered structure separately.
·Now can control whether Nether Fortresses can generate aboveground or underground or both.
·Renamed biome config names to match the new biome names in 1.13.

     Biomes:
·Added Ultra Amplified versions of all Oceans! Deep Oceans has Shipwrecks and Ocean Monuments while shallow Oceans has Ruins, Treasure Chests, and Ocean Monuments!
·All Ocean biomes can spawn a new kind of Treasure Chest that is marked with an 'X' made of Red Sand at Y = 52! I call this feature... Marked Treasure Chest! (Original I know lol)
·Most biomes now generate extra Gold, Redstone, and Lapis in a small range around 5 and 15 blocks below sea level to make underwater diving more rewarding.
·Many changes was made to how features (plants, snow, etc) so they spawn more efficiently and cleaner on all ledges.
·Ice Mountain biome now has lakes made of Ice and Blue Ice.
·Ice Mountain biome now can generate waterfalls made of Blue Ice.
·Ice Spike biome now has a layer of Ice with Water below it at sea level instead of Snow Blocks.
·Nether biome now spawns regions of Gravel and Soul Sand that is different at each height (before it was based on x and z positions).
·Nether biome now generates a layer of Magma Blocks several blocks below sea level Water and then generates Lava below the Magma Blocks.
·Nether biome now generates Quartz Ore less frequently above Y = 120.
·End biome now generates End Islands less frequently at higher heights.
·End biome now generates Shulker Boxes and Dragon Heads below sea level at a low chance and can spawn a Dragon Egg at any height but is extremely rare.
·Desert M biome now generates large areas of water to better fit its new 1.13 name which is Desert Lake biome.
·M forms of Redwood Taiga biomes now generates tiny boulders and their pillars are now shorter but thicker.
·Jungle Edge biomes now generates always at Jungle borders.
·All Jungle biomes no longer spawn Ocean Monuments.
·Nether biome will generate a border of Savanna biome if it touches a Snowy Tundra or Snowy Taiga biome.
·Biomes that does not use Stone for their main block now generates their block true to their biome edge (before, if you were underground, you would see the Nether's Netherrack spawning by chunks instead of following the Nether's biome edge cleanly)
·Savanna Plateau and Shattered Savanna Plateau now generates giant pillars under its dirt terrain!
·Tall Birch Forest Hills biome now generates more giant Birch Trees to make it more dense.
·Desert Biomes, Snowy Beach biome, and Gravely Mountain biomes now have blocks that cannot fall under their Sand/Gravel to help reduce the insane nearly-unplayable lag that occurs when ledges of Sand/Gravel begins falling.
·Removed Bamboo Forest due to Bamboo Jungle being added in vanilla 1.14 and that this biome caused a great deal of lag from all the Sugar Cane.
       
       Structures:
·Hay Bales in M forms of Jungles are now more common.
·Dungeons now have a new appearance for what biome they are in. (Forms include normal, mesa, desert, snow, nether, end, dark forest, mushroom, and swamp)
·Dungeon now have less strict requirements on where they can generate and so spawnrates and density bands have been re-adjusted to account for more successful generations.
·Mineshafts now takes on the Swamp variant when in Dark Forest biome and Ocean variant when in any Ocean biome.
·Mineshafts now place their supports/archs even when the hallway is not in the ground.
·Fossils now generates on the surface of a ledge that does not have direct sunlight (before it could spawn floating in midair).
·End Cities now generates much, much bigger (three full branches instead of one).
·Desert Temples now have more TNT and is more dangerous to make safe. Be very careful!
·Jungle Temples now have more hidden chests, more Dispensers that shoot arrows, and some Cobblestone is infested with Silverfish.
·Witch Huts now generate 2 Witches at start instead of 1.
·Witch Huts now continually spawn Witches over time which makes a Witch farm now possible.
·Woodland Mansions cannot spawn in Plains biome or mutated Birch Forest biomes now.
·Igloos can now also spawn in Ice Mountain biome and all Snowy Taiga biomes.
·Fixed Ocean Monuments so most rooms can be accessed now without resorting to TNT to break walls. Though there is a glitch that lets terrain blocks generate inside the monument at the moment.
·Ocean Monuments now continually spawn Guardians over time which makes a Guardian farm now possible.
·Adjusted what kind of blocks spawn in Villages.
·Ravines are now split between two levels. The first kind of Ravine spawn higher at a high rate to make a maze. The second kind of Ravine spawns around lava level and are extremely long. Both kinds of ravines are much shorter than the Ravines in 1.12 version of this mod.
·Cave Cavities have been redone. They now spawn giant areas with ledges along the edges with stalagmites in the ceiling. Giant pillars will also spawn that flattens out towards the bottom to provide a path for the player to walk across past the lava floor of the cave.
·Icy Village now generates in Snowy Beach and Snowy Tundra biomes as well.
·Crosses in Swampland Hills biome will now have a 10% chance of generating a Wither Skeleton Skull instead of a normal Skeleton Skull if it has room for the skull.

       
    (V.4.4 Changes) (1.12 Minecraft)
    
     Config:
·Added super secret setting that does something! (Can't say what lol. Try it!)
·Fixed some config namings.
·Added config option to turn on and off mini-structures such as Crosses, Hay Bale, Fossils, Sun Shrine, Stonehenges, and Desert Wells.
·Made lower config values now spawn more Villages, Temples, Nether Fortresses, End Cities, Ocean Monuemnts, and Woodland Mansions but raised default value to keep same default spawnrates.
    
     Biomes:
·Biome distribution is now much better when fewer biomes are selected in the config settings.
·Fixed bug where Desert M Biome spawned instead of Desert Hills Biome.
·Bamboo Forest now spawns Llamas, Parrots, Pigs, Chickens, and Bats instead of default passive mobs.
·Increased Pumpkin spawn rate due to difficulty in finding them with current terrain.
·Fixed Chorus Plant's spawn placement so they are less clustered. (Probably not noticable)
·Fixed bug where End Islands could spawn low enough to replace Bedrock at Y = 0 with End Stone.
·Increased Slime spawnrate in Swampland M Biome.

    Structures:
·Added a very rare Sun Shrine to generate in any biome with "Hills" in its name. (Except for Extreme Hills Biomes)
·Added a rare small Stonehenge to generate in any biome with "Hills" in its name (Except for Extreme Hills Biomes) and has a very small chance of generating an Enchanting Table.
·Villages in Stone Beach Biome generates Stone instead of Dirt so the bottom of Farmlands blend better with the biome.
·For Mesa and Desert biomes, replaced the 1% Dungeon chance of Giant Zombie Spawners with Illusioner Spawners as Giant Zombies cannot spawn through Mob Spawners.
·The bug that caused Ocean Monument to not spawn at correct heights is squashed. Now they generate between the height Y = 97 and Y = 242 regardless of what the terrain height is.
·Witch Huts' Brewing Stand now can have up three potions which can be Potion of Leaping, Potion of Poison, and Potion of Night Vision with the last potion being the rarest.

     Misc:
·Updated info that shows under this mod's World Type to be more accurate.
  
  

    (V.4.3 Changes) (1.12 Minecraft)
    
    Config:
·Fixed several config descriptions, names, and order.
·Added config option to choose whether Giant Pit Mineshafts or aboveground Floating Mineshafts spawn!
·Increased max possible spawnrate for several options from 500 to 1000.
·Decreased minimum possible spawn height for Sea Level option from 65 to 0.
·Biome Size option can be set to 1 now. (Looks really ridiculous though lol)
·Broken up the Ore Spawnrate option to being many options to control each ore's spawnrate individually.
·Added option to control Magma spawnrate below Y = 100 in Nether Biome.
·Added option to control single Lava Block spawnrate in Nether Biome.
·Changed Silverfish Block Spawnrate option for Extreme Hills Biome and Stronghold into two seperate options.
    
    Biomes:
·Made Plains, Forest, and Ice Mountain unable to spawn through the GenLayer for hills if those biomes are disabled.(example: Roofed Forests Biomes may generate Plains Biomes in them which is unwanted if player has the option for Plains disabled.)
·Lava Ocean will spawn instead of Snow Blocks in Ice Mountain and Ice Spike Biome when the Lava Ocean config option is turned on.
·Decreased Lava spawn rate in Nether to cut down a bit on the excessive amount of Lavafalls. 
·Slightly adjusted Magma and Quartz spawnrates in Nether Biome.
·Made Taiga M and Cold Taiga M Biomes look like a miniature Mega Taiga Biome.
·Made Swampland M Biome now have wooden crosses with a chance of a hidden chest, thicker spooky trees, and swamp water can spawn at higher heights. 
·Made Mega Spruce Taiga and Redwood Taiga Hills M Biomes (both looks almost exactly the same) have larger boulders that are stacked on top of each other to form massive pillars.
·Made boulders have decreased chance of spawning ores.
·Changed Cactus generation to be shorter and have arms to look much better instead of looking like a green stick lol.
·Changed Desert M Biome to have Smooth Sandstone at top with patches of Sand along with increased Dead Bush spawnrate and taller Cactus than normal Desert Biomes.
·Jungle M Biomes now generates large bodies of water (that aren't floating or at edge of cliffs) and will spawn huge amounts of flowers but with no red flowers. In addition, Hay Bale Piles will rarely spawn as well.
·Jungle Biomes will very rarely spawn Green Concrete Powder Patches but Jungle M Biomes will spawn them more commonly.
·Fixed cascading worldgen lag due to Chorus Plant.

    Structures:
·Added Dark Oak Village to spawn in Roofed Forest Biome.
·Changed what Doors spawn for some Village types.
  
    Misc:
·World generation progress is shown on loading screen when creating a new world!



   (V.4.2 Changes) (1.12 Minecraft)
   
    Biomes:
·Fixed major crash when selecting certain single biomes to spawn through config.    
·Added massive Dark Oak trees to Roofed Forest M biomes.    
·Adjusted world height that giant Birch Trees can generate to in Birch Forest M biome so the tree does not get cut off as often.
    
    Structures:
·Fixed Mineshaft bug not spawning Rails in them.
·Rails now will spawn in Icy Mineshafts (but will break off Ice Block when a block update occurs).
    


   (V.4.1 Changes) (1.12 Minecraft)

    Major Change:
·Added config options to select biomes, biome size, ores spawnrates, structures spawnrates, and certain decorative generations!

    Biomes:
·Fixed Bug that prevented M variants of biomes from spawning naturally.
·Increased spawnrate of natural M variant Biomes.
·Attempting to optimize biome's ore generation to make creating a new world work faster.
·Bamboo Forest generates less Sugar Cane and Vines to help reduce lag. 
·Vines in Bamboo Forest do no generate underground anymore.
·Smoothed out border of End Biome and Nether Biome against other biomes.
·Slightly reduced End Island spawn rate in End Biome to make it a little less messy.
·Made Birch Forest biomes have taller trees to match the crazy landscape.
·Added massive 2 by 2 Birch Trees to Birch Forest M biomes.

    Structures:
·Attempting to optimize Cave Cavities and Ravines to reduce slowdown with world generation.
·Ravines have more variety in their height size.
·Cave Cavities spawn slightly less now.
·Fixed bug where normal sized Dirt Room Mineshafts spawned underground instead of aboveground.
·Added Nether style Mineshaft to spawn in Nether Biome.



    (V.4.0 Changes) (1.12 Minecraft)

    Major Change:
·Made the .jar mod now a compatiable Forge mod! WOOOOOOOOOO!!!!!!!!!!!
·Updated mod from 1.11.0 to 1.12.2

    Biomes:
·Large Mushrooms now generate under floating islands and overhangs.
·Tall Grass, Deadbush, and small Mushrooms is now more likely to spawn under floating islands and overhangs.
·Changed the distribution of Corse Dirt and Grass Blocks in Bamboo Forest Biome.
·Vines now spawn from edge of a block downward until they hit another block in Bamboo Forest Biome.
·Vines now generate up to 5 blocks downward below edge of a block in Jungle Biome.
·Vines in Bamboo Forest and Jungle can generate underground too.
·Stone Beach has increased mob spawn rate.
·Made Iron Ore, Coal Ore, Redstone Ore, Andersite, Dorite, and Granite more common in Stone Beach Biome.
·Increased height at which Emerald Ore and Silverfish Stone Blocks spawn in all Extreme Hills Biome.
·Stone Blocks are now replaced with Ice Blocks in Ice Mountain Biome.
·Ice Villages can spawn in Ice Mountain Biome.
·Large underground lakes turn into Snow Blocks in Ice Spike and Ice Mountain Biome.
·Reduced waterfalls spawn rate underground.
·End, Nether, and Bamboo Forest now have specific colored water for their biomes.
·End and Nether biomes have specific grass and leaves color.
·Nether biome's sky is now a reddish color to make it more spooky!

    Structures:
·Mineshaft Torches now generate facing correct way.
·Made End Villages and End Mineshafts generate End Rods instead of Torches.
·Village paths and pillars blocks now generate properly in Nether, Mesa, and End biomes.
·Zombie Villages keep their Doors now so Zombie Villagers do not walk into sunlight and kill themselves.
·Significantly increased Dungeon spawn rate between Y = 75 and Y = 85.
·Dungeons now will spawn mobs based on what biome it is in with more mob options to choose from.
·Dungeons will no longer replace other Dungeon's Mob Spawner with Cobblestone.
·Witch Huts now have a Brewing Stand and the Cauldron partially filled with water.
·Sharply decreased the excessive rate that chests spawn in Nether Fortress as well as buffed loot of one chest in the fortress.
·Fixed stucture spawn issues again...
·Decreased Structure spawnrates to better balance the world.



    (V.3.1 Changes) (1.12 Minecraft)
    
    Biomes:
·Fixed bug that turned Cold Beach biome into a snowed-in Bamboo Forest.
·Ice Patches generate in Cold Beach biome now.
·Increased Lava Lake spawn rate in Nether Biome.
·Decreased Glowstone, Quartz Ore, and Magma Blocks spawn rate in Nether Biome.
·Quartz Ores and Magma Blocks should now be able to generate at top of Nether Biome landscape.
·Iron Ore, Coal Ore, and Andersite spawn more frequently in Stone Beach Biome at all heights.
·Granite spawns more frequently between Y = 120 and Y = 170 in Stone Beach Biome.
·Dorite spawns more frequently between Y = 150 and Y = 190 in Stone Beach Biome.
·Gold Ores now generates up to Y = 250 in Mesa biomes.
·Ice Mountain Biome now generates a layer of Snow Blocks on top of Ice Blocks instead of Dirt Blocks.

    Structures:
·Fixed bug that made Woodland Mansion spawn in incorrect biomes.
·Tweaked structure generations based on bug fix for Mansions.
·Decreased Spawn rates for all structures by a little bit.
·Decreased spawn rate of Silverfish Blocks in Strongholds.
·Slime Lakes are more uncommon but made Water Lakes generate more at top of land.
·Adjusted height of Nether Fortresses that generate aboveground in Nether Biomes so they should spawn higher.
·Cave cavities now can remove Magma Blocks so no more floating Magma Blocks at lower levels in Nether Biome.
·Cave cavities now generate through water so it leave less chunks uncarved at walls.



    (V.3 Changes) (1.12 Minecraft)

    Biomes:
·Flower Forest generate more flowers in greater density.
·Sunflower Plains generate more Sunflowers in greater density.
·Made Hell biome (Nether biome) less common in overworld and now can only be found next to Mesa Bryce biome.
·Made Hell biome now look very similar to the Nether with all the natural blocks found in Nether plus Nether Wart.
·Added End biome to overworld with islands and Chorus Plant and can only be found next to Ice Spike biome.
·Water level raised to Y = 75 for entire world.
·Added Stone Beach, Cold Beach, and Mesa biomes that was missing from world generation.
·Added a customized Beach Biome that looks like a bamboo forest. (Sugar Cane generate at insane rate and are extra tall)
·Sugar Cane generate a bit higher now.
·Vines generate up to Y = 250 now.
·Trees now can generate under islands and overhangs.
·Increased spawn rate of small Mushrooms in Swamp biome so they can be found easier among the layers of land.

    Structures:
·Made Slime Lakes unable to be covered by blocks so more of them are exposed.
·Increased Woodland Mansion spawn.
·Decreased Fossil spawn rate.
·Added 3 double chests to Ocean Monuments with loot.
·Added tons of Guardians to Ocean Monuments.
·Added End Cities to generate in End biomes.
·Added Nether Fortresses to generate aboveground in Hell biomes (Nether biome).
·Made Nether Fortresses spawn underground around Y = 35 in any biome.
·Slightly increased Zombie Vilage spawn rate.
·Made Zombie Village have Mossy Cobblestone to help distinguish them from normal Villages.
·Mineshafts now generate Rails and Minecarts properly.
·Decreased chest spawn rate in Mineshafts.
·Changed ratio of normal floating Mineshafts to Mineshafts with massive pits.
·Mineshafts now made of Ice blocks in Ice Spike biome.
·Mineshafts now made of Birch Planks in Ice Plains, Birch Forests, and Cold Beach biomes.
·Mineshafts now made of Jungle Planks in Jungle biome.
·Mineshafts now made of Spruce Planks in Taiga biome.
·Mineshafts now made of Sandstone blocks in Desert biome.
·Mineshafts now made of Purpur blocks in End biome.
·Mineshafts now made of Acacia planks in Savanna biome.
·Mineshafts now made of Stone blocks in Stone Beach biomes.
·Mineshafts now made of Grass blocks in Swamp biomes.
·Dark Oak Mineshafts spawn in Nether biomes as well.
·Made Mineshaft paths generate directly in sunlight.
·Made Stronghold generate completely in air instead of in pieces. 
·Replace Silverfish spawner in Portal Room with Endermite spawner.
·Strongholds now generate much much larger and 3 kinds of rooms now has a Silverfish spawner.
·Strongholds generate more chest hallways and Libraries to make up for increased difficulty caused by Silverfish spawners.
·Villages now spawn in End, Nether, Mesa, Ice Spikes, Cold Taiga, and Stone Beach biomes with their own variation of the Village.
·Slightly reduced number of Ravines.
·Readjusted ratio of ores and stones in Mega Taiga boulders so Diamond Ores are slightly more rare.



    (V.1 Changes) (1.12 Minecraft)

    Terrain:
·Made world terrain far more amplified than what amplified world type normally makes. The terrain is a mixture of being layered, full of overhangs, and having loads of floating islands.
·Change caves to be supermassive open areas to create giant underground caverns that are filled with lava at the bottom.
·Increased Ravine spawn rate significantly to insane values as well as limit their height to just be barely above lava at y = 10. This creates a massive maze-like network underground. 
·Made all ores and stone patches (such as granite) spawn higher to compensate for the amplified terrain.
·Made Lapis Ore, Coal Ore, Redstone Ore, and Iron Ore slightly more common. 

    Biomes:
·Made all Biomes generate much smaller to help player not have to travel as far to reach a new Biome.
·Replaced Ocean Biome with Ice Spike Biome.
·Replaced Deep Ocean Biome with Mesa Bryce Biome.
·Added Mushroom Biome to cold biome and neutral Biome generation list so it spawns inland in cold and neutral biome areas.
·Increased spawn rate of small and large mushrooms in Mushroom Biome.
·Gave all Birch Forests super tall Birch Trees that are larger than normal Birch Forest M Trees.
·Made Ice Spikes have a bigger chance of generating extra tall Ice Spikes as well and making those spike's heights much taller.
·Ice Spike's Spikes now will continue to generate downward to the first non-air block below y = 50.  
·Made Spikes in Mesa Bryce much thicker and extremely tall to around max world height.
·Added Silverfish Stone Blocks to Mesa Bryce Biomes but made their spawn rate very low.
·Increased height that Silverfish Stone Blocks spawn in Extreme Hills Biomes and also increased their spawn rate there to keep the density of Silverfish Blocks in that Biome the same.
·Made 2 by 2 trees in Jungle Biomes much taller.
·Made 2 by 2 trees in Mega Taiga Biomes much taller and made their leaves bigger to compensate.
·Changed boulders in Mega Taiga Biomes to be much larger and changed the boulder's block composition to these blocks from most common to least common: Mossy Cobblestone, Cobblestone, Andersite, Coal Ore, Iron Ore, and Diamond Ore.
·Changed Mega Taiga Biomes' and Extreme Hills M Biomes' temperatures to make it so they do not get snow at any height.
·Changed Taiga Biomes' temperature so that they get snow only when higher than y = 234.
·Made Swamp Biomes alternate between land and water when in clear view of the sky for heights between 80 and 200 to create a cool visual effect.
·Increased spawn rates for Sugar Cane in Swamp Biomes.
·Increased spawn rate of Clay in Swamp Biomes. (Can be found quickly by digging around water)
·Added Hell Biome (Nether) as a possible Biome for neutral temperature Biome areas. This Biome is characterized by having no trees or tall grass. All Grass blocks looks dead. Pigmens should spawn in large numbers in this Biome.
·Increased spawn rate of Cactus in Desert Biomes as well as increased how tall they can be.

    Structures:
·Created Slime Lakes that spawn commonly below y = 175 to help facilitate jumping off cliffs safely.
·Villages now spawn more common and can spawn in Jungle and Mesa Bryce Biomes as well. 
·Made Villages be able to fuse with other Villages. (Previously couldn't on Java Edition)
·Added a new Village type for when they spawn in Jungles. (Dubbed as Jungle Village)
·Increased spawn rate of Zombie Villages from 1 in 50 chance to 1 in 10 chance.
·Desert Temples now spawn at the highest non-air Block at their spawn locations like how Jungle Temples always did.
·Made Igloos be able to spawn in Ice Spike Biomes
·Made Jungle Temples be able to spawn in all they different types of Jungle Biomes such as Jungle M Biomes.
·Increased Spawn rare of Desert Temples, Jungle Temples, Witch Huts, and Igloos as well as allow all of them to fuse to themselves.
·Made Ocean Monuments be able to spawn at the highest non-air Block in Jungle Biomes.
·Worked really hard to get the water and rooms to spawn correctly and at the Ocean Monument's correct height.
·Made Woodland Mansions be able to spawn in Jungles Biomes and Plains Biomes as well as Increased it's spawn rate and allow for Woodland Mansions to fuse to themselves.
·Increased spawn rate of Fossil and added Savanna Biomes as a possible Biome for Fossils to spawn in. (Previously couldn't spawn in Savanna in Java Edition)
·Increased Dungeon spawn rates across the board but significantly jack up their spawn rates to extreme values for heights higher than y = 150.
·Decreased Mineshaft spawn rates slightly.
·Increased the max height at which Mineshafts can spawn. 
·Mineshafts that spawn above around y = 130 will generate normally. Any Mineshaft below that will have their Dirt Room stretched up beyond a hundred blocks to create a super deep pit in the world. In addition, these Mineshafts will have their three layers of paths separated and generally generate at bottom, middle, and top of this pit/vertically stretched Dirt Room.
·All Mineshaft Dirt Rooms now can generate even when their location is filled with liquid or air. 
·The radius of the Dirt Rooms in Mineshafts below Y = 130 is increased by 10 blocks to create 20 block wide pits in the world.
·Worked hard to get Strongholds to spawn at height of the land close to their coordinates.
·Increased spawn rate of Strongholds as well.
·Increased Desert Wells and Lava Lake spawn rates.

