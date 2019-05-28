# Telegram-ERSU-Bot


System requirements
  JDK 8
  Maven 
  
  
Compile:
  $ mvn package
  
Set settings.yaml if you want run application: 
├── target
│   └── resources/config
│       ├── settings.yaml.dist 
and you have to insert your token's bot, channel list, admin chat ...

Run:
  $ java -jar target/myapplication-SNAPSHOT-jar-with-dependencies.jar
