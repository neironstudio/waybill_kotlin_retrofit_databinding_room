package com.tetron.waybill.model.model1c

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "userInfo", indices = [Index(value = ["userId"], unique = true)])

class UserInfo {

    @NonNull
    @PrimaryKey
    @SerializedName("UserId")
    var userId: String? = null

    @SerializedName("UserName")
    var userName: String? = null

    @SerializedName("CompanyName")
    var companyName: String? = null

    @SerializedName("CompanyId")
    var companyId: String? = null

    @SerializedName("DepartmentName")
    var departmentName: String? = null

    @SerializedName("DepartmentId")
    var departmentId: String? = null

    @SerializedName("SessionId")
    var sessionId: String? = null
}