package com.example.habib.teamfav.detail

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.habib.teamfav.R
import com.example.habib.teamfav.R.id.p_img
import com.example.habib.teamfav.api.ApiRepository
import com.example.habib.teamfav.model.Player
import com.example.habib.teamfav.util.invisible
import com.example.habib.teamfav.util.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout

class PlayerActivity : AppCompatActivity(),PlayerView {
    private var player : MutableList<Player> = mutableListOf()
    private lateinit var presenter: PlayerPresenter
    private lateinit var adapter : PlayerAdapter
    private lateinit var swipe:SwipeRefreshLayout
    private lateinit var progress:ProgressBar
    private lateinit var listevent:RecyclerView
    private lateinit var img:ImageView
    private lateinit var name:TextView
    private lateinit var pos:TextView
    private lateinit var team: String
    private lateinit var play : Player
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_player)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        team = intent.getStringExtra("club")
        adapter = PlayerAdapter(player){
            //val name = play.strPlayer.toString()
            startActivity<PlayerDetailActivity>("name" to "${it.strPlayer}")
        }
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            swipe = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)
                    backgroundColor = ContextCompat.getColor(ctx, R.color.shadow)
                    listevent = recyclerView {
                        id = R.id.listEvent
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progress = progressBar {
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }
        //team = intent.getStringExtra("club")
        listevent.adapter = adapter
        val api = ApiRepository()
        val gson = Gson()
        presenter = PlayerPresenter(this,api,gson)
        presenter.getPlayer(team)
        swipe.onRefresh {
            presenter.getPlayer(team)
        }
    }

    override fun showLoading() {
        progress.visible()
    }

    override fun hideLoading() {
        progress.invisible()
    }

    override fun showPlayer(data: List<Player>) {
        swipe.isRefreshing = false
        player.clear()
        player.addAll(data)
        adapter.notifyDataSetChanged()
//        swipe.isRefreshing = false
//        Picasso.get().load(data[0].strCutout).into(img)
//        name.text = data[0].strPlayer
//        pos.text = data[0].strPosition
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return  when(item.itemId){
            android.R.id.home ->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
