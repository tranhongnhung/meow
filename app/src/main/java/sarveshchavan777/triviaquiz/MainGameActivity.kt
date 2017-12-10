package sarveshchavan777.triviaquiz

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.CountDownTimer
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import java.util.Collections


import info.hoang8f.widget.FButton
import kotlinx.android.synthetic.main.activity_game_main.*

class MainGameActivity : AppCompatActivity() {
    //    internal var buttonA: FButton
//    internal var buttonB: FButton
//    internal var buttonC: FButton
//    internal var buttonD: FButton
//    internal var triviaQuestion: TextView
//    internal var triviaQuizText: TextView
//    internal var timeText: TextView
//    internal var resultText: TextView
//    internal var coinText: TextView
    private var triviaQuizHelper: TriviaQuizHelper? = null
    private var currentQuestion: TriviaQuestion? = null
    private var list: List<TriviaQuestion> = listOf()
    private var qid = 0
    private var timeValue = 20
    private var coinValue = 0
    private var countDownTimer: CountDownTimer? = null
    private var tb: Typeface? = null
    private var sb: Typeface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_main)

        //Initializing variables
        val triviaQuestion = findViewById(R.id.triviaQuestion) as TextView
        val buttonA = findViewById(R.id.buttonA) as FButton
        val buttonB = findViewById(R.id.buttonB) as FButton
        val buttonC = findViewById(R.id.buttonC) as FButton
        val buttonD = findViewById(R.id.buttonD) as FButton
        val triviaQuizText = findViewById(R.id.triviaQuizText) as TextView
        val timeText = findViewById(R.id.timeText) as TextView
        val resultText = findViewById(R.id.resultText) as TextView
        val coinText = findViewById(R.id.coinText) as TextView

        //Setting typefaces for textview and buttons - this will give stylish fonts on textview and button etc
        tb = Typeface.createFromAsset(assets, "fonts/TitilliumWeb-Bold.ttf")
        sb = Typeface.createFromAsset(assets, "fonts/shablagooital.ttf")
        triviaQuizText.typeface = sb
        triviaQuestion.typeface = tb
        buttonA.typeface = tb
        buttonB.typeface = tb
        buttonC.typeface = tb
        buttonD.typeface = tb
        timeText.typeface = tb
        resultText.typeface = sb
        coinText.typeface = tb

        //Our database helper class
        triviaQuizHelper = TriviaQuizHelper(this)
        //Make db writable
        triviaQuizHelper!!.writableDatabase

        if (triviaQuizHelper!!.allOfTheQuestions.isEmpty()) {
            triviaQuizHelper!!.allQuestion()
        }

        list = triviaQuizHelper!!.allOfTheQuestions

        Collections.shuffle(list)

        currentQuestion = list[qid]

        //countDownTimer
        countDownTimer = object : CountDownTimer(22000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                timeText.text = timeValue.toString() + "\""

                timeValue -= 1

                if (timeValue == -1) {

                    resultText.text = getString(R.string.timeup)

                    disableButton()
                }
            }

            override fun onFinish() {
                timeUp()
            }
        }.start()

        updateQueAndOptions()


    }


    private fun updateQueAndOptions() {

        triviaQuestion.text = currentQuestion?.question
        buttonA.text = currentQuestion?.optA
        buttonB.text = currentQuestion?.optB
        buttonC.text = currentQuestion?.optC
        buttonD.text = currentQuestion?.optD


        timeValue = 20

        countDownTimer?.cancel()
        countDownTimer?.start()

        coinText.text = coinValue.toString()
        coinValue++

    }

    fun buttonA(view: View) {
        if (currentQuestion?.optA == currentQuestion?.answer) {
            buttonA.buttonColor = ContextCompat.getColor(applicationContext, R.color.lightGreen)
            if (qid < list.size - 1) {

                disableButton()
                correctDialog()
            } else {

                gameWon()

            }
        } else {

            gameLostPlayAgain()

        }
    }

    //Onclick listener for sec button
    fun buttonB(view: View) {
        if (currentQuestion?.optB == currentQuestion?.answer) {
            buttonB.buttonColor = ContextCompat.getColor(applicationContext, R.color.lightGreen)
            if (qid < list.size - 1) {
                disableButton()
                correctDialog()
            } else {
                gameWon()
            }
        } else {
            gameLostPlayAgain()
        }
    }

    //Onclick listener for third button
    fun buttonC(view: View) {
        if (currentQuestion?.optC == currentQuestion?.answer) {
            buttonC.buttonColor = ContextCompat.getColor(applicationContext, R.color.lightGreen)
            if (qid < list.size - 1) {
                disableButton()
                correctDialog()
            } else {
                gameWon()
            }
        } else {

            gameLostPlayAgain()
        }
    }

    //Onclick listener for fourth button
    fun buttonD(view: View) {
        if (currentQuestion?.optD == currentQuestion?.answer) {
            buttonD.buttonColor = ContextCompat.getColor(applicationContext, R.color.lightGreen)
            if (qid < list.size - 1) {
                disableButton()
                correctDialog()
            } else {
                gameWon()
            }
        } else {
            gameLostPlayAgain()
        }
    }

    fun gameWon() {
        val intent = Intent(this, GameWon::class.java)
        startActivity(intent)
        finish()
    }

    fun gameLostPlayAgain() {
        val intent = Intent(this, PlayAgain::class.java)
        startActivity(intent)
        finish()
    }

    fun timeUp() {
        val intent = Intent(this, Time_Up::class.java)
        startActivity(intent)
        finish()
    }

    override fun onRestart() {
        super.onRestart()
        countDownTimer?.start()
    }

    override fun onStop() {
        super.onStop()
        countDownTimer?.cancel()
    }

    override fun onPause() {
        super.onPause()
        countDownTimer?.cancel()
    }

    override fun onBackPressed() {
        val intent = Intent(this, HomeScreen::class.java)
        startActivity(intent)
        finish()
    }

    fun correctDialog() {
        val dialogCorrect = Dialog(this@MainGameActivity)
        dialogCorrect.requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (dialogCorrect.window != null) {
            val colorDrawable = ColorDrawable(Color.TRANSPARENT)
            dialogCorrect.window!!.setBackgroundDrawable(colorDrawable)
        }
        dialogCorrect.setContentView(R.layout.dialog_correct)
        dialogCorrect.setCancelable(false)
        dialogCorrect.show()

        //Since the dialog is show to user just pause the timer in background
        onPause()


        val correctText = dialogCorrect.findViewById(R.id.correctText) as TextView
        val buttonNext = dialogCorrect.findViewById(R.id.dialogNext) as FButton

        //Setting type faces
        correctText.typeface = sb
        buttonNext.typeface = sb

        buttonNext.setOnClickListener {
            dialogCorrect.dismiss()
            qid++
            currentQuestion = list[qid]
            updateQueAndOptions()
            resetColor()
            enableButton()
        }
    }


    //This method will make button color white again since our one button color was turned green
    fun resetColor() {
        buttonA.buttonColor = ContextCompat.getColor(applicationContext, R.color.white)
        buttonB.buttonColor = ContextCompat.getColor(applicationContext, R.color.white)
        buttonC.buttonColor = ContextCompat.getColor(applicationContext, R.color.white)
        buttonD.buttonColor = ContextCompat.getColor(applicationContext, R.color.white)
    }

    //This method will disable all the option button
    fun disableButton() {
        buttonA.isEnabled = false
        buttonB.isEnabled = false
        buttonC.isEnabled = false
        buttonD.isEnabled = false
    }

    //This method will all enable the option buttons
    fun enableButton() {
        buttonA.isEnabled = true
        buttonB.isEnabled = true
        buttonC.isEnabled = true
        buttonD.isEnabled = true
    }


}
