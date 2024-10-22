import java.util.* // Importa la clase Scanner para leer entradas del usuario
import kotlin.collections.ArrayList // Importa ArrayList para crear listas dinámicas

fun main() {

    // Crea un objeto Scanner para capturar la entrada del usuario
    val sc = Scanner(System.`in`)

    // Inicializa listas de distancias entre los pueblos, inicialmente con valores de 0.0
    val pueblo1 = arrayListOf(0.0, 0.0, 0.0, 0.0, 0.0)
    val pueblo2 = arrayListOf(0.0, 0.0, 0.0, 0.0, 0.0)
    val pueblo3 = arrayListOf(0.0, 0.0, 0.0, 0.0, 0.0)
    val pueblo4 = arrayListOf(0.0, 0.0, 0.0, 0.0, 0.0)
    val pueblo5 = arrayListOf(0.0, 0.0, 0.0, 0.0, 0.0)

    // Agrupa las listas de distancias en una lista de listas que representa todos los pueblos
    val misPueblo = arrayListOf(pueblo1, pueblo2, pueblo3, pueblo4, pueblo5)

    // Recorrer cada pueblo para capturar las distancias
    for (i in 0..<misPueblo.size) {
        // Recorrer cada pueblo para establecer distancias
        for (j in 0..<misPueblo[i].size) {
            // Si estamos en la misma posición (pueblo a sí mismo), la distancia es 0
            if (i == j) {
                misPueblo[i][j] = 0.0
            } else if (misPueblo[i][j] == 0.0) { // Si la distancia es 0 (no se ha introducido aún)
                // Solicita al usuario que introduzca la distancia entre los pueblos
                println("Introduce la distancia del pueblo " + (i + 1) + " al pueblo " + (j + 1) + ": ")
                misPueblo[i][j] = sc.nextDouble() // Guarda la distancia introducida
                misPueblo[j][i] = misPueblo[i][j] // Asigna la distancia en la dirección opuesta, por simetría
            }
        }
    }

    // Bucle para mostrar el menú de opciones y ejecutar la correspondiente funcionalidad
    do {
        println(
            """*-------------------------------------------------------------------------------------------------------------*

Menú del análisis de pueblos:
1. Ver la tabla de distancias entre los pueblos.
2. Mostrar qué 2 pueblos están más lejos entre sí.
3. Dado un pueblo determinado, mostrar cuál es el pueblo más lejano.
4. Mostrar el pueblo más céntrico.
5. Calcular el número de kilómetros que hace el lechero.
6. Salir.
Elige una opción:

*--------------------------------------------------------------------------------------------------------*"""
        )

        // Variable que controla si se continúa o se sale del menú
        var sino = true
        // Variable para almacenar los resultados de las opciones seleccionadas
        var Zevilla = ""

        // Captura la opción del usuario
        when (sc.nextInt()) {
            1 -> tablaDistancias(misPueblo)
            2 -> Zevilla = lejo2Pueblo(misPueblo)
            3 -> Zevilla = lejanoPueblo(misPueblo)
            4 -> Zevilla = centralPark(misPueblo)
            5 -> Zevilla= kmPueblos(misPueblo)
            6 -> sino = false
            else -> println("Opción no válida, intenta de nuevo.") // Control de error para opción inválida
        }

        // Si la opción devuelve un resultado, lo muestra
        if (Zevilla.isNotEmpty()) {
            println(Zevilla)
        }

        }while(sino) // El bucle se ejecuta mientras 'sino' sea verdadero
}

// Función para calcular el recorrido del lechero entre los pueblos
fun kmPueblos(misPueblo: java.util.ArrayList<java.util.ArrayList<Double>>): String {

    // Escáner para capturar la entrada del usuario
    val sr = Scanner(System.`in`)
    val srS= Scanner(System.`in`)

    // Variable para acumular la distancia total recorrida
    var dist = 0.0
    var result = " "// Variable que contendrá el resultado final

    // Solicitar el pueblo inicial al usuario
    println("Escribe el pueblo inicial del recorrido del lechero (1-5): ")
    val puebloIni = sr.nextInt() // Se lee el pueblo inicial (1 a 5)

    // Se prepara una lista para almacenar el recorrido del lechero
    println("Escribe el pueblo inicial del recorrido: ")
    val recorrido = java.util.ArrayList<Int>()
    var aux = 0// Variable auxiliar para capturar cada entrada del recorrido

    // Solicitar el recorrido al usuario hasta que introduzca -1
    println("Escribe el recorido del lechero el ultimo valor debe ser -1")

    // Ciclo para capturar el recorrido del lechero
    while(aux != -1 ){
        print("mete numero") // Solicita al usuario ingresar el próximo pueblo en el recorrido
        aux = sr.nextInt() // Lee el número del próximo pueblo
        if(aux!=-1){ // Si no es -1, se agrega el número del pueblo a la lista de recorrido
            recorrido.add(aux)
        }
    }

    // Validación del pueblo inicial
    if (puebloIni > 5) {
        result = "Pueblo error 404" // Si el pueblo inicial es mayor a 5, se muestra un error
    }else {
        // Se comienza a calcular la distancia total del recorrido
        var puebloAnterior = puebloIni // Variable para almacenar el pueblo desde el cual se parte
        for (pueblo in recorrido) { // Se recorre la lista de pueblos ingresados
            dist += misPueblo[puebloAnterior - 1][pueblo - 1] // Suma la distancia desde el pueblo anterior al actual
            puebloAnterior = pueblo // Actualiza el pueblo anterior con el actual
        }
        // Genera el mensaje de resultado con la distancia total recorrida
        result = "El recorrido del lechero iniciado en el pueblo $puebloIni es de $dist KM"
    }
    return result // Devolver el resultado del cálculo de la distancia total recorrida
}


