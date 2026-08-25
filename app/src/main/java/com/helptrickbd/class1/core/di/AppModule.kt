package com.helptrickbd.class1.core.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.helptrickbd.class1.core.database.AppDatabase
import com.helptrickbd.class1.core.database.BookDao
import com.helptrickbd.class1.core.database.ChapterDao
import com.helptrickbd.class1.core.database.DatabaseSeeder
import com.helptrickbd.class1.core.settings.data.SettingsRepositoryImpl
import com.helptrickbd.class1.core.settings.domain.repository.SettingsRepository
import com.helptrickbd.class1.core.sync.data.repository.CloudSyncRepositoryImpl
import com.helptrickbd.class1.core.sync.domain.repository.CloudSyncRepository
import com.helptrickbd.class1.core.sync.domain.usecase.SyncCloudDataUseCase
import com.helptrickbd.class1.core.sync.util.NetworkMonitor
import com.helptrickbd.class1.feature.home.data.repository.HomeRepositoryImpl
import com.helptrickbd.class1.feature.home.domain.repository.HomeRepository
import com.helptrickbd.class1.feature.pdf_viewer.data.PdfDownloader
import com.helptrickbd.class1.feature.pdf_viewer.data.repository.PdfRepositoryImpl
import com.helptrickbd.class1.feature.pdf_viewer.domain.repository.PdfRepository
import com.helptrickbd.class1.feature.subject_detail.data.repository.SubjectRepositoryImpl
import com.helptrickbd.class1.feature.subject_detail.domain.repository.SubjectRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "class1_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideBookDao(database: AppDatabase): BookDao {
        return database.bookDao()
    }

    @Provides
    fun provideChapterDao(database: AppDatabase): ChapterDao {
        return database.chapterDao()
    }

    @Provides
    fun provideBookmarkDao(database: AppDatabase): com.helptrickbd.class1.core.database.BookmarkDao {
        return database.bookmarkDao()
    }

    @Provides
    @Singleton
    fun provideBookmarkRepository(
        bookmarkDao: com.helptrickbd.class1.core.database.BookmarkDao
    ): com.helptrickbd.class1.feature.pdf_viewer.domain.repository.BookmarkRepository {
        return com.helptrickbd.class1.feature.pdf_viewer.data.repository.BookmarkRepositoryImpl(bookmarkDao)
    }

    @Provides
    @Singleton
    fun provideFirestore(@ApplicationContext context: Context): FirebaseFirestore? {
        return try {
            if (FirebaseApp.getApps(context).isEmpty()) {
                FirebaseApp.initializeApp(context)
            }
            FirebaseFirestore.getInstance()
        } catch (_: Exception) {
            null
        }
    }

    @Provides
    @Singleton
    fun provideCloudSyncRepository(
        @ApplicationContext context: Context,
        firestore: FirebaseFirestore?
    ): CloudSyncRepository {
        return CloudSyncRepositoryImpl(context, firestore)
    }

    @Provides
    @Singleton
    fun provideHomeRepository(
        bookDao: BookDao,
        chapterDao: ChapterDao,
        seeder: DatabaseSeeder,
        syncCloudDataUseCase: SyncCloudDataUseCase,
        networkMonitor: NetworkMonitor
    ): HomeRepository {
        return HomeRepositoryImpl(bookDao, chapterDao, seeder, syncCloudDataUseCase, networkMonitor)
    }

    @Provides
    @Singleton
    fun provideSubjectRepository(
        bookDao: BookDao,
        chapterDao: ChapterDao
    ): SubjectRepository {
        return SubjectRepositoryImpl(bookDao, chapterDao)
    }

    @Provides
    @Singleton
    fun providePdfRepository(pdfDownloader: PdfDownloader): PdfRepository {
        return PdfRepositoryImpl(pdfDownloader)
    }

    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepositoryImpl(context)
    }
}
