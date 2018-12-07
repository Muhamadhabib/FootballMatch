package com.example.habib.teamfav.detail

import com.example.habib.teamfav.model.Match
import com.example.habib.teamfav.model.Team

interface DetailView {
    fun showLoading()
    fun hideLoading()
    fun showDetail(data: List<Match>)
    fun showlogo(data1: List<Team>, data2: List<Team>)
    fun showError()
}