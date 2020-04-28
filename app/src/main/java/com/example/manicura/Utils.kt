package com.example.manicura

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat

object Utils {

    fun hideSoftKeyBoard(context: Context, view: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
            // TODO: handle exception
            e.printStackTrace()
        }

    }

    fun convertLongToDateString(systemTime: Long, formato: String): String {
        //"EEEE dd-MMM-yyyy"
        return SimpleDateFormat(formato).format(systemTime).toString()
    }

    fun calcularPrimerDiaMes(): Long {
        val horaActual = System.currentTimeMillis()
        val dias = convertLongToDateString(horaActual, "d")
        val horas = convertLongToDateString(horaActual, "k")
        val minutos = convertLongToDateString(horaActual, "m")
        var dia: Long =
            horaActual - ((dias.toLong() - 1) * 86400000) - ((horas.toLong() - 1) * 3600000) - (minutos.toLong() * 60000)
        return dia
    }

    fun calcularDiasAtras(horaActual: Long, dias: Int): Long {
        val horaFinal = horaActual - (86400000 * dias.toLong())
        return horaFinal
    }

    fun calcularDiasTranscurridos(fecha: Long): Int {
        var fechaActual = System.currentTimeMillis()
        fechaActual = fechaActual - fecha
        return SimpleDateFormat("D").format(fechaActual).toInt()
    }

    fun calcularInicioDia(fecha: Long): Long {
        val horas = convertLongToDateString(fecha, "k")
        val minutos = convertLongToDateString(fecha, "m")
        return fecha - ((horas.toLong() - 1) * 3600000) - (minutos.toLong() * 60000)
    }
}

