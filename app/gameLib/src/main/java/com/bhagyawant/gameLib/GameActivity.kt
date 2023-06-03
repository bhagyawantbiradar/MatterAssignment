package com.bhagyawant.gameLib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    var ROW_AND_COLUMN_SIZE = 4;
    lateinit var rvGrid : RecyclerView
    lateinit var tvScore : TextView
    lateinit var btnTop : Button
    lateinit var btnBottom : Button
    lateinit var btnLeft : Button
    lateinit var btnRight : Button
    lateinit var grid2dArray: Array<IntArray>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        initViews()

        //Initializing all the cells with value 0 initially
        initializeGameValues()
    }

    private fun set2AtRandomGrid() {
        val row = Random.nextInt(0,ROW_AND_COLUMN_SIZE)
        val col = Random.nextInt(0,ROW_AND_COLUMN_SIZE)

        if(grid2dArray[row][col]==0){
            grid2dArray[row][col] = 2
        }else{
            set2AtRandomGrid()
        }
    }

    private fun initializeGameValues() {
        grid2dArray = Array(ROW_AND_COLUMN_SIZE){ IntArray(ROW_AND_COLUMN_SIZE) }

        for(row in 0 until ROW_AND_COLUMN_SIZE){
            for(col in 0 until ROW_AND_COLUMN_SIZE){
                grid2dArray[row][col] = 0
            }
        }

        //Calling function twice as we need 2 cells to be filled with value 2 initially
        set2AtRandomGrid()
        set2AtRandomGrid()
    }

    private fun initViews() {
        rvGrid = findViewById(R.id.rv_grid)
        tvScore = findViewById(R.id.tv_score)
        btnTop = findViewById(R.id.btn_top)
        btnBottom = findViewById(R.id.btn_bottom)
        btnLeft = findViewById(R.id.btn_left)
        btnRight = findViewById(R.id.btn_right)
    }
}