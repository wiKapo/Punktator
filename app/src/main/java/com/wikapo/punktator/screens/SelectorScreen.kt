package com.wikapo.punktator.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wikapo.punktator.R
import com.wikapo.punktator.ui.CircularSlider

@Composable
fun SelectorScreen() {
    val context = LocalContext.current
    val orientation = LocalConfiguration.current.orientation

    Column(modifier = Modifier.fillMaxSize()) {
        Text(stringResource(id = R.string.add_player))
        Text("+ <- opens dialog with name and color picker")
        Text("LIST OF PLAYERS")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {  }) {
                Text(stringResource(id = R.string.create))
            }
        }
        CircularSlider()
    }
}