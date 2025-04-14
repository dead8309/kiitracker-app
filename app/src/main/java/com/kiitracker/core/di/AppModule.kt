package com.kiitracker.core.di

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.Firebase
import com.kiitracker.R
import com.kiitracker.core.data.datastore.UserPreferencesSerializer
import com.kiitracker.core.datastore.proto.UserPreferences
import com.kiitracker.data.auth.AuthHandler
import com.kiitracker.data.remote.UserRemoteDataSource
import com.kiitracker.data.repository.UserRepositoryImpl
import com.kiitracker.domain.interfaces.Auth
import com.kiitracker.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesFireBaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun providesFirebaseFirestore() = Firebase.firestore

    @Provides
    @Singleton
    fun providesUserRepository(db: UserRemoteDataSource): UserRepository {
        return UserRepositoryImpl(db)
    }

    @Provides
    @Singleton
    fun providesGoogleIdOption(@ApplicationContext context: Context) = GetGoogleIdOption
        .Builder()
        .setServerClientId(context.getString(R.string.web_client_id))
        .setFilterByAuthorizedAccounts(false)
        .build()

    @Provides
    @Singleton
    fun providesSignInWithGoogleOption(@ApplicationContext context: Context) =
        GetSignInWithGoogleOption
            .Builder(context.getString(R.string.web_client_id))
            .build()

    @Provides
    @Singleton
    fun provideCredentialManager(@ApplicationContext context: Context): CredentialManager {
        return CredentialManager.create(context)
    }

    @Provides
    @Singleton
    fun providesAuthHandler(
        googleIdOption: GetGoogleIdOption,
        getSignInWithGoogleOption: GetSignInWithGoogleOption,
        credentialManager: CredentialManager,
        auth: FirebaseAuth
    ): Auth {
        return AuthHandler(
            googleIdOption,
            getSignInWithGoogleOption,
            credentialManager,
            auth
        )
    }

    @Provides
    @Singleton
    fun providesUserPreferencesDataStore(
        @ApplicationContext context: Context,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        @ApplicationScope scope: CoroutineScope,
        userPreferencesSerializer: UserPreferencesSerializer
    ): DataStore<UserPreferences> =
        DataStoreFactory.create(
            serializer = userPreferencesSerializer,
            scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
            migrations = listOf()
        ) {
            context.dataStoreFile("user_prefs.pb")
        }
}