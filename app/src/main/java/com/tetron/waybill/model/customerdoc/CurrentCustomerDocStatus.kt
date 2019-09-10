package com.tetron.waybill.model.customerdoc

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "current_customer_doc_status",
    indices = [Index(value = ["id"], unique = true)]
)

data class CurrentCustomerDocStatus(

    @PrimaryKey
    val id: String,
    val isWork: Boolean = true,
    val pauseReason: String = "",
    val pauseComment: String = ""

)