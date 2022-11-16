package de.rexituz.bingo.random;

import de.rexituz.bingo.config.ConfigFile;
import de.rexituz.bingo.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Random;

public class RandomItems {
    Main plugin = Main.getPlugin();
    ConfigFile configFile = plugin.getConfigFile();
    ArrayList<Material> finalMaterials = new ArrayList<Material>();
    ArrayList<Material> easyMaterials;
    ArrayList<Material> mediumMaterials;
    ArrayList<Material> hardMaterials;
    boolean alreadyRandomized = false;

    public RandomItems(){
        easyMaterials = new ArrayList<>();
        mediumMaterials = new ArrayList<>();
        hardMaterials = new ArrayList<>();

        for(String s : configFile.getStringListFile("plugins/Bingo/config.yml", "Items.Easy")){
            if(Material.getMaterial(s) != null){
                Material mat = Material.getMaterial(s);
                easyMaterials.add(mat);
            }
        }

        for(String s : configFile.getStringListFile("plugins/Bingo/config.yml", "Items.Medium")){
            if(Material.getMaterial(s) != null){
                Material mat = Material.getMaterial(s);
                mediumMaterials.add(mat);
            }
        }

        for(String s : configFile.getStringListFile("plugins/Bingo/config.yml", "Items.Hard")){
            if(Material.getMaterial(s) != null){
                Material mat = Material.getMaterial(s);
                hardMaterials.add(mat);
            }
        }
    }

    public void randomizeItems(){
        if(!alreadyRandomized){
            for(int i = 0; i < 9; i++) {
                boolean b = true;
                while(b) {
                    Material material;
                    int rnd;
                    switch (i){
                        case 0: case 1: case 2: case 3:
                            rnd = new Random().nextInt(easyMaterials.size());
                            material = easyMaterials.get(rnd);
                            break;
                        case 4: case 5: case 6:
                            rnd = new Random().nextInt(mediumMaterials.size());
                            material = mediumMaterials.get(rnd);
                            break;
                        case 7: case 8:
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
        } else {
            System.out.println(Main.PREFIX + "Materials wurden schon gesetzt!");
        }
    }



    public ArrayList<Material> getFinalMaterials() {
        return finalMaterials;
    }
}
