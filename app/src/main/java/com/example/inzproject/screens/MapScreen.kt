package com.example.inzproject.screens

import android.annotation.SuppressLint
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.inzproject.R
import com.example.inzproject.components.MapSearchBar
import com.example.inzproject.data.MarkerType
import com.example.inzproject.data.dataclasses.MyMarker
import com.example.inzproject.data.mapROOM.database.SpecialPlace
import com.example.inzproject.helpclasses.MarkerClusterRender
import com.example.inzproject.helpclasses.bitmapDescriptorFromVector
import com.example.inzproject.viewmodels.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.compose.*
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("UnrememberedMutableState")
@OptIn(MapsComposeExperimentalApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel : MapViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()

    val mContext = LocalContext.current

    var fabPosition: FabPosition by remember {
        mutableStateOf(FabPosition.End)
    }

    var addPlaceActive by remember {
        mutableStateOf(false)
    }

    var deleteSpecialPlaceWindow by remember {
        mutableStateOf(false)
    }

    var floatingButtonIcon by remember {
        mutableStateOf(Icons.Default.Navigation)
    }

    var active by remember {
        mutableStateOf(false)
    }

    val geocoder = Geocoder(LocalContext.current)
    var addressList: List<Address>? = null

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(viewModel.latitude, viewModel.longitude), viewModel.zoom)
    }

    viewModel.getAllSpecialPlaces()

    viewModel.setCurrentLocation()

    /*viewModel.markers.add(com.example.inzproject.data.dataclasses.MyMarker(LatLng(50.24107598158953, 18.99944985875562)))
    viewModel.markers.add(com.example.inzproject.data.dataclasses.MyMarker(LatLng(50.18404284819005, 19.060459047724578)))
    viewModel.markers.add(com.example.inzproject.data.dataclasses.MyMarker(LatLng(50.18837862274404, 18.956688860933173)))
*/
    Scaffold(
        floatingActionButtonPosition = fabPosition,
        topBar = {
            MapSearchBar(
                viewModel = viewModel,
                cameraPositionState = cameraPositionState
            )
        },
        floatingActionButton = {

            if (!addPlaceActive) {
                fabPosition = FabPosition.End

                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .animateContentSize(tween(1500))
                            .height(if (active) 300.dp else 0.dp)
                            .fillMaxWidth(),
                        reverseLayout = true,
                        userScrollEnabled = true,
                        horizontalAlignment = Alignment.End
                    ) {
                        items(
                            items = viewModel.specialPlacesList.value ?: emptyList()
                        ) { item ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Box(
                                    modifier = Modifier.background(
                                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                                        shape = RoundedCornerShape(10.dp)
                                    ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(10.dp, 5.dp, 10.dp, 5.dp),
                                        text = item.itemTitle,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )
                                }
                                FloatingActionButton(
                                    modifier = Modifier
                                        .scale(0.8f),
                                    onClick = {
                                        viewModel.coords = LatLng(item.latitute, item.longitute)
                                    },
                                    containerColor = MaterialTheme.colorScheme.secondary
                                ) {
                                        Icon(
                                            imageVector = Icons.Filled.Map,
                                            contentDescription = "Place!",
                                            tint = MaterialTheme.colorScheme.onSecondary
                                        )
                                }
                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .height(5.dp)
                    )

                    FloatingActionButton(
                        onClick = {
                            active = !active

                            floatingButtonIcon =
                                if (!active) Icons.Default.Navigation else Icons.Default.Close
                        },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            imageVector = floatingButtonIcon,
                            contentDescription = "Navigation!",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            } else {
                fabPosition = FabPosition.Start
                active = false
                floatingButtonIcon = Icons.Default.Navigation

                var textState by remember {
                    mutableStateOf("")
                }

                Row(

                ) {


                    FloatingActionButton(
                        onClick = {
                            if (textState.isNotEmpty()) {
                                if (viewModel.specialMarkers.size >= 5) {
                                    Toast.makeText(
                                        mContext,
                                        "Nie możesz dodać więcej niż 5 miejsc!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    viewModel.addSpecialPlace(
                                        SpecialPlace(
                                            latitute = viewModel.newPlaceLocation?.latitude ?: 0.0,
                                            longitute = viewModel.newPlaceLocation?.longitude
                                                ?: 0.0,
                                            itemTitle = textState.trimEnd()
                                        )
                                    )

                                    viewModel.newPlaceVisibility = false
                                    addPlaceActive = false
                                }
                            } else {
                                Toast.makeText(
                                    mContext,
                                    "Nazwa nie może być pusta!!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add Place!",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Box(modifier = Modifier
                        .height(55.dp)
                        .padding(end = 30.dp),
                    ) {
                        DockedSearchBar(
                            modifier = Modifier.padding(start = 7.5.dp),
                            colors = SearchBarDefaults.colors(MaterialTheme.colorScheme.background),
                            query = textState,
                            onQueryChange = {
                                textState = it
                                            },
                            onSearch = {
                                active = !active
                            },
                            active = active,
                            onActiveChange = {
                                active = it
                            },
                            placeholder = {
                                Text("Wpisz nazwę...")
                            },
                            leadingIcon = {
                                Image(
                                    modifier = Modifier
                                        .size(40.dp),
                                    painter = painterResource(id = R.mipmap.ic_logo_foreground),
                                    contentDescription = "Logo"
                                )
                            },
                            trailingIcon = {
                                if (active) {
                                    Icon(
                                        modifier = Modifier
                                            .clickable {
                                                if (textState.isNotEmpty()) {
                                                    textState = ""
                                                } else {
                                                    active = false
                                                }
                                            },
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close Icon"
                                    )
                                }
                            },
                        ) {

                        }
                    }
                }
            }
        }
    ) {
        var isInit by mutableStateOf(false)

        var cameraZoom = 0f

        if (isSystemInDarkTheme()) {
            viewModel.setDarkMapTheme()
        } else {
            viewModel.setLightMapTheme()
        }

        if (deleteSpecialPlaceWindow) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                AlertDialogExample(
                    onDismissRequest = {
                        deleteSpecialPlaceWindow = false
                    },
                    onConfirmation = {
                        viewModel.selectedSpecialPlace?.let { place ->
                            SpecialPlace(
                                latitute = place.latitute,
                                longitute = place.longitute,
                                itemTitle = place.itemTitle
                            )
                        }?.let { placeToDelete ->
                            viewModel.deleteSpecialPlace(
                                placeToDelete
                            )
                        }

                        active = false
                        floatingButtonIcon = Icons.Default.Navigation
                        viewModel.newPlaceVisibility = false
                        deleteSpecialPlaceWindow = false
                    },
                    dialogText = "Czy chcesz usunąć ten znacznik miejsca?",
                    dialogTitle = viewModel.selectedSpecialPlace?.itemTitle ?: "ItemTitle",
                    icon = Icons.Default.LocationCity
                )
            }
        }

        GoogleMap(
            modifier = Modifier
                .fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = viewModel.state.properties,
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                mapToolbarEnabled = false,
                rotationGesturesEnabled = false
            ),
            onMapLongClick = { LatLng ->
                viewModel.newPlaceLocation = LatLng

                viewModel.newPlaceVisibility = true

                addPlaceActive = true
            },
            onMapClick = { LatLng ->
                addPlaceActive = false
                viewModel.newPlaceVisibility = false
            },
            onMapLoaded = {
                isInit = true
            }
        ) {
            val currentScreen: LatLngBounds? by mutableStateOf(cameraPositionState.projection?.visibleRegion?.latLngBounds)
            //val items by mutableStateOf(arrayListOf<MyItem>())

            /*viewModel.markers.forEach { marker ->
                items.add(MyItem(LatLng(marker.latitude, marker.longitude), "Title", "S"))
            }*/

            var clusterManager by remember { mutableStateOf<ClusterManager<MyMarker>?>(null) }

            viewModel.newPlaceLocation?.let { it1 ->
                MarkerState(
                    it1,
                )
            }?.let { it2 ->
                Marker(
                    state = it2,
                    icon = bitmapDescriptorFromVector(LocalContext.current, R.drawable.plus_outlined),
                    visible = viewModel.newPlaceVisibility
                )
            }

            MapEffect(
                key1 = viewModel.location,
                key2 = viewModel.coords
            ) { map ->

                if (viewModel.location != "") {
                    addressList = geocoder.getFromLocationName(viewModel.location, 1)

                    if (addressList?.isNotEmpty() == true) {
                        val address: Address = addressList!![0]
                        val latLng = LatLng(address.latitude, address.longitude)

                        map.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                latLng, 12f
                            ),
                            400,
                            null
                        )
                    } else {
                        Toast.makeText(
                            mContext,
                            "Nie znaleziono takiego miejsca!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    viewModel.location = ""
                }

                if (viewModel.coords != null) {
                    map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            viewModel.coords!!, 12f
                        ),
                        400,
                        null
                    )

                    viewModel.coords = null
                }

                cameraZoom = map.cameraPosition.zoom

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
                    if (item.type == MarkerType.SpecialPlace) {

                        viewModel.selectedSpecialPlace = SpecialPlace(
                            latitute = item.itemPosition.latitude,
                            longitute = item.itemPosition.longitude,
                            itemTitle = item.itemTitle
                        )

                        deleteSpecialPlaceWindow = true
                    } else {
                        Toast.makeText(mContext, "${item.temperature}°C", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
            }

            LaunchedEffect(
                key1 = cameraPositionState.isMoving,
                key2 = viewModel.specialMarkersChanged,
                key3 = isInit
            ) {
                /*items.forEach { item ->
                if (item.position == selectedItem?.position) item.isSelected = true
            }*/
                currentScreen?.apply {
                    val markers = mutableListOf<MyMarker>()

                    if (viewModel.markers.isEmpty()) {
                        for (i in 0 .. 2) {
                            addMarker(
                                currentScreen = LatLngBounds(
                                    LatLng(-87.9093218814005, 30.60710693448717),
                                    LatLng(-87.76016640751885, 163.05142668317936)
                                ),
                                markers = markers
                            )
                        }
                    } else {
                        viewModel.markers.forEach { myMarker ->
                            if (
                                myMarker.itemPosition.latitude in southwest.latitude .. northeast.latitude
                                    && myMarker.itemPosition.longitude in southwest.longitude .. northeast.longitude
                            ) {
                                if (Math.abs(cameraZoom - cameraPositionState.position.zoom) > 0.5f)
                                    addMarker(
                                        currentScreen = currentScreen,
                                        markers = markers
                                    )
                                else
                                    markers.add(myMarker)
                            } else {
                                addMarker(
                                    currentScreen = currentScreen,
                                    markers = markers
                                )
                            }
                        }
                    }

                    viewModel.markers.clear()

                    markers.forEach {
                        viewModel.markers.add(it)
                    }

                    cameraZoom = cameraPositionState.position.zoom

                    clusterManager?.clearItems()

                    clusterManager?.addItems(viewModel.markers)

                    viewModel.specialMarkers.clear()

                    viewModel.getAllSpecialPlaces()

                    viewModel.specialPlacesList.value?.forEach { specialPlace ->
                        viewModel.specialMarkers.add(
                            MyMarker(
                                itemPosition = LatLng(specialPlace.latitute, specialPlace.longitute),
                                itemTitle = specialPlace.itemTitle,
                                type = MarkerType.SpecialPlace,
                                icon = R.drawable.ic_specialplace_outlined_foreground
                            )
                        )
                    }

                    clusterManager?.addItems(viewModel.specialMarkers)

                    clusterManager?.onCameraIdle()

                    clusterManager?.cluster()
                }

                viewModel.specialMarkersChanged = false
                //isInit = false
            }
        }
    }
}

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Usuń")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Anuluj")
            }
        }
    )
}


fun addMarker(currentScreen: LatLngBounds?, markers: MutableList<MyMarker>) {
    currentScreen?.let {
        markers.add(
            MyMarker(
                itemPosition =  LatLng(
                    Random.nextDouble(
                        it.southwest.latitude + (it.northeast.latitude - it.southwest.latitude) / 5,
                        it.northeast.latitude - (it.northeast.latitude - it.southwest.latitude) / 5
                    ),
                    Random.nextDouble(
                        it.southwest.longitude + (it.northeast.longitude - it.southwest.longitude) / 5,
                        it.northeast.longitude - (it.northeast.longitude - it.southwest.longitude) / 5
                    ),
                ),
                type = MarkerType.Weather
            )
        )
    }
}