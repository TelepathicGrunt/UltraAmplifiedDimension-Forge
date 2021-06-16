### **(V.9.2.1 Changes) (1.16.5 Minecraft)**

##### Dimension:
* Fixed Trees not spawning under ledges when making other dimensions have UAD's terrain.
  (like the Overworld UAD dimension swap datapack)

##### Mixins:
* Appended mod ID to all my mixins so if they break, the logs will state that the broken mixin is from my mod.


### **(V.9.2.0 Changes) (1.16.5 Minecraft)**

##### Biomes:
* Fixed typo in dimension json file that prevented several biomes from spawning.

* Re-adjusted the backend values for biome layer picking to try and make biomes less spread out and less biased towards icy biomes.

* Ocean biomes are now much more common and not always several thousands blocks apart.


### **(V.9.1.4 Changes) (1.16.5 Minecraft)**

##### Blocks:
* Added recipes for all glow-variant blocks to the Recipe Book when you obtain Glowstone Dust.


### **(V.9.1.3 Changes) (1.16.5 Minecraft)**

##### Config:
* Cleaned up config backend a tiny bit.


### **(V.9.1.2 Changes) (1.16.5 Minecraft)**

##### Biomes:
* Improved Obsidian border between Nether and non-Nether UAD biomes so it looks less weird.


### **(V.9.1.1 Changes) (1.16.5 Minecraft)**

##### Biomes:
* Upgraded /locatebiome command to now search much, much farther in any world with UAD's biome source.

##### Dungeons:
* Fixed dungeons so their mob spawners are randomized properly.
  Please redownload the new datapack of this mod if you were using that to customize this mod.
  That is because I had to change how dungeon processors are setup.
  But if you weren't using the datapack, simply just update this mod and spawners will randomize again.

* Fixed End Dungeons being able to remove each other's End Portal block


### **(V.9.1.0 Changes) (1.16.5 Minecraft)**

##### Dimension:
* The UAD dimension json file now lets you put `"import_all_modded_biomes": true` into the biome_source section.
  This is a quick and dirty way to import all modded biomes into the dimension but those biomes will typically not look good.
  Along with it, you can put `"imported_biome_blacklist": ["mod1:slick_biome", "mod1:crazy_biome"]` into there as well to
  blacklist any biome that `import_all_modded_biomes` will grab.

##### Portal:
* `ultra_amplified_dimension:portal_center_blocks` block tag has been added that lets you change what block is required
  for the center of the portal. This has Polished Diorite by default. if you change this, you might want to change the
  Amplified Portal block's texture and loot table to match the new block you are using.

##### Biomes:
* Fixed sea being covered in terrain blocks if lowered below y = 61. Looks amazing if you put UAD's nether biome into the vanilla nether now!

##### Dungeons:
* Reduced chance of Blue Ice in Snowy Dungeons.

* Fixed Nether Dungeons having Nether Bricks placed in mid-air due to broken processor file.

* Fixed ceiling of Desert Dungeons looking weird when it meets the wall.

* Dungeons will now log error if fed an invalid identifier to a non-existent nbt file.

* Fixed Dungeon Chests being placed on walls instead of floor.

* Dungeons now use Post Processor files to place Vines and other stuff.

##### Misc:
* Made my modifyConstant mixins to the surfacebuilders no longer crash if someone else also modifyConstant the same spot.

* Made Swamp Cross use correct method for setting its chest loottable.


### **(V.9.0.3 Changes) (1.16.4 Minecraft)**

##### Dimension:
-Switched to a safer mixin to get the world's seed if no seed is specified in the JSON.


### **(V.9.0.2 Changes) (1.16.4 Minecraft)**

##### Mixins:
-Prefixed all my accessor and invoker mixins due to this bug in mixins that causes a crash for same named mixins.
 https://github.com/SpongePowered/Mixin/issues/430


### **(V.9.0.1 Changes) (1.16.4 Minecraft)**

##### MAJOR:
-PORTED FROM 1.15.2 to 1.16.4!!!!