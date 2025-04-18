package com.example.cinee.feature.home.presentation.composable

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinee.feature.home.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    onMovieClick: (movieId:Int) -> Unit,
){
    val viewModel:HomeViewModel = hiltViewModel<HomeViewModel>()

    HomeContent()
}


@Composable
fun HomeContent(){

}