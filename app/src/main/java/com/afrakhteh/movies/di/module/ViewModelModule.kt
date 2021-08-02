package com.afrakhteh.movies.di.module

import com.afrakhteh.movies.ui.fragment.comments.NewComments.NewCommentViewModel
import com.afrakhteh.movies.ui.fragment.comments.getAllComments.CommentListViewModel
import com.afrakhteh.movies.ui.fragment.detail.detail.DetailViewModel
import com.afrakhteh.movies.ui.fragment.home.home.HomeViewModel
import com.afrakhteh.movies.ui.fragment.home.new.NewLimitedMovieViewModel
import com.afrakhteh.movies.ui.fragment.home.popular.PopularLimitedMovieViewModel
import com.afrakhteh.movies.ui.fragment.home.seeAll.newSeeAll.NewSeeAllViewModel
import com.afrakhteh.movies.ui.fragment.home.seeAll.popularSeeAll.PopularSeeAllViewModel
import com.afrakhteh.movies.ui.fragment.save.SaveViewModel
import com.afrakhteh.movies.ui.fragment.search.SearchViewModel
import com.afrakhteh.movies.ui.fragment.signs.forgetPass.ForgetPasswordViewModel
import com.afrakhteh.movies.ui.fragment.signs.login.LoginViewModel
import com.afrakhteh.movies.ui.fragment.signs.register.RegisterViewModel
import org.koin.android.viewmodel.dsl.viewModel
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
    viewModel {
        DetailViewModel(get(),get())
    }
    viewModel {
        RegisterViewModel(get(),get())
    }
    viewModel {
        SearchViewModel(get(), get())
    }
    viewModel {
        NewCommentViewModel(get(),get())
    }
    viewModel {
        CommentListViewModel(get(),get())
    }
    viewModel {
        ForgetPasswordViewModel(get(),get())
    }
    viewModel {
        SaveViewModel(get())
    }
    viewModel {
        HomeViewModel(get())
    }
}