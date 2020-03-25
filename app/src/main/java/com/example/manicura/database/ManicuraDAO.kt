package com.example.manicura.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ManicuraDAO {

    @Insert
    fun insertCliente(cliente: TablaCliente)

    @Update
    fun updateCliente(cliente: TablaCliente)

    @Query("SELECT * FROM cliente_table")
    fun getClientes(): LiveData<List<TablaCliente>>

}