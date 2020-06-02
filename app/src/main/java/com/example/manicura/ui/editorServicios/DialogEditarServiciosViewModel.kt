package com.example.manicura.ui.editorServicios

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.manicura.database.ManicuraDAO

class DialogEditarServiciosViewModel(
    private val dataSource: ManicuraDAO,
    application: Application
) : ViewModel() {

    var hacerManos = MutableLiveData<Boolean>()

    val hacerPies = MutableLiveData<Boolean>()

    val monto = MutableLiveData<Double>()

    val fecha = MutableLiveData<Long>()

    val idCliente = MutableLiveData<Long>()

}
