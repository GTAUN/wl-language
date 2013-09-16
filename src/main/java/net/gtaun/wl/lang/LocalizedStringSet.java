package net.gtaun.wl.lang;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.util.config.Configuration;
import net.gtaun.shoebill.util.config.FileConfiguration;
import net.gtaun.shoebill.util.config.YamlConfiguration;

public class LocalizedStringSet
{
	private static final Pattern REF_REGEX = Pattern.compile("#[A-Z0-9_.]+#", Pattern.CASE_INSENSITIVE);

	
	private LanguageService languageService;
	private Map<Language, Configuration> configs;
	
	
	LocalizedStringSet(LanguageService languageService, File dir)
	{
		this.languageService = languageService;
		
		configs = new TreeMap<>();
		for (Language lang : Language.values())
		{
			File file = new File(dir, lang.getAbbr() + ".yml");
			FileConfiguration config = new YamlConfiguration(file);
			if (file.isFile()) config.load();
			configs.put(lang, config);
		}
	}
	
	private String processString(Language lang, String str)
	{
		String processed = str;
		Matcher matcher = REF_REGEX.matcher(str);
		while (matcher.find())
		{
			String match = matcher.group();
			String key = match.substring(1, match.length()-1);
			processed = processed.replace(match, getString(lang, key));
		}
		return processed;
	}
	
	public String getString(Language lang, String key)
	{
		String str = configs.get(lang).getString(key, null);
		if (str != null) return processString(lang, str);
		
		for (Language subLang : lang.getSubstitutes())
		{
			str = configs.get(subLang).getString(key, null);
			if (str != null) return processString(lang, str);
		}
		
		return key;
	}
	
	public String getString(Player player, String key)
	{
		return getString(languageService.getPlayerLanguage(player), key);
	}
	
	public String format(Player player, String key, Object... objects)
	{
		String format = getString(player, key);
		return String.format(format, objects);
	}
}