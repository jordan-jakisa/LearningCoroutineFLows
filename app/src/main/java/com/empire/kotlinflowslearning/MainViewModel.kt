package com.empire.kotlinflowslearning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    init {
        collectFlow()
    }

    //Cold flow: will not emit any values if there are no collectors attached.
    val countDownFlow = flow<Int>{
        val startingValue = 10
        var currentValue = startingValue
        emit(currentValue)
        while (currentValue > 0){
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

    private fun collectFlow(){
        viewModelScope.launch {
            countDownFlow.collect{ time ->
                println("Current time is: $time")
            }
        }
    }
}