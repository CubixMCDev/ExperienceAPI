package eu.cubixmc.experienceapi;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ExpCmd extends Command{
	
	public Main main;
	private ExpManager expM;
	private String PREFIX;
	
	public ExpCmd(Main main) {
		super("experience", null, "exp");
		this.main = main;
		this.expM = new ExpManager(main);
		this.PREFIX = main.getPrefix();
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		if(!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage(new TextComponent("Commande non accessible pour la console"));
			return;
		}
		
		ProxiedPlayer p = (ProxiedPlayer) sender;
		
		if(args.length == 0) {
			sendMessage(p, PREFIX + "§eVous disposez d'actuellement §6" + expM.getExp(p.getUniqueId()) + " §ed'expérience.");
			sendMessage(p, PREFIX + "§eVous êtes au niveau §6" + expM.getLevel(p.getUniqueId()) + ". §eIl vous manque §6" + (expM.getXPfromLevel(expM.getLevel(p.getUniqueId()) + 1) - expM.getExp(p.getUniqueId())) + " §ed'expériences pour passer au niveau §6" + (expM.getLevel(p.getUniqueId()) + 1));
			return;
		}
		
		if(args.length == 3) {
			if(args.length < 3) {
				sendHelp(p);
				return;
			}
			if(args[0].equalsIgnoreCase("add")) {
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
				
				if(target == null) {
					sendMessage(p, PREFIX + "§cLe joueur n'existe pas ou est hors ligne !");
					return;
				}
				
				int amount = Integer.valueOf(args[2]);
				expM.addExp(amount, target.getUniqueId());
				sendMessage(p, PREFIX + "§7Vous venez d'ajouter §6" + amount + " d'expériences à §e" + target.getName());
				sendMessage(p, PREFIX + "§7Vous passer au niveau §d" + expM.getLevel(p.getUniqueId()));
			}
			if(args[0].equalsIgnoreCase("setlevel")) {
				ProxiedPlayer target = ProxyServer.getInstance().getPlayer(args[1]);
				
				if(target == null) {
					sendMessage(p, PREFIX + "§cLe joueur n'existe pas ou est hors ligne !");
					return;
				}
				
				int lvl = Integer.valueOf(args[2]);
				expM.setLevel(lvl, target.getUniqueId());
				expM.setExp(expM.getXPfromLevel(lvl), target.getUniqueId());
			}
		}
	}
	
	private void sendHelp(ProxiedPlayer p) {
		// TODO Auto-generated method stub
		
	}

	public void sendMessage(ProxiedPlayer p, String string) {
		p.sendMessage(new TextComponent(string));
	}

}
