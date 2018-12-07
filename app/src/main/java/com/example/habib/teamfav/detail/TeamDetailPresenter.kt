package com.example.habib.teamfav.detail

import com.example.habib.teamfav.api.ApiRepository
import com.example.habib.teamfav.api.TheSportDBApi
import com.example.habib.teamfav.model.TeamsResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.IOException

class TeamDetailPresenter(private val view : TeamDetailView,
                          private val api : ApiRepository,
                          private val gson: Gson){
    fun getTeamDetail(teamId : String){
        view.showLoading()
            doAsync {
                try {
                    val data = gson.fromJson(api.doRequest(TheSportDBApi.getTeamInfo(teamId)),
                            TeamsResponse::class.java
                    )
                    uiThread {
                        view.hideLoading()
                        view.showTeamList(data.teams)
                    }
                }catch (e:IOException){
                    uiThread {
                        view.hideLoading()
                        view.showError()
                    }
                }
            }

    }
}