package com.wrongwrong.repositorylanganalyzer

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException

class RetroFitRepos {
    fun GetRepos(id: String): List<Repo>? {
        val gson = GsonBuilder().serializeNulls().create()
        var retrofit: Retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://api.github.com/users/")
                .build()
        var service = retrofit.create(IGetRepos::class.java)
        var call = service.GetRepos(id)
        var arr: List<Repo>? = null
        call.enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>?, response: Response<List<Repo>>?) {
                try{
                    arr = response!!.body()
                    //Log.d("onResponse", arr!![0].full_name)
                }catch (e: IOException){
                    //Log.d("onResponse", "IOException")
                }
            }

            override fun onFailure(call: Call<List<Repo>>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
        return arr
    }

    interface IGetRepos{
        @GET("{id}/repos")
        fun GetRepos(@Path("id") userID : String) : Call<List<Repo>>
    }
}