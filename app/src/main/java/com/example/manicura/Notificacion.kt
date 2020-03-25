package com.example.manicura

import java.io.Serializable

class Notificacion(
    public val Nombre: String,
    public val Mensaje: String,
    public val manos:Boolean = true,
    public val pies: Boolean = true) :
    Serializable