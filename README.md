# MAVEN

For developers that want to add UAD to their mod's workspace:

<blockquote>repositories {

&nbsp; maven {

&nbsp; &nbsp; url "https://nexus.resourcefulbees.com/repository/maven-public/"

&nbsp; }

}</blockquote>

&nbsp;

Don't forget to change 9.1.4 with the actual latest version of this mod.

<blockquote>dependencies {


&nbsp; ...


&nbsp; implementation fg.deobf("com.telepathicgrunt:UltraAmplifiedMod:1.16.5-9.1.4-forge")


}</blockquote>

&nbsp;

**Add these two properties to both of your run configs in the build.gradle file. These will allow Blame's mixins to work. After you add the properties lines, refresh Gradle and run `genEclipseRuns` or `genIntellijRuns` or `genVSCodeRuns` based on what IDE you are using.**

<blockquote>minecraft {


&nbsp; ...


&nbsp; runs {


&nbsp; &nbsp; client {


&nbsp; &nbsp; &nbsp; ...


&nbsp; &nbsp; &nbsp; property 'mixin.env.remapRefMap', 'true'


&nbsp; &nbsp; &nbsp; property 'mixin.env.refMapRemappingFile', "${projectDir}/build/createSrgToMcp/output.srg"


&nbsp; &nbsp; }

&nbsp; &nbsp; server {


&nbsp; &nbsp; &nbsp; ...


&nbsp; &nbsp; &nbsp; property 'mixin.env.remapRefMap', 'true'


&nbsp; &nbsp; &nbsp; property 'mixin.env.refMapRemappingFile', "${projectDir}/build/createSrgToMcp/output.srg"


&nbsp; &nbsp; }


&nbsp; }


}</blockquote>

**____________________________________________________________________________**

&nbsp;

