package com.tetron.waybill.model.track


import com.google.gson.annotations.SerializedName

class WayPathOrder {

    @SerializedName("userid")
    var userId: Int? = null

    @SerializedName("customerid")
    var customerId: Int? = null

    @SerializedName("lench")
    var length: Int? = null

    @SerializedName("comment")
    var comment: String? = null

    @SerializedName("useridstring")
    var userIdString: String? = null

    @SerializedName("guid")
    var guid: Int? = null


}