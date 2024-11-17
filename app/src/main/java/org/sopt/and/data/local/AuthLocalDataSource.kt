package org.sopt.and.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "auth")

class AuthLocalDataSource(private val context: Context) {
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

    companion object {
        @Volatile
        private var instance: AuthLocalDataSource? = null

        fun getInstance(context: Context): AuthLocalDataSource {
            return instance ?: synchronized(this) {
                instance ?: AuthLocalDataSource(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }
}