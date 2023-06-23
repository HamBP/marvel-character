package me.algosketch.shopliveassignment.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.algosketch.shopliveassignment.data.source.local.FavoriteCharacterDao
import me.algosketch.shopliveassignment.data.source.local.MarvelDatabase
import me.algosketch.shopliveassignment.data.source.remote.MarvelCharacterService
import me.algosketch.shopliveassignment.data.source.remote.RetrofitFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideMarvelCharacterService(): MarvelCharacterService {
        return RetrofitFactory.create()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): MarvelDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MarvelDatabase::class.java,
            MarvelDatabase.databaseName,
        ).build()
    }

    @Provides
    fun provideFavoriteCharacterDao(database: MarvelDatabase): FavoriteCharacterDao =
        database.favoriteDao()
}