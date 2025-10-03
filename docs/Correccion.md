# Informe de Corrección

**Fundamentos de Programación Funcional y Concurrente**  
**Informe de corrección sobre las funciones implementadas en `ConjuntosDifusos`.**

---

## Función `grande(d, e)`

### Definición matemática

La función `grande(d, e)` construye un conjunto difuso de números enteros "grandes".  
Se define como:

$$
f(x) = \begin{cases}
0 & x \leq 0 \\
\left(\frac{x}{x+d}\right)^e & x > 0
\end{cases}
$$

Donde:
- $d \geq 1$ controla el desplazamiento.
- $e \geq 1$ controla la intensidad del crecimiento.

Además, se asegura que $f(x) \in [0,1]$.

### Código en Scala

```scala
def grande(d: Int, e: Int): ConjDifuso = {
  require(d >= 1, "d debe ser mayor o igual a 1")
  require(e >= 1, "e debe ser mayor o igual a 1")

  (x: Int) => {
    if (x <= 0) 0.0
    else {
      val base = x.toDouble / (x + d).toDouble
      val res = math.pow(base, e.toDouble)
      math.max(0.0, math.min(1.0, res))
    }
  }
}
```

### Argumentación de corrección

Queremos demostrar que:

$$
\forall x \in \mathbb{Z}^+ : P_{grande}(x) == f(x)
$$

- **Caso base**: $x \leq 0$  
  $$
  P_{grande}(x) = 0.0 \quad \land \quad f(x) = 0
  $$

- **Caso inductivo**: $x > 0$  
  $$
  P_{grande}(x) = \left(\frac{x}{x+d}\right)^e
  $$

Este valor siempre cumple $0 < P_{grande}(x) < 1$, y al aplicar `math.max` y `math.min`, se garantiza que el resultado esté en $[0,1]$.

**Conclusión:**  
$$
\forall x \in \mathbb{Z} : P_{grande}(x) == f(x)
$$

---

## Función `complemento(c)`

### Definición matemática

El complemento de un conjunto difuso $S$ está dado por:

$$
f_{\neg S}(x) = 1 - f_S(x)
$$

con la condición de mantener el rango en $[0,1]$.

### Código en Scala

```scala
def complemento(c: ConjDifuso): ConjDifuso = {
  (x: Int) => {
    val v = c(x)
    val res = 1.0 - v
    math.max(0.0, math.min(1.0, res))
  }
}
```

### Argumentación de corrección

Queremos demostrar que:

$$
\forall x \in \mathbb{Z} : P_{complemento}(x) == 1 - f(x)
$$

- Para cualquier $x$, si $c(x) \in [0,1]$, entonces $1 - c(x) \in [0,1]$.
- La instrucción `math.max(0.0, math.min(1.0, res))` garantiza que el resultado nunca salga de ese intervalo.

**Conclusión:**  
$$
\forall x \in \mathbb{Z} : P_{complemento}(x) == f_{\neg S}(x)
$$

---

## Función `union(cd1, cd2)`

### Definición matemática

La unión de dos conjuntos difusos $S_1$ y $S_2$ está definida como:

$$
f_{S_1 \cup S_2}(x) = \max(f_{S_1}(x), f_{S_2}(x))
$$

### Código en Scala

```scala
def union(cd1: ConjDifuso, cd2: ConjDifuso): ConjDifuso = {
  (x: Int) => Math.max(cd1(x), cd2(x))
}
```

### Argumentación de corrección

- Por definición de unión difusa, se toma el mayor grado de pertenencia de ambos conjuntos.
- `Math.max(cd1(x), cd2(x))` cumple exactamente esta propiedad.

**Conclusión:**  
$$
\forall x \in \mathbb{Z} : P_{union}(x) == f_{S_1 \cup S_2}(x)
$$

---

## Función `interseccion(cd1, cd2)`

### Definición matemática

La intersección de dos conjuntos difusos $S_1$ y $S_2$ está definida como:

$$
f_{S_1 \cap S_2}(x) = \min(f_{S_1}(x), f_{S_2}(x))
$$

### Código en Scala

```scala
def interseccion(cd1: ConjDifuso, cd2: ConjDifuso): ConjDifuso = {
  (x: Int) => Math.min(cd1(x), cd2(x))
}
```

### Argumentación de corrección

- Por definición de intersección difusa, se toma el menor grado de pertenencia de ambos conjuntos.
- `Math.min(cd1(x), cd2(x))` cumple esta propiedad directamente.

**Conclusión:**  
$$
\forall x \in \mathbb{Z} : P_{interseccion}(x) == f_{S_1 \cap S_2}(x)
$$

---

## Función `inclusion(cd1, cd2)`

### Definición matemática

Se dice que $S_1 \subseteq S_2$ si y sólo si:

$$
\forall x \in U : f_{S_1}(x) \leq f_{S_2}(x)
$$

En este taller se evalúa el universo en $[0, 1000]$.

### Código en Scala

```scala
def inclusion(cd1: ConjDifuso, cd2: ConjDifuso): Boolean = {
  @tailrec
  def aux(i: Int): Boolean = {
    if (i > 1000) true
    else if (cd1(i) <= cd2(i)) aux(i + 1)
    else false
  }
  aux(0)
}
```

### Argumentación de corrección

Queremos demostrar que:

$$
P_{inclusion}(cd1, cd2) = true \iff \forall x \in [0,1000] : cd1(x) \leq cd2(x)
$$

- **Caso base:** $i = 0$  
  Se evalúa $cd1(0) \leq cd2(0)$.  
  Si es cierto, continúa; si es falso, retorna `false`.

- **Caso inductivo:** $i = k+1$  
  La función hace una **llamada de cola**:  
  $$
  aux(k) = true \rightarrow aux(k+1)
  $$

- **Caso final:** $i > 1000$  
  En este punto, ya se han verificado todos los elementos, y se retorna `true`.

### Representación de la pila de llamadas

```mermaid
graph TD
  A0[aux(0)] --> A1[aux(1)]
  A1 --> A2[aux(2)]
  A2 --> ... --> Af[aux(1000)]
  Af --> End[true]
```

**Conclusión:**  
$$
\forall x \in [0,1000] : P_{inclusion}(cd1, cd2) == (cd1(x) \leq cd2(x))
$$

---

## Función `igualdad(cd1, cd2)`

### Definición matemática

Dos conjuntos difusos son iguales si:

$$
S_1 = S_2 \iff (S_1 \subseteq S_2) \land (S_2 \subseteq S_1)
$$

### Código en Scala

```scala
def igualdad(cd1: ConjDifuso, cd2: ConjDifuso): Boolean = {
  inclusion(cd1, cd2) && inclusion(cd2, cd1)
}
```

### Argumentación de corrección

- La igualdad se define como inclusión mutua.
- El programa verifica ambas condiciones con `&&`.

**Conclusión:**  
$$
\forall x \in [0,1000] : P_{igualdad}(cd1, cd2) == (cd1(x) == cd2(x))
$$

---

# Conclusión general

Cada función implementada cumple exactamente con su **definición matemática**:

- `grande(d,e)` genera un conjunto difuso de números grandes.
- `complemento(c)` retorna el grado de pertenencia al complemento.
- `union` e `interseccion` siguen las definiciones clásicas extendidas a conjuntos difusos.
- `inclusion` utiliza recursión de cola para verificar la relación en $[0,1000]$.
- `igualdad` aplica inclusión mutua.

**Por lo tanto, todas las funciones son correctas respecto a su especificación.**
