package com.afrakhteh.movies.di.module

import com.afrakhteh.movies.data.repository.MainRepository
import org.koin.dsl.module

/*User Repository requires ApiHelper in the constructor
which will be provided by the Koin here.*/

val repositoryModule = module {
    single {
        MainRepository(get())
    }
}
