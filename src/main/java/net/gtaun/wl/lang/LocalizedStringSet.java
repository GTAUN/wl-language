package net.gtaun.wl.lang;

import net.gtaun.shoebill.data.Color;
import net.gtaun.shoebill.object.Player;
import net.gtaun.shoebill.util.config.Configuration;
import net.gtaun.shoebill.util.config.FileConfiguration;
import net.gtaun.shoebill.util.config.YamlConfiguration;

import java.io.File;
import java.text.ChoiceFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalizedStringSet {
    private static final Pattern REF_REGEX = Pattern.compile("#[A-Z0-9_.]+#", Pattern.CASE_INSENSITIVE);
    private LanguageService languageService;
    private Map<Language, Configuration> configs;
    private Map<Language, Integer> languageStrings;
    private int maxStrings;

    LocalizedStringSet(LanguageService languageService, File dir) {
        this.languageService = languageService;
        configs = new TreeMap<>();
        languageStrings = new TreeMap<>();
        maxStrings = 0;

        Set<String> allKeys = new HashSet<>();

        for (Language lang : Language.values()) {
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

    private String processString(Language lang, String str) {
        String processed = str;
        Matcher matcher = REF_REGEX.matcher(str);
        while (matcher.find()) {
            String match = matcher.group();
            String key = match.substring(1, match.length() - 1);
            processed = processed.replace(match, get(lang, key));
        }
        return processed;
    }

    public String get(Language lang, String key) {
        String str = configs.get(lang).getString(key, null);
        if (str != null) return processString(lang, str);

        for (Language subLang : lang.getSubstitutes()) {
            str = configs.get(subLang).getString(key, null);
            if (str != null) return processString(lang, str);
        }

        return "#" + key + "#";
    }

    public String get(Player player, String key) {
        return get(languageService.getPlayerLanguage(player), key);
    }

    public String format(Language language, String key, Object... objects) {
        String format = get(language, key);
        return String.format(format, objects);
    }

    public String format(Player player, String key, Object... objects) {
        return format(languageService.getPlayerLanguage(player), key, objects);
    }

    public String choice(Language language, String key, Object... objects) {
        String choice = get(language, key);
        choice = replaceObjectCodes(choice, "\\{", "\\}");
        choice = String.format(choice, objects);
        choice = replaceColorCodes(choice, "\\{", "\\}", true);
        MessageFormat messageFormat = new MessageFormat(choice);
        ChoiceFormat choiceFormat = applyFormats(messageFormat);
        if(choiceFormat != null) {
            messageFormat.setFormatByArgumentIndex(0, choiceFormat);
        }
        for(int i = 0; i < objects.length; i++) {
            if(!(objects[i] instanceof Number))
                objects[i] = 0;
        }
        choice = messageFormat.format(objects);
        choice = replaceColorCodes(choice, "\\{", "\\}", false);
        return choice;
    }

    public String choice(Player player, String key, Object... objects) {
        return choice(languageService.getPlayerLanguage(player), key, objects);
    }

    public PlayerStringSet getStringSet(Player player) {
        return new PlayerStringSet(player);
    }

    public int getStrings(Language lang) {
        return languageStrings.get(lang);
    }

    public int getMaxStrings() {
        return maxStrings;
    }

    public class PlayerStringSet {
        private final Player player;

        private PlayerStringSet(Player player) {
            this.player = player;
        }

        public LocalizedStringSet getLocalizedStringSet() {
            return LocalizedStringSet.this;
        }

        public PlayerStringSet forOthers(Player player) {
            return new PlayerStringSet(player);
        }

        public String get(String key) {
            return LocalizedStringSet.this.get(languageService.getPlayerLanguage(player), key);
        }

        public String format(String key, Object... objects) {
            String format = LocalizedStringSet.this.get(player, key);
            return String.format(format, objects);
        }

        public String choice(String key, Object... objects) {
            String choice = LocalizedStringSet.this.get(player, key);
            choice = replaceObjectCodes(choice, "\\{", "\\}");
            choice = String.format(choice, objects);
            choice = replaceColorCodes(choice, "\\{", "\\}", true);
            MessageFormat messageFormat = new MessageFormat(choice);
            ChoiceFormat choiceFormat = applyFormats(messageFormat);
            messageFormat.setFormatByArgumentIndex(0, choiceFormat);
            choice = messageFormat.format(objects);
            choice = replaceColorCodes(choice, "\\{", "\\}", false);
            return choice;
        }

        public void sendMessage(Color color, String key) {
            player.sendMessage(color, get(key));
        }

        public void sendMessage(Color color, String key, Object... objects) {
            player.sendMessage(color, get(key), objects);
        }
    }

    //apply format for choices
    private ChoiceFormat applyFormats(MessageFormat subFormat) {
        for(Format format : subFormat.getFormats()) {
            if(!(format instanceof ChoiceFormat)) {
                continue;
            }

            ChoiceFormat choice = (ChoiceFormat) format;
            String[] choiceFormats = (String[]) choice.getFormats();
            for(int i = 0; i < choiceFormats.length; i++) {
                String innerFormat = choiceFormats[i];
                if(innerFormat.contains("{")) {
                    BeanMessageFormat recursive = new BeanMessageFormat(innerFormat);
                    choiceFormats[i] = recursive.inner.toPattern();
                }
            }

            choice.setChoices(choice.getLimits(), choiceFormats);
            return choice;
        }
        return null;
    }

    public String replaceColorCodes(String text, String start, String end, boolean pack) {
        if(pack) {
            Pattern p = Pattern.compile("(?<=" + start + ")[ABCDEF0-9]{6}(?=" + end + ")");
            Matcher m = p.matcher(text);
            while (m.find()) {
                text = text.replaceAll(start + m.group() + end, "#" + m.group());
            }
            return text;
        }
        else {
            Pattern p = Pattern.compile("(?<=#)[ABCDEF0-9]{6}");
            Matcher m = p.matcher(text);
            while(m.find()) {
                text = text.replaceAll("#" + m.group(), start + m.group() + end);
            }
            return text;
        }
    }

    public static String replaceObjectCodes(String text, String anfang, String ende) {
        Pattern p = Pattern.compile("(?<="+anfang+")[%]\\d[$]\\w.*(?="+ende+")");
        Matcher m = p.matcher(text);

        while(m.find()) {
            Pattern p2 = Pattern.compile("[%]\\d*[$]\\w");
            Matcher m2 = p2.matcher(m.group());
            while(m2.find()) {
                text = text.replace(m2.group(), (Integer.parseInt(m2.group().trim().substring(1, m2.group().length()-2))-1) + ", choice");
            }
        }
        return text;
    }
}
