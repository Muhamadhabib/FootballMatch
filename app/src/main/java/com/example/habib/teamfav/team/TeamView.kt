package com.example.habib.teamfav.team

import com.example.habib.teamfav.model.Teams

interface TeamView{
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data : List<Teams>)
    fun showError()
}