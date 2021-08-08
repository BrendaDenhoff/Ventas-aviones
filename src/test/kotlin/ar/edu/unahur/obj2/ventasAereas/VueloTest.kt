package ar.edu.unahur.obj2.ventasAereas

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class VueloTest : DescribeSpec({
    val avion1 = Avion(150, 5.500)
    val avion2 = Avion(87, 7.600)
    val avion3 = Avion(43, 10.800)

    val vueloDePasajeros = VueloDePasajeros(LocalDate.of(2021, 5, 14), avion1, "Buenos Aires", "Madrid", 12, 20000)
    val vueloDeCarga = VueloDeCarga(LocalDate.of(2021, 5, 21), avion2, "Rusia", "Argentina", 25, 70000)
    val vueloCharter = VueloCharter(LocalDate.of(2021, 6, 1), avion3, "Buenos Aires", "Misiones", 2, 10000, 7)

    describe("Cantidad de asientos libres en :") {
        it("vuelo de pasajeros") {
            vueloDePasajeros.cantidadDeAsientosLibres().shouldBe(150)
            Empresa.venderPasaje(vueloDePasajeros, LocalDate.of(2021, 3, 20), 23590256)
            Empresa.venderPasaje(vueloDePasajeros, LocalDate.of(2021, 9, 26), 37690256)
            Empresa.venderPasaje(vueloDePasajeros, LocalDate.of(2021, 3, 20), 23590256)
            Empresa.venderPasaje(vueloDePasajeros, LocalDate.of(2021, 9, 26), 37690256)
            Empresa.venderPasaje(vueloDePasajeros, LocalDate.of(2021, 3, 20), 23590256)
            Empresa.venderPasaje(vueloDePasajeros, LocalDate.of(2021, 9, 26), 37690256)
            vueloDePasajeros.cantidadDeAsientosLibres().shouldBe(144)
        }
        it("vuelo de carga") {
            vueloDeCarga.cantidadDeAsientosLibres().shouldBe(10)
            Empresa.venderPasaje(vueloDeCarga, LocalDate.of(2021, 6, 6), 5234423)
            Empresa.venderPasaje(vueloDeCarga, LocalDate.of(2021, 10, 14), 45634)
            Empresa.venderPasaje(vueloDeCarga, LocalDate.of(2021, 6, 6), 5234423)
            Empresa.venderPasaje(vueloDeCarga, LocalDate.of(2021, 10, 14), 45634)
            vueloDeCarga.cantidadDeAsientosLibres().shouldBe(6)
        }
        it("vuelo charter") {
            vueloCharter.cantidadDeAsientosLibres().shouldBe(11)
            Empresa.venderPasaje(vueloCharter, LocalDate.of(2021, 6, 22), 923748923)
            Empresa.venderPasaje(vueloCharter, LocalDate.of(2021, 2, 11), 563121123)
            vueloCharter.cantidadDeAsientosLibres().shouldBe(9)

        }
    }
    describe("Es un vuelo relajado?") {
        it("Vuelo charter")
        {
            vueloCharter.esVueloRelajado().shouldBeTrue()
        }
        it("Vuelo pasajeros")
        {
            vueloDePasajeros.esVueloRelajado().shouldBeFalse()
        }
        it("Vuelo de carga")
        {
            vueloDeCarga.esVueloRelajado().shouldBeTrue()
        }
    }
    describe("puede vender pasajes") {
        it("criterio seguro") {
            vueloDeCarga.puedeVenderPasaje().shouldBeTrue()
            Empresa.venderPasaje(vueloDeCarga, LocalDate.of(2021, 6, 6), 5234423)
            Empresa.venderPasaje(vueloDeCarga, LocalDate.of(2021, 10, 14), 45634)
            Empresa.venderPasaje(vueloDeCarga, LocalDate.of(2021, 6, 6), 5234423)
            Empresa.venderPasaje(vueloDeCarga, LocalDate.of(2021, 10, 14), 45634)
            Empresa.venderPasaje(vueloDeCarga, LocalDate.of(2021, 6, 6), 5234423)
            Empresa.venderPasaje(vueloDeCarga, LocalDate.of(2021, 10, 14), 45634)
            Empresa.venderPasaje(vueloDeCarga, LocalDate.of(2021, 6, 6), 5234423)
            Empresa.venderPasaje(vueloDeCarga, LocalDate.of(2021, 10, 14), 45634)
            vueloDeCarga.puedeVenderPasaje().shouldBeFalse()
        }
    }
    describe("Precio del pasaje para un vuelo") {
        it("Vuelo charter") {
            vueloCharter.precioDelVuelo().shouldBe(10000)
        }
        it("Vuelo pasajeros") {
            vueloDeCarga.precioDelVuelo().shouldBe(70000)
        }
        it("Vuelo de carga") {
            vueloDePasajeros.precioDelVuelo().shouldBe(20000)
        }
    }
    describe("Importe total del vuelo") {
        it("Vuelo charter") {
            Empresa.venderPasaje(vueloCharter, LocalDate.of(2021, 3, 20), 23590256)
            Empresa.venderPasaje(vueloCharter, LocalDate.of(2021, 9, 26), 37690256)
            Empresa.venderPasaje(vueloCharter, LocalDate.of(2021, 4, 28), 42890256)
            Empresa.importeTotalDelVuelo(vueloCharter).shouldBe(30000)
        }
        it("Vuelo pasajeros") {
            Empresa.venderPasaje(vueloDeCarga, LocalDate.of(2021, 4, 16), 37890256)
            Empresa.venderPasaje(vueloDeCarga, LocalDate.of(2021, 5, 22), 16284036)
            Empresa.venderPasaje(vueloDeCarga, LocalDate.of(2021, 5, 22), 16284036)
            Empresa.importeTotalDelVuelo(vueloDeCarga).shouldBe(210000)
        }
        it("Vuelo de carga") {
            Empresa.venderPasaje(vueloDePasajeros, LocalDate.of(2021, 5, 22), 11284036)
            Empresa.venderPasaje(vueloDePasajeros, LocalDate.of(2021, 3, 16), 13684036)
            Empresa.venderPasaje(vueloDePasajeros, LocalDate.of(2021, 7, 10), 19284036)
            Empresa.importeTotalDelVuelo(vueloDePasajeros).shouldBe(60000)
        }
    }
})