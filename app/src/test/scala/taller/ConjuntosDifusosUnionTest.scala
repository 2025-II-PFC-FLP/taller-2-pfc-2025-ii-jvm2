package taller

import org.scalatest.funsuite.AnyFunSuite
import org.junit.runner.RunWith
import org.scalatestplus.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ConjuntosDifusosUnionTest extends AnyFunSuite {
  val cd = new ConjuntosDifusos

  //Conjuntos para test

  val A: cd.ConjDifuso = {
    case 1 => 0.7
    case 2 => 0.4
    case 3 => 0.0
    case 4 => 0.9
    case _ => 0.0 //vacio

  }

  val B: cd.ConjDifuso = {
    case 1 => 0.5
    case 2 => 0.9
    case 3 => 0.3
    case 4 => 0.9
    case _ => 0.0
  }

  test("Union aplica max(f(x), g(x)) en valores especificos"){
    assert(cd.union(A,B)(1) == 0.7) //max(0.7,0.5)
    assert(cd.union(A,B)(2) == 0.9) //max(0.4,0.9)
    assert(cd.union(A,B)(3) == 0.3) //max(0.0,0.3)

  }

  test("Propiedad conmutativa") {
    val xs = Seq(1, 2, 3, 4, 10)
    xs.foreach { x =>
      assert(cd.union(A, B)(x) == cd.union(B, A)(x))
    }
  }

  test("no menor que ningún operando") {
    val xs = Seq(1, 2, 3, 4, 10)
    xs.foreach { x =>
      val u = cd.union(A, B)(x)
      assert(u >= A(x) && u >= B(x))
    }
  }

  test("valores fuera del dominio") {
    assert(cd.union(A, B)(100) == 0.0)
  }

  test("rango [0,1]") {
    val xs = Seq(-5, 0, 1, 2, 3, 50)
    xs.foreach { x =>
      val u = cd.union(A, B)(x)
      assert(u >= 0.0 && u <= 1.0)
    }
  }






}
