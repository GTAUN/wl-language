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

import net.gtaun.shoebill.Shoebill;
import net.gtaun.shoebill.common.dialog.AbstractDialog;
import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.wl.common.dialog.AbstractListDialog;
import net.gtaun.wl.lang.Language;
import net.gtaun.wl.lang.LanguageService;

public class LanguageSelectionDialog extends AbstractListDialog
{
	public LanguageSelectionDialog(final Player player, Shoebill shoebill, EventManager eventManager, AbstractDialog parentDialog, final LanguageService languageService)
	{
		super(player, shoebill, eventManager, parentDialog);
		this.caption = "Please select your language";
		for (final Language lang : Language.values())
		{
			dialogListItems.add(new DialogListItem(lang.getName())
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
