package com.afrakhteh.movies.di.module

import com.afrakhteh.movies.data.repository.MainRepository
import com.afrakhteh.movies.ui.fragment.home.new.NewLimitedMovieViewModel
import com.afrakhteh.movies.ui.fragment.home.popular.PopularLimitedMovieViewModel
import com.afrakhteh.movies.ui.fragment.home.seeAll.newSeeAll.NewSeeAllViewModel
import com.afrakhteh.movies.ui.fragment.home.seeAll.popularSeeAll.PopularSeeAllViewModel
import com.afrakhteh.movies.ui.fragment.signs.login.LoginViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.android.viewmodel.scope.getViewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        LoginViewModel(get(), get())
    }
    viewModel {
        NewLimitedMovieViewModel(get(), get())
    }
    viewModel {
        PopularLimitedMovieViewModel(get(), get())
    }
    viewModel {
        NewSeeAllViewModel(get(), get())
    }
    viewModel {
        PopularSeeAllViewModel(get(), get())
    }
}