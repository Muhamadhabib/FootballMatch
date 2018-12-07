package com.example.habib.teamfav.api

import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class ApiRepositoryTest {

    @Test
    fun doRequest() {
        val apiRepository = mock(ApiRepository::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/eventspastleague.php?id=4328"
        val url2 = "https://www.thesportsdb.com/api/v1/json/1/eventsnextleague.php?id=4328"
        val url3 = "https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=English%20Premier%20League"
        apiRepository.doRequest(url)
        apiRepository.doRequest(url2)
        apiRepository.doRequest(url3)
        verify(apiRepository).doRequest(url)
        verify(apiRepository).doRequest(url2)
        verify(apiRepository).doRequest(url3)
    }
}