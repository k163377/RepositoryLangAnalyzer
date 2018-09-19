package com.wrongwrong.repositorylanganalyzer

import android.os.Build
import android.support.annotation.RequiresApi
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class GitHubUtil {
    companion object {
        fun makeRankOfLangs(Repos: List<Repo>?): List<Pair<String, Int>>{
            var map = HashMap<String, Int>()
            var temp: String
            for(repo in Repos!!){ //nullチェックは外でやること
                if(null == repo.language){
                    temp = "OtherLanguage"
                    repo.language = temp
                } else {
                    temp = repo.language!!
                } //言語にnullを入れたくないので、チェックはここでやる
                if(map.contains(repo.language)) map[temp] = map[temp]!!+1
                else map[temp] = 1
            }
            return map.toList().sortedByDescending { pair -> pair.second }
        }
    }
}