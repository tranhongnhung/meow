package sarveshchavan777.triviaquiz

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson

import info.hoang8f.widget.FButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeScreen : AppCompatActivity() {



    var dialog: ProgressDialog ?= null
    companion object {
        var listQuestion : ArrayList<QuestionModel> = arrayListOf()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)
        val trvia = TriviaQuizHelper(this)
        val playGame = findViewById(R.id.playGame) as FButton
        val quit = findViewById(R.id.quit) as FButton
        val tQ = findViewById(R.id.tQ) as TextView
        dialog = TriviaQuizHelper.getProgressDialog(this)
        val infoAPI = ApiClient.getClient()?.create(QuestionInfoAPI::class.java)
        val lessonCall = infoAPI?.loadListLesson()
        //PlayGame button - it will take you to the MainGameActivity
        if (!TriviaQuizHelper.isNetworkConnected(this)) {
            Toast.makeText(this, "Please Connect Internet", Toast.LENGTH_SHORT).show()
        } else {
            dialog!!.show()
            trvia.deleteQuestionTable()
            lessonCall?.enqueue(object : Callback<ExerciseModel> {
                override fun onResponse(call: Call<ExerciseModel>, response: Response<ExerciseModel>) {
                    Log.e("Response", "==> " + Gson().toJson(response.body()))
                    dialog!!.dismiss()
                    listQuestion.addAll(response.body()?.QuestionModelList!!)
                    Log.e("Response", "==> " + Gson().toJson(listQuestion))
                }

                override fun onFailure(call: Call<ExerciseModel>, t: Throwable) {
                    Log.e("Error", ": 1")
                    t.printStackTrace()
                    dialog!!.dismiss()
                }
            })
        }
        playGame.setOnClickListener {
            if (!TriviaQuizHelper.isNetworkConnected(this)) {
                Toast.makeText(this, "Please Connect Internet", Toast.LENGTH_SHORT).show()
            } else {
                if (listQuestion.size == 0) {
                    dialog!!.show()
                    lessonCall?.enqueue(object : Callback<ExerciseModel> {
                        override fun onResponse(call: Call<ExerciseModel>, response: Response<ExerciseModel>) {
                            Log.e("Response", "==> " + Gson().toJson(response.body()))
                            dialog!!.dismiss()
                            listQuestion.addAll(response.body()?.QuestionModelList!!)
                            Log.e("Response", "==> " + Gson().toJson(listQuestion))
                            val intent = Intent(this@HomeScreen, MainGameActivity::class.java)
                            startActivity(intent)
//                            finish()
                        }

                        override fun onFailure(call: Call<ExerciseModel>, t: Throwable) {
                            Log.e("Error", ": 1")
                            t.printStackTrace()
                            dialog!!.dismiss()
                        }
                    })
                }
            }
            val intent = Intent(this@HomeScreen, MainGameActivity::class.java)
            startActivity(intent)
            finish()
        }
        //Quit button - This will quit the game
        quit.setOnClickListener { finish() }

        //Typeface - this is for fonts style
        val typeface = Typeface.createFromAsset(assets, "fonts/shablagooital.ttf")
        playGame.typeface = typeface
        quit.typeface = typeface
        tQ.typeface = typeface
    }
}
