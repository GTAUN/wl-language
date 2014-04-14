package net.gtaun.wl.lang;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
	private Map<Language, Integer> languageStrings;
	private int maxStrings;
	
	
	LocalizedStringSet(LanguageService languageService, File dir)
	{
		this.languageService = languageService;
		configs = new TreeMap<>();
		languageStrings = new TreeMap<>();
		maxStrings = 0;
		
		Set<String> allKeys = new HashSet<>();
		
		for (Language lang : Language.values())
		{
			File file = new File(dir, lang.getAbbr() + ".yml");
			FileConfiguration config = new YamlConfiguration(file);
			if (file.isFile()) config.load();
			configs.put(lang, config);
			
			Collection<String> keys = config.getKeyList();
			allKeys.addAll(keys);
			languageStrings.put(lang, keys.size());
		}

		maxStrings = allKeys.size();
	}
	
	private String processString(Language lang, String str)
	{
		String processed = str;
		Matcher matcher = REF_REGEX.matcher(str);
		while (matcher.find())
		{
			String match = matcher.group();
			String key = match.substring(1, match.length()-1);
			processed = processed.replace(match, get(lang, key));
		}
		return processed;
	}
	
	public String get(Language lang, String key)
	{
		String str = configs.get(lang).getString(key, null);
		if (str != null) return processString(lang, str);
		
		for (Language subLang : lang.getSubstitutes())
		{
			str = configs.get(subLang).getString(key, null);
			if (str != null) return processString(lang, str);
		}
		
		return "#" + key + "#";
	}
	
	public String get(Player player, String key)
	{
		return get(languageService.getPlayerLanguage(player), key);
	}
	
	public String format(Player player, String key, Object... objects)
	{
		String format = get(player, key);
		return String.format(format, objects);
	}
	
	public int getStrings(Language lang)
	{
		return languageStrings.get(lang);
	}

	public int getMaxStrings()
	{
		return maxStrings;
	}
}
