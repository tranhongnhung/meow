package sarveshchavan777.triviaquiz

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by twinklestar on 2017/12/09.
 */
interface QuestionInfoAPI {
    @GET("api/grammar")
     fun loadListLesson(): Call<ExerciseModel>
}