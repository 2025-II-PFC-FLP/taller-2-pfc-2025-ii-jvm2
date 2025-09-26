package taller

class ConjuntosDifusos {

  type ConjDifuso = Int => Double

  def pertenece(elem: Int, s: ConjDifuso): Double = s(elem)

  //-----Conjunto de numeros "Grandes"-----
  def grande(d: Int, e: Int): ConjDifuso = {
    require(d >= 1, "d debe ser mayor o igual a 1")
    require(e >= 1, "e debe ser mayor o igual a 1")

    //Retorna una funcion caracteristica del conjunto difuso
    (x: Int) => {
      if (x <= 0) 0.0 //Si x <= 0, no se considera grande
      else {
        val base = x.toDouble / (x + d).toDouble // base = x/(x+d)
        math.pow(base, e.toDouble) // Eleva base^e y ajusta que tan "grande" es x
      }
    }
  }

}
