package com.tetron.waybill.model.model1c


import androidx.annotation.NonNull
import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.google.gson.annotations.SerializedName
import com.tetron.waybill.data.db.converter.DateConverter
import java.util.*


@Entity(
    tableName = "permission",
    indices = [Index(value = ["waybillId"])],
    foreignKeys = [
        ForeignKey(
            entity = WaybillDoc::class,
            parentColumns = ["waybillId"],
            childColumns = ["waybillId"],
            onDelete = CASCADE
        )]
)

class Permission {

    @PrimaryKey
    @NonNull
    @SerializedName("WaybillId")
    var waybillId: String? = null

    @SerializedName("IssueDate")
    @TypeConverters(DateConverter::class)
    var issueDate: Date? = null

    @SerializedName("PermissionId")
    var permissionId: String? = null

    @SerializedName("IssuedBy")
    var issuedBy: String? = null

    @SerializedName("Comment")
    var comment: String? = null

}