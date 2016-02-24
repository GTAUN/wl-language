package net.gtaun.wl.lang;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

public class LocalizedStringStatistic
{
	private Set<LocalizedStringSet> stringSets;
	private Language fallbackLanguage;
	private boolean autoSubFallbackLanguages;
	
	
	public LocalizedStringStatistic()
	{
		stringSets = Collections.newSetFromMap(new WeakHashMap<LocalizedStringSet, Boolean>());
		fallbackLanguage = Language.ENGLISH;
		autoSubFallbackLanguages = true;
	}
	
	public void add(LocalizedStringSet stringSet)
	{
		stringSets.add(stringSet);
	}
	
	public int getStrings(Language lang)
	{
		int max = 0;
		for (LocalizedStringSet stringSet : stringSets)
		{
			max += stringSet.getStrings(lang);
		}
		return max;
	}
	
	public int getMaxStrings()
	{
		int max = 0;
		for (LocalizedStringSet stringSet : stringSets)
		{
			max += stringSet.getMaxStrings();
		}
		return max;
	}
	
	public float getCoverPercent(Language lang)
	{
		return (float)getStrings(lang) / getMaxStrings();
	}

	public Language getFallbackLanguage() { return fallbackLanguage; }

	public void setFallbackLanguage(Language lang) { fallbackLanguage = lang; }

	public boolean isAutoSubFallbackLanguages() { return autoSubFallbackLanguages; }

	public void toggleAutoSubFallbackLanguages(boolean enable) { autoSubFallbackLanguages = enable; }
}
