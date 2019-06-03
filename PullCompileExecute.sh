#!/bin/bash
git pull
mvn package
cd ./target/
java -jar ERSUBot-0.1-SNAPSHOT-jar-with-dependencies.jar