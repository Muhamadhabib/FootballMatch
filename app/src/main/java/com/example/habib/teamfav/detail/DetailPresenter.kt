package com.example.habib.teamfav.detail

import com.example.habib.teamfav.api.ApiRepository
import com.example.habib.teamfav.api.TheSportDBApi
import com.example.habib.teamfav.model.MatchResponse
import com.example.habib.teamfav.model.TeamResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class DetailPresente(private val view:DetailView,
                     private val api:ApiRepository,
                     private val gson:Gson){
    fun getTeam(idTeam1:String?,idTeam2:String?){
//        doAsync {
//            val data1 = gson.fromJson(api.doRequest(TheSportDBApi.getTeamInfo(idTeam1)), TeamResponse::class.java)
//            val data2 = gson.fromJson(api.doRequest(TheSportDBApi.getTeamInfo(idTeam2)), TeamResponse::class.java)
//            uiThread {
//                view.hideLoading()
//                view.showlogo(data1.teams,data2.teams)
//            }
//        }
        view.showLoading()
            async(UI){
                try {
                    val data1 = bg {
                        gson.fromJson(api.doRequest(TheSportDBApi.getTeamInfo(idTeam1)),
                                TeamResponse::class.java)
                    }
                    val data2 = bg {
                        gson.fromJson(api.doRequest(TheSportDBApi.getTeamInfo(idTeam2)),
                                TeamResponse::class.java)
                    }
                    view.hideLoading()
                    view.showlogo(data1.await().teams,data2.await().teams)
                }catch (e:IOException){
                    view.hideLoading()
                    view.showError()
                }
            }

    }
    fun getMatch(idEvent:String?){
//        doAsync {
//            val data = gson.fromJson(api.doRequest(TheSportDBApi.getMatchDetail(idEvent)),MatchResponse::class.java)
//            uiThread {
//                view.hideLoading()
//                view.showDetail(data.events)
//            }
//        }
        view.showLoading()
            async(UI){
                try {
                    val data = bg {
                        gson.fromJson(api.doRequest(TheSportDBApi.getMatchDetail(idEvent)),
                                MatchResponse::class.java)
                    }
                    view.hideLoading()
                    view.showDetail(data.await().events)
                }catch (e:IOException){
                    view.hideLoading()
                    view.showError()
                }
            }

    }
}