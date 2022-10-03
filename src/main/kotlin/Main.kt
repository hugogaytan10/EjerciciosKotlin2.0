import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.reflect.KFunction2

fun main(args: Array<String>) {

    val cestaList = listOf<Pair<Double, Double>>(
        100.0 to 16.0,
        500.0 to 10.0
    )
    val ans = allCesta(cestaList) { a, b -> discount(a, b) }
    //retornar el total de la cesta con iva y descuento
    println(ans)
    //retornar una lista de cesta ya con descuentos aplicados
    println(newListCesta(cestaList) { a, b -> discount(a, b) })
    //mostrar un nuevo diccionario con palabras y longitud de cada una
    val dictionary = dictionaryWords("Soy una nueva frase")
    println(dictionary)
    //CREACION DE DICCIONARIO DE MATERIAS Y CALIFIACIONES
    val dictionarySchool : Map<String, Double> = mapOf(
        "Movil" to 99.0,
        "WEB" to 82.5,
        "Taller de investigacion" to 72.20,
        "Logica funcional" to 69.9
    )
    val returnDictionarySchool = califications(dictionarySchool)
    println(returnDictionarySchool)

    //ejercicio de vectores
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.UP
    val vect1 = Triple(40.5, 20.5, 48.2)
    val vect2 = Pair(10.5, -48.2)
    println(df.format(moduloVector3D(vect1)))
    println(df.format(moduloVector2D(vect2)))
    //ejericcio 6
    val valoresLista: MutableList<Double> = mutableListOf()
    for(i in 1..30){
        val r = (20..50).random().toDouble()
        valoresLista.add(r)
    }
    valoresLista.add(1.0)
    valoresLista.add(15.0)
    valoresLista.add(80.0)
    valoresLista.add(100.0)
    valoresLista.sort()
    println(atipico(valoresLista))
}
//DESCUENTO AL PRODUCTO
private fun discount(amount: Double, discount: Double): Double{
    return amount - (amount * (discount / 100))
}
//APLICACION DE IVA
private fun IVA(amount: Double, iva: Double): Double{
    return amount + (amount * (iva/100))
}
/* SUMA DE TODA LA CESTA CON IVA Y DESCUENTO */
private fun allCesta (cestaList: List<Pair<Double, Double>>, op: (Double, Double) -> Double): Double{
    var total: Double = 0.0
    //RECORRIDO DE LA CESTA
    for(i in cestaList.indices){
        //APLICACION DE DESCUENTO
        val auxTotal = op(cestaList[i].first, cestaList[i].second)
        //APLICACION DE IVA
        total += IVA(auxTotal, 16.0)
    }
    return total
}

//APLICAR DECUENTO E IVA Y DEVOLVER UNA LISTA
private fun newListCesta (cestita: List<Pair<Double,Double>>, op: (Double, Double) -> Double): MutableList<Double> {
    val listDiscounts = mutableListOf<Double>()
    for(i in cestita.indices){
        //APLICACION DE DESCUENTO
        val auxAmount = op(cestita[i].first, cestita[i].second)
        //AGREGAMOS MONTO YA CON DESCUENTO A LA LISTA
        listDiscounts.add(auxAmount)
    }
    return listDiscounts
}

private fun dictionaryWords (phrase: String): MutableMap<String, Int> {
    val dictionary: MutableMap<String, Int> = mutableMapOf()
    var word: String = ""
    var cont: Int = 0
    //recorrido de la frase
    for(i in phrase){
        //si es un espacio empieza otra palabra y la agregamos
        if(i === ' '){
            dictionary.put(word, cont)
            cont = 0
            word = ""
            //SINO SEGUIMOS SUMANDO A LAS VARIABLES
        }else{
            cont++
            word += i
        }

    }
    return dictionary
}
private fun califications (map: Map<String, Double>): MutableMap<String, String> {
    val ans: MutableMap<String, String> = mutableMapOf()
    for ((key, value) in map){
        when(value){
            in 95.0..100.0 -> ans[key] = "Excelente"
            in 85.0..94.0 -> ans[key] = "Notable"
            in 75.0..84.0 -> ans[key] = "Bueno"
            in 70.0..74.0 ->  ans[key] = "Suficiente"
            else -> ans[key] = "Desempeño insuficiente"
        }

    }
    return ans;
}

private fun moduloVector3D( v: Triple<Double, Double, Double>): Double {
    return sqrt(v.first.pow(2.0) + v.second.pow(2.0) + v.third.pow(2.0))
}
private fun moduloVector2D (v: Pair<Double, Double>): Double {
    return sqrt(v.first.pow(2.0) + v.second.pow(2.0))
}

private fun atipico(numberList: List<Double>): List<Double>{
    val newList : MutableList<Double> = mutableListOf()
    val superior : List<Double>
    val inferior : List<Double>
    if(numberList.size % 2 === 0){
        superior = numberList.subList(0, numberList.size / 2)
        inferior = numberList.subList(numberList.size / 2, numberList.size )
    }else{
        superior = numberList.subList(0, numberList.size/2)
        inferior = numberList.subList(numberList.size/2+1, numberList.size)
    }
    //cuartiles
    val q1 = mediana(superior)
    val q3 = mediana(inferior)
    val riq = q3 - q1
    val qInfo =  q1 - 1.5 * riq
    val qExt = q3 + 1.5 * riq
    println("Valores atipicos")
    for(i in numberList.indices){
        if(numberList[i] < qInfo
            || numberList[i] > qExt){
            newList.add(numberList[i])
        }

    }
    return newList

}

private fun mediana(data: List<Double>) : Double {
    return if(data.size % 2 ==0) (data[data.size /2] + data[data.size/2-1]) /2
    else data[data.size/2]
}