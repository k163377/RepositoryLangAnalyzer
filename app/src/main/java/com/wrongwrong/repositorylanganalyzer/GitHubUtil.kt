package com.wrongwrong.repositorylanganalyzer

import android.os.Build
import android.support.annotation.RequiresApi
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class GitHubUtil {
    companion object {
        // API叩いてJSON取得
        @RequiresApi(Build.VERSION_CODES.N)
        fun getJsonFromURL(url: URL): String {
            var resultJson = ""
            val br = BufferedReader(InputStreamReader( url.openStream() ))

            for(line in br.lines()) resultJson += line

            return resultJson
        }

        // リポジトリ一覧をJSONから取得
        fun getRepsFromJSON(json: String): ArrayList<String>{
            val full_name = "full_name\":\""

            val split = json.split("\",\"")
            val regex = Regex("$full_name.+")
            val reps = ArrayList<String>()
            var result: MatchResult?

            for(str in split){
                result = regex.find(str)
                if(result != null) reps.add(result.value.substring(full_name.length))
            }
            return reps
        }

        //jsonからdescriptionの項を取得
        fun getDescriptionFromJSON(json: String): ArrayList<String>{
            var description = "description\":"

            var split = json.split(",\"")
            var regex = Regex("$description.+")
            var descriptions = ArrayList<String>()
            var temp: String

            for(str in split){
                var result = regex.find(str)
                if(result != null){
                    temp = result.value.substring(description.length)
                    if(temp.last() == '"') temp = temp.substring(0, temp.length-1).substring(1)
                    descriptions.add(temp)
                }
            }
            return descriptions
        }

        //jsonから言語の項を取得
        fun getLanguagesFromJSON(json: String): ArrayList<String>{
            var language = "language\":"

            var split = json.split(",\"")
            var regex = Regex("$language.+")
            var languages = ArrayList<String>()

            for(str in split){
                var result = regex.find(str)
                if(result != null){
                    var temp = result.value.substring(language.length)
                    if(temp.equals("null")) languages.add("Other Language")
                    else languages.add(temp.substring(0, temp.length-1).substring(1))
                }
            }
            return languages
        }

        //言語の項の列から、どの言語がどの順で多いかをソート済みリストとして返す
        fun makeRankOfLangs(langs:  ArrayList<String>): List<Pair<String, Int>>{
            var map = HashMap<String, Int>()
            for(lang in langs){
                if(map.contains(lang)) map.set(lang, map.get(lang)!! +1)
                else map.set(lang, 1)
            }
            return map.toList().sortedByDescending { pair -> pair.second }
        }
    }
}