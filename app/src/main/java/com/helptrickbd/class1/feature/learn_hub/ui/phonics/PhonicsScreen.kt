package com.helptrickbd.class1.feature.learn_hub.ui.phonics

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AutoStories
import androidx.compose.material.icons.rounded.Extension
import androidx.compose.material.icons.rounded.GridOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.helptrickbd.class1.R
import com.helptrickbd.class1.core.designsystem.components.StandardTopBar
import com.helptrickbd.class1.feature.learn_hub.ui.phonics.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhonicsScreen(
    viewModel: PhonicsViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var modeBackStack by rememberSaveable { mutableStateOf(listOf(uiState.screenMode.name)) }
    var tabHistory by rememberSaveable { mutableStateOf(listOf(uiState.selectedTab.name)) }

    DisposableEffect(Unit) {
        onDispose { viewModel.stopAudio() }
    }

    val onModeClick: (PhonicsScreenMode) -> Unit = { mode ->
        if (mode.name != modeBackStack.lastOrNull()) {
            modeBackStack = (modeBackStack - mode.name) + mode.name
            viewModel.onScreenModeSelected(mode)
        }
    }

    val onTabClick: (PhonicsTab) -> Unit = { tab ->
        if (tab.name != tabHistory.lastOrNull()) {
            tabHistory = (tabHistory - tab.name) + tab.name
            viewModel.onTabSelected(tab)
        }
    }

    val handleBack: () -> Unit = {
        when {
            uiState.detailItem != null -> viewModel.onDetailDismiss()
            modeBackStack.size > 1 -> {
                val nextModes = modeBackStack.dropLast(1)
                modeBackStack = nextModes
                viewModel.onScreenModeSelected(PhonicsScreenMode.valueOf(nextModes.last()))
            }
            tabHistory.size > 1 -> {
                val nextTabs = tabHistory.dropLast(1)
                tabHistory = nextTabs
                viewModel.onTabSelected(PhonicsTab.valueOf(nextTabs.last()))
            }
            else -> onBackClick()
        }
    }

    BackHandler(enabled = true, onBack = handleBack)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            StandardTopBar(
                title = stringResource(R.string.phonics_title),
                navigationIcon = Icons.AutoMirrored.Rounded.ArrowBack,
                onNavigationClick = handleBack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            PrimaryTabRow(
                selectedTabIndex = if (uiState.selectedTab == PhonicsTab.VOWELS) 0 else 1,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                Tab(
                    selected = uiState.selectedTab == PhonicsTab.VOWELS,
                    onClick = { onTabClick(PhonicsTab.VOWELS) },
                    text = { Text(stringResource(R.string.phonics_tab_vowels), fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = uiState.selectedTab == PhonicsTab.CONSONANTS,
                    onClick = { onTabClick(PhonicsTab.CONSONANTS) },
                    text = { Text(stringResource(R.string.phonics_tab_consonants), fontWeight = FontWeight.Bold) }
                )
            }

            SecondaryTabRow(
                selectedTabIndex = uiState.screenMode.ordinal,
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                contentColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 6.dp)
            ) {
                Tab(
                    selected = uiState.screenMode == PhonicsScreenMode.SOUNDBOARD,
                    onClick = { onModeClick(PhonicsScreenMode.SOUNDBOARD) },
                    icon = { Icon(Icons.Rounded.GridOn, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    text = { Text(stringResource(R.string.phonics_mode_soundboard), fontWeight = FontWeight.SemiBold) }
                )
                Tab(
                    selected = uiState.screenMode == PhonicsScreenMode.STORYBOOK,
                    onClick = { onModeClick(PhonicsScreenMode.STORYBOOK) },
                    icon = { Icon(Icons.Rounded.AutoStories, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    text = { Text(stringResource(R.string.phonics_mode_storybook), fontWeight = FontWeight.SemiBold) }
                )
                Tab(
                    selected = uiState.screenMode == PhonicsScreenMode.WORD_BUILDER,
                    onClick = { onModeClick(PhonicsScreenMode.WORD_BUILDER) },
                    icon = { Icon(Icons.Rounded.Extension, contentDescription = null, modifier = Modifier.size(18.dp)) },
                    text = { Text(stringResource(R.string.phonics_mode_word_builder), fontWeight = FontWeight.SemiBold) }
                )
            }

            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                when (uiState.screenMode) {
                    PhonicsScreenMode.SOUNDBOARD -> {
                        SoundboardGridView(
                            items = uiState.items,
                            displayMode = uiState.displayMode,
                            currentlyPlayingId = uiState.currentlyPlayingId,
                            onModeToggle = { viewModel.onModeToggle() },
                            onItemClick = { viewModel.onItemClick(it) }
                        )
                    }
                    PhonicsScreenMode.STORYBOOK -> {
                        PictureBookPagerScreen(
                            items = uiState.items,
                            audioPlayer = viewModel.audioPlayer,
                            currentlyPlayingId = uiState.currentlyPlayingId
                        )
                    }
                    PhonicsScreenMode.WORD_BUILDER -> {
                        WordBuilderLabScreen(
                            items = uiState.items,
                            audioPlayer = viewModel.audioPlayer
                        )
                    }
                }
            }
        }

        uiState.detailItem?.let { detail ->
            PhonicsDetailSheet(
                item = detail,
                isPlaying = uiState.currentlyPlayingId == detail.id,
                onReplay = { viewModel.onItemClick(detail) },
                onDismiss = { viewModel.onDetailDismiss() }
            )
        }
    }
}
