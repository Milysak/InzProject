package com.example.inzproject.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.inzproject.components.MapSearchBar
import com.example.inzproject.data.dataclasses.MyMarker
import com.example.inzproject.helpclasses.MarkerClusterRender
import com.example.inzproject.viewmodels.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.*
import kotlin.random.Random
import java.util.concurrent.ThreadLocalRandom

@SuppressLint("UnrememberedMutableState")
@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun MapScreen(
    viewModel : MapViewModel = hiltViewModel(),
) {
    /*viewModel.markers.add(com.example.inzproject.data.dataclasses.MyMarker(LatLng(50.24107598158953, 18.99944985875562)))
    viewModel.markers.add(com.example.inzproject.data.dataclasses.MyMarker(LatLng(50.18404284819005, 19.060459047724578)))
    viewModel.markers.add(com.example.inzproject.data.dataclasses.MyMarker(LatLng(50.18837862274404, 18.956688860933173)))
*/
    Scaffold(
        topBar = { MapSearchBar() }
    ) {
        var isInit by mutableStateOf(false)

        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(viewModel.latitude, viewModel.longitude), viewModel.zoom)
        }

        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = viewModel.state.properties,
            uiSettings = MapUiSettings(zoomControlsEnabled = false, mapToolbarEnabled = false),
            onMapLongClick = { LatLng ->

            },
            onMapClick = { LatLng ->

            }
        ) {
            val currentScreen: LatLngBounds? by mutableStateOf(cameraPositionState.projection?.visibleRegion?.latLngBounds)
            //val items by mutableStateOf(arrayListOf<MyItem>())
            val mContext = LocalContext.current

            /*viewModel.markers.forEach { marker ->
                items.add(MyItem(LatLng(marker.latitude, marker.longitude), "Title", "S"))
            }*/

            var clusterManager by remember { mutableStateOf<ClusterManager<MyMarker>?>(null) }

            MapEffect(viewModel.markers) { map ->
                // map.addCircle(viewModel.circle)
                viewModel.markers.clear()

                if (clusterManager == null) {
                    clusterManager = ClusterManager<MyMarker>(mContext, map).apply {
                        renderer = MarkerClusterRender(viewModel, mContext, map, this) { }
                    }
                }

//                 clusterManager?.renderer = MarkerClusterRender(mContext, map, clusterManager!!)

                clusterManager?.clearItems()

                clusterManager?.setOnClusterClickListener { cluster ->
                    val builder = LatLngBounds.builder()
                    for (item in cluster.items) {
                        builder.include(item.position)
                    }
                    val bounds = builder.build()
                    map.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(bounds, 100),
                        400,
                        null
                    )
                    true
                }

                clusterManager?.setOnClusterItemClickListener { item ->
                    true
                }

                clusterManager?.cluster()
                isInit = true
            }

            LaunchedEffect(
                key1 = cameraPositionState.isMoving,
                key2 = isInit,
            ) {
                /*items.forEach { item ->
                if (item.position == selectedItem?.position) item.isSelected = true
            }*/

                currentScreen?.apply {
                    if (viewModel.markers.isEmpty()) {
                        for (i in 0 .. 2) {
                            viewModel.markers.add(
                                MyMarker(
                                    LatLng(
                                        Random.nextDouble(
                                            southwest.latitude + (northeast.latitude - southwest.latitude)/5,
                                            northeast.latitude - (northeast.latitude - southwest.latitude)/5
                                        ),
                                        Random.nextDouble(
                                            southwest.longitude + (northeast.longitude - southwest.longitude)/5,
                                            northeast.longitude - (northeast.longitude - southwest.longitude)/5
                                        ),
                                    )
                                )
                            )
                        }
                    } else {
                        /*clusterManager?.markerCollection?.markers?.forEach { marker ->
                            if (
                                !(marker.position.latitude in southwest.latitude .. northeast.latitude
                                        && marker.position.longitude in southwest.longitude .. northeast.longitude)
                            ) {
                                viewModel.markers.add(
                                    MyMarker(
                                        LatLng(
                                            Random.nextDouble(
                                                southwest.latitude,
                                                northeast.latitude
                                            ),
                                            Random.nextDouble(
                                                southwest.longitude,
                                                northeast.longitude
                                            ),
                                        )
                                    )
                                )

                                viewModel.markers = viewModel.markers.filter { myMarker ->
                                    myMarker.itemPosition != marker.position
                                } as MutableList<MyMarker>
                            }
                        }*/

                        val markers = mutableListOf<MyMarker>()
                        viewModel.markers.forEach {myMarker ->
                            if (myMarker.itemPosition.latitude in southwest.latitude .. northeast.latitude
                                    && myMarker.itemPosition.longitude in southwest.longitude .. northeast.longitude) {
                                markers.add(myMarker)
                            } else {
                                markers.add(
                                    MyMarker(
                                        LatLng(
                                            Random.nextDouble(
                                                southwest.latitude + (northeast.latitude - southwest.latitude)/5,
                                                northeast.latitude - (northeast.latitude - southwest.latitude)/5
                                            ),
                                            Random.nextDouble(
                                                southwest.longitude + (northeast.longitude - southwest.longitude)/5,
                                                northeast.longitude - (northeast.longitude - southwest.longitude)/5
                                            ),
                                        )
                                    )
                                )
                            }
                        }
                        viewModel.markers.clear()

                        markers.forEach {
                            viewModel.markers.add(it)
                        }
                    }

                    clusterManager?.clearItems()
                    clusterManager?.addItems(viewModel.markers)
                }

                clusterManager?.onCameraIdle()
                clusterManager?.cluster()

                isInit = false
            }
        }
    }
}