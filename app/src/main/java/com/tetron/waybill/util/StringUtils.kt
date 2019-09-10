package com.tetron.waybill.util

import java.security.MessageDigest

fun String.toMD5() = MessageDigest.getInstance("MD5").digest(this.toByteArray()).toHex()

fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }