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

import net.gtaun.shoebill.common.player.AbstractPlayerContext;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;

public class PlayerLanguageContext extends AbstractPlayerContext
{
	private Language language;
	
	
	public PlayerLanguageContext(EventManager rootEventManager, Player player)
	{
		super(rootEventManager, player);
	}
	
	@Override
	protected void onInit()
	{
		
	}
	
	@Override
	protected void onDestroy()
	{
		
	}
	
	public Language getLanguage()
	{
		return language;
	}
	
	public void setLanguage(Language lang)
	{
		this.language = lang;
		player.setCodepage(lang.getCodepage());
	}
}
