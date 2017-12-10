package sarveshchavan777.triviaquiz

import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

import info.hoang8f.widget.FButton

class Time_Up : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time__up)
        //Initialize
        val playAgainButton = findViewById(R.id.playAgainButton) as FButton
        val timeUpText = findViewById(R.id.timeUpText) as TextView

        //play again button onclick listener
        playAgainButton.setOnClickListener {
            val intent = Intent(this@Time_Up, MainGameActivity::class.java)
            startActivity(intent)
            finish()
        }


        //Setting typefaces for textview and button - this will give stylish fonts on textview and button
        val typeface = Typeface.createFromAsset(assets, "fonts/shablagooital.ttf")
        timeUpText.typeface = typeface
        playAgainButton.typeface = typeface
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
