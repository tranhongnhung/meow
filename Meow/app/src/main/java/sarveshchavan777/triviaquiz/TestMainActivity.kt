package sarveshchavan777.triviaquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_test_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TestMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_main)

        testbtn.setOnClickListener {
            val infoAPI = ApiClient.getClient()?.create(QuestionInfoAPI::class.java)
            val lessonCall = infoAPI?.loadListLesson()
            lessonCall?.enqueue(object : Callback<ExerciseModel> {
                override fun onResponse(call: Call<ExerciseModel>, response: Response<ExerciseModel>) {
                    Log.e("Response", "==> " + Gson().toJson(response.body()))
                }

                override fun onFailure(call: Call<ExerciseModel>, t: Throwable) {
                    Log.e("Error", ": 1")
                    t.printStackTrace()
                }
            })
        }
    }
}
