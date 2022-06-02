package com.dev.typechamp.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import com.dev.typechamp.repositories.BaseRepository
import com.dev.typechamp.utils.VolleyCallback
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.zipWith

class BaseViewModel(private val baseRepository: BaseRepository) : ViewModel() {
    private val disposables = CompositeDisposable()

    fun getNewChallenge(division: String, context: Context, callback: VolleyCallback) {
        baseRepository.fetchChallenge(division, context, callback)
    }

    fun getDivisionHighScore(division: String): MutableLiveData<Long> {
        val liveData =  MutableLiveData<Long>()
        baseRepository.getDivisionHighScore(division).subscribe(
            { highScore: Long -> liveData.value = highScore },
            { error: Throwable -> Log.e("Error", error.toString()) }
        ).addTo(disposables)
        return liveData
    }

    fun setDivisionHighScore(division: String, highScore: Long) {
        baseRepository.setDivisionHighScore(division, highScore)
    }

    fun getDivisionAvgTime(division: String): MutableLiveData<Long> {
        val liveData = MutableLiveData<Long>()
        baseRepository.getDivisionAvgTime(division).subscribe(
            { avgTime: Long -> liveData.value = avgTime },
            { error: Throwable -> Log.e("Error", error.toString()) }
        ).addTo(disposables)
        return liveData
    }

    private fun calculateNewAvgTime(newTime: Long, oldAvgTime: Long, numChallenges: Int): Long {
        return (newTime + numChallenges * oldAvgTime) / (numChallenges + 1)
    }

    fun setDivisionAvgTime(division: String, time: Long) {
        /* uses the number of division challenges, old division average time, and the new time to
           compute and set the new avg time */

        val numChallengesSingle = baseRepository.getNumDivisionChallenges(division)
        val oldAvgTimeSingle = baseRepository.getDivisionAvgTime(division)

        numChallengesSingle.zipWith(
            oldAvgTimeSingle,
            fun(numChallenges: Int, oldAvgTime: Long): Long {
                baseRepository.setNumDivisionChallenges(division, numChallenges + 1)
                return calculateNewAvgTime(time, oldAvgTime, numChallenges)
            }
        ).subscribe {
                newAvg: Long -> baseRepository.setDivisionAvgTime(division, newAvg)
        }.addTo(disposables)
    }

    fun getNumDivisionChallenges(division: String): MutableLiveData<Int> {
        val liveData = MutableLiveData<Int>()
        baseRepository.getNumDivisionChallenges(division).subscribe(
            { numChallenges: Int -> liveData.value = numChallenges },
            { error: Throwable -> Log.e("Error", error.toString()) }
        ).addTo(disposables)
        return liveData
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}