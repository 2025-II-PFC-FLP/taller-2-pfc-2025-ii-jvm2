# Informe de Proceso

## 1. Introducción
Los conjuntos difusos permiten modelar pertenencia parcial de un elemento a un conjunto, con valores en el intervalo [0,1].  
En este taller se implementaron distintas operaciones sobre conjuntos difusos en Scala: **grande, complemento, inclusión, igualdad, unión e intersección**.  
El objetivo fue aplicar conceptos de **programación funcional y recursión de cola**, representando los conjuntos como funciones `Int => Double`.

---

## 2. Función `grande`

### 2.1 Explicación
La función `grande(d,e)` define un conjunto difuso que modela la noción de "ser grande".  
Matemáticamente:

$$
f(x) =
\begin{cases}
0 & \text{si } x \leq 0 \\
\left(\frac{x}{x+d}\right)^e & \text{si } x > 0
\end{cases}
$$

En el código:
- Se asegura que `d ≥ 1` y `e ≥ 1`.
- Si `x ≤ 0`, el grado de pertenencia es `0.0`.
- En otro caso, se calcula `base = x/(x+d)` y se eleva a la potencia `e`.
- Finalmente se aplica un `clamp` para mantener el resultado en `[0,1]`.

### 2.2 Ejemplo
Para `d=1, e=2` y `x=2`:  
$$
f(2) = \left(\frac{2}{2+1}\right)^2 = \left(\frac{2}{3}\right)^2 = 0.444...
$$

### 2.3 Pila de llamados
```mermaid
sequenceDiagram
    participant Main
    participant grande
    Main->>grande: grande(1,2)(2)
    grande-->>Main: 0.444...
```
## 3. Función `complemento`

### 3.1 Explicación
El complemento de un conjunto difuso `c` se define como:

$$
f_{\neg c}(x) = 1 - f_c(x)
$$

En el código:
- Se evalúa el conjunto original en `x` (`c(x)`).
- Se calcula `1.0 - v`.
- Se asegura que el valor esté en `[0,1]` usando un clamp.

### 3.2 Ejemplo
Si `c(2) = 0.444...`, entonces:

$$
f_{\neg c}(2) = 1 - 0.444... = 0.555...
$$

### 3.3 Pila de llamados
```mermaid
sequenceDiagram
    participant Main
    participant complemento
    Main->>complemento: complemento(c)(2)
    complemento->>c: c(2)
    c-->>complemento: 0.444...
    complemento-->>Main: 0.555...
```
## 4. Función `inclusión`

### 4.1 Explicación
Un conjunto difuso `A` está incluido en `B` si para todo `x` se cumple:

$$
f_A(x) \leq f_B(x)
$$

En el código:
- Se implementa una función auxiliar recursiva `aux(i)` con **recursión de cola**.
- La función itera desde `i = 0` hasta `1000`, comparando los valores.
- Si en algún `i` ocurre que `cd1(i) > cd2(i)`, retorna `false`.
- Si se recorren todos los valores sin problema, retorna `true`.

### 4.2 Ejemplo
Si para todos los `x` en el rango `0..1000` se cumple `cd1(x) <= cd2(x)`, entonces `inclusion(cd1, cd2)` es `true`.  
Por ejemplo:
- Si `cd1(5) = 0.4` y `cd2(5) = 0.6` → la condición se cumple.
- Si en algún `x` se diera que `cd1(x) > cd2(x)` → devuelve `false`.

### 4.3 Pila de llamados
```mermaid
sequenceDiagram
    participant Main
    participant inclusion
    participant aux
    Main->>inclusion: inclusion(cd1, cd2)
    inclusion->>aux: aux(0)
    aux->>aux: aux(1)
    aux->>aux: aux(2)
    ...
    aux-->>inclusion: true/false
    inclusion-->>Main: resultado
```
## 5. Función `igualdad`

### 5.1 Explicación
Dos conjuntos difusos `A` y `B` son iguales si cada uno está incluido en el otro:

$$
A = B \iff (A \subseteq B) \wedge (B \subseteq A)
$$

En el código:
- Se reutiliza la función `inclusion`.
- Se devuelve `true` solo si `inclusion(cd1, cd2)` y `inclusion(cd2, cd1)` son ambas verdaderas.

### 5.2 Ejemplo
Si `cd1(x) = cd2(x)` para todo `x`, entonces:
$$
igualdad(cd1, cd2) = true
$$

Si existe algún `x` donde difieran, la igualdad es `false`.

### 5.3 Pila de llamados
```mermaid
sequenceDiagram
    participant Main
    participant igualdad
    participant inclusion
    Main->>igualdad: igualdad(cd1, cd2)
    igualdad->>inclusion: inclusion(cd1, cd2)
    inclusion-->>igualdad: true/false
    igualdad->>inclusion: inclusion(cd2, cd1)
    inclusion-->>igualdad: true/false
    igualdad-->>Main: resultado
```
## 6. Función `unión`

### 6.1 Explicación
La unión de dos conjuntos difusos `A` y `B` se define como:

$$
f_{A \cup B}(x) = \max(f_A(x), f_B(x))
$$

En el código:
- Se evalúan ambos conjuntos en el mismo `x`.
- Se retorna el máximo de los dos valores.

### 6.2 Ejemplo
Si `cd1(5) = 0.4` y `cd2(5) = 0.7`, entonces:
$$
f_{A \cup B}(5) = 0.7
$$

### 6.3 Pila de llamados
```mermaid
sequenceDiagram
    participant Main
    participant union
    Main->>union: union(cd1, cd2)(5)
    union->>cd1: cd1(5)
    cd1-->>union: 0.4
    union->>cd2: cd2(5)
    cd2-->>union: 0.7
    union-->>Main: max(0.4, 0.7) = 0.7
```
## 7. Función `intersección`

### 7.1 Explicación
La intersección de dos conjuntos difusos `A` y `B` se define como:

$$
f_{A \cap B}(x) = \min(f_A(x), f_B(x))
$$

En el código:
- Se evalúan ambos conjuntos en el mismo `x`.
- Se retorna el mínimo de los dos valores.

### 7.2 Ejemplo
Si `cd1(5) = 0.4` y `cd2(5) = 0.7`, entonces:
$$
f_{A \cap B}(5) = 0.4
$$

### 7.3 Pila de llamados
```mermaid
sequenceDiagram
    participant Main
    participant interseccion
    Main->>interseccion: interseccion(cd1, cd2)(5)
    interseccion->>cd1: cd1(5)
    cd1-->>interseccion: 0.4
    interseccion->>cd2: cd2(5)
    cd2-->>interseccion: 0.7
    interseccion-->>Main: min(0.4, 0.7) = 0.4
```