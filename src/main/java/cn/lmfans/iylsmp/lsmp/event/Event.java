package cn.lmfans.iylsmp.lsmp.event;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class Event implements Listener {

    final String URL = "jdbc:mysql://101.32.14.173:3306/iylsmp?characterEncoding=UTF-8";
    final String PASSWORD = "liangmixx23";
    final String USERNAME = "iylsmp";

    @EventHandler
//   一个事件
    public void onEntityDeath(PlayerDeathEvent event) throws SQLException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Properties properties = new Properties();
        properties.put("debug", "true");
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        //配置并且链接到数据库

        Player player = event.getEntity();
        Player killer = player.getKiller();
        PreparedStatement select = conn.prepareStatement("SELECT `heart` FROM `hearts` WHERE `name`=?");
        select.setString(1, player.getName());
        ResultSet res = select.executeQuery();
        res.next();
        double heartPlayer = res.getDouble("heart");
        PreparedStatement update = conn.prepareStatement("UPDATE `hearts` SET `heart`=? WHERE `name`=?");
        update.setDouble(1, heartPlayer - 2);
        update.setString(2, player.getName());
        update.executeUpdate();
        conn.close();
        if(killer == null){
            //首先需要读取 因为只有一个内容，所以不用while循环处理了
            //处理获取的内容
            //直接怼进数据库里
        }else{
            //首先需要读取 因为只有一个内容，所以不用while循环处理了
            //处理获取的内容
            //直接怼进数据库里
            ItemStack heart = new ItemStack(Material.DRAGON_EGG, 1);
            ItemMeta heartMeta = heart.getItemMeta();
            heartMeta.setDisplayName("心");
            List<String> lore = new ArrayList<>();
            lore.add("全服最宝贵的东西之一: 心心。");
            lore.add("你可以合成他，也可以在你击败玩家时获得他。");
            heartMeta.setLore(lore);
            heart.setItemMeta(heartMeta);

            killer.getInventory().addItem(heart);
            killer.sendMessage(ChatColor.GREEN + "恭喜你拥有了一颗心心！右键使用它！");
        }
        //被杀死到玩家也需要在数据库中减少心心

    }

    @EventHandler
    public void onPlayerRespawnEvent(PlayerRespawnEvent event) throws IOException, SQLException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Player player = event.getPlayer();
        Properties properties = new Properties();
        properties.put("debug", "true");
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        //初始化变量，链接数据库
        PreparedStatement select = conn.prepareStatement("SELECT `heart` FROM `hearts` WHERE `name`=?");
        select.setString(1, player.getName());
        ResultSet res = select.executeQuery();
        //运行一下Select语句
        res.next();
        double playerHeart = res.getDouble("heart");
        //不用While遍历了，直接获取就行
        if (playerHeart > 2) {
            player.sendMessage("你现在的生命最大值是: " + ChatColor.RED + playerHeart);
//            player.sendMessage("你的" + ChatColor.GREEN + "心心" + ChatColor.WHITE + "被" + ChatColor.RED + killer.getName() + ChatColor.WHITE + "吃掉了");
            //发送信息
            //设置血量
            player.setMaxHealth(playerHeart);
            //降低数据库血量
            //改完一堆数据就可以滚进数据库了
        } else {
            // 如果这个b不太幸运，他就被banned 15天
            player.sendMessage("你现在的生命最大值是: " + ChatColor.RED + playerHeart);
            BanList banList = Bukkit.getBanList(BanList.Type.NAME);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 15);
            Date banExpireDate = calendar.getTime();

            banList.addBan(player.getName(), "你的生命已经被服务器吃掉了！", banExpireDate, "Server");
            player.kickPlayer("因为你的生命已经被服务器吃掉了，所以你被封15天咯！");
        }
        conn.close();
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) throws SQLException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Player player = event.getPlayer();
        Properties properties = new Properties();
        properties.put("debug", "true");
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        //初始化变量，连数据库
        PreparedStatement select = conn.prepareStatement("SELECT `heart` FROM `hearts` WHERE name=?");
        select.setString(1, player.getName());
        ResultSet res = select.executeQuery();
        //从数据库里搞点数据出来
        if (res.next()) {
            // 如果这个玩家没有数据，则执行插入数据库
            player.setMaxHealth(res.getDouble("heart"));
        } else {
            //否则就修改血量

            PreparedStatement insert = conn.prepareStatement("INSERT INTO `hearts`(`name`, `heart`, `uuid`) VALUES (?,20,'暂时不支持添加UUID')");
            insert.setString(1, player.getName());
            insert.executeUpdate();
        }
        conn.close();
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) throws SQLException {

        Player player = event.getPlayer();
        //处理返回内容
        if (event.getMaterial() == Material.DRAGON_EGG && player.getGameMode() == GameMode.SURVIVAL) {
            //如果右键的物品为龙蛋
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            ItemStack heart = new ItemStack(Material.DRAGON_EGG, 1);
            ItemMeta heartMeta = heart.getItemMeta();
            heartMeta.setDisplayName("心");
            List<String> lore = new ArrayList<>();
            lore.add("全服最宝贵的东西之一: 心心。");
            lore.add("你可以合成他，也可以在你击败玩家时获得他。");
            heartMeta.setLore(lore);
            heart.setItemMeta(heartMeta);

            Properties properties = new Properties();
            properties.put("debug", "true");
            Connection conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
            //链接数据库，初始化变量
            PreparedStatement select = conn.prepareStatement("SELECT `heart` FROM `hearts` WHERE name=?");
            select.setString(1,player.getName());
            ResultSet res = select.executeQuery();
            //读数据库内容
            res.next();
            double playerHeart = res.getDouble("heart");
            player.getInventory().removeItem(heart);
            player.sendMessage(ChatColor.RED + "恭喜你又多了一颗心心！");
            player.setMaxHealth(playerHeart + 2);
            //修改数据库
            PreparedStatement update= conn.prepareStatement("UPDATE `hearts` SET heart=? WHERE name=?");
            update.setDouble(1,playerHeart+2);
            update.setString(2,player.getName());
            update.executeUpdate();
            conn.close();
        }
    }
}