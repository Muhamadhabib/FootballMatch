package com.example.habib.teamfav.prev

import com.example.habib.teamfav.api.ApiRepository
import com.example.habib.teamfav.api.TheSportDBApi
import com.example.habib.teamfav.model.Match
import com.example.habib.teamfav.model.MatchResponse
import com.example.habib.teamfav.search.SearchResponse
import com.example.habib.teamfav.util.ContextProviderTest
import com.example.habib.teamfav.util.CoroutineContextProvider
import com.google.gson.Gson
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class PrevPresenterTest {
    @Mock
    private lateinit var view: TeamsView
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var gson: Gson
    private var  id:String = 4328.toString()
    private lateinit var prevPresenter: PrevPresenter
    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        prevPresenter = PrevPresenter(view,apiRepository,gson, ContextProviderTest())
    }
    @Test
    fun getPrevList() {
        val match : MutableList<Match> = mutableListOf()
        val response = MatchResponse(match)
        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getPrevMatch(id)),
                MatchResponse::class.java
        )).thenReturn(response)

        prevPresenter.getPrevList(id)

        verify(view).showLoading()
        verify(view).showTeamList(match)
        verify(view).hideLoading()
    }

    @Test
    fun getNextList() {
        val match : MutableList<Match> = mutableListOf()
        val response = MatchResponse(match)
        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDBApi.getNextMatch(id)),
                MatchResponse::class.java
        )).thenReturn(response)

        prevPresenter.getNextList(id)

        verify(view).showLoading()
        verify(view).showTeamList(match)
        verify(view).hideLoading()
    }
    @Test
    fun getEventSearch(){
        val match : MutableList<Match> = mutableListOf()
        val response = SearchResponse(match)
        val name  = "Arsenal"
        `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getEventbySearch(name)),
                SearchResponse::class.java)).thenReturn(response)
        prevPresenter.getEventSearch(name)
        verify(view).showLoading()
        verify(view).showTeamList(match)
        verify(view).hideLoading()
    }
}