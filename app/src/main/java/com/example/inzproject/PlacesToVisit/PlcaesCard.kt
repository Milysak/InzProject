package com.example.inzproject.PlacesToVisit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.inzproject.PlacesToVisit.ROOM.FavouritePlacesDatabase
//import com.example.inzproject.PlacesToVisit.ROOM.PlaceClassDatabase
//import com.example.inzproject.PlacesToVisit.ROOM.FavouritePlacesDatabase

import com.example.inzproject.PlacesToVisit.Repository.PlaceState
import com.example.inzproject.WeatherForecast.presentation.WeatherState

import com.example.inzproject.ui.theme.beige
import com.example.inzproject.ui.theme.beigebrown
import com.example.inzproject.ui.theme.graySurface
import com.example.inzproject.ui.theme.typography1
import com.example.inzproject.viewmodels.PlacesViewModel
import kotlinx.coroutines.launch

@Composable
fun PlacesCard(
    state: PlaceState,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    viewModel: PlacesViewModel

) {

    println(state.PlaceInfo?.results)
    state.PlaceInfo?.results.let { data ->
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .fillMaxWidth(),
            elevation = 2.dp,
            backgroundColor = beigebrown,
            shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            ) {
                items(items = data ?: emptyList()) { place ->

                    if (place.rating != null && place.rating >= viewModel.filterMinRating && place.rating <= viewModel.filterMaxRating) {
                        PlaceListItem(place = place,viewModel)
                    }
                }

            }
        }
    }
}


@Composable
fun PlaceListItem(place: PlaceClass,viewModel: PlacesViewModel) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = beige,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Row(Modifier.clickable { /* Handle click if needed */ }) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = place.name, style = typography1.h6)

                Row {
                    Text(text = place.vicinity, style = typography1.caption)}
                    Spacer(modifier = Modifier.size(10.dp))

                    Row {
                        Text(text = place.rating.toString(), style = typography1.caption)
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            modifier = Modifier.height(15.dp)

                        )
                        Spacer(modifier = Modifier.size(20.dp))
                         var newplace=PlaceClass(place_id = place.place_id, name = place.name, vicinity = place.vicinity, rating = place.rating, icon = place.icon)

                           ClickableHeart(newplace,viewModel)}
                    }
                }
            }
        }


@Composable
fun ClickableHeart(newplace:PlaceClass,viewModel: PlacesViewModel){
    var isFavourite by remember{ mutableStateOf(false) }
    val context = LocalContext.current
    val database = FavouritePlacesDatabase.getDatabase(context)
    val placeDao = database?.placeDao()
    val coroutineScope = rememberCoroutineScope()

    var Message:String
    var heartIcon : ImageVector


    if (isFavourite){
        heartIcon = Icons.Default.Favorite
        Message = "Place added to your favorites list"
    }
    else {
        heartIcon = Icons.Default.FavoriteBorder
        Message = "Place remove from your favorites list"
    }
    Icon(imageVector = heartIcon,
        contentDescription = null,
        modifier = Modifier
            .clickable {
                if (isFavourite) {
                    coroutineScope.launch {
                        println(newplace.name)

                        viewModel.savePlace(newplace)
                      //  placeDao.insertPlace(newplace)
                    }
                } else {
                    coroutineScope.launch {
                        //   println(newplace.placeName)
                        //   placeDao.deletePlaceByNameAndCity(newplace.placeName, newplace.cityName)
                    }

                }
                isFavourite = !isFavourite

            }
            .padding(4.dp)
    )

}



