package com.tetron.waybill.view.dialog

class DialogConfirmationMessage(
    title: String,
    confirmationMessage: String
) : SimpleAlertDialog(SimpleAlertDialog.TypeDialog.CONFIRMATION_MESSAGE.name, title, null, null, confirmationMessage)