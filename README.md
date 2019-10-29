# Telegram-ERSU-Bot

**Telegram-ERSU-Bot** is the platform that powers **@ERSUbot**, a Telegram bot aided at helping students find informations about ERSU services.

This bot sends also the ERSU blog news and the related canteen menu in this Telegram channel [@ERSUnews](https://t.me/ERSUnews).

### Using the live version
The bot is live on Telegram with the username [@ERSUbot](https://telegram.me/ERSUbot).
Send **'/start'** to start it, **'/help'** to see a list of commands.

Please note that the commands and their answers are in Italian.

---

### System requirements

- openjdk-11-jdk
- maven

### Setting up a local istance
If you want to test the bot by creating your personal istance, follow this steps:
* **Clone this repository** or download it as zip.
* **Send a message to your bot** on Telegram, even '/start' will do. If you don't, you could get an error
* Copy the file resources/config/settings.yml.dist into resources/config/settings.yml and insert your configuration (If you don't have a token, message Telegram's [@BotFather](http://telegram.me/Botfather) to create a bot and get a token for it)
* Now you can compile it:
```
$ mvn package
```
and execute it :
```
$ cd target/
$ java -jar ERSUBot-0.1-SNAPSHOT-jar-with-dependencies.jar
```

### License
This open-source software is published under the GNU General Public License (GNU GPL) version 3. Please refer to the "LICENSE" file of this project for the full text.

### Credits
This project is made possible thanks to the contributions of:

- [Davide Carnemolla](https://github.com/daxcpp)
- [Pierpaolo Pecoraio](https://github.com/Pierpaolo791)
- [Stefano Borzì](https://github.com/Helias)




