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

import net.gtaun.shoebill.common.AbstractShoebillContext;
import net.gtaun.shoebill.common.player.PlayerLifecycleHolder;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.wl.lang.dialog.LanguageSelectionDialog;
import net.gtaun.wl.lang.event.PlayerChangeLanguageEvent;

import java.io.File;

public class LanguageServiceImpl extends AbstractShoebillContext implements LanguageService
{
	private PlayerLifecycleHolder contexts;
	private LocalizedStringStatistic statistic;
	
	
	public LanguageServiceImpl(EventManager rootEventManager)
	{
		super(rootEventManager);
		init();
	}

	@Override
	protected void onInit()
	{
		contexts = new PlayerLifecycleHolder(eventManagerNode);
		statistic = new LocalizedStringStatistic();
		
		contexts.registerClass(PlayerLanguageContext.class);
	}

	@Override
	protected void onDestroy()
	{

	}
	
	@Override
	public void showLanguageSelectionDialog(Player player, SelectLanguageCallback callback)
	{
		LanguageSelectionDialog.create(player, parentEventManager, this, callback).show();
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
		eventManagerNode.dispatchEvent(new PlayerChangeLanguageEvent(player, lang), player);
	}

	@Override
	public Language getFallbackLanguage()
	{
		return statistic.getFallbackLanguage();
	}

	@Override
	public void setFallbackLanguage(Language lang)
	{
		statistic.setFallbackLanguage(lang);
	}

	@Override
	public boolean isAutoSubFallbackLanguages() { return  statistic.isAutoSubFallbackLanguages(); }

	@Override
	public void toggleAutoSubFallbackLanguages(boolean enable) { statistic.toggleAutoSubFallbackLanguages(enable); }

	@Override
	public LocalizedStringSet createStringSet(File dir)
	{
		LocalizedStringSet stringSet = new LocalizedStringSet(this, dir);
		statistic.add(stringSet);
		return stringSet;
	}

	@Override
	public int getStrings(Language lang)
	{
		return statistic.getStrings(lang);
	}

	@Override
	public int getMaxStrings()
	{
		return statistic.getMaxStrings();
	}

	@Override
	public float getCoverPercent(Language lang)
	{
		return statistic.getCoverPercent(lang);
	}
}
