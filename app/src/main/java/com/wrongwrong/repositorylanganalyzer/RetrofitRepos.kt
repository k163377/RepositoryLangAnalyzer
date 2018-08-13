package com.wrongwrong.repositorylanganalyzer

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

fun getReposCall(id: String): Call<List<Repo>> {
    val gson = GsonBuilder().serializeNulls().create()
    val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://api.github.com/users/")
            .build()
    val service = retrofit.create(IGetRepos::class.java)
    return service.getRepos(id)
}

private interface IGetRepos{
    @GET("{id}/repos")
    fun getRepos(@Path("id") userID : String) : Call<List<Repo>>
}