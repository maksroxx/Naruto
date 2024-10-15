package com.example.borutoapp.presentation.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.borutoapp.util.Constants.BASE_URL
import com.example.borutoapp.util.PaletteGenerator.convertImageUrlToBitmap
import com.example.borutoapp.util.PaletteGenerator.extractColorFromBitmap
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailsScreen(
    navController: NavHostController,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val selectedHero by detailViewModel.selectedHero.collectAsState()
    val colorPalette by detailViewModel.colorPalette

    if (colorPalette.isNotEmpty()) {
        DetailContent(
            navController = navController,
            selectedHero = selectedHero,
            colors = colorPalette
        )
    } else {
        detailViewModel.generateColorPalette()
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        detailViewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.GenerateColorPalette -> {
                    val bitmap = convertImageUrlToBitmap(
                        imageUrl = "$BASE_URL${selectedHero?.image}",
                        context = context
                    )
                    if (bitmap != null) {
                        detailViewModel.setColorPalette(
                            colors = extractColorFromBitmap(
                                bitmap = bitmap
                            )
                        )
                    }
                }
            }
        }
    }
}