![Ultra Amplified Logo](https://i.imgur.com/iOOp7IS.png)

## **CURRENTLY FOR 1.16.5 MC**

**Fabric version of this mod can be found here: https://www.curseforge.com/minecraft/mc-mods/ultra-amplified-dimension-fabric**

&nbsp;

**1.16.5 News:** Added Recipe Book entries of my mod's recipes

&nbsp;

**_(Please report any bugs you find)_**

&nbsp;

**____________________________________________________________________________**

&nbsp;

## **WHAT IS ULTRA AMPLIFIED?**

Ultra Amplified Dimension is a dimension mod that flattens the land into floating layers and uses vanilla-like biomes with nearly every feature modified to fit better with this insane landscape! This includes trees spawning under the layers with patches of Glowstone-infused blocks providing light and large columns connecting layers! Some biomes were improved greatly such as Tall Birch Forest generating massive thick 2x2 trees and ocean biomes now being giant floating bowls of water! And yes, the Nether and End biome is also included in this world too to make things more interesting. Even the Ice Mountain biome was transformed into Iced Terrain biome where all the Stone is now Ice!

&nbsp;

Furthermore, you can find some new structures like Mushroom Temples or Ice Spike Temples and when you dig down, you will find a massive maze-like network of Ravines that leads to giant Cave Cavities with the floor filled with lava! The world is meant to be very difficult and tough but also fun to explore every corner! Be sure to stay safe and bring torches with you as you explore the lower layers! (Also, turn on Heavy Fog in the configs for an even more amazing experience!)

&nbsp;

And lastly, when using this mod, you can craft the 7 glowstone infused blocks with the Crafting Table! Place the dirt-like block to infused in center and then put Glowstone above, below, left, and right of the block to craft the glow variant. Only Glowstone Ore cannot be crafted so it matches the behavior of vanilla ores which cannot be crafted.

&nbsp;

**Also, be sure to check out the images page for several screenshots of what the world looks like! And yes, you can use this mod in a modpack.**

**____________________________________________________________________________**

&nbsp;

## HOW DO I ENTER THIS WORLD?

When using this mod and want to enter this dimension in any worldtype, be sure to build this portal like shown below! 8 Polished Granite, 10 Polished Andesite Slabs, and 1 Polished Diorite. Then right click on the Polished Diorite with Flint and Steel to create the portal block. Right click again on portal block (while not sneaking or crouching) to teleport to the Ultra Amplified Dimension! The reason why right clicking while crouching won't teleport you is so you can now place blocks on the portal itself if you want to. Also, you can mine the portal with any tool or your bare hands if you misplaced it (the portal will drop Polished Diorite when mined). You can change what blocks are needed for the portal frames or what activation items work by using a datapack to override the default tags! An already made datapack you can edit is further down.

&nbsp;

![Picture showing how to make the portal to enter Ultra Amplified Dimension](https://media.forgecdn.net/attachments/292/975/2020-05-18_17.png)

_While playing in UAD's dimension, do note that it may take a moment to load chunks when running a large render distance._

**____________________________________________________________________________**

&nbsp;

## **HOW CAN I CONFIGURE THIS MOD?**
One of the biggest feature of this mod is nearly EVERYTHING worldgen is configurable! And to make it easier for you, all you have to do is download the below datapack that works for both Forge and Fabric, unzip it, edit the JSON files to the settings you want, and then put the datapack into your world's datapack folder which is found in the world's saves folder. When making a new world from scratch, you can click the datapack button and just put your datapack in the folder that was automatically opened for you. Though do note, there is a config file for this mod for the stuff that is not able to be done through datapacks such as heavy fog, allowing nether portals, cloud height, and making UAD's portal block always exit player's to the Overworld from UAD instead of their original dimensions.

 

**------------------**

## **[DATAPACK](https://github.com/TelepathicGrunt/UltraAmplifiedDimension-Fabric/releases/tag/9.0.X)**

**[https://github.com/TelepathicGrunt/UltraAmplifiedDimension-Fabric/releases/tag/9.1.X](https://github.com/TelepathicGrunt/UltraAmplifiedDimension-Fabric/releases/tag/9.1.X)**

**------------------**

&nbsp;

Lets take a look at the datapack! When you enter the data folder, you'll see 4 folder named c, forge, minecraft, and ultra_amplified_dimension. The first three are just for putting my blocks in the right tags for other mods to work with. We can just ignore these folder for most use cases. Instead, open up ultra_amplified_dimension and you will be greeted by several folders that we can check out!

![](https://imgur.com/PAgEN8o)

![A view in the datapack that shows ultra amplified dimension's worldgen folders such as dimension, structures, and worldgen.](https://i.imgur.com/PAgEN8o.png)

**DIMENSION**

This folder holds the `ultra_amplified_dimension.json` file which creates the UAD dimension! Inside it, you can edit all kinds of stuff such as "biome_size" or "sea_level". At the bottom, there are a bunch of structures listed that you can change how common they are. Or you can just delete the structure's entry from this file to make the structure not spawn at all in this dimension! The noise values that created this dimension's shape is also exposed here and can be changed for more crazy or wild world shapes!

&nbsp;

But the really cool thing is the "regions" section. In here, we have "ocean_biomes", "end_biomes", "nether_biomes", "hot_biomes", "warm_biomes", "cool_biomes", and "icy_biomes" lists. And each entry in this list is a group of biomes with a weight. We can add or remove biomes from here to customize the world more!Even other mod's biomes should work if you add them here. But make sure every region has at least 1 biome group in it. Now, "main_biome" and "weight" are the two entries all biome groups must define in order to work. Increase the weight to make a biome more likely to spawn. But you can add "sub_biome", "border_biome", "shore_biome", "mutated_biome", "mutated_sub_biome", or "mutated_border_biome" entries too to add some variety to the main biome. In the future, I would like to try and add a config option that automatically adds other mod's biomes to the dimension so you don't have to manually add them all by JSON but for now, you'll have to edit this file instead.

&nbsp;

If you are daring, go to the config for this mod instead and set "import_all_modded_biomes" to true to import all modded biomes into the dimension! However, most other mod's biomes will not look good in the dimension. This is because UAD's biomes were highly tailored and tweaked to fit the terrain generation perfectly. But other mod's biomes are not made to fill land underneath or be so high up. The "imported_biome_blacklist" is also available to blacklist biomes if you choose to turn on "import_all_modded_biomes". But otherwise, I would recommend manually adding specific modded biomes you know will look good in UAD to the "region" section instead.

&nbsp;

If you are looking to swap the Overworld with UAD's dimension, use this datapack instead that works for both Forge and Fabric:

**[https://github.com/TelepathicGrunt/UltraAmplifiedDimension-Fabric/releases/tag/9.1.X2](https://github.com/TelepathicGrunt/UltraAmplifiedDimension-Fabric/releases/tag/9.1.X2)**

&nbsp;

**DIMENSION_TYPE**

The `ultra_amplified_dimension.json` in this folder determines additional properties of the world such as "ambient_light", "has_skylight", "ultrawarm" (evaporates placed water), "bed_works" (false will make beds explode), and a bit more.

&nbsp;

**LOOT_TABLES**

This folder has a blocks and chests folders. The blocks folder holds the loot tables that controls the drops for UAD's Amplified Portal block, glow-variant blocks, and cactus blocks. The folder called chests holds the loot table that controls what chests contains in UAD's structures such as Mushroom Temples or Dungeons. Edit these to change the loot and drops for chests and blocks!

&nbsp;

**RECIPES**

This controls the crafting recipe for UAD's glow variant blocks. Edit this if you want to make the glow-variant blocks uncraftable or change it if it is conflicting with another mod's recipe.

&nbsp;

**STRUCTURES**

Here, the structures folder will hold the nbt files for all of UAD's structures and nbt features. The parts that makes up the Ice Spike Temple, Dungeons, Sun Shrine, Stonehenge, and more are all located here. You can use Structure Blocks to spawn the structure piece, make edits, resave the changed piece to nbt, and then move it from the world's "generated" folder into this structures folder here to change UAD's structure piece. You can add your own nbt files into here as well and then use a special configured_feature in the worldgen folder to spawn it in the world!

&nbsp;

**TAGS**

Here's where the tags that UAD uses are located. Inside the items folder here is "portal_activation_items.json" where you can add or remove as many items as you want that can be used to create UAD's portal! Inside the blocks folder, "portal_corner_blocks.json" and "portal_non_corner_blocks.json" are used to create the portal frame. These can be edited to allow other kinds of blocks to make the portal besides Polished Granite and Polished Andesite Slabs. The rest of the tags are used for worldgen and should only be edited if there is an issue with worldgen but that shouldn't ever happen.

&nbsp;

**WORLDGEN**

THIS is the BIG folder that contains most of UAD's worldgen logic! The three folder that you would most likely want to edit are the biome, configured_carver, and configured_features folders. The biomes folder has every UAD's biome and you can edit their JSON to add/remove features, structures, carvers, mobs, whatever you want! You can even change the biome's sky color or grass color or more! Just know that the "depth" and "scale" values do nothing in UAD's dimension at the moment. The configured_carver has the ravines and cave cavity files. You can edit their "probability" entry to make the ravines or caves more rare or common. Higher number is more common. You can also change the tallness of ravines or what height they spawn at.


&nbsp;

The configured_feature folder has nearly EVERYTHING you can think of. It controls the placements of trees, how common ores are, etc. The one entry you may want to edit is the "minecraft:count_extra" entry or "ultra_amplified_dimension:ledge_surface_placer" entry at the bottom of the jsons. These two control how often that feature spawn per chunk. For "minecraft:count_extra", the count is how many attempts to spawn the feature while extra_chance is how likely that extra_count number of attempts will be tried on top of count's number. For "ultra_amplified_dimension:ledge_surface_placer", the column_passes entry is how many times the game will scan the chunk from top to sea level and valid_spot_chance is how often it will attempt to spawn the feature when it hits the surface of land as it moved down the chunk.

&nbsp;

The rest of the the JSON should be somewhat easy to work with. Such as copying an ore's file and just swapping out "state": { "Name": "minecraft:coal_ore" }, to the ore you want to spawn instead. Then add the new ore feature you made into the biome's JSON file to have the biome spawn it. Just be aware, "state" entries needs all the properties of the block listed out if the block has properties. Example: "state": { "Properties": {"snowy": "false"}, "Name": "minecraft:grass_block" },

&nbsp;

The one feature I would like to point out is in ministructures/hay_bale_piles_common.json. This configured feature uses my "ultra_amplified_dimension:nbt_feature" feature that I made and this feature lets you specify any nbt file to spawn! Remember back in the STRUCTURES section when I said you can add your own nbt files to that folder? Well, with this configured feature, you can spawn those nbt files in the world! Pretty neat right? Just be sure to add the new configured feature that you make with this to the biome's JSON file so the biome knows to spawn it.

**____________________________________________________________________________**

&nbsp;

**If you find an issue, glitch, or have a suggestion about my mod, let me know! But if you don't have a GitHub account to report in the Issue tab above, just comment what the problem is below and I'll try and get back to you ASAP! :)**

&nbsp;

**Discord Link to #telepathicgrunt-mods channel for my mods! :**

**[https://discord.gg/SM7WBT6FGu](https://discord.gg/SM7WBT6FGu "https://discord.gg/SM7WBT6FGu")**

**[![discord-logo-png-free-transparent-png-logos-discord-png-logo-300_300 (PNG)  | BeeIMG](https://www.freepnglogos.com/uploads/discord-logo-png/concours-discord-cartes-voeux-fortnite-france-6.png)](https://discord.gg/SM7WBT6FGu "https://discord.gg/SM7WBT6FGu")**

