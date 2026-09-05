package com.helptrickbd.class1.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.core.notification.domain.repository.NotificationRepository
import com.helptrickbd.class1.core.settings.domain.model.ThemeMode
import com.helptrickbd.class1.core.settings.domain.repository.SettingsRepository
import com.helptrickbd.class1.core.settings.domain.usecase.ClearCacheUseCase
import com.helptrickbd.class1.core.settings.domain.usecase.GetStorageInfoUseCase
import com.helptrickbd.class1.core.analytics.domain.AnalyticsTracker
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import com.helptrickbd.class1.feature.home.domain.model.LayoutMode
import com.helptrickbd.class1.feature.home.domain.repository.HomeRepository
import com.helptrickbd.class1.core.util.AppVersionProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import com.helptrickbd.class1.R
import com.helptrickbd.class1.core.util.UiText
import kotlinx.coroutines.FlowPreview

sealed interface HomeUiEvent {
    data class ShowToast(val message: UiText) : HomeUiEvent
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val getStorageInfoUseCase: GetStorageInfoUseCase,
    private val clearCacheUseCase: ClearCacheUseCase,
    private val settingsRepository: SettingsRepository,
    private val notificationRepository: NotificationRepository,
    private val analyticsTracker: AnalyticsTracker,
    private val versionProvider: AppVersionProvider
) : ViewModel() {

    private val _selectedCurriculum = MutableStateFlow(AppConfig.DEFAULT_CURRICULUM)
    val selectedCurriculum = _selectedCurriculum.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _uiEvent = Channel<HomeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _isUpdateDialogDismissed = MutableStateFlow(false)

    // SSOT for Settings - combining flows properly
    val settingsState: StateFlow<HomeSettingsState> = combine(
        getStorageInfoUseCase(),
        settingsRepository.getThemeMode(),
        settingsRepository.getLayoutMode(),
        notificationRepository.getUnreadCount()
    ) { storage, theme, layout, unreadCount ->
        HomeSettingsState(storage, theme, layout, unreadCount)
    }.distinctUntilChanged()
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeSettingsState()
    )

    val showUpdateDialog: StateFlow<Boolean> = combine(
        repository.getMinAppVersion(),
        _isUpdateDialogDismissed
    ) { minAppVersion, isDismissed ->
        minAppVersion > versionProvider.currentVersionCode && !isDismissed
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = false
    )

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HomeUiState> = combine(
        _selectedCurriculum,
        _searchQuery
            .debounce { query -> if (query.isEmpty()) 0L else AppConfig.SEARCH_DEBOUNCE_MS }
            .distinctUntilChanged()
    ) { curriculum, query ->
        Pair(curriculum, query)
    }.flatMapLatest { (curriculum, query) ->
        combine(
            repository.getBooks(curriculum),
            repository.getResumeBook(),
            repository.searchBooksAndChapters(query, curriculum),
            repository.getCloudNotice()
        ) { books, resumeBook, searchResults, cloudNotice ->
            when {
                books.isEmpty() && query.isEmpty() -> {
                    HomeUiState.Empty(UiText.StringResource(R.string.error_no_books_found))
                }
                query.isNotEmpty() && searchResults.isEmpty() -> {
                    HomeUiState.Empty(UiText.StringResource(R.string.error_no_search_results))
                }
                else -> {
                    HomeUiState.Success(
                        userName = AppConfig.DEFAULT_USER_NAME,
                        selectedCurriculum = curriculum,
                        resumeBook = resumeBook,
                        books = books,
                        searchQuery = query,
                        searchResults = searchResults,
                        cloudNotice = cloudNotice
                    )
                }
            }
        }.distinctUntilChanged()
    }.catch { e ->
        emit(HomeUiState.Error(UiText.DynamicString(e.message ?: "Unknown Error")))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState.Loading
    )

    init {
        viewModelScope.launch {
            analyticsTracker.logScreenView("HomeScreen")
        }
    }

    fun onDismissUpdateDialog() {
        _isUpdateDialogDismissed.value = true
    }

    fun onCurriculumSelected(curriculum: Curriculum) {
        _selectedCurriculum.value = curriculum
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        if (query.length >= AppConfig.SEARCH_MIN_QUERY_LENGTH) {
            viewModelScope.launch {
                analyticsTracker.logEvent("search_executed", mapOf("query_length" to query.length))
            }
        }
    }

    fun onClearSearch() {
        _searchQuery.value = ""
    }

    fun onToggleFavorite(bookId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(bookId, isFavorite)
            val msgRes = if (isFavorite) R.string.msg_added_to_favorites else R.string.msg_removed_from_favorites
            _uiEvent.send(HomeUiEvent.ShowToast(UiText.StringResource(msgRes)))
        }
    }

    fun onClearCache() {
        viewModelScope.launch {
            clearCacheUseCase()
            _uiEvent.send(HomeUiEvent.ShowToast(UiText.StringResource(R.string.msg_cache_cleared_successfully)))
        }
    }

    fun onThemeSelected(mode: ThemeMode) {
        viewModelScope.launch {
            settingsRepository.setThemeMode(mode)
            analyticsTracker.logEvent("theme_changed", mapOf("mode" to mode.name))
        }
    }

    fun onToggleLayoutMode(mode: LayoutMode) {
        viewModelScope.launch {
            settingsRepository.setLayoutMode(mode)
        }
    }
}
