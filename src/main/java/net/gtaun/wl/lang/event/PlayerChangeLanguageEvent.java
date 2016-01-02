package net.gtaun.wl.lang.event;

import net.gtaun.shoebill.event.player.PlayerEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.wl.lang.Language;

public class PlayerChangeLanguageEvent extends PlayerEvent
{
	private final Language language;
	
	
	public PlayerChangeLanguageEvent(Player player, Language language)
	{
		super(player);
		this.language = language;
	}
	
	public Language getLanguage()
	{
		return language;
	}
}
