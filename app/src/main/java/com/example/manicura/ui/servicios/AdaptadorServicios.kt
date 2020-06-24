package com.example.manicura.ui.servicios

import android.content.Context
import android.os.Build
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.manicura.R
import com.example.manicura.TodosServicios
import com.example.manicura.Utils
import kotlinx.android.synthetic.main.lista_servicios.view.tv_Monto
import kotlinx.android.synthetic.main.lista_todos_servicios.view.*
import java.text.DecimalFormat
import java.util.*

/**
 * Creada por Andrés Cermeño el 26/5/2020
 */

private val ManoVerde: Int = R.mipmap.mano_verde
private val ManoGris: Int = R.mipmap.mano_gris
private val PieVerde: Int = R.mipmap.pie_verde
private val PieGris: Int = R.mipmap.pie_gris

class AdaptadorServicios(
    private var listaDeServicios: List<TodosServicios>,
    private var contexto: Context, var itemClickListener: ServiciosFragment
) : RecyclerView.Adapter<AdaptadorServicios.ViewHolder>() {

    class ViewHolder(var vista: View, var contexto: Context) : RecyclerView.ViewHolder(vista) {

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(servicios: TodosServicios, clickListener: OnItemClickListener) {
            val dec = DecimalFormat.getCurrencyInstance(Locale.CANADA)
            vista.tv_Cliente.text = servicios.nombre_cliente
            vista.tv_fecha.text =
                Utils.convertLongToDateString(servicios.fecha, "dd-MM-yyyy")
            vista.tv_Monto.text = dec.format(servicios.montoPagado).toString()
            if (servicios.manos) {
                vista.iv_manos.setImageResource(ManoVerde)
            } else {
                vista.iv_manos.setImageResource(ManoGris)
            }
            if (servicios.pies) {
                vista.iv_pies.setImageResource(PieVerde)
            } else {
                vista.iv_pies.setImageResource(PieGris)
            }

            vista.setOnClickListener {
                clickListener.onItemCLick(servicios)
                //clickListener.onItemCLick(servicios.clienteId, servicios.fecha, servicios.nombre_cliente)
                TransitionManager.beginDelayedTransition(vista.cardView, AutoTransition())
                vista.expandible.visibility = View.VISIBLE
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.lista_todos_servicios, parent, false)
        return ViewHolder(layout, contexto)
    }

    override fun getItemCount() = listaDeServicios.size

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaDeServicios[position], itemClickListener)
    }

    interface OnItemClickListener {
        fun onItemCLick(servicios: TodosServicios)
//        fun onItemCLick(idCliente: Long, fecha: Long, cliente: String)
    }
}