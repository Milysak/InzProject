package com.example.inzproject.screens

import android.annotation.SuppressLint
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
import com.example.inzproject.R
import com.example.inzproject.components.AlertDialogDeletePlace
import com.example.inzproject.components.MapSearchBar
import com.example.inzproject.data.MarkerType
import com.example.inzproject.data.mapROOM.database.SpecialPlace
import com.example.inzproject.helpfunctions.*
import com.example.inzproject.viewmodels.MapViewModel
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("UnrememberedMutableState", "PotentialBehaviorOverride")
@OptIn(MapsComposeExperimentalApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    viewModel : MapViewModel = hiltViewModel(),
) {
    val mContext = LocalContext.current

    val owner = LocalLifecycleOwner.current

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

    var isInit by mutableStateOf(false)

    var ifReorganizeMarkers by mutableStateOf(false)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(
                viewModel.latitude,
                viewModel.longitude
            ),
            viewModel.zoom)
    }

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
                                        active = !active

                                        floatingButtonIcon = Icons.Default.Navigation

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
                                if ((viewModel.specialPlacesList.value?.size ?: 0) >= 5) {
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
        if (isSystemInDarkTheme()) {
            viewModel.setDarkMapTheme()
        } else {
            viewModel.setLightMapTheme()
        }

        if (deleteSpecialPlaceWindow) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                AlertDialogDeletePlace(
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

            viewModel.newPlaceLocation?.let { it1 ->
                MarkerState(
                    it1,
                )
            }?.let { it2 ->
                Marker(
                    state = it2,
                    icon = bitmapDescriptorFromVectorForSpecialPlaces(
                        mContext,
                        R.drawable.plusspecialmarker_foreground
                    ),
                    visible = viewModel.newPlaceVisibility,
                    onClick = {
                        true
                    }
                )
            }

            MapEffect(key1 = ifReorganizeMarkers) {map ->
                reorganizeWeatherMarkers(
                    context = mContext,
                    viewModel = viewModel,
                    map = map,
                    currentScreen = currentScreen,
                    cameraPositionState = cameraPositionState
                )
            }

            MapEffect(key1 = isInit) { map ->
                map.setOnMarkerClickListener{
                    if (
                        it.title == MarkerType.SpecialPlace.toString()
                    ) {

                        viewModel.selectedSpecialPlace = SpecialPlace(
                            latitute = it.position.latitude,
                            longitute = it.position.longitude,
                            itemTitle = it.snippet!!
                        )

                        deleteSpecialPlaceWindow = true
                    } else {
                            Toast.makeText(
                                mContext,
                                "${it.snippet}°C",
                                Toast.LENGTH_SHORT).show()
                    }
                    true
                }

                viewModel.cameraZoom = map.cameraPosition.zoom

                viewModel.specialPlacesList.observe(
                    owner,
                    Observer {
                        reorganizeSpecialPlacesMarkers(
                            context = mContext,
                            viewModel = viewModel,
                            map = map
                        )
                    }
                )
            }

            MapEffect(
                key1 = viewModel.location,
                key2 = viewModel.coords
            ) { map ->
                if (viewModel.location != "") {
                    navigateToByName(
                        context = mContext,
                        viewModel = viewModel,
                        map = map
                    )
                }

                if (viewModel.coords != null) {
                    navigateToByLatLng(
                        viewModel = viewModel,
                        map = map
                    )
                }
            }

            LaunchedEffect(
                key1 = cameraPositionState.isMoving,
                key2 = viewModel.specialPlacesList.value
            ) {
                if (
                    !cameraPositionState.isMoving || viewModel.specialMarkersChanged
                ) ifReorganizeMarkers = !ifReorganizeMarkers
            }
        }
    }
}