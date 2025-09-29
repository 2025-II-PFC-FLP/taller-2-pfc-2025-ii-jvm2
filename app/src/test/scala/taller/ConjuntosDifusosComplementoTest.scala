package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ConjuntosDifusosComplementoTest extends AnyFunSuite {
  val cd = new ConjuntosDifusos

  //--------Pruebas para el conjunto difuso "Complemento"
  test("complemento aplica 1 - f(x) para varios x (grande(1,2))") {
    val g = cd.grande(1, 2)
    val cg = cd.complemento(g)
    val xs = Seq(-1, 0, 1, 2, 10, 100)
    xs.foreach { x =>
      assert(cg(x) == (1.0 - g(x)))
    }
  }

  test("doble complemento recupera el conjunto original (involutiva)") {
    val g = cd.grande(1, 2)
    val cc = cd.complemento(cd.complemento(g))
    val xs = Seq(0, 1, 2, 5, 50)
    xs.foreach { x =>
      assert(cc(x) == g(x))
    }
  }

  test("complemento invierte la relación de orden para grande") {
    val g = cd.grande(1, 2)
    val cg = cd.complemento(g)
    assert(g(5) <= g(10))
    assert(cg(5) >= cg(10))
  }

  test("complemento respeta bordes 0 y 1 para conjunto artificial") {
    val s: Int => Double = (x: Int) => if (x == 0) 0.0 else if (x == 10) 1.0 else 0.5
    val cs = cd.complemento(s)
    assert(cs(0) == 1.0)  // complemento de 0 es 1
    assert(cs(10) == 0.0) // complemento de 1 es 0
    assert(cs(1) == 0.5)  // complemento de 0.5 es 0.5
  }

  test("suma de conjunto y complemento es 1 (propiedad)") {
    val combos = Seq((1, 2), (5, 3), (2, 4))
    combos.foreach { case (d, e) =>
      val g = cd.grande(d, e)
      val cg = cd.complemento(g)
      val xs = Seq(0, 1, 5, 10, 100)
      xs.foreach { x =>
        assert(g(x) + cg(x) == 1.0)
      }
    }
  }
}

