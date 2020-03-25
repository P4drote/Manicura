package com.example.manicura.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "servicios_table", foreignKeys = arrayOf(
        ForeignKey(
            entity = TablaCliente::class,
            parentColumns = arrayOf("clienteId"),
            childColumns = arrayOf("clienteId")
        )
    )
)
data class TablaServicio(
    @ColumnInfo(name = "clienteId")
    @PrimaryKey
    var clienteId: Long,
    var montoPagado: Long = 0L,
    val fecha: Long = System.currentTimeMillis(),
    var manos: Boolean = true,
    var pies: Boolean = false
)