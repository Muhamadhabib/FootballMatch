package com.example.habib.teamfav.favorite

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.example.habib.teamfav.R
import com.example.habib.teamfav.db.Favorite
//import com.example.habib.teamfav.db.Favorite.Companion.EVENT_ID
import com.example.habib.teamfav.db.database
//import com.example.habib.teamfav.detail.DETAIL
import com.example.habib.teamfav.detail.DetailActivity
//import com.example.habib.teamfav.util.DETAIL
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout


class FavFragment : Fragment(),AnkoComponent<Context> {
    private var favorite: MutableList<Favorite> = mutableListOf()
    private lateinit var listEvent: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var adapter: FavoriteAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = FavoriteAdapter(favorite){
            ctx.startActivity<DetailActivity>("id" to "${it.idEvent}","home" to "${it.idHome}","away" to "${it.idAway}")
        }
        listEvent.adapter = adapter
        showFavorite()
        swipeRefresh.onRefresh {
            favorite.clear()
            showFavorite()
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }
    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams(width = matchParent,height = wrapContent)
            orientation = LinearLayout.VERTICAL

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorPrimaryDark,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent,height = wrapContent)
                    listEvent = recyclerView {
                        lparams(width = matchParent,height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                }
            }
        }
    }
    private fun showFavorite(){
        context?.database?.use{
            swipeRefresh.isRefreshing = false
            val result = select(Favorite.TABLE_FAVORITE)
            val fav = result.parseList(classParser<Favorite>())
            favorite.addAll(fav)
            adapter.notifyDataSetChanged()
        }
    }
}
