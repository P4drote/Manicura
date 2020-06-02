package com.example.manicura

import java.io.Serializable

/**
 * Creada por Andrés Cermeño el 26/5/2020
 */
class TodosServicios(
    var nombre_cliente: String = "",
    var clienteId: Long = 0L,
    var montoPagado: Double = 0.0,
    var fecha: Long = 0L,
    var manos: Boolean = true,
    var pies: Boolean = false
) : Serializable