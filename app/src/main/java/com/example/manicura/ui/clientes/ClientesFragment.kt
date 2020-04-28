package com.example.manicura.ui.clientes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.manicura.R

class ClientesFragment : Fragment() {

    private lateinit var clientesViewModel: ClientesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        clientesViewModel = ViewModelProvider(this)[ClientesViewModel::class.java]
        val root = inflater.inflate(R.layout.fragment_clientes, container, false)
        val textView: TextView = root.findViewById(R.id.tv_Titulo)


        return root
    }
}
