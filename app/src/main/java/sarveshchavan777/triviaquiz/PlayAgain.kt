package sarveshchavan777.triviaquiz

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView


class PlayAgain : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.play_again)
        //Initialize
        val playAgain = findViewById(R.id.playAgainButton) as Button
        val wrongAnsText = findViewById(R.id.wrongAns) as TextView

        //play again button onclick listener
        playAgain.setOnClickListener {
            val intent = Intent(this@PlayAgain, MainGameActivity::class.java)
            startActivity(intent)
            finish()
        }

        //Setting typefaces for textview and button - this will give stylish fonts on textview and button
        val typeface = Typeface.createFromAsset(assets, "fonts/shablagooital.ttf")
        playAgain.typeface = typeface
        wrongAnsText.typeface = typeface
    }

    override fun onBackPressed() {
        finish()
    }
}
