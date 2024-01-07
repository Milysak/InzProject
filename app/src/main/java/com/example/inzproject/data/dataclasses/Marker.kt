package com.example.inzproject.data.dataclasses

import android.health.connect.datatypes.units.Temperature
import androidx.compose.ui.graphics.Color
import com.example.inzproject.data.MarkerType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.clustering.ClusterItem

data class MyMarker(
    val itemPosition: LatLng,
    val itemTitle: String = "Marker",
    var itemSnippet: String = "M",
    val type: MarkerType,
    val color: Color? = null,
    val icon: Int? = null,
    var temperature: Double? = null,
) : ClusterItem {
    public var isSelected: Boolean = false

    override fun getPosition(): LatLng =
        itemPosition

    override fun getTitle(): String =
        itemTitle

    override fun getSnippet(): String =
        itemSnippet
}