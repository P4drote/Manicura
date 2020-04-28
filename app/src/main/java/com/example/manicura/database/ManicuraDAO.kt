package com.example.manicura.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.manicura.Notificacion
import com.example.manicura.Servicios

@Dao
interface ManicuraDAO {

    @Insert
    fun insertCliente(cliente: TablaCliente)

    @Insert
    fun insertServicio(servicio: TablaServicio)

    @Update
    fun updateCliente(cliente: TablaCliente)

    @Query("SELECT nombre_cliente FROM cliente_table")
    fun getClientes(): LiveData<List<String>>

    @Query("SELECT montoPagado, fecha, pies, manos FROM servicios_table WHERE clienteId = (SELECT clienteId FROM cliente_table WHERE nombre_cliente = :nombre) ORDER BY fecha DESC")
    fun getServicios(nombre: String): List<Servicios>

//    @Query("SELECT COUNT(*) FROM servicios_table WHERE clienteId = (SELECT clienteId FROM cliente_table WHERE nombre_cliente = :nombre)")
//    fun contarServicios(nombre : String): Int

    @Query("SELECT clienteId FROM cliente_table WHERE nombre_cliente = :nombre ORDER BY clienteId DESC LIMIT 1")
    fun getIdCliente(nombre: String): Long


    @Query("SELECT nombre_cliente, fecha_ultima_visita, manos, pies FROM  cliente_table WHERE  fecha_ultima_visita BETWEEN :finNotificacion AND :inicioNotificacion  ORDER BY fecha_ultima_visita DESC")
    fun getNotificaciones(inicioNotificacion: Long, finNotificacion: Long): List<Notificacion>

    @Query("SELECT nombre_cliente FROM cliente_table WHERE clienteId = :id")
    fun getNombreCliente(id: Long): String

    @Query("SELECT SUM(montoPagado) FROM servicios_table WHERE fecha BETWEEN :fechaActual AND :fechaInicioMes")
    fun getGanancias(fechaActual: Long, fechaInicioMes: Long): Double
}