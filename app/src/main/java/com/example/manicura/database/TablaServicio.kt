package com.example.manicura.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "servicios_table",
    primaryKeys = arrayOf("clienteId", "fecha"),
    foreignKeys = arrayOf(
        ForeignKey(
            entity = TablaCliente::class,
            parentColumns = arrayOf("clienteId"),
            childColumns = arrayOf("clienteId")
        )
    )
)
data class TablaServicio(
    @ColumnInfo(name = "clienteId")
    var clienteId: Long = 0L,
    var fecha: Long = 0L,
    var montoPagado: Double = 0.0,
    var manos: Boolean = true,
    var pies: Boolean = false
)