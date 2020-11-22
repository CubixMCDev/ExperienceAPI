package eu.cubixmc.experienceapi;

import java.util.HashMap;
import java.util.Map;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin{
	
	private SQLConnection database;
	private PlayerDataManager dataManager = new PlayerDataManager(this);
	public Map<ProxiedPlayer, PlayerData> dataPlayers = new HashMap<>();
	private String PREFIX = "§8■ §dExpérience §8» ";
	
	public void onEnable() {
		getProxy().getPluginManager().registerCommand(this, new ExpCmd(this));
		getProxy().getPluginManager().registerListener(this, new PlayerListener(this));
		database = new SQLConnection(this, "jdbc:mysql://", "localhost", "cubixbase", "Cubix", "CubixTMPlanet2019");
		database.connection();
	}
	
	public SQLConnection getSql() {
		return database;
	}
	
	public String getPrefix() {
		return PREFIX;
	}
	
	public PlayerDataManager getDataManager() {
		return dataManager;
	}
	
	public Map<ProxiedPlayer, PlayerData> getDataPlayers(){
		return dataPlayers;
	}
}
