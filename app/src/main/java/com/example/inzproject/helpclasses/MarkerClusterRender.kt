package com.example.inzproject.helpclasses

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.provider.Settings.Global
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
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

    var icon: Int by mutableStateOf(R.drawable.ic_sunnycloudy)

    private var clusterMap: HashMap<String, Marker> = hashMapOf()

    @OptIn(DelicateCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onBeforeClusterItemRendered(item: MyMarker, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)

        val itemM = item as com.example.inzproject.data.dataclasses.MyMarker

        viewModel.getWeather(item.itemPosition.latitude, item.itemPosition.longitude)

        icon = itemM.icon ?: (viewModel.weatherData?.weatherType?.iconRes ?: R.drawable.ic_sunnycloudy)

        item.temperature = viewModel.weatherData?.temperatureCelsius

        markerOptions.icon(bitmapDescriptorFromVector(context, icon, itemM))
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
        //setMarker((clusterItem as MyItem), marker)
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

@RequiresApi(Build.VERSION_CODES.R)
fun bitmapDescriptorFromVector(
    context: Context,
    @DrawableRes vectorDrawableResourceId: Int,
    marker: MyMarker
): BitmapDescriptor? {
    val backgroundTint = if (marker.type != MarkerType.SpecialPlace) {
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