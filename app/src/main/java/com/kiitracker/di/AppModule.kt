package com.kiitracker.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kiitracker.R
import com.kiitracker.data.auth.AuthHandler
import com.kiitracker.data.db.FireStoreDB
import com.kiitracker.data.repository.UserRepositoryImpl
import com.kiitracker.domain.interfaces.Auth
import com.kiitracker.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
    fun providesUserRepository(db: FireStoreDB): UserRepository {
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
    fun providesSignInWithGoogleOption(@ApplicationContext context: Context) = GetSignInWithGoogleOption
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
}