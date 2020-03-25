package com.example.manicura

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.lista_notificaciones.view.*
import java.util.*


class AdaptadorPrincipal(private var lista: List<Notificacion>, private var contexto: Context) : RecyclerView.Adapter<AdaptadorPrincipal.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.lista_notificaciones, parent, false)

        return ViewHolder(
            layout,
            contexto
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(lista[position])
    }

    override fun getItemCount() = lista.size

    class ViewHolder(var vista: View, var contexto: Context): RecyclerView.ViewHolder(vista){

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind (notificacion: Notificacion){

            vista.tvNombreCliente.text = notificacion.Nombre
            vista.tvMensaje.text = notificacion.Mensaje
            if (notificacion.manos){
                vista.ivManos.setImageResource(R.mipmap.mano_verde)
            }else{
                vista.ivManos.setImageResource(R.mipmap.mano_gris)
            }
            if(notificacion.pies){
                vista.ivPies.setImageResource(R.mipmap.pie_verde)
            }else{
                vista.ivPies.setImageResource(R.mipmap.pie_gris)
            }

            vista.tvIcon.text = notificacion.Nombre.substring(0,1);

//            val mRandom = Random()
//            val color: Int =
//                Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256))
//            (vista.tvIcon.getBackground() as GradientDrawable).setColor(color)




        }
    }




}


