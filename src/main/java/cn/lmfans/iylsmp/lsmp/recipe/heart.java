package cn.lmfans.iylsmp.lsmp.recipe;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class heart {
    public static void recipeHeart(){
        ItemStack heart = new ItemStack(Material.DRAGON_EGG,1);
        ItemMeta heartMeta = heart.getItemMeta();
        heartMeta.setDisplayName("心");
        List<String> lore = new ArrayList<>();
        lore.add("全服最宝贵的东西之一: 心心。");
        lore.add("你可以合成他，也可以在你击败玩家时获得他。");
        heartMeta.setLore(lore);
        heart.setItemMeta(heartMeta);

        ShapedRecipe recipe = new ShapedRecipe(heart);
        recipe.shape("DDD","ACA","BBB");
        recipe.setIngredient('D',Material.TOTEM_OF_UNDYING);
        recipe.setIngredient('A',Material.DIAMOND_BLOCK);
        recipe.setIngredient('C',Material.GOLDEN_APPLE);
        recipe.setIngredient('B',Material.NETHERITE_INGOT);
        Bukkit.addRecipe(recipe);
    }

}
