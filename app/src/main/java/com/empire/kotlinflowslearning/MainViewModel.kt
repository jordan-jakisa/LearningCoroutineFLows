package com.empire.kotlinflowslearning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    init {
        collectFlow()
    }

    //Cold flow: will not emit any values if there are no collectors attached.
    val countDownFlow = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue
        emit(currentValue)
        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }

    private fun collectFlow() {
        viewModelScope.launch {
            countDownFlow
                //terminal flow operators
                .filter { time ->
                    time % 2 == 0
                    //filter flow operator, filters a list basing on a defined criteria.
                }
                .map { time ->
                    time * time
                    //map: operator takes the time value and multiplies it by itself
                }.onEach { time ->
                    println(time)
                    // onEach operator is similar to the collect operator but it doesn't conclude the flow
                }
                .collect { time ->
                    println("Current time is: $time")
                }

            //Terminal flow operator: Counts the number of items matching the condition
            val count = countDownFlow.count {
                it % 2 == 0
            }
            println("The count is $count")

            //Terminal flow operator: Counts the number of items matching the condition
            val reduce = countDownFlow
                // .fold(100)
                .reduce { accumulator, value ->
                    accumulator + value
                }
            println("Reduce is: $reduce")

            val indexes = (0..5).asFlow()

            // .buffer() ensures that code block in flow and collect run in different coroutines.
            // .conflate() opposite to buffer
        }

        //does the same role as the above block of code without the flow operators
        countDownFlow.onEach { time ->
            println(time)
        }.launchIn(viewModelScope)
    }
}