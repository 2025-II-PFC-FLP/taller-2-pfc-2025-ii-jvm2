
# Casos De Prueba

Los ***casos de prueba*** diseñados para validar el correcto funcionamiento de las operaciones definidas sobre conjuntos difusos 
Cada función fue probada con al menos cinco casos.

---

# Funciones Implementadas



## - Funcion Grande `grande(d,e)`
Devuelve el valor mayor entre dos grados de pertenencia.

| Nº | Entrada `(d, e)` | Salida esperada | Explicación       |
|:--:|:-----------------|:----------------|:------------------|
| 1 | (0.2, 0.7)       | 0.7             | Mayor es 0.7      |  
| 2 | (0.5, 0.3)       | 0.5             | Mayor es 0.5      |  
| 3 | (0.0, 0.0)       | 0.0             | Ambos son iguales |  
| 4 | (0.9, 0.4)       | 0.9             | Mayor es 0.9      |  
| 5 | (0.1, 1.0)       | 1.0             | Mayor es es 1.0   |  

---

## - Funcion Complemento `complemento(c)`
Calcula el complemento de un conjunto difuso: `1-c(x)`


| Nº    | Entrada `c(x)` | x | Salida esperada | Explicación        |
|:------|:---------------|:--|:---------------:|:-------------------|
| 1     | 0.2            | 3 |       0.8       | $ 1 - 0.2 = 0.8  $ |
| 2     | 0.0            | 1 |       1.0       | $1 - 0 = 1  $      |
| 3     | 0.5            | 2 |       0.5       | $1 - 0.5 = 0.5  $  |
| 4     | 1.0            | 4 |       0.0       | Complemento total  |
| 5     | 0.75           | 6 |      0.25       | Grado intermedio   |

---

## - Funcion Inclusion `inclusion(cd1,cd2)`
Verifica si todo elemento de `cd1` esta incluido en `cd2` (`cd1(x) ≤ cd2(x)` para todo `x`).

| Nº |    `cd1(x)`     |    `cd2(x)`     | Resultado esperado | Explicación                          |
|:--:|:---------------:|:---------------:|:------------------:|:-------------------------------------|
| 1 | [0.2, 0.5, 0.7] | [0.3, 0.6, 0.8] |        `true `       | Todos los grados de `cd1 ≤ cd2 `       |
| 2 | [0.5, 0.7, 0.9] | [0.5, 0.7, 0.9] |       ` true   `     | Iguales conjuntos                    |
| 3 | [0.4, 0.8, 0.6] | [0.4, 0.5, 0.9] |       `false `       | El segundo elemento falla            |
| 4 | [0.0, 0.0, 0.0] | [1.0, 1.0, 1.0] |        `true`        | Conjunto vacío difuso                |
| 5 | [1.0, 0.9, 0.8] | [0.5, 0.7, 0.6] |       `false `       | Todos los valores de `cd1` son mayores |

La función `inclusion(cd1, cd2)` utiliza recursión de cola para verificar si todos los valores de pertenencia de `cd1` son menores o iguales que los de `cd2`.  
El siguiente diagrama representa el flujo de llamadas internas de la función auxiliar:

```mermaid
graph TD
    A[Inicio: inclusion(cd1, cd2)] --> B[Llamar inclusionAux(cd1, cd2, i = 0)]
    B --> C{¿i < longitud?}
    C -->|Sí| D[Comparar cd1(i) ≤ cd2(i)]
    D -->|Verdadero| E[Incrementar i y volver a inclusionAux]
    D -->|Falso| F[Retornar false]
    E --> C
    C -->|No| G[Retornar true]
```
## Descripción:

* La recursión se mantiene mientras existan elementos por comparar.
* Si en algún punto `cd1(i) > cd2(i)`, la función retorna `false`.
* Si termina todas las comparaciones sin fallar, retorna `true`.

---


## - Funcion Igualdad `igualdad(cd1,cd2)`
Comprueba si dos conjuntos son iguales (`cd1(x) == cd2(x)` para todo `x`).

| Nº | `cd1(x)`        | `cd2(x)`        | Resultado esperado | Explicación                 |
|:--:|:----------------|:----------------|:------------------:|:----------------------------|
| 1 | [0.2, 0.5, 0.7] | [0.2, 0.5, 0.7] |      ` true  `       | Todos los valores coinciden |
| 2 | [0.4, 0.6, 0.9] | [0.4, 0.6, 0.8] |      ` false  `      | Último valor diferente      |
| 3 | [0.0, 0.0, 0.0] | [0.0, 0.0, 0.0] |       ` true  `      | Ambos vacíos                |
| 4 | [1.0, 0.9, 0.8] | [1.0, 0.9, 0.7] |       `false    `    | Último difiere              |
| 5 | [0.5, 0.5, 0.5] | [0.5, 0.5, 0.5] |        `true `       | Igualdad perfecta           |


---


## - Funcion Union `union(cd2,cd2)`
Devuelve el máximo entre los valores de pertenencia de dos conjuntos.

| Nº | `cd1(x)` | `cd2(x)`  | x | Salida esperada | Explicación       |
|:--:|:---------|:----------|:--|:---------------:|:------------------|
| 1 | 0.3      | 0.7       | 2 |       0.7       | Mayor valor       |
| 2 | 0.5      | 0.5       | 1 |       0.5       | Iguales           |
| 3 | 0.0      | 0.4       | 3 |       0.4       | Uno nulo          |
| 4 | 0.8      | 0.2       | 4 |       0.8       | Predomina `cd1`     |
| 5 | 0.9      | 0.9       | 5 |       0.9       | Coinciden valores |

---

## - Función Interseccion `interseccion(cd1, cd2)`
Devuelve el mínimo entre los valores de pertenencia de dos conjuntos.

| Nº | `cd1(x)` | `cd2(x)` | x | Salida esperada | Explicación     |
|:--:|:---------|:---------|:--|:---------------:|:----------------|
| 1 | 0.3      | 0.7      | 2 |       0.3       | Menor valor     |
| 2 | 0.6      | 0.6      | 1 |       0.6       | Iguales         |
| 3 | 0.0      | 0.4      | 3 |       0.0       | Uno nulo        |
| 4 | 0.8      | 0.2      | 4 |       0.2       | Predomina menor |
| 5 | 0.9      | 1.0      | 5 |       0.9       | Mínimo de ambos |

---

# Pila de llamados General (en orden de ejecucion de los conjuntos difusos)

```mermaid
flowchart TD
A[Inicio del programa] --> B[Definir conjuntos difusos cd1 y cd2]
B --> C[Llamar grande(d, e)]
C --> D[Llamar complemento(cd1)]
D --> E[Llamar union(cd1, cd2)]
E --> F[Llamar interseccion(cd1, cd2)]
F --> G[Llamar inclusion(cd1, cd2)]
G --> H[Llamar igualdad(cd1, cd2)]
H --> I[Mostrar resultados]
I --> J[Fin del programa]

```

---


