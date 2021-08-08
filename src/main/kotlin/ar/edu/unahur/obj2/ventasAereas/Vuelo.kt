package ar.edu.unahur.obj2.ventasAereas

import java.time.LocalDate


class Pasaje(val vuelo: Vuelo, val fechaDeVenta: LocalDate, val dniPasajero: Int)

class Avion(val cantidadDeAsientos: Int, val alturaDeCabina: Double)

abstract class Vuelo(val fecha: LocalDate, val avion: Avion, val origen: String, val destino: String, val tiempoDeVuelo: Int, val precioEstandar: Int) {

    val pasajes = mutableListOf<Pasaje>()

    var politicaDePrecio: PoliticaDeVenta = Estricta

    var criterioDeVenta: CriterioDeVenta = Empresa.criterioDeVenta

    fun puedeVenderPasaje() = criterioDeVenta.puedeVenderPasajes(this)

    open fun cantidadDeAsientosOcupados() = pasajes.size

    abstract fun cantidadDeAsientosDisponibles(): Int

    fun cantidadDeAsientosLibres() = cantidadDeAsientosDisponibles() - cantidadDeAsientosOcupados()

    fun precioDelVuelo() = politicaDePrecio.precioDeVenta(this)

    fun esVueloRelajado() = avion.alturaDeCabina > 4 && this.cantidadDeAsientosDisponibles() < 100


}

class VueloDePasajeros(fecha: LocalDate, avion: Avion, origen: String, destino: String, tiempoDeVuelo: Int,  precioEstandar: Int): Vuelo(fecha, avion, origen, destino, tiempoDeVuelo, precioEstandar) {

    override fun cantidadDeAsientosDisponibles() = avion.cantidadDeAsientos

}

class VueloDeCarga(fecha: LocalDate, avion: Avion, origen: String, destino: String, tiempoDeVuelo: Int, precioEstandar: Int): Vuelo(fecha, avion, origen, destino, tiempoDeVuelo, precioEstandar) {

    override fun cantidadDeAsientosDisponibles() = 10

}

class VueloCharter(fecha: LocalDate, avion: Avion, origen: String, destino: String, tiempoDeVuelo: Int, precioEstandar: Int, val pasajerosVIP: Int): Vuelo(fecha, avion, origen, destino, tiempoDeVuelo, precioEstandar) {

    override fun cantidadDeAsientosDisponibles() = avion.cantidadDeAsientos - 25

    override fun cantidadDeAsientosOcupados() = pasajes.size + pasajerosVIP
}

abstract class PoliticaDeVenta() {
    abstract fun precioDeVenta(vuelo: Vuelo): Int
}

object Estricta: PoliticaDeVenta() {
    override fun precioDeVenta(vuelo: Vuelo) = vuelo.precioEstandar
}

object VentaAnticipada: PoliticaDeVenta() {
    override fun precioDeVenta(vuelo: Vuelo): Int {
        return if(vuelo.cantidadDeAsientosOcupados() < 40) {
            vuelo.precioEstandar * 30/ 100
        } else if (vuelo.cantidadDeAsientosOcupados() >= 40 || vuelo.cantidadDeAsientosOcupados() <= 79) {
            vuelo.precioEstandar * 60 / 100
        } else {
            vuelo.precioEstandar
        }
    }
}

object Remate: PoliticaDeVenta() {
    override fun precioDeVenta(vuelo: Vuelo): Int {
        return if (vuelo.cantidadDeAsientosLibres() > 30) {
            vuelo.precioEstandar * 25 / 100
        } else {
            vuelo.precioEstandar * 50 / 100
        }
    }
}
