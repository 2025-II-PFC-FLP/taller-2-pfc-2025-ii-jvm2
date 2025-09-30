package taller

import org.scalatest.funsuite.AnyFunSuite
import org.scalatestplus.junit.JUnitRunner
import org.junit.runner.RunWith

@RunWith(classOf[JUnitRunner])
class ConjuntosDifusosInterseccionTest extends AnyFunSuite{
  val cd = new ConjuntosDifusos

  val A: cd.ConjDifuso = {
    case 1 => 0.7
    case 2 => 0.4
    case 3 => 0.0
    case 4 => 0.9
    case _ => 0.0
  }

  val B: cd.ConjDifuso = {
    case 1 => 0.5
    case 2 => 0.9
    case 3 => 0.3
    case 4 => 0.9
    case _ => 0.0
  }


  test("aplica min(f(x), g(x)) en valores específicos") {
    assert(cd.interseccion(A, B)(1) == 0.5) // min(0.7,0.5)
    assert(cd.interseccion(A, B)(2) == 0.4) // min(0.4,0.9)
    assert(cd.interseccion(A, B)(3) == 0.0) // min(0.0,0.3)
  }

  test("Propiedad conmutativa") {
    val xs = Seq(1, 2, 3, 4, 10)
    xs.foreach { x =>
      assert(cd.interseccion(A, B)(x) == cd.interseccion(B, A)(x))
    }
  }

  test("no mayor que ningún operando") {
    val xs = Seq(1, 2, 3, 4, 10)
    xs.foreach { x =>
      val i = cd.interseccion(A, B)(x)
      assert(i <= A(x) && i <= B(x))
    }
  }

  test("respeta valores fuera del dominio (ambos 0)") {
    assert(cd.interseccion(A, B)(100) == 0.0)
  }

  test("rango [0,1]") {
    val xs = Seq(-5, 0, 1, 2, 3, 50)
    xs.foreach { x =>
      val i = cd.interseccion(A, B)(x)
      assert(i >= 0.0 && i <= 1.0)
    }
  }
}
