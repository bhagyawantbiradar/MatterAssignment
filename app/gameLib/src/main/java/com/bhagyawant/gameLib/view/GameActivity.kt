package com.bhagyawant.gameLib.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bhagyawant.gameLib.R
import com.bhagyawant.gameLib.adapter.GridRecyclerAdapter
import com.bhagyawant.gameLib.utils.NonScrollableGridLayoutManager
import com.bhagyawant.gameLib.utils.SwipeGestureListener
import com.bhagyawant.gameLib.view_model.GameViewModel
import kotlin.random.Random

class GameActivity : AppCompatActivity(), SwipeGestureListener.OnSwipeListener {

    lateinit var gameViewModel: GameViewModel
    var ROW_AND_COLUMN_SIZE = 4
    lateinit var rvGrid : RecyclerView
    lateinit var tvScore : TextView
    lateinit var btnTop : Button
    lateinit var btnBottom : Button
    lateinit var btnLeft : Button
    lateinit var btnRight : Button
    lateinit var grid2dArray: Array<IntArray>
    var finalScore = 0
    private lateinit var gestureDetector: GestureDetector


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        initViews()

        //Initializing all the cells with value 0 initially
        initializeGameValues()
        refreshGridAndScore()


        btnLeft.setOnClickListener {
            moveLeftAndAdjust()
        }

        btnRight.setOnClickListener {
            moveRightAndAdjust()
        }

        btnTop.setOnClickListener {
            moveTopAndAdjust()
        }

        btnBottom.setOnClickListener {
            moveBottomAndAdjust()
        }
    }

    private fun putRandom(){
        val emptyCells = mutableListOf<Pair<Int,Int>>()

        for(row in 0 until ROW_AND_COLUMN_SIZE){
            for(col in 0 until ROW_AND_COLUMN_SIZE){
                if(grid2dArray[row][col]==0){
                    emptyCells.add(Pair(row,col))
                }
            }
        }

        if(emptyCells.isNotEmpty()){
            val randomCell = emptyCells[Random.nextInt(emptyCells.size)]
            val randomValue = if(Random.nextBoolean()) 2 else 4
            grid2dArray[randomCell.first][randomCell.second] = randomValue
        }else{
            Toast.makeText(this,"Game Over, Try new Game",Toast.LENGTH_LONG).show()
        }
    }

    private fun moveBottomAndAdjust() {
        var isMerged = false
        for(col in 0 until ROW_AND_COLUMN_SIZE){
            val temp = IntArray(ROW_AND_COLUMN_SIZE)
            var index = ROW_AND_COLUMN_SIZE-1
            for(row in ROW_AND_COLUMN_SIZE-1 downTo 0){
                if(grid2dArray[row][col]!=0){
                    temp[index] = grid2dArray[row][col]
                    index--
                }
            }
            for(row in 0 until ROW_AND_COLUMN_SIZE){
                grid2dArray[row][col] = temp[row]
            }
        }

        for(col in 0 until ROW_AND_COLUMN_SIZE){
            for(row in ROW_AND_COLUMN_SIZE-1 downTo 1){
                if(grid2dArray[row][col]!=0 && grid2dArray[row][col]==grid2dArray[row-1][col]){
                    grid2dArray[row][col] *= 2
                    finalScore += grid2dArray[row][col]
                    grid2dArray[row-1][col] = 0
                    isMerged = true
                }
            }
        }

        if(isMerged){
            moveBottomAndAdjust()
        }else{
            putRandom()
            refreshGridAndScore()
        }
    }

    private fun moveTopAndAdjust() {
        var isMerged = false
        for(col in 0 until ROW_AND_COLUMN_SIZE){
            val temp = IntArray(ROW_AND_COLUMN_SIZE)
            var index = 0

            for(row in 0 until ROW_AND_COLUMN_SIZE){
                if(grid2dArray[row][col]!=0){
                    temp[index] = grid2dArray[row][col]
                    index++
                }
            }
            for(row in 0 until ROW_AND_COLUMN_SIZE){
                grid2dArray[row][col] = temp[row]
            }

        }

        for(col in 0 until ROW_AND_COLUMN_SIZE){
            for(row in 0 until ROW_AND_COLUMN_SIZE-1){
                if(grid2dArray[row][col]!=0 && grid2dArray[row][col]==grid2dArray[row+1][col]){
                    grid2dArray[row][col] *= 2
                    finalScore += grid2dArray[row][col]
                    grid2dArray[row+1][col] = 0
                    isMerged = true
                }
            }
        }

        if(isMerged){
            moveTopAndAdjust()
        }else{
            putRandom()
            refreshGridAndScore()
        }
    }

    private fun refreshGridAndScore() {
        rvGrid.layoutManager = NonScrollableGridLayoutManager(this,grid2dArray[0].size)
        rvGrid.adapter = GridRecyclerAdapter(this,grid2dArray)
        tvScore.text = "Score : $finalScore"
    }

    private fun moveLeftAndAdjust() {
        var isMerged = false
        for(row in 0 until ROW_AND_COLUMN_SIZE){
            val temp = IntArray(ROW_AND_COLUMN_SIZE)
            var index = 0
            for(col in 0 until ROW_AND_COLUMN_SIZE){
                if(grid2dArray[row][col]!=0){
                    temp[index] = grid2dArray[row][col]
                    index++
                }
            }
            temp.copyInto(grid2dArray[row])
        }

        for(row in 0 until ROW_AND_COLUMN_SIZE){
            for(col in 0 until ROW_AND_COLUMN_SIZE-1){
                if(grid2dArray[row][col]!=0 && grid2dArray[row][col]==grid2dArray[row][col+1]){
                    grid2dArray[row][col] *= 2
                    finalScore += grid2dArray[row][col]
                    grid2dArray[row][col+1] = 0
                    isMerged = true
                }
            }
        }

        if(isMerged){
            moveLeftAndAdjust()
        }else{
            putRandom()
            refreshGridAndScore()
        }
    }

    private fun moveRightAndAdjust(){
        var isMerged = false
        for(row in 0 until ROW_AND_COLUMN_SIZE){
            val temp = IntArray(ROW_AND_COLUMN_SIZE)
            var index = ROW_AND_COLUMN_SIZE-1
            for(col in ROW_AND_COLUMN_SIZE-1 downTo 0){
                if(grid2dArray[row][col]!=0){
                    temp[index] = grid2dArray[row][col]
                    index--
                }
            }
            temp.copyInto(grid2dArray[row])
        }

        for(row in 0 until ROW_AND_COLUMN_SIZE){
            for(col in ROW_AND_COLUMN_SIZE-1 downTo 1){
                if(grid2dArray[row][col]!=0 && grid2dArray[row][col]==grid2dArray[row][col-1]){
                    grid2dArray[row][col]*=2
                    finalScore += grid2dArray[row][col]
                    grid2dArray[row][col-1]=0
                    isMerged = true
                }
            }
        }

        if(isMerged){
            moveRightAndAdjust()
        }else{
            putRandom()
            refreshGridAndScore()
        }
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
        gestureDetector = GestureDetector(this,SwipeGestureListener(this))
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    override fun onSwipeLeft() {
        moveLeftAndAdjust()
    }

    override fun onSwipeRight() {
        moveRightAndAdjust()
    }

    override fun onSwipeUp() {
        moveTopAndAdjust()
    }

    override fun onSwipeDown() {
        moveBottomAndAdjust()
    }
}