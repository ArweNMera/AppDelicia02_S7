package com.delicia.app.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.delicia.app.data.local.AppDatabase
import com.delicia.app.data.local.relations.OrderWithItems
import com.delicia.app.domain.usecase.GetOrderHistoryUseCase
import kotlinx.coroutines.launch

class OrderHistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val getOrderHistoryUseCase: GetOrderHistoryUseCase

    private val _orders = MutableLiveData<List<OrderWithItems>>()
    val orders: LiveData<List<OrderWithItems>> = _orders

    init {
        // Obtenemos las dependencias de la base de datos
        val db = AppDatabase.getDatabase(application)
        // Creamos la instancia del caso de uso con sus dependencias
        getOrderHistoryUseCase = GetOrderHistoryUseCase(application, db.orderDao(), db.userDao())
        // Llamamos a la función para cargar el historial
        fetchOrderHistory()
    }

    private fun fetchOrderHistory() {
        viewModelScope.launch {
            // Ejecutamos el caso de uso
            val result = getOrderHistoryUseCase()
            result.onSuccess { orderList ->
                // Si todo sale bien, actualizamos el LiveData con la lista de pedidos
                _orders.postValue(orderList)
            }.onFailure {
                // En un caso real, aquí manejaríamos el error para mostrar un mensaje al usuario
                _orders.postValue(emptyList()) // Por ahora, mostramos una lista vacía si hay error
            }
        }
    }
}