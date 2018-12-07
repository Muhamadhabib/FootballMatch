package com.example.habib.teamfav.detail

import com.example.habib.teamfav.api.ApiRepository
import com.example.habib.teamfav.api.TheSportDBApi
import com.example.habib.teamfav.model.PlayerResponse
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PlayerPresenter(private val view : PlayerView,
                      private val api:ApiRepository,
                      private val gson: Gson){
    fun getPlayer(team: String){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(api.doRequest(TheSportDBApi.getPlayer(team)),
                    PlayerResponse::class.java)
            uiThread {
                view.hideLoading()
                view.showPlayer(data.player)
            }
        }
    }
    fun getDetailPlayer(name: String){
        view.showLoading()
        doAsync {
            val data = gson.fromJson(api.doRequest(TheSportDBApi.getDetailPlayer(name)),
                    PlayerResponse::class.java)
            uiThread {
                view.hideLoading()
                view.showPlayer(data.player)
            }
        }
    }
}