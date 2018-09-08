package com.wrongwrong.repositorylanganalyzer

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.Serializable

val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
        .baseUrl("https://api.github.com/users/")
        .build()

data class Owner(
        var login: String,
        var id: Long,
        var node_id: String,
        var avatar_url: String,
        var gravatar_id: String,
        var url: String,
        var html_url: String,
        var followers_url: String,
        var following_url: String,
        var gists_url: String,
        var starred_url: String,
        var subscriptions_url: String,
        var organizations_url: String,
        var repos_url: String,
        var events_url: String,
        var received_events_url: String,
        var type: String,
        var site_admin: Boolean
): Serializable

data class Permissions(
        var admin: Boolean,
        var push: Boolean,
        var pull: Boolean
): Serializable

data class License(
        var key: String,
        var name: String,
        var spdx_id: String,
        var url: String,
        var name_id: String
): Serializable

data class Repo(
        var id: Long,
        var node_id: String,
        var name: String,
        var full_name: String,

        var owner: Owner,

        var private: Boolean,
        var html_url: String,
        var description: String?,
        var fork: Boolean,
        var url: String,
        var archive_url: String,
        var assignees_url: String,
        var blobs_url: String,
        var branches_url: String,
        var collaborators_url: String,
        var comments_url: String,
        var commits_url: String,
        var compare_url: String,
        var contents_url: String,
        var contributors_url: String,
        var deployments_url: String,
        var downloads_url: String,
        var events_url: String,
        var forks_url: String,
        var git_commits_url: String,
        var git_refs_url: String,
        var git_tags_url: String,
        var git_url: String,
        var issue_comment_url: String,
        var issue_events_url: String,
        var issues_url: String,
        var keys_url: String,
        var labels_url: String,
        var languages_url: String,
        var merges_url: String,
        var milestones_url: String,
        var notifications_url: String,
        var pulls_url: String,
        var releases_url: String,
        var ssh_url: String,
        var stargazers_url: String,
        var statuses_url: String,
        var subscribers_url: String,
        var subscription_url: String,
        var tags_url: String,
        var teams_url: String,
        var trees_url: String,
        var clone_url: String,
        var mirror_url: String,
        var hooks_url: String,
        var svn_url: String,
        var homepage: String,
        var language: String?,
        var forks_count: Int,
        var stargazers_count: Int,
        var watchers_count: Int,
        var size: Int,
        var default_branch: String,
        var open_issues_count: Int,
        var topics: List<String>,
        var has_issues: Boolean,
        var has_projects: Boolean,
        var has_wiki: Boolean,
        var has_pages: Boolean,
        var has_downloads: Boolean,
        var archived: Boolean,
        var pushed_at: String,
        var created_at: String,
        var updated_at: String,
        var permissions: Permissions,
        var allow_rebase_merge: Boolean,
        var allow_squash_merge: Boolean,
        var allow_merge_commit: Boolean,
        var subscribers_count: Int,
        var network_count: Int,
        var license: License
): Serializable

interface IGetRepos{
    @GET("{id}/repos")
    fun getRepos(@Path("id") userID : String) : Call<List<Repo>>
}
val service: IGetRepos = retrofit.create(IGetRepos::class.java)

fun getReposCall(id: String): Call<List<Repo>> {
    return service.getRepos(id)
}