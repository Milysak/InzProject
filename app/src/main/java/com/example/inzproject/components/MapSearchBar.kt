package com.example.inzproject.components

import android.widget.ScrollView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSearchBar() {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var items = remember {
        mutableStateListOf("Gliwice")
    }

    Box(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.55f)
            .padding(horizontal = 7.dp)
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter),
            query = text,
            onQueryChange = { text = it },
            onSearch = {
                if(text !in items) {
                    items.add(0, text)
                }
                active = false
                text = ""
                       },
            active = active,
            onActiveChange = {
                active = it
            },
            placeholder = { Text("Wyszukaj miejsce...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                           if(active) {
                               Icon(
                                   modifier = Modifier
                                       .clickable {
                                           if(text.isNotEmpty()) {
                                               text = ""
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
            items.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 15.dp)
                        .clickable {
                            text = it
                        }
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 14.dp),
                        imageVector = Icons.Default.History,
                        contentDescription = "History Icon"
                    )
                    Text(text = it)
                }
            }
        }
    }
}