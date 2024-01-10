package com.example.inzproject.helpclasses

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.provider.Settings.Global
import android.transition.Transition
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.inzproject.R
import com.example.inzproject.data.MarkerType
import com.example.inzproject.data.dataclasses.MyMarker
import com.example.inzproject.domain.weather.WeatherData
import com.example.inzproject.ui.theme.*
import com.example.inzproject.viewmodels.MapViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import kotlinx.coroutines.*
import javax.inject.Inject

class MarkerClusterRender<MyMarker: ClusterItem> @Inject constructor(
    private val viewModel: MapViewModel,
    private var context: Context,
    var googleMap: GoogleMap,
    clusterManager: ClusterManager<MyMarker>,
    private var onInfoWindowClick: ((Any) -> Unit)?,
    ) : DefaultClusterRenderer<MyMarker>(context, googleMap, clusterManager) {

    private var clusterMap: HashMap<String, Marker> = hashMapOf()

    //var icon by mutableStateOf(com.google.android.material.R.drawable.ic_mtrl_chip_close_circle)

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onBeforeClusterItemRendered(item: MyMarker, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)

        /*val itemM = item as com.example.inzproject.data.dataclasses.MyMarker

        viewModel.getWeather(
            latitude = item.itemPosition.latitude,
            longitude = item.itemPosition.longitude
        ) {
            item.temperature = it?.temperatureCelsius
        }

        val icon: Int = item.icon ?: (
                viewModel.weatherData?.weatherType?.iconRes ?:
                com.google.android.material.R.drawable.ic_mtrl_chip_close_circle
                )

        markerOptions.icon(
            item.icon?.let {
                bitmapDescriptorFromVector(context, it, item)
            } ?:
            loadIcon(
                context,
                viewModel,
                item,
                R.drawable.filled_heart
            )
        )*/

        /*val itemM = item as com.example.inzproject.data.dataclasses.MyMarker

        if (item.type == MarkerType.Weather) {
            viewModel.getWeather(
                item.itemPosition.latitude,
                item.itemPosition.longitude
            ) { weather ->

                val painter =
                    rememberAsyncImagePainter(model = weather?.weatherType?.iconRes)

                item.temperature = weather?.temperatureCelsius

                markerOptions.icon(
                    bitmapDescriptorFromVector(
                        context,
                        (weather?.weatherType?.iconRes!!),
                        item
                    )
                )


            }
        } else {
            markerOptions.icon(bitmapDescriptorFromVector(context, item.icon!!, item))
        }*/
    }

    override fun shouldRenderAsCluster(cluster: Cluster<MyMarker>): Boolean {
        return cluster.size > 10
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onClusterItemRendered(
        clusterItem: MyMarker,
        marker: Marker
    ) {
        super.onClusterItemRendered(clusterItem, marker)
        //clusterMap[(clusterItem as MyItem).title] = marker
        //setMarker((clusterItem as MyMarker), marker)
    }

    @SuppressLint("PotentialBehaviorOverride")
    private fun setMarker(poi: MyMarker, marker: Marker?) {
        val markerColor = BitmapDescriptorFactory.HUE_RED
        marker?.let {
            changeMarkerColor(it, markerColor)
        }
        googleMap.setOnInfoWindowClickListener {
            onInfoWindowClick?.let { it1 -> it1(it.tag as MyMarker) }
        }
    }

    private fun getClusterMarker(itemId: String): Marker? {
        return if (clusterMap.containsKey(itemId)) clusterMap[itemId]
        else null
    }

    private fun changeMarkerColor(marker: Marker, color: Float) {
        try {
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(color));
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}

fun loadIcon(
    context: Context,
    viewModel: MapViewModel,
    marker: MyMarker,
    placeHolder: Int,
): BitmapDescriptor? {
    try {
        var bitmap: Bitmap? = null
        Glide.with(context)
            .asBitmap()
            .load(viewModel.getWeatherIcon(
                marker.itemPosition.latitude,
                marker.itemPosition.longitude
            ))
            .error(placeHolder)
            // to show a default icon in case of any errors
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: com.bumptech.glide.request.transition.Transition<in Bitmap>?
                ) {
                    bitmap = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

            })
        return BitmapDescriptorFactory.fromBitmap(bitmap!!)
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }

}

fun loadIcon1(
    context: Context,
    viewModel: MapViewModel,
    placeHolder: Int,
): Drawable? {
    try {
        var bitmap: Drawable? = null
        Glide.with(context)
            .load(viewModel.weatherData?.weatherType?.iconRes)
            .error(placeHolder)
            // to show a default icon in case of any errors
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: com.bumptech.glide.request.transition.Transition<in Drawable>?
                ) {
                    bitmap = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    TODO("Not yet implemented")
                }

            })
        return bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        return null
    }

}

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
    val foregroundTint = if (context.resources.configuration.isNightModeActive) Color(0xFF000D1B) else Color(0xFFF2F6FF)

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

@RequiresApi(Build.VERSION_CODES.R)
fun bitmapDescriptorFromVector(
    context: Context,
    @DrawableRes vectorDrawableResourceId: Int
): BitmapDescriptor? {
    val backgroundTint = if (context.resources.configuration.isNightModeActive) beigebrown else beige

    val foregroundTint = if (context.resources.configuration.isNightModeActive) Color(0xFF000D1B) else Color(0xFFF2F6FF)

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

    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}