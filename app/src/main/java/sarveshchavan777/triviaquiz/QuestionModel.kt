package sarveshchavan777.triviaquiz

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by twinklestar on 2017/12/09.
 */
class QuestionModel constructor(): Serializable {
    @SerializedName("id")
     var id: Int = 0

    @SerializedName("content")
     var content: String? = ""

    @SerializedName("question_a")
     var questionA: String? = ""

    @SerializedName("question_b")
     var questionB: String? = ""

    @SerializedName("question_c")
     var questionC: String? = ""

    @SerializedName("question_d")
     var questionD: String? = ""

    @SerializedName("flag_question_a")
     var flagA: Int? = 0

    @SerializedName("flag_question_b")
     var flagB: Int? = 0

    @SerializedName("flag_question_c")
     var flagC: Int? = 0

    @SerializedName("flag_question_d")
     var flagD: Int? = 0



}