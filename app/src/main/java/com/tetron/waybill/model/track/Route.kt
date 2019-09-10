package com.tetron.waybill.model.track

import com.google.gson.annotations.SerializedName

class Route {

    @SerializedName("orderid")
    var orderId: Int? = null

    @SerializedName("comment")
    var comment: String? = null

    @SerializedName("osrm_route")
    var osrmRoute: String? = null

    @SerializedName("yandex_route")
    var yandexRoute: String? = null

    @SerializedName("yandex_lench")
    var yandexLength: Int? = null

    @SerializedName("osrm_lench")
    var osrmLench: String? = null

    @SerializedName("osrm_html")
    var osrmHtml: String? = null

    @SerializedName("username")
    var userName: String? = null

    @SerializedName("userid")
    var userId: String? = null


}