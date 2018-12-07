package com.example.habib.teamfav.api

import android.net.Uri
import com.example.habib.teamfav.BuildConfig

object TheSportDBApi {
    val Id = 4328
    fun getTeamInfo(idTeam:String?):String{
        return BuildConfig.BASE_URL+"api/v1/json/${BuildConfig.API_KEY}"+"/lookupteam.php?id="+idTeam
    }
    fun getPrevMatch(idLeague:String?):String{
        return BuildConfig.BASE_URL+"api/v1/json/${BuildConfig.API_KEY}"+"/eventspastleague.php?id="+idLeague
    }
    fun getNextMatch(idLeague: String?):String{
        return BuildConfig.BASE_URL+"api/v1/json/${BuildConfig.API_KEY}"+"/eventsnextleague.php?id="+idLeague
    }
    fun getMatchDetail(idEvent:String?):String{
        return BuildConfig.BASE_URL+"api/v1/json/${BuildConfig.API_KEY}"+"/lookupevent.php?id="+idEvent
    }
    fun getTeam(league:String?):String{
        return BuildConfig.BASE_URL+"api/v1/json/${BuildConfig.API_KEY}"+"/search_all_teams.php?l="+league
    }
    fun getTeambySearch(event: String?): String{
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.API_KEY}" + "/searchteams.php?t="+event
    }
    fun getEventbySearch(event: String?): String{
        return BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.API_KEY}" + "/searchevents.php?e="+event
    }
    fun getPlayer(team:String?):String{
        return BuildConfig.BASE_URL+"api/v1/json/${BuildConfig.API_KEY}"+"/searchplayers.php?t="+team
    }
    fun getDetailPlayer(name:String?):String{
        return BuildConfig.BASE_URL+"api/v1/json/${BuildConfig.API_KEY}"+"/searchplayers.php?p="+name
    }
}