// Función para encontrar el pueblo más central (el que tiene menor distancia promedio a los demás)
fun centralPark(misPueblo: ArrayList<ArrayList<Double>>): String {

    var distancia = 0.0 // Variable para almacenar la distancia mayor encontrada entre pueblos
    var distanciaAux = 0.0 // Variable auxiliar que acumula la suma de distancias para un pueblo específico
    var poblo = 0 // Variable para almacenar el índice del pueblo más central

    // Recorrer cada pueblo para calcular la suma de distancias hacia todos los demás pueblos
    for(i in 0..<misPueblo.size){
        for(j in 0..<misPueblo[i].size){
            distanciaAux += misPueblo[i][j] // Suma la distancia del pueblo 'i' hacia el pueblo 'j'
        }
        // Si la suma de distancias de este pueblo es mayor que la anterior mayor registrada
        if(distancia<distanciaAux){
            poblo = i // Actualiza el índice del pueblo más central
            distancia = distanciaAux// Actualiza la distancia más grande acumulada
        }
    }
    poblo++ // Incrementa el índice del pueblo porque los pueblos están numerados desde 1 y no desde 0
    return "El pueblo más central es $poblo" // Devuelve el resultado con el número del pueblo más central
}

fun lejanoPueblo(misPueblo: ArrayList<ArrayList<Double>>): String {

    // Crear un escáner para capturar la entrada del usuario
    val sr = Scanner(System.`in`)
    var distancia = 0.0// Variable para almacenar la mayor distancia encontrada
    println("El pueblo desde el que se quiera calcular el pueblo más lejano: ")

    // Capturar el número del pueblo del que se desea saber cuál es el más lejano
    var poblito = sr.nextInt()-1 // Se resta 1 para trabajar con índices de la lista
    var poblito2 = 0 // Variable para almacenar el índice del pueblo más lejano encontrado

    // Recorrer todas las distancias del pueblo seleccionado hacia los demás pueblos
    for (j in 0..<misPueblo[poblito].size){
        // Si la distancia actual es mayor que la máxima registrada, se actualiza
        if(distancia<misPueblo[poblito][j]){
            distancia = misPueblo[poblito][j] // Actualizar la distancia más lejana
            poblito2 = j // Actualizar el índice del pueblo más lejano
        }
    }

    // Incrementar los índices de pueblos en 1 para mostrar el número real
    poblito ++
    poblito2++

    // Retorna un mensaje con el pueblo original y el pueblo más lejano a él
    return "El pueblo mas lejano de $poblito es $poblito2"
}


fun lejo2Pueblo(misPueblo: ArrayList<ArrayList<Double>>): String {

    // Variables para almacenar los índices de los dos pueblos más lejanos
    var poblo1 = 0 // Pueblo 1 más lejano
    var poblo2 = 0 // Pueblo 2 más lejano
    var dist = 0.0 // Variable para guardar la distancia máxima entre los dos pueblos

    // Recorrer cada pueblo, i representa el primer pueblo
    for (i in 0..<misPueblo.size){
        // Recorrer nuevamente cada pueblo, j representa el segundo pueblo
        for(j in 0..<misPueblo.size){
            // Si la distancia entre el pueblo i y el pueblo j es mayor que la distancia máxima guardada
            if(dist<misPueblo[i][j]){
                // Actualizar la distancia máxima
                dist = misPueblo[i][j]
                // Guardar los índices de los pueblos más lejanos
                poblo1= i // El primer pueblo
                poblo2= j // El segundo pueblo
            }
        }
    }
    // Devolver un mensaje que indica cuáles son los dos pueblos más lejanos y su distancia
    return "Los pueblos más lejanos entre si son el pueblo ${poblo1 +1} y el pueblo ${poblo2 + 1}, con una distancia de $dist km."
}

fun tablaDistancias(misPueblo: ArrayList<ArrayList<Double>>) {

    // Recorremos cada array que representa las distancias de un pueblo a los demás
    for (array in misPueblo) {
        print("|")
        // Recorremos cada distancia en el array actual
        for (distancia in array) {
            // Imprimir la distancia formateada con dos decimales, con un ancho fijo
            print("%6.2f |".format(distancia))
        }
        println() // Imprimir una nueva línea después de cada fila de distancias
        println() // Imprimir una línea en blanco para mejorar la legibilidad
    }
}
