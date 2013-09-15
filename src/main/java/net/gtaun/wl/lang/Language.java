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

public enum Language
{
	CHINESE_SIMPLIFIED	("CHS", 936,	"Simplified Chinese",	"简体中文",		"CHT", "ENG"),
	CHINESE_TRADITIONAL	("CHT", 950,	"Traditional Chinese",	"繁體中文",		"CHS", "ENG"),
	ENGLISH				("ENG", 1252,	"English",				"English"		),
	GERMAN				("DEU", 1252,	"German", 				"Deutsch",		"ENG"),
	SPANISH				("ESP", 1252,	"Spanish", 				"Español",		"ENG"),
	FRENCH				("FRA", 1252,	"French",				"Français",		"ENG"),
	ITALIAN				("ITA", 1252,	"Italian", 				"Italiano",		"ENG"),
	HUNGARIAN			("HUN", 1250,	"Hungarian", 			"Magyar",		"ENG"),
	DUTCH				("NLD", 1252,	"Dutch",				"Nederlands",	"ENG"),
	PORTUGUESE			("PTG", 1252,	"Portuguese", 			"Português",	"ENG"),
	SWEDISH				("SVE", 1252,	"Swedish",				"Svenska",		"ENG"),
	POLISH				("PLK", 1250,	"Polish",				"Polski",		"ENG"),
	RUSSIAN				("RUS", 1251,	"Russian",				"Русский",		"ENG"),
	ROMANIAN			("ROM", 1250,	"Romanian",				"Română",		"ENG"),
	KOREAN				("KOR", 949,	"Korean",				"한국어",		"ENG"),
	JAPANESE			("JPN", 932,	"Japanese",				"日本語",		"ENG"),
	THAI				("THA", 874,	"Thai",					"ไทย",			"ENG");
	

	private final String abbr;
	private final int codepage;
	private final String name;
	private final String nativeName;
	private final String[] substitutes;
	
	
	Language(String abbr, int codepage, String name, String nativeName, String... substitutes)
	{
		this.abbr = abbr;
		this.codepage = codepage;
		this.name = name;
		this.nativeName = nativeName;
		this.substitutes = substitutes;
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
	
	public String getNativeName()
	{
		return nativeName;
	}
	
	public String[] getSubstitutes()
	{
		return substitutes.clone();
	}
}
