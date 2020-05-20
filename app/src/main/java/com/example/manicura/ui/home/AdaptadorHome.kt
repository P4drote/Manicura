package com.example.manicura.ui.home

import android.content.Context

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.manicura.Notificacion
import com.example.manicura.R
import com.example.manicura.Utils
import kotlinx.android.synthetic.main.lista_notificaciones.view.*
import kotlin.time.ExperimentalTime

private val ManoVerde : Int = R.mipmap.mano_verde
private val ManoGris : Int = R.mipmap.mano_gris
private val PieVerde: Int = R.mipmap.pie_verde
private val PieGris: Int = R.mipmap.pie_gris

class AdaptadorHome(private var lista: List<Notificacion>, private var contexto: Context) :
    RecyclerView.Adapter<AdaptadorHome.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.lista_notificaciones, parent, false)

        return ViewHolder(layout, contexto)
    }

    @ExperimentalTime
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    override fun getItemCount() = lista.size

    class ViewHolder(var vista: View, var contexto: Context): RecyclerView.ViewHolder(vista){

        @ExperimentalTime
        @RequiresApi(Build.VERSION_CODES.N)
        fun bind (notificacion: Notificacion){

            vista.tvNombreCliente.text = notificacion.nombre_cliente
            vista.tvMensaje.text =
                "Ultimo servicio hace " + Utils.calcularDiasTranscurridos(notificacion.fecha_ultima_visita) + " días"
            if (notificacion.manos){
                vista.ivManos.setImageResource(ManoVerde)
            }else{
                vista.ivManos.setImageResource(ManoGris)
            }
            if(notificacion.pies){
                vista.ivPies.setImageResource(PieVerde)
            }else{
                vista.ivPies.setImageResource(PieGris)
            }

            vista.tvIcon.text = notificacion.nombre_cliente.substring(0, 1)

//            val mRandom = Random()
//            val color: Int =
//                Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256))
//            (vista.tvIcon.getBackground() as GradientDrawable).setColor(color)


        }
    }




}


