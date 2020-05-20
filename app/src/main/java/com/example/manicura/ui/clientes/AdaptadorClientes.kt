package com.example.manicura.ui.clientes

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.manicura.R
import com.example.manicura.Utils
import com.example.manicura.database.TablaCliente
import kotlinx.android.synthetic.main.lista_clientes.view.*
import kotlinx.android.synthetic.main.lista_clientes.view.ivManos
import kotlinx.android.synthetic.main.lista_clientes.view.ivPies
import kotlinx.android.synthetic.main.lista_clientes.view.tvIcon
import kotlinx.android.synthetic.main.lista_clientes.view.tvMensaje
import kotlinx.android.synthetic.main.lista_clientes.view.tvNombreCliente
import kotlinx.android.synthetic.main.lista_notificaciones.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.ExperimentalTime

private val ManoVerde: Int = R.mipmap.mano_verde
private val ManoGris: Int = R.mipmap.mano_gris
private val PieVerde: Int = R.mipmap.pie_verde
private val PieGris: Int = R.mipmap.pie_gris
private lateinit var datosFiltrados: List<TablaCliente>

class AdaptadorClientes(
    private var lista_clientes: List<TablaCliente>,
    private var contexto: Context, var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AdaptadorClientes.ViewHolder>(), Filterable {

    init {
        datosFiltrados = lista_clientes
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    datosFiltrados = lista_clientes
                } else {
                    val resultList = ArrayList<TablaCliente>()
                    for (row in lista_clientes) {
                        if (row.nombre!!.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(row)
                        }
                    }
                    datosFiltrados = resultList
                }
                val filterResult = FilterResults()
                filterResult.values = datosFiltrados
                return filterResult
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                datosFiltrados = results?.values as List<TablaCliente>
                notifyDataSetChanged()
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.lista_clientes, parent, false)
        return ViewHolder(layout, contexto)
    }

    @ExperimentalTime
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datosFiltrados[position], itemClickListener)


    }

    class ViewHolder(var vista: View, var contexto: Context) : RecyclerView.ViewHolder(vista) {
        @ExperimentalTime
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(clientes: TablaCliente, clickListener: OnItemClickListener) {

            vista.tvClienteId.text = clientes.clienteId.toString()
            vista.tvNombreCliente.text = clientes.nombre
            if (clientes.fechaUltimaVisita == 0L) {
                vista.tvMensaje.text = "No ha recibido servicio aún!"
            } else {
                var diasTranscurridos = Utils.calcularDiasTranscurridos(clientes.fechaUltimaVisita)
                if (diasTranscurridos == 0) {
                    vista.tvMensaje.text = "Nos visito hoy!"
                } else {
                    vista.tvMensaje.text =
                        "Han transcurrido " + diasTranscurridos.toString() + " dia desde su última visita"
                }
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Utils.convertLongToDateString(clientes.fechaUltimaVisita, "dd-MM-yyyy")
            }
            if (clientes.manos) {
                vista.ivManos.setImageResource(ManoVerde)
            } else {
                vista.ivManos.setImageResource(ManoGris)
            }
            if (clientes.pies) {
                vista.ivPies.setImageResource(PieVerde)
            } else {
                vista.ivPies.setImageResource(PieGris)
            }

            vista.tvIcon.text = clientes.nombre.substring(0, 1)

            vista.setOnClickListener {
                clickListener.onItemCLick(clientes.clienteId)

            }
        }
    }

    override fun getItemCount() = datosFiltrados.size



    interface OnItemClickListener {
        fun onItemCLick(idCliente: Long)
    }

}