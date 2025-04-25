package id.tiooooo.nontonterus.pages.home.setting

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import id.tiooooo.nontonterus.core.ui.theme.MEDIUM_PADDING
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING
import id.tiooooo.nontonterus.core.utils.AppLanguage
import id.tiooooo.nontonterus.core.utils.AppTheme
import id.tiooooo.nontonterus.core.utils.localization.stringRes

@Composable
fun SettingsDialog(
    onDismiss: () -> Unit,
    currentLanguage: AppLanguage,
    currentTheme: AppTheme,
    onApplied: (AppLanguage, AppTheme) -> Unit,
) {
    var selectedTheme by remember { mutableStateOf(currentTheme) }
    var selectedLanguage by remember { mutableStateOf(currentLanguage) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringRes("setting_title"))
        },
        text = {
            Column {
                Text(text = stringRes("setting_language"))
                LanguageRadioButton(selectedLanguage, onLanguageChange = {
                    selectedLanguage = it
                })
                Spacer(modifier = Modifier.height(MEDIUM_PADDING))
                Text(text = stringRes("setting_theme"))
                ThemeRadioButton(selectedTheme, onThemeChange = {
                    selectedTheme = it
                })
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onApplied.invoke(selectedLanguage, selectedTheme)
                onDismiss()
            }) {
                Text(stringRes("setting_apply"))
            }
        }
    )
}

@Composable
fun LanguageRadioButton(currentLanguage: AppLanguage, onLanguageChange: (AppLanguage) -> Unit) {
    Column {
        AppLanguage.entries.forEach { language ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onLanguageChange(language)
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = currentLanguage == language,
                    onClick = { onLanguageChange(language) }
                )
                Text(text = language.label, modifier = Modifier.padding(start = SMALL_PADDING))
            }
        }
    }
}

@Composable
fun ThemeRadioButton(currentTheme: AppTheme, onThemeChange: (AppTheme) -> Unit) {
    Column {
        AppTheme.entries.forEach { theme ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onThemeChange(theme)
                    },
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = currentTheme == theme,
                    onClick = { onThemeChange(theme) }
                )
                Text(
                    text = stringRes(theme.label),
                    modifier = Modifier.padding(start = SMALL_PADDING)
                )
            }
        }
    }
}

