package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ConjuntosDifusosInclusionTest extends AnyFunSuite {
  val cd = new ConjuntosDifusos

  test("vacío ⊆ universal (true)") {
    val empty: Int => Double = _ => 0.0
    val full:  Int => Double = _ => 1.0
    assert(cd.inclusion(empty, full))
  }

  test("universal ⊄ vacío (false)") {
    val empty: Int => Double = _ => 0.0
    val full:  Int => Double = _ => 1.0
    assert(!cd.inclusion(full, empty))
  }

  test("grande(1,1) ⊆ universal (true) — comprobación puntual en varios x") {
    val g = cd.grande(1,1)
    val full: Int => Double = _ => 1.0
    // comprobación con inclusion y además comprobación puntual para varios x
    assert(cd.inclusion(g, full))
    Seq(0, 1, 2, 5, 50, 500, 1000).foreach { x =>
      assert(g(x) <= full(x))
    }
  }

  test("grande(1,1) ⊄ constante 0.1 (false)") {
    val g = cd.grande(1,1)
    val small: Int => Double = _ => 0.1
    assert(!cd.inclusion(g, small))
  }

  test("diferencia en índice 1000 rompe la inclusión (false)") {
    val base: Int => Double = _ => 0.5
    val diffAt1000: Int => Double = (n: Int) => if (n == 1000) 0.9 else 0.5
    assert(!cd.inclusion(diffAt1000, base))
  }

  test("comprobación completa (sanity) para vacío ⊆ universal en todo 0..1000") {
    val empty: Int => Double = _ => 0.0
    val full:  Int => Double = _ => 1.0
    // verificamos manualmente el dominio también (útil para el informe)
    (0 to 1000).foreach { i =>
      assert(empty(i) <= full(i))
    }
    assert(cd.inclusion(empty, full))
  }
}
