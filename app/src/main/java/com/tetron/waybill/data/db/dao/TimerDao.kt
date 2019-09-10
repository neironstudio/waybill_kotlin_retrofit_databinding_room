package com.tetron.waybill.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.tetron.waybill.model.model1c.CustomerDoc
import com.tetron.waybill.model.timer.Timer


@Dao
interface TimerDao {

    @Insert(onConflict = REPLACE)
    fun addTimer(timer: Timer): Long

    @Query("SELECT * FROM timer WHERE customerDocId = :customerDocId LIMIT 1")
    suspend fun getTimer(customerDocId: String): Timer?

    @Update
    fun update(timer: Timer)

    @Query(
        "SELECT " +
                "customerDocId,startTime,countWorkTime,countBaseTime,countLunchTime,countDriverTime,countCustomerTime,timer.currentState AS currentState " +
                "FROM (" +
                "SELECT id" +
                "  FROM current_customer_doc_status) " +
                "  LEFT JOIN  timer ON timer.customerDocId" +
                "  LIMIT 1"
    )
    fun getTimer(): LiveData<Timer>

    @Query("DELETE  FROM timer")
    fun clearTimer()

    @Query("UPDATE timer SET currentState = :currentState  WHERE customerDocId =:id")
    suspend fun updateTimerType(
        id: String,
        currentState: String

    )

    @Query("UPDATE customer_doc SET timeToCustomer = :timeToCustomer WHERE id = (SELECT id FROM current_customer_doc_status LIMIT 1)")
    suspend fun updateTimerFromBase(timeToCustomer: Long)


    @Query("SELECT * FROM customer_doc LIMIT 1")
    suspend fun getCurrentCustomerDoc(): CustomerDoc
}