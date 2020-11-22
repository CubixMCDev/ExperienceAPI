package eu.cubixmc.experienceapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.md_5.bungee.api.connection.ProxiedPlayer;


public class SQLConnection {
	
	private Connection connection;
	private String urlbase,host,database,user,pass;
	public Main main;
	
	public SQLConnection(Main main, String urlbase, String host, String database, String user, String pass) {
		this.main = main;
		this.urlbase = urlbase;
		this.host = host;
		this.database = database;
		this.user = user;
		this.pass = pass;
	}

	public void connection(){
		if(!isConnected()){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(urlbase + host + "/" + database, user, pass);
				System.out.println("Party & Friends, Connection : ON");
			} catch (SQLException | ClassNotFoundException e) {				
				e.printStackTrace();
			}
		}
	}
	
	public void disconnect(){
		if(isConnected()){
			try {
				connection.close();
				System.out.println("Party & Friends, Connection : OFF");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean isConnected(){
		return connection != null;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public PlayerData createPlayerData(ProxiedPlayer p){
		
		if(!main.getDataPlayers().containsKey(p)){
			try {
				PreparedStatement rs = connection.prepareStatement("SELECT exp, level FROM players WHERE uuid = ?");
				rs.setString(1, p.getUniqueId().toString());
				ResultSet result = rs.executeQuery();
				
				int exp = 0;
				int level = 0;
				
				while(result.next()){
					exp = result.getInt("exp");
					level = result.getInt("level");
				}
				
				PlayerData dataPlayer = new PlayerData();
				dataPlayer.setExp(exp);
				dataPlayer.setLevel(level);
				return dataPlayer;
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return new PlayerData();
		
	}
	
	public void updatePlayerData(ProxiedPlayer p){
		
		if(main.getDataPlayers().containsKey(p)){
			
			PlayerData dataPlayers = main.getDataPlayers().get(p);
			int exp = dataPlayers.getExp();
			int level = dataPlayers.getLevel();
			
			try {
				PreparedStatement rs = connection.prepareStatement("UPDATE players SET level = ?, exp = ? WHERE uuid = ?");
				rs.setInt(1, level);
				rs.setInt(2, exp);
				rs.setString(3, p.getUniqueId().toString());
				rs.executeUpdate();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}

}
