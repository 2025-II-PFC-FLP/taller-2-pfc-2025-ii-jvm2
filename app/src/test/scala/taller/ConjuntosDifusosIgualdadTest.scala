package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ConjuntosDifusosIgualdadTest extends AnyFunSuite {
  val cd = new ConjuntosDifusos

  test("vacío == vacío (true)") {
    val empty: Int => Double = _ => 0.0
    assert(cd.igualdad(empty, empty))
  }

  test("universal == universal (true)") {
    val full: Int => Double = _ => 1.0
    assert(cd.igualdad(full, full))
  }

  test("0.5 == 0.5 (true)") {
    val half: Int => Double = _ => 0.5
    assert(cd.igualdad(half, half))
  }

  test("pares == copia de pares (true)") {
    val even: Int => Double = (n: Int) => if (n % 2 == 0) 1.0 else 0.0
    val evenCopy: Int => Double = (n: Int) => if (n % 2 == 0) 1.0 else 0.0
    assert(cd.igualdad(even, evenCopy))
  }

  test("0.5 != casi 0.5 que difiere en un índice (false)") {
    val half: Int => Double = _ => 0.5
    val almostHalf: Int => Double = (n: Int) => if (n == 999) 0.6 else 0.5
    assert(!cd.igualdad(half, almostHalf))
  }

  test("doble complemento recupera el conjunto original (involutiva) y por tanto igualdad") {
    val half: Int => Double = _ => 0.5
    val doble = cd.complemento(cd.complemento(half))
    assert(cd.igualdad(doble, half))
  }

  test("grande(1,1) == grande(1,1) (true)") {
    val g = cd.grande(1,1)
    assert(cd.igualdad(g, cd.grande(1,1)))
  }
}
