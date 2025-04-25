package id.tiooooo.nontonterus.pages.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import id.tiooooo.nontonterus.R
import id.tiooooo.nontonterus.core.ui.base.BaseScaffold
import id.tiooooo.nontonterus.core.ui.component.AnimatedPreloader
import id.tiooooo.nontonterus.core.ui.theme.EXTRA_LARGE_PADDING
import id.tiooooo.nontonterus.core.ui.theme.MEDIUM_PADDING
import id.tiooooo.nontonterus.core.ui.theme.SMALL_PADDING
import id.tiooooo.nontonterus.core.ui.theme.textMedium12
import id.tiooooo.nontonterus.core.ui.theme.textMedium20
import id.tiooooo.nontonterus.core.utils.localization.stringRes
import id.tiooooo.nontonterus.pages.home.MovieHomeRoute

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    screenModel: SplashScreenModel
) {
    val navigator = LocalNavigator.currentOrThrow

    LaunchedEffect(Unit) {
        screenModel.dispatch(SplashIntent.NavigateToHome)
        screenModel.effect.collect { effect ->
            when (effect) {
                SplashEffect.NavigateToHome -> navigator.replaceAll(MovieHomeRoute())
            }
        }
    }

    BaseScaffold {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(bottom = MEDIUM_PADDING),
            ) {
                AnimatedPreloader(
                    modifier = Modifier
                        .size(225.dp)
                        .align(Alignment.CenterHorizontally),
                    animationRes = id.tiooooo.nontonterus.core.R.raw.lottie_loading,
                )
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(
                            vertical = SMALL_PADDING,
                            horizontal = EXTRA_LARGE_PADDING,
                        )
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.app_name),
                    style = textMedium20().copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                )
                Text(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(horizontal = EXTRA_LARGE_PADDING)
                        .align(Alignment.CenterHorizontally),
                    text = stringRes("splash_text_subtitle"),
                    style = textMedium12().copy(
                        fontWeight = FontWeight.Light,
                        color = Color.White,
                    )
                )
            }
        }
    }
}