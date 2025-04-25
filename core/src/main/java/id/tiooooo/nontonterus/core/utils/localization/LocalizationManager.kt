package id.tiooooo.nontonterus.core.utils.localization

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import id.tiooooo.nontonterus.core.local.datastore.AppDatastore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.util.Locale

class LocalizationManager(private val context: Context) {
    private var localizedStrings: Map<String, String> = emptyMap()

    suspend fun loadLanguage(languageCode: String) {
        withContext(Dispatchers.IO) {
            try {
                val fileName = "strings_${languageCode.lowercase(Locale.ROOT)}.json"
                val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
                val jsonObject = JSONObject(json)

                val map = mutableMapOf<String, String>()
                jsonObject.keys().forEach { key ->
                    map[key] = jsonObject.getString(key)
                }

                localizedStrings = map
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun getString(key: String, vararg args: Any?): String {
        val rawString = localizedStrings[key] ?: key
        return try {
            rawString.replace("%s", args.firstOrNull()?.toString() ?: "")
        } catch (e: Exception) {
            rawString
        }
    }
}


class LocalizationProvider(private val manager: LocalizationManager) {
    fun string(key: String, vararg args: Any?): String = manager.getString(key, *args)

}

private val LocalStrings = staticCompositionLocalOf<LocalizationProvider> {
    error("LocalizationProvider not initialized")
}

@Composable
fun ProvideLocalization(
    provider: LocalizationProvider,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalStrings provides provider) {
        content()
    }
}

@Composable
fun stringRes(key: String, vararg args: Any?): String {
    return LocalStrings.current.string(key, *args)
}

@Composable
fun rememberLocalization(appDatastore: AppDatastore): LocalizationProvider {
    val context = LocalContext.current
    val languageCode by appDatastore.selectedLanguage
        .map { it.ifEmpty { "en" } }
        .collectAsState(initial = "en")

    val localizationManager = remember { LocalizationManager(context) }

    return produceState(
        initialValue = LocalizationProvider(localizationManager),
        key1 = languageCode
    ) {
        localizationManager.loadLanguage(languageCode)
        value = LocalizationProvider(localizationManager)
    }.value
}
