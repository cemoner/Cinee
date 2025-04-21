package com.example.cinee.feature.auth.di

import com.example.cinee.feature.auth.data.repository.AuthenticationRepositoryImpl
import com.example.cinee.feature.auth.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationRepositoryModule {
    @Provides
    @Singleton
    fun provideAuthenticationRepository(firebaseAuth: FirebaseAuth): AuthenticationRepository = AuthenticationRepositoryImpl(firebaseAuth)
}