package taller

import scala.annotation.tailrec

class ConjuntosDifusos {

  type ConjDifuso = Int => Double

  def pertenece(elem: Int, s: ConjDifuso): Double = s(elem)

  // ----- Conjunto Difuso "Grande" -----
  def grande(d: Int, e: Int): ConjDifuso = {
    require(d >= 1, "d debe ser mayor o igual a 1")
    require(e >= 1, "e debe ser mayor o igual a 1")

    (x: Int) => {
      if (x <= 0) 0.0 //Si x no es positivo entonces es grado 0
      else {
        val base = x.toDouble / (x + d).toDouble // base = x/(x+d)
        val res = math.pow(base, e.toDouble)
        math.max(0.0, math.min(1.0, res)) //garantizar rango [0,1]
      }
    }
  }

  // ----- Conjunto Difuso "Complemento" -----
  def complemento(c: ConjDifuso): ConjDifuso = {
    (x: Int) => {
      val v = c(x)              // aplicamos la función al valor x
      val res = 1.0 - v         // definición de complemento
      math.max(0.0, math.min(1.0, res)) // Rango
    }
  }

  // ----- Conjunto Difuso "Inclusión" -----
  def inclusion(cd1: ConjDifuso, cd2: ConjDifuso): Boolean = {
    @tailrec
    def aux(i: Int): Boolean = {
      if (i > 1000) true
      else if (cd1(i) <= cd2(i)) aux(i + 1) // llamada de cola
      else false
    }
    aux(0)
  }

  // ----- Conjunto Difuso "Igualdad" -----
  def igualdad(cd1: ConjDifuso, cd2: ConjDifuso): Boolean = {
    inclusion(cd1, cd2) && inclusion(cd2, cd1)
  }

  //--------Union---------

  def union(cd1: ConjDifuso, cd2: ConjDifuso): ConjDifuso={
    (x: Int) => Math.max(cd1(x), cd2(x))
  }


  //-------Interseccion----------

  def interseccion(cd1: ConjDifuso,cd2: ConjDifuso): ConjDifuso={
    (x: Int) => Math.min(cd1(x), cd2(x))
  }

}


