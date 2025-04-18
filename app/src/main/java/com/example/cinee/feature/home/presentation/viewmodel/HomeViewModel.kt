package com.example.cinee.feature.home.presentation.viewmodel

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import com.example.cinee.datastore.model.UserAccount
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val dataStore: DataStore<UserAccount>)
    :ViewModel()
{


}