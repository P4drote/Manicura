package com.example.manicura.ui.clientes

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.manicura.R
import com.example.manicura.Utils
import com.example.manicura.database.TablaCliente
import kotlinx.android.synthetic.main.lista_clientes.view.*

private val ManoVerde: Int = R.mipmap.mano_verde
private val ManoGris: Int = R.mipmap.mano_gris
private val PieVerde: Int = R.mipmap.pie_verde
private val PieGris: Int = R.mipmap.pie_gris

class AdaptadorClientes(
    private var lista_clientes: List<TablaCliente>,
    private var contexto: Context, var itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<AdaptadorClientes.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.lista_clientes, parent, false)
        return ViewHolder(layout, contexto)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista_clientes[position], itemClickListener)


    }

    class ViewHolder(var vista: View, var contexto: Context) : RecyclerView.ViewHolder(vista) {
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

            Utils.convertLongToDateString(clientes.fechaUltimaVisita, "dd-MM-yyyy")
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

            vista.setOnClickListener {
                clickListener.onItemCLick(clientes)
            }
        }
    }

    override fun getItemCount() = lista_clientes.size

    interface OnItemClickListener {
        fun onItemCLick(tablaCliente: TablaCliente)
    }


}