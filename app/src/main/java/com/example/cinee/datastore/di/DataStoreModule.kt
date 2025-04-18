package com.example.cinee.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import com.example.cinee.datastore.model.UserAccount
import com.example.cinee.datastore.serializer.UserAccountSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import androidx. datastore. dataStoreFile
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun providesAccountDataStore(
        @ApplicationContext context: Context,
    ): DataStore<UserAccount> =
         DataStoreFactory.create(
            serializer = UserAccountSerializer,
            produceFile = { context.dataStoreFile("user_account.pb") }
    )
}