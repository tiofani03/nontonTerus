package id.tiooooo.nontonterus.core.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import id.tiooooo.nontonterus.core.constant.Constant.PREF_NAME
import id.tiooooo.nontonterus.core.utils.AppLanguage
import id.tiooooo.nontonterus.core.utils.AppTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppDatastore(
    private val context: Context,
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREF_NAME)

    companion object {
        val ACTIVE_THEME = stringPreferencesKey("ACTIVE_THEME")
        val SELECTED_LANGUAGE = stringPreferencesKey("SELECTED_LANGUAGE")
    }

    suspend fun setActiveTheme(theme: String) {
        context.dataStore.edit { prefs ->
            prefs[ACTIVE_THEME] = theme
        }
    }

    suspend fun setSelectedLanguage(value: String) {
        context.dataStore.edit { prefs ->
            prefs[SELECTED_LANGUAGE] = value
        }
    }

    val activeTheme: Flow<String> = context.dataStore.data
        .map { prefs ->
            prefs[ACTIVE_THEME] ?: AppTheme.SYSTEM.label
        }

    val selectedLanguage: Flow<String> = context.dataStore.data
        .map { prefs ->
            prefs[SELECTED_LANGUAGE] ?: AppLanguage.ENGLISH.code
        }
}