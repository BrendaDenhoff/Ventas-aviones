package ar.edu.unahur.obj2.ventasAereas

import java.time.LocalDate

object Empresa {

    var pasajesVendidos = mutableListOf<Pasaje>()

    var criterioDeVenta: CriterioDeVenta = Segura

    var vuelos = mutableListOf<Vuelo>()

    fun venderPasaje(vuelo: Vuelo, fecha: LocalDate, dniPasajero: Int) {
        if(!vuelo.puedeVenderPasaje()) {
            Error("no se puede vender el pasaje")
        } else {
            vuelo.pasajes.add(Pasaje(vuelo, fecha, dniPasajero))
            agregarVuelo(vuelo)
        }
    }

    fun agregarVuelo(vuelo: Vuelo) {
        if (!vuelos.contains(vuelo))
            vuelos.add(vuelo)
    }

    fun importeTotalDelVuelo(vuelo: Vuelo) = vuelo.pasajes.size * vuelo.precioDelVuelo()

    fun vuelosDelDni(dni:Int) = pasajesVendidos.filter { p->p.dniPasajero == dni }

    fun vuelosDelDestino(destino: String) = pasajesVendidos.filter { p->p.vuelo.destino == destino }

    fun fechaDeVuelosConDestinoYDni(dni: Int, origen: String) = vuelosDelDni(dni).intersect(vuelosDelDestino(origen)).map { p->p.vuelo.fecha}

    fun asientosLibresParaUnDestinoEntreFechas(destino: String, fechaInicio: LocalDate, fechaFin: LocalDate): Int {
        val vueloPosterioresAFechaInical = pasajesVendidos.filter { it.vuelo.fecha > fechaInicio }
        val vueloEntreFechas = vueloPosterioresAFechaInical.filter { it.vuelo.fecha > fechaInicio }
        val vuelosConDestino = vueloEntreFechas.filter { it.vuelo.destino == destino }
        return vuelosConDestino.sumBy { it.vuelo.cantidadDeAsientosLibres()}
    }

    fun sonCompañeras(primerDni: Int, segundoDni: Int): Boolean {
        val vuelosPersonaA = vuelosDelDni(primerDni).map { p->p.vuelo }
        val vuelosPersonaB = vuelosDelDni(segundoDni).map { p->p.vuelo }
        return vuelosPersonaA.intersect(vuelosPersonaB).size >= 3
    }

}


abstract class CriterioDeVenta() {
    abstract fun puedeVenderPasajes(vuelo: Vuelo): Boolean
}

object Segura: CriterioDeVenta() {
    override fun puedeVenderPasajes(vuelo: Vuelo) = vuelo.cantidadDeAsientosLibres() >= 3
}

object LaxaFija: CriterioDeVenta() {
    override fun puedeVenderPasajes(vuelo: Vuelo) = vuelo.cantidadDeAsientosOcupados() <= vuelo.cantidadDeAsientosDisponibles() + 10
}

object LaxaPorcentual: CriterioDeVenta() {
    override fun puedeVenderPasajes(vuelo: Vuelo) = vuelo.cantidadDeAsientosOcupados() <= vuelo.cantidadDeAsientosDisponibles() + (vuelo.cantidadDeAsientosDisponibles() * 10 / 100)
}

object Pandemia: CriterioDeVenta() {
    override fun puedeVenderPasajes(vuelo: Vuelo) = false
}

