package com.yolisstorm.chargerslist.view.citiesView

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yolisstorm.datasource.IRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.replay
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch

class CitiesListViewModel (
    private val repository: IRepository
): ViewModel() {

    private val _citiesList = MutableStateFlow<List<CityNameValueClass>>(emptyList())
    val citiesList = _citiesList.asStateFlow()

    private val countdownTimer = CountdownTimer(viewModelScope)
    val countDownTimerFlow: Flow<Int> = countdownTimer.counterStateFlow

    fun toggleCountDownTimerStart() = countdownTimer.toggleTime(COUNT_DOWN_LATCH) {
        _isTimeToMoveAtNextScreen.emit(true)
        Log.d("CitiesListViewModel", "Toggle to true")
    }

    fun updateCitiesList() {
        viewModelScope.launch {
            repository
                .getCitiesList()
                .collect { list ->
                    _citiesList.value = list.map { CityNameValueClass(it) }
                }
        }
    }

    private val _isTimeToMoveAtNextScreen = MutableStateFlow(false)
    val isTimeToMoveAtNextScreen: StateFlow<Boolean> = _isTimeToMoveAtNextScreen.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5L),
        initialValue = false
    )


    companion object {
        private const val COUNT_DOWN_LATCH = 5
        private const val COUNT_DOWN_DELAY_MILLIS = 1000L
    }

}

class CountdownTimer(
    private val timerScope: CoroutineScope
) {

    private var _counterStateFlow = MutableStateFlow(0)
    val counterStateFlow: Flow<Int> = _counterStateFlow

    private var job: Job? = null

    fun toggleTime(totalSeconds: Int, onCompletion: suspend () -> Unit) {
        if (job == null) {
            job = timerScope.launch {
                initCountdownTimer(totalSeconds)
                    .onCompletion {  ex ->
                        if (ex == null) {
                            onCompletion()
                        }
                    }
                    .collect { _counterStateFlow.emit(it) }
            }
        } 
    }

    private fun initCountdownTimer(totalSeconds: Int): Flow<Int> {
        return (1..totalSeconds).asFlow()
            .onEach {
                delay(COUNT_DOWN_DELAY_MILLIS)
            }
            .onStart { emit(0) }
            .conflate()
    }

    companion object {
        private const val COUNT_DOWN_LATCH = 5
        private const val COUNT_DOWN_DELAY_MILLIS = 1000L
    }

}