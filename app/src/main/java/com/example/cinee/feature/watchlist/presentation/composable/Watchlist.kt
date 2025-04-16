package com.example.cinee.feature.watchlist.presentation.composable

import androidx.compose.runtime.Composable
import com.example.cinee.component.text.HeaderText

@Composable
fun WatchlistScreen(
    onMovieClick: (movieId:Int) -> Unit,
){
    HeaderText("Watchlist")
}


@Composable
fun WatchlistContent(){

}