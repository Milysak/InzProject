package com.example.inzproject.helpclasses

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.provider.Settings.Global
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import com.example.inzproject.R
import com.example.inzproject.domain.weather.WeatherData
import com.example.inzproject.ui.theme.Purple40
import com.example.inzproject.ui.theme.Purple80
import com.example.inzproject.ui.theme.graySurface
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
    var weather: WeatherData? = null

    companion object {
        const val SELECTED_ALPHA = 1.0f
        const val UNSELECTED_ALPHA = 0.95f
    }

    private var clusterMap: HashMap<String, Marker> = hashMapOf()

    @OptIn(DelicateCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onBeforeClusterItemRendered(item: MyMarker, markerOptions: MarkerOptions) {
        super.onBeforeClusterItemRendered(item, markerOptions)

        GlobalScope.launch {
            val weatherFetcher = async { viewModel.getWeatherIcon(item) }
            weather = weatherFetcher.await()
            icon = weather?.weatherType?.iconRes ?: R.drawable.ic_sunnycloudy
        }

        markerOptions.icon(bitmapDescriptorFromVector(context, icon))

        if ((item as com.example.inzproject.data.dataclasses.MyMarker).isSelected) {
            markerOptions.alpha(SELECTED_ALPHA)
        } else {
            markerOptions.alpha(UNSELECTED_ALPHA)
        }
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

    @RequiresApi(Build.VERSION_CODES.R)
    private fun bitmapDescriptorFromVector(
        context: Context,
        @DrawableRes vectorDrawableResourceId: Int
    ): BitmapDescriptor? {
        val backgroundTint = if (context.resources.configuration.isNightModeActive) graySurface else Purple80
        val foregroundTint = if (context.resources.configuration.isNightModeActive) Color(0xFF000D1B) else Color(0xFFF2F6FF)

        val background = ContextCompat.getDrawable(context, R.drawable.pin_filled_2)
        background!!.setBounds(0, 0, background.intrinsicWidth + 70, background.intrinsicHeight + 70)
        background.setTint(backgroundTint.toArgb())

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
}