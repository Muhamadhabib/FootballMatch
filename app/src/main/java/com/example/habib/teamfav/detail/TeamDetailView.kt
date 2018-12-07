package com.example.habib.teamfav.detail

import com.example.habib.teamfav.model.Teams

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data: List<Teams>)
    fun showError()
}