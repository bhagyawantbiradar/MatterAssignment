package com.bhagyawant.gameLib.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bhagyawant.gameLib.R
import com.bhagyawant.gameLib.adapter.GridRecyclerAdapter
import com.bhagyawant.gameLib.utils.Constants
import com.bhagyawant.gameLib.utils.NonScrollableGridLayoutManager
import com.bhagyawant.gameLib.utils.SwipeGestureListener
import com.bhagyawant.gameLib.view_model.GameViewModel

class GameActivity : AppCompatActivity(), SwipeGestureListener.OnSwipeListener {

    lateinit var gameViewModel: GameViewModel
    lateinit var rvGrid : RecyclerView
    lateinit var tvScore : TextView
    lateinit var btnTop : Button
    lateinit var btnBottom : Button
    lateinit var btnLeft : Button
    lateinit var btnRight : Button
    lateinit var grid2dArray: Array<IntArray>
    private lateinit var gestureDetector: GestureDetector
    private var rowColSize = 4


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        rowColSize = intent.getIntExtra(Constants.ROW_COL_SIZE,4)
        initViews()

        //Initializing all the cells with value 0 initially
        setAllObservers()
        gameViewModel.initializeGameValues(rowColSize)

        btnLeft.setOnClickListener {
            gameViewModel.moveLeftAndAdjust()
        }

        btnRight.setOnClickListener {
            gameViewModel.moveRightAndAdjust()
        }

        btnTop.setOnClickListener {
            gameViewModel.moveTopAndAdjust()
        }

        btnBottom.setOnClickListener {
            gameViewModel.moveBottomAndAdjust()
        }
    }

    private fun setAllObservers() {
        gameViewModel.finalScoreLivedata.observe(this) {
            tvScore.text = "Score : $it"
        }

        gameViewModel.grid2dArrayLiveData.observe(this) {
            rvGrid.layoutManager = NonScrollableGridLayoutManager(this,it[0].size)
            rvGrid.adapter = GridRecyclerAdapter(this,it)
            grid2dArray = it
        }

        gameViewModel.isRefreshLivedata.observe(this){
            if(it){
                refreshGridAndScore()
                gameViewModel.updateRefresh(false)
            }
        }

        gameViewModel.isGameOverLiveData.observe(this){
            if(it)showGameOverAlert()
        }

        gameViewModel.cellScoreLivedata.observe(this){
            if((rowColSize==4 && it==2048) || (rowColSize==8 && it==4096)){
                showWinDialog()
            }
        }

    }

    private fun showWinDialog() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Congratulations...!!!")
            .setMessage("You won the game")
            .setPositiveButton("Try Again"){dialog,_->
                dialog.dismiss()
                gameViewModel.initializeGameValues(rowColSize)
                gameViewModel.updateGameOver(false)
            }
            .setNegativeButton("Quit"){dialog,_->
                dialog.dismiss()
                finish()
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun showGameOverAlert() {
        val builder = AlertDialog.Builder(this)
            .setTitle("Game Over")
            .setMessage("Better luck next time")
            .setPositiveButton("Try Again"){dialog,_->
                dialog.dismiss()
                gameViewModel.initializeGameValues(rowColSize)
                gameViewModel.updateGameOver(false)
            }
            .setNegativeButton("Quit"){dialog,_->
                dialog.dismiss()
                finish()
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun refreshGridAndScore() {
        rvGrid.layoutManager = NonScrollableGridLayoutManager(this,grid2dArray[0].size)
        rvGrid.adapter = GridRecyclerAdapter(this,grid2dArray)
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
        gameViewModel.moveLeftAndAdjust()
    }

    override fun onSwipeRight() {
        gameViewModel.moveRightAndAdjust()
    }

    override fun onSwipeUp() {
        gameViewModel.moveTopAndAdjust()
    }

    override fun onSwipeDown() {
        gameViewModel.moveBottomAndAdjust()
    }
}