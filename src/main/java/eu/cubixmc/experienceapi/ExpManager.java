package eu.cubixmc.experienceapi;

import java.util.ArrayList;
import java.util.UUID;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ExpManager {
	
	private Main main;
	protected String table = "players";
	
	public ExpManager(Main main) {
		this.main = main;
	}
	
	public void setLevel(int level, UUID playeruuid) {
		ProxiedPlayer p = ProxyServer.getInstance().getPlayer(playeruuid);
		if(main.getDataPlayers().containsKey(p)){
			PlayerData data = main.getDataPlayers().get(p);
			data.setLevel(level);
			main.getDataPlayers().remove(p);
			main.getDataPlayers().put(p, data);
		}
	}
	
	public void setExp(int exp, UUID playeruuid) {
		ProxiedPlayer p = ProxyServer.getInstance().getPlayer(playeruuid);
		if(main.getDataPlayers().containsKey(p)){
			PlayerData data = main.getDataPlayers().get(p);
			data.setExp(exp);
			main.getDataPlayers().remove(p);
			main.getDataPlayers().put(p, data);
		}
	}
	
	public int getLevel(UUID playeruuid) {
		ProxiedPlayer p = ProxyServer.getInstance().getPlayer(playeruuid);
		if(main.getDataPlayers().containsKey(p)){
			PlayerData data = main.getDataPlayers().get(p);
			return data.getLevel();
		}else return 0;
	}
	
	public void addExp(int amount, UUID playeruuid) {
		ProxiedPlayer p = ProxyServer.getInstance().getPlayer(playeruuid);
		if(main.getDataPlayers().containsKey(p)){
			PlayerData data = main.getDataPlayers().get(p);
			data.setExp(data.getExp() + amount);
			checkLvlUp(playeruuid);
			main.getDataPlayers().remove(p);
			main.getDataPlayers().put(p, data);
		}
	}
	
	private void checkLvlUp(UUID playeruuid) {
		ProxiedPlayer p = ProxyServer.getInstance().getPlayer(playeruuid);
		PlayerData data = main.getDataPlayers().get(p);
		if(getExp(playeruuid) == getXPfromLevel(getLevel(playeruuid) + 1)) {
			setLevel(getLevel(playeruuid) + 1, playeruuid);
			main.getDataPlayers().remove(p);
			main.getDataPlayers().put(p, data);
		}
		
	}

	public int getExp(UUID playeruuid) {
		ProxiedPlayer p = ProxyServer.getInstance().getPlayer(playeruuid);
		if(main.getDataPlayers().containsKey(p)){
			PlayerData data = main.getDataPlayers().get(p);
			return data.getExp();
		}else return 0;
	}
	
	public int getXPfromLevel(int level) {
		if(level >= 1 && level <= 16) {
			return (int)(Math.pow(level, 2) + 6 * level);
		}
		else if(level >= 17 && level <= 31) {
			return (int)( 2.5 * Math.pow(level, 2) - 40.5 * level + 360);
		}
		else if(level >= 32) {
			return (int)(4.5 * Math.pow(level, 2) - 162.5 * level + 2220);
		}else {
			return 0;
		}
	}
	
	public int getLvlfromExp(int exp) {
		if(exp >= 0 && exp <= 352) {
			int level = polynome(1, 6, -exp);
			return level;
		}else if(exp >= 353 && exp <= 1624) {
			int level = polynome(2.5, -40.5, 360-exp);
			return level;
		}else if(exp >= 1625) {
			int level = polynome(4.5, -162.5, 2220-exp);
			return level;
		}else {
			return 0;
		}
	}
	
	public static int polynome(double a, double b, double c) {
		ArrayList<Integer> solutions = new ArrayList<Integer>();
		int delta = (int) (Math.pow(b, 2) - 4 * a * c);
		if (delta > 0) {
			solutions.add((int) Math.ceil(((-b + Math.sqrt(delta)) / (2*a))));
			// solutions.add((int) ((-b + Math.sqrt(delta)) / 2*a));
		}else if(delta == 0) {
			solutions.add((int) Math.ceil((- b / (2*a))));
		}else if (delta < 0) {
			solutions.add(null);
		}
		return solutions.get(0);
	}
}
