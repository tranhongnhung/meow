package sarveshchavan777.triviaquiz

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * 
 * Created by twinklestar on 2017/12/09.
 */
class ExerciseModel constructor(data: List<QuestionModel>, total: Int = 0): Serializable {
    @SerializedName("total")
     var total: Int = 0

    @SerializedName("data")
     var QuestionModelList: List<QuestionModel>? = listOf()

}