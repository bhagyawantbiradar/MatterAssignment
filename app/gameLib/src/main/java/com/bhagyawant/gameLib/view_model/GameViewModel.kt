package com.bhagyawant.gameLib.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

private const val TAG = "GameViewModel"

@HiltViewModel
class GameViewModel @Inject constructor(): ViewModel() {

    private var rowColSize = MutableLiveData(4)
    val rowColSizeLivedata : LiveData<Int>
    get() = rowColSize

    private var isRefresh = MutableLiveData(false)
    val isRefreshLivedata : LiveData<Boolean>
        get() = isRefresh

    private var isGameOver = MutableLiveData(false)
    val isGameOverLiveData : LiveData<Boolean>
        get() = isGameOver

    private var finalScore = MutableLiveData(0)
    val finalScoreLivedata : LiveData<Int>
        get() = finalScore

    private var cellScore = MutableLiveData(0)
    val cellScoreLivedata : LiveData<Int>
        get() = cellScore

    private var grid2dArrayObj  = MutableLiveData<Array<IntArray>>()
    val grid2dArrayLiveData : LiveData<Array<IntArray>>
    get() = grid2dArrayObj



    fun initializeGameValues(rowSize : Int) {
        finalScore.value = 0
        rowColSize.value = rowSize
        rowColSize.value?.let {size->
            grid2dArrayObj.value = Array(size){ IntArray(size) }

            for(row in 0 until size){
                for(col in 0 until size){
                    grid2dArrayObj.value?.let {
                        it[row][col] = 0
                    }
                }
            }

            //Calling function twice as we need 2 cells to be filled with value 2 initially
            set2AtRandomGrid()
            set2AtRandomGrid()
        }

    }

    private fun set2AtRandomGrid() {
        rowColSize.value?.let { size->
            val row = Random.nextInt(0,size)
            val col = Random.nextInt(0,size)

            grid2dArrayObj.value?.let {grid->
                if(grid[row][col]==0){
                    grid[row][col] = 2
                }else{
                    set2AtRandomGrid()
                }
            }

        }

    }


    private fun putRandom(){
        rowColSize.value?.let { size->
            grid2dArrayObj.value?.let { grid->

                val emptyCells = mutableListOf<Pair<Int,Int>>()

                for(row in 0 until size){
                    for(col in 0 until size){
                        if(grid[row][col]==0){
                            emptyCells.add(Pair(row,col))
                        }
                    }
                }

                if(emptyCells.isNotEmpty()){
                    val randomCell = emptyCells[Random.nextInt(emptyCells.size)]
                    val randomValue = if(Random.nextBoolean()) 2 else 4
                    grid[randomCell.first][randomCell.second] = randomValue
                }else{
                    updateGameOver(true)
                }

            }
        }

    }

    fun moveLeftAndAdjust() {
        rowColSize.value?.let { size->
            grid2dArrayObj.value?.let { grid->
                finalScore.value?.let { score ->
                    var isMerged = false
                    for(row in 0 until size){
                        val temp = IntArray(size)
                        var index = 0
                        for(col in 0 until size){
                            if(grid[row][col]!=0){
                                temp[index] = grid[row][col]
                                index++
                            }
                        }
                        temp.copyInto(grid[row])
                    }

                    for(row in 0 until size){
                        for(col in 0 until size-1){
                            if(grid[row][col]!=0 && grid[row][col]==grid[row][col+1]){
                                grid[row][col] *= 2
                                cellScore.value = grid[row][col]
                                finalScore.value = score+ grid[row][col]
                                grid[row][col+1] = 0
                                isMerged = true
                            }
                        }
                    }

                    if(isMerged){
                        moveLeftAndAdjust()
                    }else{
                        putRandom()
                        updateRefresh(true)
                    }
                }
                }
            }
        }


    fun moveRightAndAdjust(){
        rowColSize.value?.let { size->
            grid2dArrayObj.value?.let { grid->
                finalScore.value?.let { score->

                    var isMerged = false
                    for(row in 0 until size){
                        val temp = IntArray(size)
                        var index = size-1
                        for(col in size-1 downTo 0){
                            if(grid[row][col]!=0){
                                temp[index] = grid[row][col]
                                index--
                            }
                        }
                        temp.copyInto(grid[row])
                    }

                    for(row in 0 until size){
                        for(col in size-1 downTo 1){
                            if(grid[row][col]!=0 && grid[row][col]==grid[row][col-1]){
                                grid[row][col]*=2
                                cellScore.value = grid[row][col]
                                finalScore.value = score + grid[row][col]
                                grid[row][col-1]=0
                                isMerged = true
                            }
                        }
                    }

                    if(isMerged){
                        moveRightAndAdjust()
                    }else{
                        putRandom()
                        updateRefresh(true)
                    }
                }
            }
        }
    }


    fun moveTopAndAdjust() {
        rowColSize.value?.let { size->
            grid2dArrayObj.value?.let { grid->
                finalScore.value?.let { score->

                    var isMerged = false
                    for(col in 0 until size){
                        val temp = IntArray(size)
                        var index = 0

                        for(row in 0 until size){
                            if(grid[row][col]!=0){
                                temp[index] = grid[row][col]
                                index++
                            }
                        }
                        for(row in 0 until size){
                            grid[row][col] = temp[row]
                        }

                    }

                    for(col in 0 until size){
                        for(row in 0 until size-1){
                            if(grid[row][col]!=0 && grid[row][col]==grid[row+1][col]){
                                grid[row][col] *= 2
                                cellScore.value = grid[row][col]
                                finalScore.value = score + grid[row][col]
                                grid[row+1][col] = 0
                                isMerged = true
                            }
                        }
                    }

                    if(isMerged){
                        moveTopAndAdjust()
                    }else{
                        putRandom()
                        updateRefresh(true)
                    }

                }
            }
        }

    }

    fun moveBottomAndAdjust() {
        rowColSize.value?.let { size->
            grid2dArrayObj.value?.let { grid->
                finalScore.value?.let { score->
                    var isMerged = false
                    for(col in 0 until size){
                        val temp = IntArray(size)
                        var index = size-1
                        for(row in size-1 downTo 0){
                            if(grid[row][col]!=0){
                                temp[index] = grid[row][col]
                                index--
                            }
                        }
                        for(row in 0 until size){
                            grid[row][col] = temp[row]
                        }
                    }

                    for(col in 0 until size){
                        for(row in size-1 downTo 1){
                            if(grid[row][col]!=0 && grid[row][col]==grid[row-1][col]){
                                grid[row][col] *= 2
                                cellScore.value = grid[row][col]
                                finalScore.value = score + grid[row][col]
                                grid[row-1][col] = 0
                                isMerged = true
                            }
                        }
                    }

                    if(isMerged){
                        moveBottomAndAdjust()
                    }else{
                        putRandom()
                        updateRefresh(true)
                    }
                }
            }
        }
    }

    fun updateRefresh(isRefresh: Boolean) {
        this.isRefresh.value = isRefresh
    }

    fun updateGameOver(isGameOver: Boolean) {
        this.isGameOver.value = isGameOver
    }


}