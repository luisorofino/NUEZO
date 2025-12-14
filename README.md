# NUEZO - Lenguaje de Programación

![Status](https://img.shields.io/badge/Status-Completed-success) ![Build](https://img.shields.io/badge/Build-WASM-blue)

**NUEZO** es un lenguaje de programación de propósito general diseñado e implementado como proyecto de la asignatura de **Procesadores de Lenguajes**. Combina características de C++, Java y Python para ofrecer una sintaxis intuitiva y accesible, compilando directamente a **WebAssembly (.wasm)**.

## Autores
* **Daniel Casquero**
* **Luis Orofino**

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
