package sarveshchavan777.triviaquiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View

class GameWon : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_won)
    }

    //This is onclick listener for button
    //it will navigate from this activity to MainGameActivity
    fun PlayAgain(view: View) {
        val intent = Intent(this@GameWon, MainGameActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        finish()
    }
}
