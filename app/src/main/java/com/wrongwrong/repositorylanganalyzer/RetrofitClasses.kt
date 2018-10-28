package com.wrongwrong.repositorylanganalyzer

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.Serializable

private val client: OkHttpClient = OkHttpClient
        .Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .build()

private val retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
        .baseUrl("https://api.github.com/users/")
        .build()

data class Owner(
        val login: String,
        val id: Long,
        val node_id: String,
        val avatar_url: String,
        val gravatar_id: String,
        val url: String,
        val html_url: String,
        val followers_url: String,
        val following_url: String,
        val gists_url: String,
        val starred_url: String,
        val subscriptions_url: String,
        val organizations_url: String,
        val repos_url: String,
        val events_url: String,
        val received_events_url: String,
        val type: String,
        val site_admin: Boolean
): Serializable
data class Permissions(
        val admin: Boolean,
        val push: Boolean,
        val pull: Boolean
): Serializable
data class License(
        val key: String,
        val name: String,
        val spdx_id: String,
        val url: String,
        val name_id: String
): Serializable

data class Repo(
        val id: Long,
        val node_id: String,
        val name: String,
        val full_name: String,
        val owner: Owner,
        val private: Boolean,
        val html_url: String,
        val description: String?,
        val fork: Boolean,
        val url: String,
        val archive_url: String,
        val assignees_url: String,
        val blobs_url: String,
        val branches_url: String,
        val collaborators_url: String,
        val comments_url: String,
        val commits_url: String,
        val compare_url: String,
        val contents_url: String,
        val contributors_url: String,
        val deployments_url: String,
        val downloads_url: String,
        val events_url: String,
        val forks_url: String,
        val git_commits_url: String,
        val git_refs_url: String,
        val git_tags_url: String,
        val git_url: String,
        val issue_comment_url: String,
        val issue_events_url: String,
        val issues_url: String,
        val keys_url: String,
        val labels_url: String,
        val languages_url: String,
        val merges_url: String,
        val milestones_url: String,
        val notifications_url: String,
        val pulls_url: String,
        val releases_url: String,
        val ssh_url: String,
        val stargazers_url: String,
        val statuses_url: String,
        val subscribers_url: String,
        val subscription_url: String,
        val tags_url: String,
        val teams_url: String,
        val trees_url: String,
        val clone_url: String,
        val mirror_url: String,
        val hooks_url: String,
        val svn_url: String,
        val homepage: String,
        var language: String?,
        val forks_count: Int,
        val stargazers_count: Int,
        val watchers_count: Int,
        val size: Int,
        val default_branch: String,
        val open_issues_count: Int,
        val topics: List<String>,
        val has_issues: Boolean,
        val has_projects: Boolean,
        val has_wiki: Boolean,
        val has_pages: Boolean,
        val has_downloads: Boolean,
        val archived: Boolean,
        val pushed_at: String,
        val created_at: String,
        val updated_at: String,
        val permissions: Permissions,
        val allow_rebase_merge: Boolean,
        val allow_squash_merge: Boolean,
        val allow_merge_commit: Boolean,
        val subscribers_count: Int,
        val network_count: Int,
        val license: License
): Serializable

interface IGetRepos{
    @GET("{id}/repos")
    fun getRepos(@Path("id") userID : String,
                 @Query("limit") limit: Int,
                 @Query("offset") offset: Long
    ) : Call<List<Repo>>
}
val reposService: IGetRepos = retrofit.create(IGetRepos::class.java)