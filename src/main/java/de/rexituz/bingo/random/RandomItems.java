package de.rexituz.bingo.random;

import de.rexituz.bingo.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RandomItems {
    ArrayList<Material> finalMaterials = new ArrayList<Material>();
    ArrayList<Material> easyMaterials = new ArrayList<Material>(Arrays.asList(Material.DIRT, Material.STICK, Material.COAL, Material.PAINTING, Material.PAPER, Material.BOWL, Material.FISHING_ROD, Material.WOOD, Material.WOOD_AXE, Material.BUCKET, Material.GLASS, Material.GOLD_NUGGET, Material.EGG, Material.APPLE, Material.FEATHER, Material.PORK, Material.WOOL, Material.STICK, Material.GLASS, Material.WHEAT, Material.SUGAR_CANE));
    ArrayList<Material> mediumMaterials = new ArrayList<Material>(Arrays.asList(Material.IRON_AXE, Material.IRON_HOE, Material.BOW, Material.FLINT_AND_STEEL, Material.FLOWER_POT_ITEM, Material.ARROW, Material.BREAD, Material.BOOK_AND_QUILL, Material.CAULDRON_ITEM, Material.FERMENTED_SPIDER_EYE));
    ArrayList<Material> hardMaterials = new ArrayList<Material>(Arrays.asList(Material.GOLD_INGOT, Material.REDSTONE, Material.HOPPER, Material.LAVA_BUCKET, Material.BOOKSHELF));
    boolean alreadyRandomized = false;

    public void randomizeItems(){
        if(!alreadyRandomized){
            for(int i = 0; i < 9; i++) {
                boolean b = true;
                while(b) {
                    Material material;
                    int rnd;
                    switch (i){
                        case 0: case 1: case 2: case 3: case 4: case 5:
                            rnd = new Random().nextInt(easyMaterials.size());
                            material = easyMaterials.get(rnd);
                            break;
                        case 6: case 7:
                            rnd = new Random().nextInt(mediumMaterials.size());
                            material = mediumMaterials.get(rnd);
                            break;
                        case 8:
                            rnd = new Random().nextInt(hardMaterials.size());
                            material = hardMaterials.get(rnd);
                            break;
                        default:
                            throw new IllegalStateException("Unexpected value: " + i);
                        }

                    if(!finalMaterials.contains(material)){
                        finalMaterials.add(material);
                        b = false;
                    }
                }
            }
            alreadyRandomized = true;
            Bukkit.broadcastMessage(finalMaterials + "");
        } else {
            System.out.println(Main.PREFIX + "Materials wurden schon gesetzt!");
        }
    }



    public ArrayList<Material> getFinalMaterials() {
        return finalMaterials;
    }
}
