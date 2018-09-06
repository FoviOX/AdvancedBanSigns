package ua.fox.bansign;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.leoko.advancedban.manager.PunishmentManager;
import me.leoko.advancedban.manager.UUIDManager;
import me.leoko.advancedban.utils.Punishment;

public class Core extends JavaPlugin implements Listener {
	String msg;
	public void onEnable() {
		System.out.println("Plugin AdvancedBanSigns "+this.getDescription().getVersion()+" enabling...");
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		saveDefaultConfig();
		msg = getConfig().getString("Message-On-Place-Sign").replaceAll("&", "§");
		System.out.println("Plugin AdvancedBanSigns "+this.getDescription().getVersion()+" fully enabled!");
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlaceSign(SignChangeEvent e) {
		if(PunishmentManager.get().isMuted(UUIDManager.get().getUUID(e.getPlayer().getName()))) {
			Punishment pun = PunishmentManager.get().getMute(UUIDManager.get().getUUID(e.getPlayer().getName()));
			Block b = e.getBlock();
			e.setCancelled(true);
			b.setType(Material.AIR);
			e.getPlayer().sendMessage(msg.replace("%reason%", pun.getReason()+"").replace("%player%", pun.getOperator()+"").replace("%time%", pun.getDuration(true)+""));
            b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, Material.WALL_SIGN.getId());
		}
	}
}
