#!/bin/bash

# Paso 1: Ejecutar el compilador Java con CUP
java -cp "./cup.jar:." constructorast.Main suma.txt

# Paso 2: Compilar el WAT a WASM
./generacion/wat2wasm codigo.wat -o codigo.wasm
