package org.sopt.and.di.module

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.sopt.and.data.ServicePool
import org.sopt.and.data.local.source.AuthLocalDataSource
import org.sopt.and.data.remote.api.AuthService
import org.sopt.and.data.remote.source.AuthRemoteDataSource
import org.sopt.and.data.repository.AuthRepositoryImpl
import org.sopt.and.domain.repository.AuthRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    companion object {
        @Provides
        @Singleton
        fun provideAuthService(): AuthService = ServicePool.authService

        @Provides
        @Singleton
        fun provideAuthLocalDataSource(
            @ApplicationContext context: Context
        ): AuthLocalDataSource = AuthLocalDataSource(context)

        @Provides
        @Singleton
        fun provideAuthRemoteDataSource(
            authService: AuthService
        ): AuthRemoteDataSource = AuthRemoteDataSource(authService)
    }
}
