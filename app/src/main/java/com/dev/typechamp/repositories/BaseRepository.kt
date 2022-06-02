package com.dev.typechamp.repositories

import android.content.Context
import android.content.SharedPreferences
import android.os.AsyncTask
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.dev.typechamp.utils.VolleyCallback
import com.dev.typechamp.utils.DIVISION_WORD_RANGE
import com.dev.typechamp.utils.sharedpreferences.AVG_TIME_TAG
import com.dev.typechamp.utils.sharedpreferences.HIGH_SCORE_TAG
import com.dev.typechamp.utils.sharedpreferences.NUM_CHALLENGES_TAG
import io.reactivex.Single
import org.json.JSONObject
import org.jsoup.Jsoup

interface BaseRepository {
    fun fetchChallenge(division: String, context: Context, callback: VolleyCallback)

    fun getDivisionHighScore(division: String): Single<Long>
    fun setDivisionHighScore(division: String, highScore: Long)

    fun getDivisionAvgTime(division: String): Single<Long>
    fun setDivisionAvgTime(division: String, avgTime: Long)

    fun getNumDivisionChallenges(division: String): Single<Int>
    fun setNumDivisionChallenges(division: String, numChallenges: Int)

    companion object {
        const val BASE_URL = "https://www.randomtext.me/api/gibberish/p-1/"
    }
}

class BaseRepositoryImpl(private val sharedPreferences: SharedPreferences) : BaseRepository {

    private fun parseJson(jsonObject: JSONObject): String {
        return Jsoup.parse(jsonObject.getString("text_out"))
            .selectFirst("p").text()
    }

    override fun fetchChallenge(division: String, context: Context, callback: VolleyCallback) {
        val queue = Volley.newRequestQueue(context)
        val wordCountMin =  DIVISION_WORD_RANGE.getValue(division)["lowerBound"]
        val wordCountMax = DIVISION_WORD_RANGE.getValue(division)["upperBound"]
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET,
            BaseRepository.BASE_URL + "$wordCountMin-$wordCountMax",
            null,
            Response.Listener<JSONObject> {
                callback.onSuccess(parseJson(it))
            },
            Response.ErrorListener { Log.e("ERROR", it.message ?: "") }
        )
        queue.add(jsonRequest)
    }

    override fun getDivisionHighScore(division: String): Single<Long> {
        return Single.just(sharedPreferences.getLong("$division/$HIGH_SCORE_TAG", 0))
    }

    override fun setDivisionHighScore(division: String, highScore: Long) {
        object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                with(sharedPreferences.edit()) {
                    putLong("$division/$HIGH_SCORE_TAG", highScore)
                    apply()
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null)
    }

    override fun getDivisionAvgTime(division: String): Single<Long> {
        return Single.just(sharedPreferences.getLong("$division/$AVG_TIME_TAG", 0))
    }

    override fun setDivisionAvgTime(division: String, avgTime: Long) {
        object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                with(sharedPreferences.edit()) {
                    putLong("$division/$AVG_TIME_TAG", avgTime)
                    apply()
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null)
    }

    override fun getNumDivisionChallenges(division: String): Single<Int> {
        return Single.just(sharedPreferences.getInt("$division/${NUM_CHALLENGES_TAG}", 0))
    }

    override fun setNumDivisionChallenges(division: String, numChallenges: Int) {
        object : AsyncTask<Unit, Unit, Unit>() {
            override fun doInBackground(vararg params: Unit?) {
                with(sharedPreferences.edit()) {
                    putInt("$division/$NUM_CHALLENGES_TAG", numChallenges)
                    apply()
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null)
    }
}