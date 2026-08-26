package com.helptrickbd.class1.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.helptrickbd.class1.core.config.AppConfig
import com.helptrickbd.class1.core.notification.domain.repository.NotificationRepository
import com.helptrickbd.class1.core.settings.domain.model.StorageInfo
import com.helptrickbd.class1.core.settings.domain.model.ThemeMode
import com.helptrickbd.class1.core.settings.domain.repository.SettingsRepository
import com.helptrickbd.class1.core.settings.domain.usecase.ClearCacheUseCase
import com.helptrickbd.class1.core.settings.domain.usecase.GetStorageInfoUseCase
import com.helptrickbd.class1.feature.home.domain.model.Book
import com.helptrickbd.class1.feature.home.domain.model.Curriculum
import com.helptrickbd.class1.feature.home.domain.model.LayoutMode
import com.helptrickbd.class1.feature.home.domain.model.SearchResult
import com.helptrickbd.class1.feature.home.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository,
    private val getStorageInfoUseCase: GetStorageInfoUseCase,
    private val clearCacheUseCase: ClearCacheUseCase,
    private val settingsRepository: SettingsRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _selectedCurriculum = MutableStateFlow(AppConfig.DEFAULT_CURRICULUM)
    val selectedCurriculum = _selectedCurriculum.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val settingsFlow = combine(
        getStorageInfoUseCase(),
        settingsRepository.getThemeMode(),
        settingsRepository.getLayoutMode(),
        notificationRepository.getUnreadCount()
    ) { storage, theme, layout, unreadCount ->
        HomeSettingsData(storage, theme, layout, unreadCount)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HomeUiState> = combine(
        _selectedCurriculum,
        _searchQuery
    ) { curriculum, query ->
        Pair(curriculum, query)
    }.flatMapLatest { (curriculum, query) ->
        val contentFlow = combine(
            repository.getBooks(curriculum),
            repository.getResumeBook(),
            repository.searchBooksAndChapters(query, curriculum),
            repository.getCloudNotice()
        ) { books, resumeBook, searchResults, cloudNotice ->
            HomeContentData(books, resumeBook, searchResults, cloudNotice)
        }

        combine(contentFlow, settingsFlow) { content, settings ->
            HomeUiState.Success(
                userName = AppConfig.DEFAULT_USER_NAME,
                selectedCurriculum = curriculum,
                resumeBook = content.resumeBook,
                books = content.books,
                storageInfo = settings.storageInfo,
                themeMode = settings.themeMode,
                layoutMode = settings.layoutMode,
                searchQuery = query,
                searchResults = content.searchResults,
                cloudNotice = content.cloudNotice,
                unreadNotifications = settings.unreadNotifications
            ) as HomeUiState
        }
    }.catch { e ->
        emit(HomeUiState.Error(e.message ?: "Unknown Error"))
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeUiState.Loading
    )

    fun onCurriculumSelected(curriculum: Curriculum) {
        _selectedCurriculum.value = curriculum
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onClearSearch() {
        _searchQuery.value = ""
    }

    fun onToggleFavorite(bookId: String, isFavorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(bookId, isFavorite)
        }
    }

    fun onClearCache() {
        viewModelScope.launch {
            clearCacheUseCase()
        }
    }

    fun onThemeSelected(mode: ThemeMode) {
        viewModelScope.launch {
            settingsRepository.setThemeMode(mode)
        }
    }

    fun onToggleLayoutMode(mode: LayoutMode) {
        viewModelScope.launch {
            settingsRepository.setLayoutMode(mode)
        }
    }
}

private data class HomeContentData(
    val books: List<Book>,
    val resumeBook: Book?,
    val searchResults: List<SearchResult>,
    val cloudNotice: String?
)

private data class HomeSettingsData(
    val storageInfo: StorageInfo,
    val themeMode: ThemeMode,
    val layoutMode: LayoutMode,
    val unreadNotifications: Int
)
