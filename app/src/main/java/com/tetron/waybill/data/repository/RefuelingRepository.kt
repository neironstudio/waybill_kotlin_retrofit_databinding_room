package com.tetron.waybill.data.repository

import com.tetron.waybill.model.waybill.Refueling

class RefuelingRepository {

    fun getRefuelings(): List<Refueling> = listOf(
        Refueling(1, "12.07.2019", "12:34", "Подольск, Газпром-Нефть", 24),
        Refueling(2, "12.07.2019", "13:58", "26-ой км МКАД, Газпром-Нефть", 10),
        Refueling(3, "12.07.2019", "16:40", "Москва, Нефтьмагистраль", 5)
    )

}