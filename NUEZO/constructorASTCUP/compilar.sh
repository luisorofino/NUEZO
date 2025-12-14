#!/bin/bash

#Paso 1: Generar los archivos a partir de ConstructorAST.cup
java -cp ./cup.jar java_cup.Main -parser ConstructorASTExp -symbols ClaseLexica -nopositions -destdir "./constructorast" ConstructorAST.cup

#Paso 2: Buscar todos los archivos .java en el directorio actual y sus subdirectorios
find . -type f -name "*.java" > sources.txt

#Paso 3: Compilar todos los archivos .java
javac -cp "./cup.jar:." $(cat sources.txt)