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

package net.gtaun.wl.lang.dialog;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.common.dialog.AbstractDialog;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.wl.common.dialog.AbstractListDialog;
import net.gtaun.wl.common.dialog.DialogUtils;
import net.gtaun.wl.lang.Language;
import net.gtaun.wl.lang.LanguageService;

public class LanguageSelectionDialog extends AbstractListDialog
{
	public LanguageSelectionDialog(final Player player, Shoebill shoebill, EventManager eventManager, AbstractDialog parentDialog, final LanguageService languageService)
	{
		super(player, shoebill, eventManager, parentDialog);
		this.caption = "Please select your language";
		
		Map<Float, Language> languages = new TreeMap<Float, Language>(new Comparator<Float>()
		{
			@Override
			public int compare(Float o1, Float o2)
			{
				return o1 < o2 ? 1 : -1;
			}
		});
		for (final Language lang : Language.values())
		{
			languages.put(languageService.getCoverPercent(lang) - (lang.ordinal() / 1000000.0f), lang);
		}
		
		for (final Language lang : languages.values())
		{
			final float coverPercent = languageService.getCoverPercent(lang) * 100.0f;
			String item = String.format("%1$s(%2$1.1f%% Completed)", DialogUtils.rightPad(lang.getName(), 24, 8), coverPercent);
			dialogListItems.add(new DialogListItem(item)
			{
				@Override
				public void onItemSelect()
				{
					player.playSound(1083, player.getLocation());
					languageService.setPlayerLanguage(player, lang);
				}
			});
		}
	}
	
	@Override
	protected void onClickCancel()
	{
		super.onClickCancel();
		show();
	}
}
