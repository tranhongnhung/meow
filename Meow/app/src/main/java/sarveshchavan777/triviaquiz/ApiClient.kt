package sarveshchavan777.triviaquiz

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by twinklestar on 2017/12/09.
 */
class ApiClient {
    companion object {
        val BASE_URL = "https://english-meow-learning.000webhostapp.com/"
        private var retrofit: Retrofit? = null
        fun getClient(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit
        }
    }


}