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

import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.service.Service;

public interface LanguageService extends Service
{
	@FunctionalInterface
	public interface SelectLanguageCallback
	{
		void onSelectLanguage(Player player, Language lang);
	}
	
	
	void showLanguageSelectionDialog(Player player, SelectLanguageCallback callback);
	
	Language getPlayerLanguage(Player player);

	void setPlayerLanguage(Player player, Language lang);

	Language getFallbackLanguage();

	void setFallbackLanguage(Language lang);

	boolean isAutoSubFallbackLanguages();

	void toggleAutoSubFallbackLanguages(boolean enable);
	
	LocalizedStringSet createStringSet(File dir);
	
	int getStrings(Language lang);
	
	int getMaxStrings();

	float getCoverPercent(Language lang);
}
