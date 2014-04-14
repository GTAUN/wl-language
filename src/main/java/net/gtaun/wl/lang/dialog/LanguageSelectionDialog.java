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

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import net.gtaun.shoebill.object.Player;
import net.gtaun.util.event.EventManager;
import net.gtaun.wl.common.dialog.DialogUtils;
import net.gtaun.wl.common.dialog.WlListDialog;
import net.gtaun.wl.common.dialog.WlListDialog.WlListDialogBuilder;
import net.gtaun.wl.lang.Language;
import net.gtaun.wl.lang.LanguageService;

public class LanguageSelectionDialog
{
	public static WlListDialog create(Player player, EventManager rootEventManager, LanguageService service)
	{
		Map<Float, Language> languages = new TreeMap<Float, Language>((Float o1, Float o2) -> o1 < o2 ? 1 : -1);
		Arrays.stream(Language.values()).forEach((lang) ->
		{
			float coverPercent = service.getCoverPercent(lang) * 100.0f;
			if (coverPercent > 0.0f) languages.put(coverPercent, lang);
		});
		
		return WlListDialog.create(player, rootEventManager)
			.caption("Please select your language")
			.execute((b) ->
				{
					languages.entrySet().forEach((entry) ->
					{
						Language lang = entry.getValue();
						String itemText = String.format("%1$s(%2$s, %3$1.1f%%)", DialogUtils.rightPad(lang.getNativeCp1252()+" ", 16, 8), lang.getName(), entry.getKey());
						b.item(itemText, (d) ->
						{
							player.playSound(1083, player.getLocation());
							service.setPlayerLanguage(player, lang);
						});
					});
				})
			.build();
	}
}
