package com.example.inzproject.helpfunctions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import com.example.inzproject.R
import com.example.inzproject.data.MarkerType
import com.example.inzproject.ui.theme.*
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

@RequiresApi(Build.VERSION_CODES.R)
fun bitmapDescriptorFromVector(
    context: Context,
    @DrawableRes vectorDrawableResourceId: Int,
    type: String
): BitmapDescriptor? {
    val backgroundTint = if (type != MarkerType.SpecialPlace.toString()) {
        if (context.resources.configuration.isNightModeActive) Purple80 else Purple40
    } else {
        if (context.resources.configuration.isNightModeActive) PurpleGrey80 else PurpleGrey40
    }

    val background = ContextCompat.getDrawable(context, R.drawable.pin_filled_2)
    background!!.setBounds(0, 0, background.intrinsicWidth + 70, background.intrinsicHeight + 70)
    backgroundTint?.toArgb()?.let { background.setTint(it) }

    val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
    vectorDrawable!!.setBounds(
        30,
        15,
        vectorDrawable.intrinsicWidth + 45,
        vectorDrawable.intrinsicHeight + 20
    )

    val bitmap = Bitmap.createBitmap(
        background.intrinsicWidth + 70,
        background.intrinsicHeight + 70,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    background.draw(canvas)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

fun bitmapDescriptorFromVectorForSpecialPlaces(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}