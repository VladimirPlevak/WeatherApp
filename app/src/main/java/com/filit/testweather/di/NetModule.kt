package com.filit.testweather.di

import com.filit.domain.*
import com.filit.testweather.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

val netModule = module {
    single { createLoggingInterceptor() }
    single { createOkHttpClient(get()) }
    single { createGson() }
    single { createRetrofit(get(), get()) }
}

private const val CONNECT_TIMEOUT_SECONDS = 30L
private const val READ_TIMEOUT_SECONDS = 60L
private const val WRITE_TIMEOUT_SECONDS = 60L

private fun createLoggingInterceptor() =
    HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

private fun createOkHttpClient(
    loggingInterceptor: HttpLoggingInterceptor
) =
    OkHttpClient
        .Builder()
        .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
        .addInterceptor(loggingInterceptor)
        .build()

private fun createGson() = GsonBuilder().create()

private fun createRetrofit(
    gson: Gson,
    httpClient: OkHttpClient
) =
    Retrofit
        .Builder()
        .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(httpClient)
        .baseUrl(BuildConfig.API_HOST)
        .build()

private class RxErrorHandlingCallAdapterFactory : CallAdapter.Factory() {

    companion object {
        fun create(): CallAdapter.Factory = RxErrorHandlingCallAdapterFactory()
    }

    private val factory by lazy { RxJava3CallAdapterFactory.create() }

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit) =
        RxCallAdapterWrapper(factory.get(returnType, annotations, retrofit) as CallAdapter<*, *>)


    private inner class RxCallAdapterWrapper<R>(
        private val adapter: CallAdapter<R, *>
    ) : CallAdapter<R, Any> {

        override fun responseType(): Type = adapter.responseType()

        override fun adapt(call: Call<R>): Any =
            when (val adapted = adapter.adapt(call)) {
                is Completable -> adapted.onErrorResumeNext {
                    Completable.error(getError(it))
                }
                is Single<*> -> adapted.onErrorResumeNext {
                    Single.error(getError(it))
                }
                is Flowable<*> -> adapted.onErrorResumeNext { t: Throwable ->
                    Flowable.error(getError(t))
                }
                is Maybe<*> -> adapted.onErrorResumeNext { t: Throwable ->
                    Maybe.error(getError(t))
                }
                is Observable<*> -> adapted.onErrorResumeNext { t: Throwable ->
                    Observable.error(getError(t))
                }
                else -> RuntimeException("Observable Type not supported")
            }

        private fun getError(throwable: Throwable) =
            when (throwable) {
                is HttpException -> getApiException(throwable)
                is IOException -> NetworkException(throwable)
                else -> throwable
            }

        private fun getApiException(httpException: HttpException): Throwable {
            val response = httpException.response()
            val apiExceptionBuilder = ApiException.Builder(
                "Request failed: code = ${response?.code()}, message = ${response?.message()}"
            )
            response?.errorBody()?.string()?.let { errorBodyString ->
                try {
                    val errorBodyElement = JsonParser().parse(errorBodyString)
                    if (errorBodyElement.isJsonObject) {
                        val errorBodyObject = errorBodyElement.asJsonObject
                        val message = errorBodyObject.get("message")?.asString
                        apiExceptionBuilder.setErrorMessage(message)
                    }
                } catch (ignored: Throwable) {
                }
            }
            val apiException = apiExceptionBuilder.build()
            return when (response?.code()) {
                HttpURLConnection.HTTP_NOT_FOUND -> NotFoundException(apiException)
                HttpURLConnection.HTTP_INTERNAL_ERROR, HttpURLConnection.HTTP_BAD_GATEWAY ->
                    ServerException(apiException)
                HttpURLConnection.HTTP_BAD_REQUEST -> BadRequestException(apiException)
                else -> apiException
            }
        }

    }
}