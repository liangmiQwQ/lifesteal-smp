package cn.lmfans.iylsmp.lsmp;

import cn.lmfans.iylsmp.lsmp.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import cn.lmfans.iylsmp.lsmp.recipe.heart;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public final class Lsmp extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("[LSMP]Server run");
        heart.recipeHeart();
        getServer().getPluginManager().registerEvents(new Event(),this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("[LSMP]Server down");
    }
}
