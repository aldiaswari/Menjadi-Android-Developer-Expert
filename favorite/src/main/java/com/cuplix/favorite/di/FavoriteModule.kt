package com.cuplix.favorite.di

import android.content.Context
import com.cuplix.cupita.di.FavoriteModuleDependencies
import com.cuplix.favorite.FavoriteFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteModule {

    fun inject(FavoriteFragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(FavoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteModule
    }
}