package com.example.inzproject.screens
import androidx.compose.material3.IconButton
//import androidx.compose.material3.icons.filled.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.example.inzproject.PlacesToVisit.*
import com.example.inzproject.PlacesToVisit.ROOM.FavouritePlacesDatabase

import com.example.inzproject.WeatherForecast.presentation.ui.theme.DeepBlue
import com.example.inzproject.viewmodels.PlacesViewModel
import com.google.android.material.progressindicator.CircularProgressIndicator
import kotlinx.coroutines.launch

//import kotlin.coroutines.jvm.internal.CompletedContinuation.context


@Composable
fun LikeToVisitScreen(viewModel: PlacesViewModel = hiltViewModel()) {
    var textState by remember { mutableStateOf("") }
    var switchState by remember { mutableStateOf(false) }
    var PlacesList: List<PlaceClass>?
    var isFilterDialogVisible by remember { mutableStateOf(false) }


    val context = LocalContext.current





if(viewModel.state.PlaceInfo==null && viewModel.state.error==null && textState == "") {
    LaunchedEffect(key1 = Unit) {
        var res = viewModel.getPlacesAsync(context)

    }
}

//    LaunchedEffect(key1 = Unit) {
//        // Korutyna dla Compose
//        println("kupa")
//        val response = viewModel.getPlacesAsync()
//
//        Log.d("ResponseDebug", response.toString());
//        if (response.isSuccessful) {
//            val placeresponse: PlacesResponse? = response.body()
//       PlacesList = placeresponse?.results
//            placeresponse?.results?.forEach { placeInfo ->
//                println("Place ID: ${placeInfo.place_id}")
//                println("Name: ${placeInfo.name}")
//                println("Rating: ${placeInfo.rating ?: "N/A"}")
//                println("Opening Hours: ${placeInfo.opening_hours?.open_now ?: "N/A"}")
////                println("Type: ${placeInfo.type.joinToString(", ")}")
//                println("Vicinity: ${placeInfo.vicinity}")
//                println("Icon Link: ${placeInfo.icon}")
//                println("------------------------------")
//            }
//        } else {
//            println("Failed to retrieve data. Status code: ${response.code()}")
//        }
//    }


 //viewModel.loadPlaces()

    //println(viewMode)
    // Kolor różowego tła
    val pinkBackgroundColor = Color(0xFFFFB6C1)

    // Kolor czerwony
    val ButtonColors = Color(0xFFB51CEC)

    // Kolor ciemnego różu
    val darkPinkColor = Color(0xFFE0B9EE)

//    viewModel.loadPlaces()
//    val places = remember { DataProvider.placeList }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(darkPinkColor)
        ) {
            // TopAppBar z przyciskiem kalendarza, polem tekstowym i przyciskiem wyszukiwania
            TopAppBar(

                backgroundColor = Color(0xFF821FFC),
                elevation = 0.dp,
                contentColor = Color.White,
                modifier = Modifier.height(130.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {


                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        // Przycisk z ikoną kalendarza

                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(pinkBackgroundColor, shape = CircleShape),

                            contentAlignment = Alignment.Center
                        ) {

                           var Con= LocalContext

                            IconButton(


                                onClick = {

                                    println(viewModel.state.PlaceInfo)
                                    // viewsavePlaces(places,viewModel)
                                    isFilterDialogVisible = true
                                    // Tutaj możesz umieścić kod, który zostanie wykonany po zamknięciu okna dialogowego
       println(viewModel.LocalizationCoordinates)
                                    /* Akcja przycisku kalendarza */
                                    println("czy twoje")
                                    println(viewModel.currentLocation)
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.FilterAlt,
                                    contentDescription = "Przycisk filtrów",
                                    tint = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.weight(0.8f))

                        // Pole tekstowe
                        TextInputDemo(textState) { newTextInput ->
                            textState = newTextInput
                        }

                        Spacer(modifier = Modifier.weight(0.8f))

                        // Przycisk z ikoną wyszukiwania
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(pinkBackgroundColor, shape = CircleShape),

                            contentAlignment = Alignment.Center
                        ) {



                            IconButton(
                                onClick = {
                                  //  viewModel.getlocationfromname("London")

                                         println("babuszka")
                                    println(textState)
                                        viewModel.setLocalization(textState)



                                        // Tutaj możesz użyć wartości coordinates, która zostanie zaktualizowana po załadowaniu danych

                                        // Następnie możesz kontynuować z funkcją getPlacesAsync, itd.


                                        viewModel.getPlacesAsync(context)




                                    //    viewModel.getlocationfromname("London")

                                       // viewModel.getPlacesAsync()


                                    //              viewModel.makeApiRequest("-33.8670522%2C151.1957362",1500,"restaurant")
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Wyszukaj",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        // Leading icon
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            modifier = Modifier.clickable { /* Obsługa kliknięcia */ }
                        )

                        // Switch
                        Switch(
                            checked = switchState,
                            onCheckedChange = { checked ->
                                switchState = checked
                            },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        // Trailing icon
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            modifier = Modifier.clickable { /* Obsługa kliknięcia */ }
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(5.dp))
            if (isFilterDialogVisible) {
                OpenFilterDialog(
                   isDialogVisible = isFilterDialogVisible,

                    onClose = { isFilterDialogVisible = false }
                    ,viewModel


                )

            }
            PlacesCard(state = viewModel.state, backgroundColor = DeepBlue,viewModel = viewModel)

        }
                if (viewModel.state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                viewModel.state.error?.let { error ->
                    androidx.compose.material.Text(
                        text = error,
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        // modifier = Modifier.align(Alignment.Center)
                    )


                }

            }
        }


@Composable
fun OpenFilterDialog(isDialogVisible: Boolean, onClose: () -> Unit, placesViewModel: PlacesViewModel) {
    if (isDialogVisible) {
        AlertDialog(
            onDismissRequest = onClose,
            title = {
                Text("Filtrowanie")
            },
            text = {
                FiltersForm(placesViewModel =placesViewModel

                )
            },
            confirmButton = {
                Button(
                    onClick = onClose
                ) {
                    Text("Zamknij")
                }
            }
        )
    }
}

@Composable
fun FiltersForm(placesViewModel: PlacesViewModel) {
    var placeType by remember { mutableStateOf(placesViewModel.filterPlaceType) }
    var numericRange by remember { mutableStateOf(placesViewModel.filterRadius) }
    var minRating by remember { mutableStateOf(placesViewModel.filterMinRating) }
    var maxRating by remember { mutableStateOf(placesViewModel.filterMaxRating) }
    var keyword by remember { mutableStateOf(TextFieldValue(placesViewModel.filterKeyword)) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Pole tekstowe na wpisanie typu miejsca
        TextField(
            value = placeType,
            onValueChange = {
                placeType = it
            },
            label = { Text("Type of place") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Pole do wprowadzenia zasięgu liczbowego
        OutlinedTextField(
            value = numericRange.toString(),
            onValueChange = {
                numericRange = it.toIntOrNull() ?: 1
            },
            label = { Text("Numeric Range") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            // Pole tekstowe na wpisanie minimalnej oceny
            OutlinedTextField(
                value = minRating.toString(),
                onValueChange = {
                    if (it.isNotBlank()) {
                        minRating = it.toDoubleOrNull()?.coerceIn(0.0, 5.0) ?: 0.0
                    }

                },
                label = { Text("Min Rating") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 4.dp)
            )

            // Pole tekstowe na wpisanie maksymalnej oceny
            OutlinedTextField(
                value = maxRating.toString(),
                onValueChange = {
                    if (it.isNotBlank()) {
                        maxRating = it.toDoubleOrNull()?.coerceIn(0.0, 5.0) ?: 5.0
                    }
                                //   maxRating = it.toDoubleOrNull()?: 5.0

                },
                label = { Text("Max Rating") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp)
            )
        }

        // Pole tekstowe na wpisanie tekstu
        TextField(
            value = keyword,
            onValueChange = {
                keyword = it
            },
            label = { Text("Keyword") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Przycisk do zastosowania filtrów
        Button(
            onClick = {
                placesViewModel.setFilters(keyword = keyword, minRating = minRating, maxRating = maxRating, radius = numericRange, placeType = placeType)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Apply Filters")
        }
    }
}
@Composable
fun RangeInputField(range: IntRange, onRangeChange: (IntRange) -> Unit) {
    var startValue by remember { mutableStateOf(range.first.toString()) }
    var endValue by remember { mutableStateOf(range.last.toString()) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        // Pole tekstowe na początek zakresu
        OutlinedTextField(
            value = startValue,
            onValueChange = {
               startValue = it
                val start = startValue.toIntOrNull() ?: range.first
                val end = endValue.toIntOrNull() ?: range.last
                onRangeChange(start..end)
            },
            label = { Text("Start Value") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
        )

        // Pole tekstowe na koniec zakresu
        OutlinedTextField(
            value = endValue,
            onValueChange = {
                endValue = it
                val start = startValue.toIntOrNull() ?: range.first
                val end = endValue.toIntOrNull() ?: range.last
                onRangeChange(start..end)
            },
            label = { Text("End Value") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        )
    }


}




@Composable
fun NumberSelector(selectedNumber: Double, onNumberSelected: (Double) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        // Dropdown z wyborem liczby od 1 do 5
        DropdownMenu(
            expanded = false,
            onDismissRequest = { /* Handle close request */ },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            for (i in 1..5) {
                DropdownMenuItem(
                    onClick = {
                        onNumberSelected(i.toDouble())
                    }
                ) {
                    Text(i.toString())
                }
            }
        }

        // Przycisk do rozwinięcia dropdown
        Button(
            onClick = { /* Handle dropdown click */ },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text("Select Rating: $selectedNumber")
        }
    }
}


//@Composable
//fun PlaceListItem(place: PlaceClass) {
//    Card(
//        modifier = Modifier
//            .padding(horizontal = 8.dp, vertical = 8.dp)
//            .fillMaxWidth(),
//        elevation = 2.dp,
//        backgroundColor = graySurface,
//        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
//    ) {
//        Row(Modifier.clickable { }) {
//          //  PlaceImage(place)
//            Column(
//                 modifier = Modifier
//                     .padding(8.dp)
//                     .fillMaxWidth()
//                     .align(Alignment.CenterVertically)
//            ) {
//                Text(text = place.name, style = typography1.h6)
//
//              Row() {
//                  Text(text = place.vicinity, style = typography1.caption)
//                  Spacer(modifier = Modifier.size(10.dp))
//
//                 // var newplace=PlaceClass(type = place.type, image = 2, placeName = place.placeName, cityName = place.cityName, availabilityHours = place.availabilityHours, googleNote = place.googleNote)
//
//                 //   ClickableHeart(newplace)}
//
//
//                Row() {
//                    Text(text = place.rating.toString(), style = typography1.caption)
//                    Icon(
//                        imageVector = Icons.Default.Star,
//                        contentDescription = null,
//                    modifier = Modifier.height(15 .dp))
//                }
//            }
//        }
//    }
//
//}
@Composable
fun OpenFilterDialog12() {
    var isDialogVisible by remember { mutableStateOf(true) }

    if (isDialogVisible) {
        AlertDialog(
            onDismissRequest = {
                isDialogVisible = false
            },
            title = {
                Text("Filtrowanie")
            },
            text = {
                // Tutaj dodaj swoje filtry
            },
            confirmButton = {
                Button(
                    onClick = {
                        isDialogVisible = false
                    }
                ) {
                    Text("Zamknij")
                }
            }
        )
    }
}

@Composable
fun OpenFilterDialog() {
    val context = LocalContext.current
    val density = LocalDensity.current.density

    val dialogLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        // Tutaj możesz obsłużyć wynik z okna dialogowego
    }

    var isDialogVisible by remember { mutableStateOf(true) }

    DisposableEffect(isDialogVisible) {
        onDispose {
            // Tutaj możesz dodać kod, który zostanie wykonany po zamknięciu okna dialogowego
        }
    }

    if (isDialogVisible) {
        Dialog(
            onDismissRequest = { isDialogVisible = false },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            // Zawartość okna dialogowego z filtrami
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                // Tutaj dodaj swoje filtry

                // Przycisk zamknięcia okna dialogowego
                Button(
                    onClick = { isDialogVisible = false },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.End)
                ) {
                    Text("Zamknij")
                }
            }
        }
    }
}

@Composable
 fun PlaceImage(place: Place) {
    Image(
        painter = painterResource(id = place.image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .padding(8.dp)
            .size(84.dp)
            .clip(RoundedCornerShape(corner = CornerSize(16.dp)))
    )
}

//@Preview
//@Composable
//fun PreviewPlaceItem() {
//    val place = DataProvider.place
//
//    PlaceListItem(place = place, navigateToProfile = {}, viewModel = viewModel)
//}

//
//fun Icon(imageVector: Int, contentDescription: String, tint: Color) {
//
//}
//
//fun savePlaces(places: List<Place>,viewModel: PlacesViewModel) {
//
//    viewModel.savePlaces(places)
//}
//@Composable
//fun ClickableHeart(newplace:ClassPlace){
//    var isFavourite by remember{ mutableStateOf(false) }
//    val context = LocalContext.current
//        val database = FavouritePlacesDatabase.getDatabase(context)
//    val placeDao = database.placeDao()
//    val coroutineScope = rememberCoroutineScope()
//
//    var Message:String
//var heartIcon : ImageVector
//
//
//        if (isFavourite){
//      heartIcon = Icons.Default.Favorite
//        Message = "Place added to your favorites list"
//    }
//    else {
//   heartIcon = Icons.Default.FavoriteBorder
//Message = "Place remove from your favorites list"
//    }
//    Icon(imageVector = heartIcon,
//    contentDescription = null,
//    modifier = Modifier
//        .clickable {
//            if (isFavourite) {
//                coroutineScope.launch {
//                    println(newplace.placeName)
//                    placeDao.insertPlace(newplace)
//                }
//            } else {
//                coroutineScope.launch {
//                    println(newplace.placeName)
//                    placeDao.deletePlaceById(newplace.id)
//                }
//
//            }
//            isFavourite = !isFavourite
//
//        }
//        .padding(4.dp)
//    )
//
//      }


