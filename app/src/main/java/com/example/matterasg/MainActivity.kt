package com.example.matterasg

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.bhagyawant.gameLib.utils.Constants
import com.bhagyawant.gameLib.view.GameActivity

/**
 * Created simple activity to start the game.
 * As the game is actually separate library module which can be launched from any app just by including the
 * library in gradle file
 */

class MainActivity : AppCompatActivity() {
    private lateinit var btn4x4 : Button
    private lateinit var btn8x8 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn4x4 = findViewById(R.id.btn_4x4)
        btn8x8 = findViewById(R.id.btn_8x8)

        btn4x4.setOnClickListener {
            startGame(4)
        }
        btn8x8.setOnClickListener {
            startGame(8)
        }

    }

    private fun startGame(i: Int) {
        val startGameIntent = Intent(this, GameActivity::class.java)
        startGameIntent.putExtra(Constants.ROW_COL_SIZE,i)
        startActivity(startGameIntent)
    }


}