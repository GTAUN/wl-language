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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Language
{
	CHINESE_SIMPLIFIED	("CHS", 936,	"Simplified Chinese",	"¼òÌåÖÐÎÄ",		"简体中文",		"CHT", "ENG"),
	CHINESE_TRADITIONAL	("CHT", 950,	"Traditional Chinese",	"ÁcÅé¤¤¤å",		"繁體中文",		"CHS", "ENG"),
	ENGLISH				("ENG", 1252,	"English",				"English",		"English"		),
	RUSSIAN				("RUS", 1251,	"Russian",				"Ðóññêèé",		"Русский",		"ENG"),
	GERMAN				("DEU", 1252,	"German", 				"Deutsch", 		"Deutsch",		"ENG"),
	SPANISH				("ESP", 1252,	"Spanish", 				"Español", 		"Español",		"ENG"),
	FRENCH				("FRA", 1252,	"French",				"Français",		"Français",		"ENG"),
	ITALIAN				("ITA", 1252,	"Italian", 				"Italiano", 	"Italiano",		"ENG"),
	HUNGARIAN			("HUN", 1250,	"Hungarian", 			"Magyar", 		"Magyar",		"ENG"),
	DUTCH				("NLD", 1252,	"Dutch",				"Nederlands",	"Nederlands",	"ENG"),
	PORTUGUESE			("PTG", 1252,	"Portuguese", 			"Português", 	"Português",	"ENG"),
	SWEDISH				("SVE", 1252,	"Swedish",				"Svenska",		"Svenska",		"ENG"),
	POLISH				("PLK", 1250,	"Polish",				"Polski",		"Polski",		"ENG"),
	ROMANIAN			("ROM", 1250,	"Romanian",				"Română",		"Română",		"ENG"),
	KOREAN				("KOR", 949,	"Korean",				"ÇÑ±¹¾î",		"한국어",			"ENG"),
	JAPANESE			("JPN", 932,	"Japanese",				"“ú–{Œê",		"日本語",		"ENG"),
	THAI				("THA", 874,	"Thai",					"ä·Â",			"ไทย",			"ENG");
	
	
	private static final Map<String, Language> VALUES = new HashMap<>();
	public static Language get(String abbr)
	{
		return VALUES.get(abbr);
	}
	
	static
	{
		for(Language val : values()) VALUES.put(val.abbr, val);
	}
	

	private final String abbr;
	private final int codepage;
	private final String name;
	private final String nativeCp1252;
	private final String nativeName;
	private final String[] substitutesAbbr;
	private List<Language> substitutes;
	
	
	Language(String abbr, int codepage, String name, String nativeCp1252, String nativeName, String... substitutesAbbr)
	{
		this.abbr = abbr;
		this.codepage = codepage;
		this.name = name;
		this.nativeCp1252 = nativeCp1252;
		this.nativeName = nativeName;
		this.substitutesAbbr = substitutesAbbr;
	}
	
	public String getAbbr()
	{
		return abbr;
	}
	
	public int getCodepage()
	{
		return codepage;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getNativeCp1252()
	{
		return nativeCp1252;
	}
	
	public String getNativeName()
	{
		return nativeName;
	}
	
	public List<Language> getSubstitutes()
	{
		if (substitutes == null)
		{
			substitutes = new ArrayList<>();
			for (String abbr : substitutesAbbr) substitutes.add(get(abbr));
			substitutes = Collections.unmodifiableList(substitutes);
		}
		return substitutes;
	}
}
