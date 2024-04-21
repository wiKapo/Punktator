package com.wikapo.punktator

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.LocaleListCompat
import com.wikapo.punktator.screens.SelectorScreen
import com.wikapo.punktator.ui.theme.PunktatorTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PunktatorTheme {
                // A surface container using the 'background' color from the theme
                val configuration = LocalConfiguration.current
                Scaffold(topBar = {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        color = NavigationBarDefaults.containerColor,
                        tonalElevation = NavigationBarDefaults.Elevation
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextButton(
                                modifier = Modifier
                                    .width((configuration.screenWidthDp - 60).dp)
                                    .height(60.dp),
                                onClick = {}) {
                                Text(
                                    stringResource(id = R.string.app_name),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.contentColorFor(
                                        NavigationBarDefaults.containerColor
                                    )
                                )
                            }
                            TextButton(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(35.dp),
                                onClick = {
                                    AppCompatDelegate.setApplicationLocales(
                                        LocaleListCompat.forLanguageTags(
                                            if (AppCompatDelegate.getApplicationLocales()
                                                    .toLanguageTags() == "en"
                                            )
                                                "pl"
                                            else "en"
                                        )
                                    )
                                },
                                shape = CircleShape,
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Image(
                                    painter = painterResource(
                                        id = if (AppCompatDelegate.getApplicationLocales()
                                                .toLanguageTags() == "en"
                                        )
                                            R.drawable.flag_pl
                                        else R.drawable.flag_uk,
                                    ), contentDescription = null,
                                    contentScale = ContentScale.FillBounds
                                )
                            }
                        }
                    }
                }) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        SelectorScreen()
//                    CircularSlider()
                    }
                }
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {}
            }
        }
    }
}