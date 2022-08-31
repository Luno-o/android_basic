package ru.skillbox.dependency_injection.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import ru.skillbox.dependency_injection.data.Api
import ru.skillbox.dependency_injection.data.AppVersionInterceptor
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    @BaseClient
    fun providesOkHttpClient(
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .followRedirects(true)
            .build()
    }


    @Provides
    @AppVersionLoggingInterceptorClient
    fun providesOkHttpAppVersionClient(
        @ru.skillbox.dependency_injection.di.AppVersionInterceptor
        loggingInterceptor: Interceptor,
        @ru.skillbox.dependency_injection.di.HttpLoggingInterceptor
        appVersionInterceptor: Interceptor
    ): OkHttpClient {

        return OkHttpClient.Builder()
            .addNetworkInterceptor(
                loggingInterceptor
            )
            .addNetworkInterceptor(
                appVersionInterceptor
            )
            .followRedirects(true)
            .build()
    }

    @Provides
    @Singleton
    @BaseRetrofit
    fun providesBaseRetrofit(
        @BaseClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://google.com")
            .client(okHttpClient)
            .build()
    }
    @Provides
    @Singleton
    @AppVersionRetrofit
    fun providesLoggingRetrofit(
        @AppVersionLoggingInterceptorClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://google.com")
            .client(okHttpClient)
            .build()
    }

@Provides
@ru.skillbox.dependency_injection.di.AppVersionInterceptor
fun providesLoggingInterceptor():Interceptor{
    return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
}
@Provides
@ru.skillbox.dependency_injection.di.HttpLoggingInterceptor
fun providesAppVersionInterceptor():Interceptor{
    return AppVersionInterceptor()
}
    @Provides
    @BaseApi
    fun providesBaseApi(
        @BaseRetrofit retrofit: Retrofit): Api {
        return retrofit.create()
    }
    @Provides
    @AppVersionApi
    fun providesAppVersionApi(
        @AppVersionRetrofit retrofit: Retrofit): Api {
        return retrofit.create()
    }

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppVersionLoggingInterceptorClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppVersionInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppVersionRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AppVersionApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseApi

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HttpLoggingInterceptor

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseClient

