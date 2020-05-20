package com.example.manicura

import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.util.*
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertLongToDateString(systemTime: Long, formato: String): String {
        val instant: Instant = Instant.ofEpochMilli(systemTime)
        val zoneId: ZoneId = ZoneId.of("America/Santiago")
        val fechaActualSeg = ZonedDateTime.ofInstant(instant, zoneId)
        val formateador = DateTimeFormatter.ofPattern(formato)
        //"EEEE dd-MMM-yyyy"
        return fechaActualSeg.format(formateador)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calcularPrimerDiaMes(horaActual: Long): Long {
        val millisecondsSinceEpoch = System.currentTimeMillis()
        val instant: Instant = Instant.ofEpochMilli(millisecondsSinceEpoch)

        val zoneId: ZoneId = ZoneId.of("America/Santiago")
        val fechaActual = ZonedDateTime.ofInstant(instant, zoneId)

        var firstOfMonth: ZonedDateTime = fechaActual.with(ChronoField.DAY_OF_MONTH, 1)
        firstOfMonth = firstOfMonth.toLocalDate().atStartOfDay(zoneId)

        return (firstOfMonth.toEpochSecond() * 1000)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calcularDiasAtras(horaActual: Long, dias: Int): Long {
        val instant: Instant = Instant.ofEpochMilli(horaActual)

        val zoneId: ZoneId = ZoneId.of("America/Santiago")
        val fechaActual = ZonedDateTime.ofInstant(instant, zoneId)

        var formatoHora = DateTimeFormatter.ofPattern("HH")
        val hora = fechaActual.format(formatoHora)
        var formatoMinuto = DateTimeFormatter.ofPattern("mm")
        var minutos = fechaActual.format(formatoMinuto)
        val horaActual = hora.toLong()
        var minActual = minutos.toLong()

        var diasAtras = fechaActual.minusDays(dias.toLong())
        diasAtras = diasAtras.minusHours(horaActual)
        diasAtras = diasAtras.minusMinutes(minActual)

        return (diasAtras.toEpochSecond() * 1000)
    }

    fun calcularDiasTranscurridos(fecha: Long): Int {
        val dec = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        var fechaActual = System.currentTimeMillis()
        var fechafinal = (fechaActual - fecha)
        return (fechafinal / 86400000L).toInt()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calcularInicioDia(fecha: Long): Long {
        val horas = convertLongToDateString(fecha, "k")
        val minutos = convertLongToDateString(fecha, "m")
        return fecha - ((horas.toLong() - 1) * 3600000) - (minutos.toLong() * 60000)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun calcularUltimoDiaMes(horaActual: Long): Long {
        val calculo = Utils.calcularPrimerDiaMes(horaActual)
        val instant: Instant = Instant.ofEpochMilli(calculo)
        val zoneId: ZoneId = ZoneId.of("America/Santiago")
        val fechaActual = ZonedDateTime.ofInstant(instant, zoneId)

        var firstOfMonth: ZonedDateTime = fechaActual.with(ChronoField.DAY_OF_MONTH, 1)
        firstOfMonth = firstOfMonth.toLocalDate().atStartOfDay(zoneId)
        val firstOfNextMonth: ZonedDateTime = firstOfMonth.plusMonths(1L)
        val formatoSeg = DateTimeFormatter.ofPattern("ss")
        val seg = firstOfNextMonth.format(formatoSeg)
        val segResto = seg.toLong() + 1L
        var ultimoDiaMes = firstOfNextMonth.minusSeconds(segResto)

        return (ultimoDiaMes.toEpochSecond() * 1000L)
    }
}

