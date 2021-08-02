package com.afrakhteh.movies.di.module

import android.content.Context
import androidx.room.Room
import com.afrakhteh.movies.data.api.ApiHelper
import com.afrakhteh.movies.data.api.ApiHelperImpl
import com.afrakhteh.movies.data.api.ApiService
import com.afrakhteh.movies.data.db.SaveDataBase
import com.afrakhteh.movies.util.consts.CONSTANTS
import com.afrakhteh.movies.util.consts.URLS
import com.afrakhteh.movies.util.nework.NetworkHelper
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import org.koin.dsl.module


//we want to provide as dependencies


private fun provideNetWorkHelper(context: Context) = NetworkHelper(context)

private fun provideOkHttpClient() =
    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(
            HttpLoggingInterceptor.Level.BODY
        )

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else {
        OkHttpClient.Builder().build()
    }

private fun provideMoshi(): Moshi {
    return Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

private fun provideRetrofit(okHttpClient: OkHttpClient, base_url: String): Retrofit =
    Retrofit.Builder()
        .baseUrl(base_url)
        .addConverterFactory(MoshiConverterFactory.create(provideMoshi()).asLenient())
        .client(okHttpClient)
        .build()


private fun provideApiService(retrofit: Retrofit): ApiService =
    retrofit.create(ApiService::class.java)


private fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper


private fun provideRoom(context: Context): SaveDataBase =
    Room.databaseBuilder(
        context,
        SaveDataBase::class.java, CONSTANTS.DB_DATABASE_NAME
    ).build()


/*The module is a collection of dependencies
we are going to provide to the application
we will pass the single instance of all the functions we created
using get() here to pass the dependency to the constructor*/

val appModule = module {

    single { provideOkHttpClient() }
    single { provideRetrofit(get(), URLS.BASE_URL) }
    single { provideApiService(get()) }
    single { provideNetWorkHelper(androidContext()) }

    /* to pass the ApiHelper as a param,
    we need to provide it as well from the module.*/
    single<ApiHelper> { return@single ApiHelperImpl(get()) }

   single { provideRoom(androidContext()) }


}