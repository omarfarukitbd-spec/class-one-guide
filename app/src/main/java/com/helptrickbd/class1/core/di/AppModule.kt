package com.helptrickbd.class1.core.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.helptrickbd.class1.core.database.AppDatabase
import com.helptrickbd.class1.core.database.BookDao
import com.helptrickbd.class1.core.database.SubjectDao
import com.helptrickbd.class1.feature.home.data.repository.HomeRepositoryImpl
import com.helptrickbd.class1.feature.home.domain.repository.HomeRepository
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
        ).build()
    }

    @Provides
    fun provideSubjectDao(database: AppDatabase): SubjectDao {
        return database.subjectDao()
    }

    @Provides
    fun provideBookDao(database: AppDatabase): BookDao {
        return database.bookDao()
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
    fun provideHomeRepository(): HomeRepository {
        return HomeRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideSubjectRepository(): SubjectRepository {
        return SubjectRepositoryImpl()
    }
}
