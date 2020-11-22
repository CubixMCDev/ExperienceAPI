package eu.cubixmc.experienceapi;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerDataManager {
	
	private Main main;
	
	public PlayerDataManager(Main main) {
		this.main = main;
	}

	public void loadPlayerData(ProxiedPlayer p){
		if(!main.getDataPlayers().containsKey(p)){
			PlayerData playerData = main.getSql().createPlayerData(p);
			main.getDataPlayers().put(p, playerData);
			System.out.println("Experience API : Donnees du joueur " + p.getName() + " chargees avec succès.");
		}
	}
	
	public void savePlayerData(ProxiedPlayer p){
		if(main.getDataPlayers().containsKey(p)){
			main.getSql().updatePlayerData(p);
			System.out.println("Experience API : Donnees du joueur " + p.getName() + " sauvegardees avec succès.");	
		}
	}

}
