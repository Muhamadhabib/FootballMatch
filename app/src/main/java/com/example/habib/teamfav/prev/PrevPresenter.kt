package com.example.habib.teamfav.prev

import com.example.habib.teamfav.api.ApiRepository
import com.example.habib.teamfav.api.TheSportDBApi
import com.example.habib.teamfav.model.MatchResponse
import com.example.habib.teamfav.search.SearchResponse
import com.example.habib.teamfav.util.CoroutineContextProvider
import com.google.gson.Gson
//import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import java.io.IOException
import java.lang.Exception
import java.sql.Connection
import javax.net.ssl.HttpsURLConnection

class PrevPresenter(private val view:TeamsView,
                    private val api:ApiRepository,
                    private val gson:Gson,
                    private val context: CoroutineContextProvider =
                            CoroutineContextProvider()){

    fun getPrevList(id:String?){
        view.showLoading()
//        doAsync {
//            val data = gson.fromJson(
//                    api.doRequest(TheSportDBApi.getPrevMatch()),
//                    MatchResponse::class.java
//            )
//            uiThread {
//                view.hideLoading()
//                view.showTeamList(data.events)
//            }
//        }
        async(context.main){
            try {
                val data = bg {
                    gson.fromJson(api.doRequest(TheSportDBApi.getPrevMatch(id)),
                            MatchResponse::class.java)
                }
                view.showTeamList(data.await().events)
                view.hideLoading()
            }catch (e:IOException){
                view.showError()
                view.hideLoading()
            }
        }
    }
    fun getNextList(id: String?){
        view.showLoading()
//        doAsync {
//            val data = gson.fromJson(
//                    api.doRequest(TheSportDBApi.getNextMatch()),
//                    MatchResponse::class.java
//            )
//            uiThread {
//                view.hideLoading()
//                view.showTeamList(data.events)
//            }
//        }
        async(context.main){
            try {
                val data = bg {
                    gson.fromJson(api.doRequest(TheSportDBApi.getNextMatch(id)),
                            MatchResponse::class.java)
                }
                view.showTeamList(data.await().events)
                view.hideLoading()
            }catch (e:IOException){
                view.showError()
                view.hideLoading()
            }
        }
    }
    fun getEventSearch(name:String?){
        view.showLoading()
        async(context.main){
            try {
                val data = bg {
                    gson.fromJson(api.doRequest(TheSportDBApi.getEventbySearch(name)),
                            SearchResponse::class.java)
                }
                view.showTeamList(data.await().event)
                view.hideLoading()
            }catch (e:IOException){
                view.showError()
                view.hideLoading()
            }
        }
    }
}