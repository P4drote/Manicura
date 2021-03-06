package com.example.manicura.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.manicura.Notificacion
import com.example.manicura.Servicios
import com.example.manicura.TodosServicios

@Dao
interface ManicuraDAO {

    @Insert
    fun insertCliente(cliente: TablaCliente)

    @Insert
    fun insertServicio(servicio: TablaServicio)

    @Update
    fun updateCliente(cliente: TablaCliente)

    @Delete
    fun deleteCliente(tablaCliente: TablaCliente)

    @Query("SELECT nombre_cliente FROM cliente_table")
    fun getClientes(): LiveData<List<String>>

    @Query("SELECT montoPagado, fecha, pies, manos FROM servicios_table WHERE clienteId = (SELECT clienteId FROM cliente_table WHERE nombre_cliente = :nombre) ORDER BY fecha DESC")
    fun getServicios(nombre: String): List<Servicios>

//    @Query("SELECT COUNT(*) FROM servicios_table WHERE clienteId = (SELECT clienteId FROM cliente_table WHERE nombre_cliente = :nombre)")
//    fun contarServicios(nombre : String): Int

    @Query("SELECT clienteId FROM cliente_table WHERE nombre_cliente LIKE :nombre")
    fun getIdCliente(nombre: String): Long

    @Query("SELECT nombre_cliente, fecha_ultima_visita, manos, pies FROM  cliente_table WHERE  fecha_ultima_visita BETWEEN :finNotificacion AND :inicioNotificacion  ORDER BY fecha_ultima_visita DESC")
    fun getNotificaciones(inicioNotificacion: Long, finNotificacion: Long): List<Notificacion>

    @Query("SELECT nombre_cliente FROM cliente_table WHERE clienteId = :id")
    fun getNombreCliente(id: Long): String

    @Query("SELECT SUM(montoPagado) FROM servicios_table WHERE fecha BETWEEN :fechaActual AND :fechaInicioMes")
    fun getGanancias(fechaActual: Long, fechaInicioMes: Long): Double

    @Query("SELECT * FROM cliente_table ORDER BY nombre_cliente ASC")
    fun getTodosLosClientes(): LiveData<List<TablaCliente>>

    @Query("SELECT * FROM cliente_table WHERE clienteId = :ClienteId")
    fun getCliente(ClienteId: Long): TablaCliente

    @Query("SELECT cliente_table.nombre_cliente, servicios_table.clienteId, servicios_table.fecha, servicios_table.montoPagado, servicios_table.pies, servicios_table.manos  FROM cliente_table, servicios_table WHERE (fecha > :fechainicial and fecha < :fechafinal) AND (cliente_table.clienteId = servicios_table.clienteId) ORDER BY fecha DESC")
    fun getServiciosPorFecha(fechainicial: Long, fechafinal: Long): List<TodosServicios>


    @Query("SELECT cliente_table.nombre_cliente, servicios_table.clienteId, servicios_table.fecha, servicios_table.montoPagado, servicios_table.manos, servicios_table.pies  FROM cliente_table, servicios_table WHERE cliente_table.clienteId = servicios_table.clienteId ORDER BY fecha DESC")
    fun getTodosServicios(): List<TodosServicios>
}