package com.wrongwrong.repositorylanganalyzer

import android.util.Log
import com.google.gson.GsonBuilder
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException

fun getReposCall(id: String): Call<List<Repo>> {
    val gson = GsonBuilder().serializeNulls().create()
    var retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://api.github.com/users/")
            .build()
    var service = retrofit.create(IGetRepos::class.java)
    var call = service.getRepos(id)
    return call
    /*call.enqueue(object : Callback<List<Repo>> {
        override fun onResponse(call: Call<List<Repo>>?, response: Response<List<Repo>>?) {
            try{
                arr = response!!.body()
                for(repo in arr!!) Log.d("Repos", repo.full_name)
               // Log.d("onResponse", arr!![0].full_name)
            }catch (e: IOException){
                Log.d("onResponse", "IOException")
            }
        }

        override fun onFailure(call: Call<List<Repo>>?, t: Throwable?) {
            Log.d("onFailure", "")
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    })*/
}

interface IGetRepos{
    @GET("{id}/repos")
    fun getRepos(@Path("id") userID : String) : Call<List<Repo>>
}