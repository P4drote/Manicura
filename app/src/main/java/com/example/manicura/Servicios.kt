package com.example.manicura

import java.io.Serializable

class Servicios(
    var montoPagado: Double = 0.0,
    var fecha: Long = System.currentTimeMillis(),
    var manos: Boolean = true,
    var pies: Boolean = false
) : Serializable