# NUEZO - Programming Language 

![Status](https://img.shields.io/badge/Status-Completed-success) ![Build](https://img.shields.io/badge/Build-WASM-blue)

(In Spanish below)

**NUEZO** is a general-purpose programming language designed and implemented as a project for the **Language Processors** course. It combines features from C++, Java, and Python to offer an intuitive and accessible syntax, compiling directly to **WebAssembly (.wasm)**.

## Authors
* **Daniel Casquero** (https://github.com/DanielCP444)
* **Luis Orofino** (https://github.com/luisorofino)

## Language features

NUEZO is a typed language that includes advanced features for memory management and data structures:

* **Data Types:**
    * `znum`: Integers.
    * `rnum`: Real numbers.
    * `state`: Booleans (`on`/`off`).
* **Data Structures:**
    * `chain`: multidimensional arrays.
    * `block`: Structs for compound data types.
    * `dir`: Pointers and dynamic memory management (`new`, `&`, `_`).
* **Control Flow:**
    * Conditionals: `if`, `elif`, `else`.
    * Loops: `while`, `for`, `for each`.
* **Functions:** Support for functions with return values, `silent` (void) functions and recursion.
* **Entry Point:** Main function is called `engine`.

## Technologies Used

* **Java**: Base language of the compiler.
* **JFlex**: Lexical analyzer generation (`lexico.l`).
* **CUP**: Syntax analyzer generation (`ConstructorAST.cup`).
* **WebAssembly (WASM)**: Generated binary code format.

## Compilation and Execution

The project includes bash scripts to facilitate compiling and testing the examples.

For more information on execution, read [this file](https://github.com/luisorofino/NUEZO/blob/main/NUEZO/constructorASTCUP/_LEER_ANTES_DE_EJECUTAR.txt)


# NUEZO - Lenguaje de Programación

![Status](https://img.shields.io/badge/Status-Completed-success) ![Build](https://img.shields.io/badge/Build-WASM-blue)

**NUEZO** es un lenguaje de programación de propósito general diseñado e implementado como proyecto de la asignatura de **Procesadores de Lenguajes**. Combina características de C++, Java y Python para ofrecer una sintaxis intuitiva y accesible, compilando directamente a **WebAssembly (.wasm)**.

## Autores
* **Daniel Casquero** (https://github.com/DanielCP444)
* **Luis Orofino** (https://github.com/luisorofino)

## Características del Lenguaje

NUEZO es un lenguaje tipado que incluye características avanzadas de gestión de memoria y estructuras de datos:

* **Tipos de Datos:**
    * `znum`: Enteros.
    * `rnum`: Reales.
    * `state`: Booleanos (`on`/`off`).
* **Estructuras de Datos:**
    * `chain`: Arrays multidimensionales.
    * `block`: Structs para tipos de datos compuestos.
    * `dir`: Punteros y gestión de memoria dinámica (`new`, `&`, `_`).
* **Control de Flujo:**
    * Condicionales: `if`, `elif`, `else`.
    * Bucles: `while`, `for`, `for each`.
* **Funciones:** Soporte para funciones con retorno, funciones `silent` (void) y recursividad.
* **Punto de Entrada:** La función principal se denomina `engine`.

## Tecnologías Utilizadas

* **Java**: Lenguaje base del compilador.
* **JFlex**: Generación del analizador léxico (`lexico.l`).
* **CUP**: Generación del analizador sintáctico (`ConstructorAST.cup`).
* **WebAssembly (WASM)**: Formato de código binario generado.

## Compilación y Ejecución

El proyecto incluye scripts de bash para facilitar la compilación y prueba de los ejemplos.

Para más información sobre la ejecución, leer [este archivo](https://github.com/luisorofino/NUEZO/blob/main/NUEZO/constructorASTCUP/_LEER_ANTES_DE_EJECUTAR.txt)
