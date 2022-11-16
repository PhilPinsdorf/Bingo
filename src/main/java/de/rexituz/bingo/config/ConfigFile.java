package de.rexituz.bingo.config;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConfigFile {
    //FILE SPEICHERUNG
    public boolean saveFile(String path, String name, String data) {
        File file = new File(path);
        checkPath(file);
        YamlConfiguration filecon = YamlConfiguration.loadConfiguration(file);
        filecon.set(name, data);
        try {
            filecon.save(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    public boolean saveFile(String path, String name, boolean data) {
        File file = new File(path);
        checkPath(file);
        YamlConfiguration filecon = YamlConfiguration.loadConfiguration(file);
        filecon.set(name, data);

        try {
            filecon.save(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    public boolean saveFile(String path, String name, long data) {
        File file = new File(path);
        checkPath(file);
        YamlConfiguration filecon = YamlConfiguration.loadConfiguration(file);
        filecon.set(name, data);
        try {
            filecon.save(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    public boolean saveFile(String path, String name, double data) {
        File file = new File(path);
        checkPath(file);
        YamlConfiguration filecon = YamlConfiguration.loadConfiguration(file);
        filecon.set(name, data);
        try {
            filecon.save(file);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }


    public String getStringFile(String path, String name) {
        File file = new File(path);
        checkPath(file);
        YamlConfiguration filecon = YamlConfiguration.loadConfiguration(file);
        if(filecon.isSet(name)) {
            return filecon.getString(name);
        }else {
            return null;
        }
    }

    public double getDoubleFile(String path, String name) {
        File file = new File(path);
        checkPath(file);
        YamlConfiguration filecon = YamlConfiguration.loadConfiguration(file);
        if(filecon.isSet(name)) {
            return filecon.getDouble(name);
        }else {
            return 0;
        }
    }

    public Integer getIntFile(String path, String name) {
        File file = new File(path);
        checkPath(file);
        YamlConfiguration filecon = YamlConfiguration.loadConfiguration(file);
        if(filecon.isSet(name)) {
            return filecon.getInt(name);
        }else {
            return 0;
        }
    }

    public boolean getBooleanFile(String path, String name) {
        File file = new File(path);
        checkPath(file);
        YamlConfiguration filecon = YamlConfiguration.loadConfiguration(file);
        return filecon.getBoolean(name);
    }

    public List<?> getListFile(String path, String name) {
        File file = new File(path);
        checkPath(file);
        YamlConfiguration filecon = YamlConfiguration.loadConfiguration(file);
        if(filecon.isSet(name)) {
            return filecon.getList(name);
        }else {
            return null;
        }
    }

    public List<String> getStringListFile(String path, String name) {
        File file = new File(path);
        checkPath(file);
        YamlConfiguration filecon = YamlConfiguration.loadConfiguration(file);
        if(filecon.isSet(name)) {
            return filecon.getStringList(name);
        }else {
            return null;
        }
    }

    public boolean checkPath(File file) {
        if(!new File(file.getParent()).exists()) {
            new File(file.getParent()).mkdirs();
            System.out.println("neuer Path wurde erstellt!");
        }
        if(!file.exists()) {
            try {
                file.createNewFile();
                System.out.println("neue File wurde erstellt!");
                return true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }
    //ENDE

    public void clearFile(String path) {
        File file = new File(path);
        if(!checkPath(file)) {
            file.delete();
            checkPath(file);
        }
    }
}
