package com.tetron.waybill.adapters

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.tetron.waybill.model.waybill.Refueling
import java.text.SimpleDateFormat
import java.util.*


object BindingAdapters {

    @BindingAdapter("isEnabled")
    @JvmStatic
    fun isViewEnabled(view: View, isValid: Boolean?) {
        isValid?.let {
            view.isEnabled = it
        }
    }

    @BindingAdapter("items")
    @JvmStatic
    fun setItems(listView: RecyclerView, items: List<Refueling>) {
        (listView.adapter as RefuelingsListAdapter).submitList(items)
    }

    @BindingAdapter("formattedDateTime")
    @JvmStatic
    fun setFormattedDateTime(view: TextView, dateTime: Date?) {
        dateTime?.let {
            val formatter = SimpleDateFormat("dd.MM.yyyy, HH:mm", Locale.US)
            view.text = formatter.format(dateTime)
        }
    }

    @BindingAdapter("formattedTime")
    @JvmStatic
    fun setFormattedTime(view: TextView, time: Int?) {
        time?.let {
            val hours = time / 60
            val minutes = time % 60
            view.text = String.Companion.format("$hours:$minutes")
        }
    }

    @BindingAdapter("errorText")
    @JvmStatic
    fun setErrorMessage(view: TextInputLayout, errorMessage: Int?) {
        view.error = errorMessage?.let { view.context.getString(errorMessage) }
    }

    @BindingAdapter("setText")
    @JvmStatic
    fun setTextFromResource(view: TextView, resourceId: Int?) {
        view.text = if (resourceId != null) view.context.getString(resourceId) else ""
    }

    @BindingAdapter("android:visibility")
    @JvmStatic
    fun setVisibility(view: View, value: Boolean) {
        view.visibility = if (value) View.VISIBLE else View.GONE
    }

    @BindingAdapter("android:alpha")
    @JvmStatic
    fun setAlpha(view: View, value: Boolean) {
        view.alpha = if (value) 1F else 0.5F
    }
}