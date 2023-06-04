package com.bhagyawant.gameLib.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "GameViewModel"

@HiltViewModel
class GameViewModel @Inject constructor(): ViewModel() {

    private val grid2dArrayObj  = MutableLiveData<Array<IntArray>>()
    val grid2dArrayLiveData : LiveData<Array<IntArray>>
    get() = grid2dArrayObj

    init {

    }


}