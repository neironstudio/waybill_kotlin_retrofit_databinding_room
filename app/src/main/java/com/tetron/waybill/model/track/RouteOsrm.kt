package com.tetron.waybill.model.track

import com.google.gson.annotations.SerializedName

class RouteOsrm {

    @SerializedName("code")
    var code: String? = null

    @SerializedName("routes")
    var routeOsrm: List<RoutesOsrmItem>? = null
}