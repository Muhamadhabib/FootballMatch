package com.example.habib.teamfav.team

import com.example.habib.teamfav.api.ApiRepository
import com.example.habib.teamfav.api.TheSportDBApi
import com.example.habib.teamfav.model.Match
import com.example.habib.teamfav.model.MatchResponse
import com.example.habib.teamfav.model.Teams
import com.example.habib.teamfav.model.TeamsResponse
import com.example.habib.teamfav.prev.PrevPresenter
import com.example.habib.teamfav.prev.TeamsView
import com.example.habib.teamfav.util.ContextProviderTest
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamPresenterTest {
    @Mock
    private lateinit var view: TeamView
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var gson: Gson
    private var  id:String = 4328.toString()
    private lateinit var presenter: TeamPresenter
    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = TeamPresenter(view,apiRepository,gson, ContextProviderTest())
    }
    @Test
    fun getTeamList() {
        val team : MutableList<Teams> = mutableListOf()
        val response = TeamsResponse(team)
        val league = "English Premiere League"
        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeam(league)),
                TeamsResponse::class.java
        )).thenReturn(response)

        presenter.getTeamList(league)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeamList(team)
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun getTeamSearch() {
        val team : MutableList<Teams> = mutableListOf()
        val response = TeamsResponse(team)
        val name = "Arsenal"
        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getTeambySearch(name)),
                TeamsResponse::class.java
        )).thenReturn(response)

        presenter.getTeamSearch(name)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeamList(team)
        Mockito.verify(view).hideLoading()
    }
}