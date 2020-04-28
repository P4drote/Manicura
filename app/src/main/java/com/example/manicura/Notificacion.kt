package com.example.manicura

import java.io.Serializable

class Notificacion(
    val nombre_cliente: String,
    val fecha_ultima_visita: Long,
    val manos: Boolean = true,
    val pies: Boolean = true
) :
    Serializable

