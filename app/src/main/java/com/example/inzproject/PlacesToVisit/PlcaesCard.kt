package com.example.inzproject.PlacesToVisit

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.inzproject.PlacesToVisit.ROOM.FavouritePlacesDatabase
//import com.example.inzproject.PlacesToVisit.ROOM.PlaceClassDatabase
//import com.example.inzproject.PlacesToVisit.ROOM.FavouritePlacesDatabase

import com.example.inzproject.PlacesToVisit.Repository.PlaceState
import com.example.inzproject.WeatherForecast.presentation.WeatherState
import com.example.inzproject.helpfunctions.createGradientBrush

import com.example.inzproject.ui.theme.beige
import com.example.inzproject.ui.theme.beigebrown
import com.example.inzproject.ui.theme.graySurface
import com.example.inzproject.ui.theme.typography1
import com.example.inzproject.viewmodels.PlacesViewModel
import kotlinx.coroutines.*

@Composable
fun PlacesCard(
    context: Context,
    state: PlaceState,
    switchState: Boolean,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    viewModel: PlacesViewModel

) {
    println(state.PlaceInfo?.results)

    val localeContext = LocalContext.current
    val database = FavouritePlacesDatabase.getInstance(localeContext)

    val coroutineScope =  CoroutineScope(Dispatchers.Main)

    var nullList: List<PlaceClass>? = null
    var listOfPlaces by remember {
        mutableStateOf(nullList)
    }

    if (switchState) {
        coroutineScope.launch(Dispatchers.IO) {
            /*val list = async { database.placeDao().getAllPlaces() }
            if (list.await().isEmpty()) println("Pusta baza!") else println("Nie pusta baza!")
            list.await().forEach {
                println(it.name)
            }*/
            listOfPlaces = database.placeDao().getAllPlaces()
        }
    } else {
        listOfPlaces = state.PlaceInfo?.results
    }

    listOfPlaces.let { data ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .padding(bottom = 20.dp),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                items(
                    items = data ?: emptyList()
                ) { place ->

//                    var IsInLoveList = false
//                    coroutineScope.launch(Dispatchers.IO) {
//                         IsInLoveList = database.placeDao().checkIfPlaceExists(place.place_id)
//                    }
                    println(place.name)

                    if (place.rating != null && place.rating >= viewModel.filterMinRating && place.rating <= viewModel.filterMaxRating) {
                        PlaceListItem(
                            place = place,
                            viewModel = viewModel,

                        ) { clickedPlace ->

                            // Create a Uri from an intent string. Use the result to create an Intent.
                            val gmmIntentUri = Uri.parse("geo:0,0?q=${clickedPlace.name}")

                            // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

                            // Make the Intent explicit by setting the Google Maps package
                            mapIntent.setPackage("com.google.android.apps.maps")

                            // Attempt to start an activity that can handle the Intent
                            startActivity(localeContext, mapIntent, null)

                        }
                    }
                }
            }
        }
    }
}


@SuppressLint("SuspiciousIndentation")
@Composable
fun PlaceListItem(place: PlaceClass, viewModel: PlacesViewModel, onItemClick: (PlaceClass) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {

            Divider(
                color = MaterialTheme.colorScheme.onBackground.copy(0.2f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(0.8f)
                        .align(Alignment.CenterVertically)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(end = 50.dp),
                            color = MaterialTheme.colorScheme.onBackground,
                            text = place.name,
                            style = typography1.h6
                        )
                    }

                    Row {
                        Text(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                            text = place.vicinity,
                            style = typography1.caption
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .size(10.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            color = MaterialTheme.colorScheme.primary,
                            text = place.rating.toString(),
                            style = typography1.caption
                        )

                        Spacer(
                            modifier = Modifier
                                .width(5.dp)
                        )

                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.height(15.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(8.dp)
                ) {
                    val newplace = PlaceClass(
                        place_id = place.place_id,
                        name = place.name,
                        vicinity = place.vicinity,
                        rating = place.rating,
                        icon = place.icon,
                    )

                    ClickableHeart(newplace, viewModel)

                    Icon(
                        imageVector = Icons.Filled.Map,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                onItemClick(place)
                            }
                            .padding(4.dp),
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}


@Composable
fun ClickableHeart(
    newplace: PlaceClass,
    viewModel: PlacesViewModel,

) {
    var isFavourite by remember{ mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val database = FavouritePlacesDatabase.getInstance(LocalContext.current)

    var Message: String
    var heartIcon : ImageVector






    if (isFavourite){
        heartIcon = Icons.Default.Favorite
        Message = "Place added to your favorites list"
    }
    else {
        heartIcon = Icons.Default.FavoriteBorder
        Message = "Place remove from your favorites list"
    }
    Icon(
        imageVector = heartIcon,
        contentDescription = null,
        modifier = Modifier
            .clickable {
                if (!isFavourite) {
                    coroutineScope.launch {
                        println(newplace.name)

                        database
                            .placeDao()
                            .insertPlace(newplace)
                        //viewModel.savePlace(newplace)
                        //  placeDao.insertPlace(newplace)
                    }
                } else {
                    coroutineScope.launch {

                        database
                            .placeDao()
                            .deletePlace(newplace)

                        //viewModel.deletePlace(newplace)
                        //   println(newplace.placeName)
                        //   placeDao.deletePlaceByNameAndCity(newplace.placeName, newplace.cityName)
                    }

                }
                isFavourite = !isFavourite
            }
            .padding(4.dp),
        tint = MaterialTheme.colorScheme.onBackground
    )

}




