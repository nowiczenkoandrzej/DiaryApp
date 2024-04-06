package com.an.diaryapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.an.diaryapp.feature_settings.data.AppSettingsSerializer
import com.an.diaryapp.feature_settings.domain.model.AppSettings
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


private const val USER_PREFERENCES_NAME = "user_preferences"
const val DATA_STORE_FILE_NAME = "user_pref.pb"
@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {

    @Singleton
    @Provides
    fun provideProtoDataStore(
        @ApplicationContext app: Context
    ): DataStore<AppSettings> {
        return DataStoreFactory.create(
            serializer = AppSettingsSerializer,
            produceFile = {
                app.dataStoreFile(DATA_STORE_FILE_NAME)
            },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }
}
