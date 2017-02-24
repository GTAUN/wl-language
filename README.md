# Multi Language Support Plugin for Shoebill

This project serves as an support plugin for (Shoebill) Gamemodes or Plugins that want to offer multiple languages.
This project is a part of the New WL-World Project. For further information, please refer to [Main Item](https://github.com/GTAUN/wl-gamemode).

# How to use this?

* Add the dependency for this plugin to your Gamemode’s pom.xml file:
```xml
<dependency>
  	<groupId>net.gtaun.wl</groupId>
	<artifactId>wl-language</artifactId>
	<version>1.0-SNAPSHOT</version>
	<type>jar</type>
	<scope>compile</scope>
</dependency>
```
* In your Gamemode, use the following Code to get the plugin’s service:
```java
LanguageService languageService = Service.get(LanguageService.class);
```
* When you got your LanguageService instance, you can use the ```createStringSet()``` method from the LanguageService interface. You will have to give a path to a directory, which contains your language files. Language files are in the YAML (.yml) format, and should start with the 3-letter-Abbreviation of the language ([Abbreviation list](http://www.abbreviations.com/acronyms/LANGUAGES3L)). If you want to create a language file for the English language, you will have to create a file called ENG.yml in your language folder you specified in the ```createStringSet()``` method. An example usage of the ```createStringSet()``` method would look like this:
```java
localizedStringSet = languageService.createStringSet(new File(getDataDir(), "text")); //points to the plugin's data dir -> text
```
* If you haven’t created a language file yet, this is how to do it:
	* Create an .yml file with the 3-Letter-Abbreviation of your target language (e.g. ENG.yml) in your language folder you specified in the ```createStringSet()``` method.
	* Add your text like this (make sure you add 2 whitespaces after your catgory definition (e.g. „LoginDialog“)):
  ```yml
  LoginDialog:
    Information: “Welcome to my server, please login:“
    Login: “Login“
    Quit: “Quit“
  ```
* You can access your StringSet like this:
```java
String welcomeMessage = localizedStringSet.get(player,„LoginDialog.Information“); //would return the welcome message in the player’s language
```
You can also directly use the ```format()``` method. This will replace the placeholders in your sentence. These placeholders are normal placeholders which are also beeing used in ```String.format();```

* If you want to change the player’s language, you can use the LanguageService’s method ```languageService.setPlayerLanguage();``` or show the player a generated ListDialog with available languages, including their total coverage by using this method: ```languageService.showLanguageSelectionDialog();```
* You also want to add the plugin to the resources.yml file in the shoebill directory at the plugins section. Otherwise, you will get an exception at runtime, because the LanguageService will not be loaded.

# OPEN SOURCE LICENSE

[GNU AFFERO GENERAL PUBLIC LICENSE, version 3](http://www.gnu.org/licenses/agpl-3.0.html)

# LIMITS AND WARNING

Please observe the following terms, otherwise please do not use any component or any code from this project:
* Please follow the clause in AGPL v3 license, that is, to maintain its source be opened after any modification to the code.
* DO NOT remove any information about copyright and license.
* DO NOT remove any information and message about original authors and copyright.
* DO NOT use any component or code of this project on any commercial/profit-making server.
* DO NOT use "WL-World", "New WL-World" or any relevant name as server name on the server WITHOUT authorization.
* DO NOT advertise the server by using "WL-World", "New WL-World" or any relevant name WITHOUT authorization.

