package com.example.habib.teamfav.team

import com.example.habib.teamfav.api.ApiRepository
import com.example.habib.teamfav.api.TheSportDBApi
import com.example.habib.teamfav.model.TeamsResponse
import com.example.habib.teamfav.search.TeamSearchResponse
import com.example.habib.teamfav.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class TeamPresenter(private val view:TeamView,
                    private val api: ApiRepository,
                    private val gson: Gson,
                    private val context: CoroutineContextProvider =
                            CoroutineContextProvider()){
    fun getTeamList(league : String?){
//        view.showLoading()
//        doAsync {
//            val data = gson.fromJson(api.doRequest(TheSportDBApi.getTeam(league)),
//                    TeamsResponse::class.java)
//            uiThread {
//                view.hideLoading()
//                view.showTeamList(data.teams)
//            }
//        }
        view.showLoading()
        async(context.main){
            try {
                val data = bg {
                    gson.fromJson(api.doRequest(TheSportDBApi.getTeam(league)),
                            TeamsResponse::class.java)
                }
                view.showTeamList(data.await().teams)
                view.hideLoading()
            }catch (e:IOException){
                view.showError()
                view.hideLoading()
            }
        }
    }
    fun getTeamSearch(name: String?){
        view.showLoading()
//        doAsync {
//            val data = gson.fromJson(api
//                    .doRequest(TheSportDBApi.getTeambySearch(name)),
//                    TeamSearchResponse::class.java
//            )
//
//            uiThread {
//                view.hideLoading()
//                view.showTeamList(data.teams)
//            }
//        }
        async(context.main){
            try {
                val data = bg {
                    gson.fromJson(api.doRequest(TheSportDBApi.getTeambySearch(name)),
                            TeamsResponse::class.java)
                }
                view.hideLoading()
                view.showTeamList(data.await().teams)
            }catch (e:IOException){
                view.showError()
                view.hideLoading()
            }
        }
    }
}