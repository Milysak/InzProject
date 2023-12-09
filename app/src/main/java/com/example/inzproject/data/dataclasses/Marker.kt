package com.example.inzproject.data.dataclasses

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class MyMarker(
    val itemPosition: LatLng,
    val itemTitle: String = "Marker",
    var itemSnippet: String = "M",
) : ClusterItem {
    public var isSelected: Boolean = false

    override fun getPosition(): LatLng =
        itemPosition

    override fun getTitle(): String =
        itemTitle

    override fun getSnippet(): String =
        itemSnippet
}