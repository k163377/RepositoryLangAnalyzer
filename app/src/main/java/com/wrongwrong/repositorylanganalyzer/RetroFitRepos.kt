package com.wrongwrong.repositorylanganalyzer

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

fun getReposCall(id: String): Call<List<Repo>> {
    val gson = GsonBuilder().serializeNulls().create()
    var retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://api.github.com/users/")
            .build()
    var service = retrofit.create(IGetRepos::class.java)
    var call = service.getRepos(id)
    return call
}

private interface IGetRepos{
    @GET("{id}/repos")
    fun getRepos(@Path("id") userID : String) : Call<List<Repo>>
}