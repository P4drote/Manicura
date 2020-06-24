package com.example.manicura.ui.nuevoServicio

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.manicura.R
import com.example.manicura.Servicios
import com.example.manicura.Utils
import kotlinx.android.synthetic.main.lista_servicios.view.*

private val ManoVerde: Int = R.mipmap.mano_verde
private val ManoGris: Int = R.mipmap.mano_gris
private val PieVerde: Int = R.mipmap.pie_verde
private val PieGris: Int = R.mipmap.pie_gris

class AdaptadorNuevoServicio(
    private var listaDeServicios: List<Servicios>,
    private var contexto: Context
) : RecyclerView.Adapter<AdaptadorNuevoServicio.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.lista_servicios, parent, false)
        return ViewHolder(layout, contexto)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaDeServicios[position])
    }

    override fun getItemCount() = listaDeServicios.size

    class ViewHolder(var vista: View, var contexto: Context) : RecyclerView.ViewHolder(vista) {

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(servicios: Servicios) {
            //val mientras : Long = 86400000 * 2 //21 * 86400000‬
//            //val prueba =  System.currentTimeMillis() - mientras
//            val prueba =  System.currentTimeMillis()
//            DateUtils.MONTH_FORMAT
//            //vista.tv_Ultimavisita.text = prueba.toString()
//            val prueba2=SimpleDateFormat("d").format(TimeUnit.DAYS.convert(prueba, TimeUnit.DAYS)).toString()
//            val prueba21 = SimpleDateFormat("k").format(TimeUnit.DAYS.convert(prueba, TimeUnit.DAYS)).toString()
//            val prueba22 = SimpleDateFormat("m").format(TimeUnit.DAYS.convert(prueba, TimeUnit.DAYS)).toString()
//            val prueba3 = (prueba2.toInt()-1)  * 86400000 + (prueba21.toInt() * 3600000) + (prueba22.toInt() * 60000)
//            val final = System.currentTimeMillis() - prueba3
//            vista.tv_Ultimavisita.text = SimpleDateFormat("dd-MM-yyyyy HH:mm:ss.SSS").format(TimeUnit.DAYS.convert(final, TimeUnit.DAYS)).toString()
            vista.tv_Ultimavisita.text =
                Utils.convertLongToDateString(servicios.fecha, "dd-MM-yyyy")
            vista.tv_Monto.text = servicios.montoPagado.toString()
            if (servicios.manos) {
                vista.iv_Manos.setImageResource(ManoVerde)
            } else {
                vista.iv_Manos.setImageResource(ManoGris)
            }
            if (servicios.pies) {
                vista.iv_Pies.setImageResource(PieVerde)
            } else {
                vista.iv_Pies.setImageResource(PieGris)
            }
        }
    }

}