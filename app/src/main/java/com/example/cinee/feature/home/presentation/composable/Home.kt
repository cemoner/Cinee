package com.example.cinee.feature.home.presentation.composable

import androidx.compose.runtime.Composable
import com.example.cinee.component.text.BodyText
import com.example.cinee.component.text.HeaderText

@Composable
fun HomeScreen(
    onMovieClick: (movieId:Int) -> Unit,
){
    BodyText("Home")
}


@Composable
fun HomeContent(){

}