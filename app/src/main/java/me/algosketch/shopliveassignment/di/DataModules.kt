package me.algosketch.shopliveassignment.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.algosketch.shopliveassignment.data.source.remote.MarvelCharacterService
import me.algosketch.shopliveassignment.data.source.remote.RetrofitFactory

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun provideMarvelCharacterService() : MarvelCharacterService {
        return RetrofitFactory.create()
    }
}