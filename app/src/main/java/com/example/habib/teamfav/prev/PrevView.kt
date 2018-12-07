package com.example.habib.teamfav.prev

import com.example.habib.teamfav.model.Match

interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Match>)
    fun showError()
}