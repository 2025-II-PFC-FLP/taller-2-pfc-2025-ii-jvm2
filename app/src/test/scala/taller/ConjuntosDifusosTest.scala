package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ConjuntosDifusosTest extends AnyFunSuite {
  val cd = new ConjuntosDifusos

  // ----------Pruebas para el conjunto difuso "grande"----------
  test("Mayor d implica menor grado de pertenencia para un mismo x") {
    val g1 = cd.grande(1, 2)
    val g5 = cd.grande(5, 2)
    assert(g1(10) > g5(10)) // con d más grande, x tarda más en volverse "grande"
  }

  test("Mayor e implica menor grado de pertenencia para un mismo x y d") {
    val g2 = cd.grande(1, 2)
    val g5 = cd.grande(1, 5)
    assert(g2(10) > g5(10)) // con e más grande , la función crece más lentamente
  }

  test("El conjunto grande es monótono creciente en x") {
    val g = cd.grande(1, 2)
    assert(g(5) <= g(10)) // si x crece, el grado no debería disminuir
  }

  test("Para x muy grande, grande(d,e)(x) tiende a 1") {
    val g = cd.grande(1, 2)
    assert(g(1000) > 0.99 && g(1000) < 1.0) // para x muy grande, el valor debe estar cerca de 1
  }

  test("El límite en x=0 marca la transición entre 0 y positivo") {
    val g = cd.grande(1, 2)
    assert(g(0) == 0.0) // en x=0, el valor es 0
    assert(g(1) > 0.0)  // inmediatamente después, positivo
  }
}
