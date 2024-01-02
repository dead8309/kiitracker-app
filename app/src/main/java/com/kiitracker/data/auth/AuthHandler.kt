package com.kiitracker.data.auth


import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.ClearCredentialException
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kiitracker.domain.interfaces.Auth
import com.kiitracker.domain.models.SignInResult
import com.kiitracker.domain.models.UserData
import com.kiitracker.domain.models.toUserData
import kotlinx.coroutines.tasks.await

const val TAG = "AuthHandler"

class AuthHandler(
    private val googleIdOption: GetGoogleIdOption,
    private val signInWithGoogleOption: GetSignInWithGoogleOption,
    private val credentialManager: CredentialManager,
    private val auth: FirebaseAuth
) : Auth {

    private var flagUseGoogleSignIn = false

    override val currentUser: UserData?
        get() = auth.currentUser.toUserData()

    override suspend fun login(context: Context): SignInResult {
        val request: GetCredentialRequest = GetCredentialRequest.Builder()
            .addCredentialOption(
                if (flagUseGoogleSignIn) {
                    // Using Google Sign Legacy Api if onetap has been temporarily blocked
                    signInWithGoogleOption
                } else {
                    // Using OneTap Sign In Api
                    googleIdOption
                }
            )
            .build()

        return try {
            val result = credentialManager.getCredential(
                request = request,
                context = context,
            )
            handleSignIn(result)
        } catch (e: GetCredentialException) {
            if (e.message!!.contains("Caller has been temporarily blocked")) {
                /**
                 * This is a known issue with the CredentialManager API. It is being
                 * temporarily blocked by Google. The workaround is to use the legacy
                 * sign in API.
                 *
                 * https://issuetracker.google.com/issues/317345961
                 */
                Log.e(TAG, "Temporarily blocked, showing legacy sign in")
                flagUseGoogleSignIn = true
                login(context)
            } else {
                Log.e(TAG, "Error getting credential", e)
                SignInResult(
                    data = null,
                    errorMessage = e.message
                )
            }
        }
    }

    override suspend fun logout() {
        try {
            auth.signOut()
            // TODO: Refactor this once the issue is fixed
            // https://issuetracker.google.com/issues/314926460
            // val request = ClearCredentialStateRequest()
            // credentialManager.clearCredentialState(request)
        } catch (e: ClearCredentialException) {
            Log.e(TAG, "Error clearing credential", e)
        }
    }

    private suspend fun handleSignIn(result: GetCredentialResponse): SignInResult {
        return when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken
                        Log.d(TAG, "Received google id token: $idToken")
                        loginWithFirebase(idToken)
                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Received an invalid google id token response", e)
                        SignInResult(
                            data = null,
                            errorMessage = e.message
                        )
                    }
                } else {
                    Log.e(TAG, "Unexpected type of credential")
                    SignInResult(
                        data = null,
                        errorMessage = "Unexpected type of credential"
                    )
                }
            }

            else -> {
                Log.e(TAG, "Unexpected type of credential")
                SignInResult(
                    data = null,
                    errorMessage = "Unexpected type of credential"
                )
            }
        }
    }

    private suspend fun loginWithFirebase(idToken: String): SignInResult {
        val googleCredentials = GoogleAuthProvider.getCredential(idToken, null)
        val user = auth.signInWithCredential(googleCredentials).await().user
        Log.d(TAG,"Successfully signed in user ${user?.uid}")
        return SignInResult(
            data = user?.run {
                UserData(
                    uid = uid,
                    email = email ?: "",
                    photoUrl = photoUrl?.toString(),
                    username = displayName
                )
            },
            errorMessage = null
        )
    }
}