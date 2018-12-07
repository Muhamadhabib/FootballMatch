package com.example.habib.teamfav.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.habib.teamfav.favorite.FavoFragment
import com.example.habib.teamfav.R
import com.example.habib.teamfav.R.id.*
import com.example.habib.teamfav.favorite.FavFragment
import com.example.habib.teamfav.next.NextFragment
import com.example.habib.teamfav.prev.PrevFragment
import com.example.habib.teamfav.team.TeamFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                prev ->{
                    loadPrevFragment(savedInstanceState)
                }
                next ->{
                    loadnextFragment(savedInstanceState)
                }
                team ->{
                    loadTeamFragment(savedInstanceState)
                }
                fav ->{
                    loadfavFragment(savedInstanceState)
                }
                favo ->{
                    loadfavoFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = prev
    }
    private fun loadPrevFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, PrevFragment(), PrevFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadnextFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, NextFragment(), NextFragment::class.java.simpleName)
                    .commit()
        }
    }
    private fun loadfavFragment(savedInstanceState: Bundle?){
        if(savedInstanceState == null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container,FavFragment(),FavFragment::class.java.simpleName)
                    .commit()
        }
    }
    private fun loadfavoFragment(savedInstanceState: Bundle?){
        if(savedInstanceState == null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FavoFragment(), FavoFragment::class.java.simpleName)
                    .commit()
        }
    }
    private fun loadTeamFragment(savedInstanceState: Bundle?){
        if(savedInstanceState == null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, TeamFragment(),TeamFragment::class.java.simpleName)
                    .commit()
        }
    }
}
