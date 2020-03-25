package com.example.manicura.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cliente_table")
data class TablaCliente(
    @PrimaryKey(autoGenerate = true)
    var clienteId: Long = 0L,

    @ColumnInfo(name = "nombre_cliente")
    var nombre: String = "",

    @ColumnInfo(name = "fecha_ultima_visita")
    var fechaUltimaVisita: Long = System.currentTimeMillis()
)

//Para restar 30 dias
//new Date(System.currentTimeMillis() - 30 * 24 * 60 * 60 * 1000)