package com.example.habib.teamfav.detail

import com.example.habib.teamfav.model.Player

interface PlayerView{
    fun showLoading()
    fun hideLoading()
    fun showPlayer(data: List<Player>)
}