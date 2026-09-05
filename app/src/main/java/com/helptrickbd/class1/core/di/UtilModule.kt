package com.helptrickbd.class1.core.di

import com.helptrickbd.class1.core.util.AppVersionProvider
import com.helptrickbd.class1.core.util.AppVersionProviderImpl
import com.helptrickbd.class1.core.util.StorageProvider
import com.helptrickbd.class1.core.util.StorageProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilModule {
    @Binds
    @Singleton
    abstract fun bindAppVersionProvider(impl: AppVersionProviderImpl): AppVersionProvider

    @Binds
    @Singleton
    abstract fun bindStorageProvider(impl: StorageProviderImpl): StorageProvider
}
