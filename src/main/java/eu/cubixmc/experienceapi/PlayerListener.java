package eu.cubixmc.experienceapi;

import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerListener implements Listener {
	
	public Main main;
	private PlayerDataManager dataM;
	
	public PlayerListener(Main main) {
		this.main = main;
		this.dataM = new PlayerDataManager(main);
	}
	
	@EventHandler
	public void onJoin(PostLoginEvent e) {
		dataM.loadPlayerData(e.getPlayer());
	}
	
	@EventHandler
	public void onDisconnect(PlayerDisconnectEvent e) {
		dataM.savePlayerData(e.getPlayer());
	}

}
