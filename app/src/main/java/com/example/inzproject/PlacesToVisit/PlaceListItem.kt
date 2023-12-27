package com.example.inzproject.PlacesToVisit.ui


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


import com.example.inzproject.PlacesToVisit.Place
import com.example.inzproject.ui.theme.graySurface
import com.example.inzproject.ui.theme.typography1
import com.example.inzproject.viewmodels.PlacesViewModel

//import com.example.inzproject.PlacesToVisit.ui.theme

//import com.example.inzproject.PlacesToVisit.Place

@Composable
fun PlaceListItem(place: Place, navigateToProfile: (Place) -> Unit, viewModel: PlacesViewModel) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = graySurface,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Row(Modifier.clickable { navigateToProfile(place) }) {
            PlaceImage(place)
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = place.placeName, style = typography1.h6)
                Text(text = place.cityName, style = typography1.caption)
                Text(text = place.googleNote.toString(), style = typography1.caption)
            }
        }
    }
}

@Composable
private fun PlaceImage(place: Place) {
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
//fun PreviewPuppyItem() {
//    val place = DataProvider.place
//   PlaceListItem(place = place, navigateToProfile = {})
//}

