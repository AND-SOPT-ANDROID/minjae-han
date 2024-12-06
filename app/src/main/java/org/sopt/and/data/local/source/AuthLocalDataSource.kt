package org.sopt.and.data.local.source

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val Context.dataStore by preferencesDataStore(name = "auth")
    private val tokenKey = stringPreferencesKey("token")

    suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    fun getToken(): Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[tokenKey]
    }

    suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(tokenKey)
        }
    }
}
