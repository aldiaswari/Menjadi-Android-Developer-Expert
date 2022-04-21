package com.cuplix.favorite.di

import android.content.Context
import com.cuplix.cupita.di.FavoriteModuleDepedencies
import com.cuplix.favorite.FavoriteFragment
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDepedencies::class])
interface FavoriteComponent {

    fun inject(fragment: FavoriteFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDepedencies: FavoriteModuleDepedencies): Builder
        fun build(): FavoriteComponent
    }

}