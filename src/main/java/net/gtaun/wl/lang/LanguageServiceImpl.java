/**
 * WL Multi Language Support Plugin
 * Copyright (C) 2013 MK124
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.gtaun.wl.lang;

import java.io.File;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.common.AbstractShoebillContext;
import net.gtaun.shoebill.common.player.PlayerLifecycleHolder;
import net.gtaun.shoebill.event.PlayerEventHandler;
import net.gtaun.shoebill.event.player.PlayerConnectEvent;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.util.event.EventManager.HandlerPriority;
import net.gtaun.wl.lang.dialog.LanguageSelectionDialog;

public class LanguageServiceImpl extends AbstractShoebillContext implements LanguageService
{
	private PlayerLifecycleHolder contexts;
	
	
	public LanguageServiceImpl(Shoebill shoebill, EventManager rootEventManager)
	{
		super(shoebill, rootEventManager);
		init();
	}

	@Override
	protected void onInit()
	{
		contexts = new PlayerLifecycleHolder(shoebill, eventManager);
		contexts.registerClass(PlayerLanguageContext.class);
		
		eventManager.registerHandler(PlayerConnectEvent.class, playerEventHandler, HandlerPriority.NORMAL);
	}

	@Override
	protected void onDestroy()
	{
		
	}

	@Override
	public Language getPlayerLanguage(Player player)
	{
		PlayerLanguageContext context = contexts.getObject(player, PlayerLanguageContext.class);
		return context.getLanguage();
	}

	@Override
	public void setPlayerLanguage(Player player, Language lang)
	{
		PlayerLanguageContext context = contexts.getObject(player, PlayerLanguageContext.class);
		context.setLanguage(lang);
	}

	@Override
	public LocalizedStringSet createStringSet(File dir)
	{
		return new LocalizedStringSet(this, dir);
	}
	
	private PlayerEventHandler playerEventHandler = new PlayerEventHandler()
	{
		protected void onPlayerConnect(PlayerConnectEvent event)
		{
			Player player = event.getPlayer();
			new LanguageSelectionDialog(player, shoebill, eventManager, null, LanguageServiceImpl.this).show();
		}
	};
}
