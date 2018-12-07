package com.example.habib.teamfav.team


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.*
import android.widget.*

import com.example.habib.teamfav.R
import com.example.habib.teamfav.R.array.league
import com.example.habib.teamfav.R.color.colorAccent
import com.example.habib.teamfav.api.ApiRepository
import com.example.habib.teamfav.detail.TeamDetailActivity
import com.example.habib.teamfav.model.Match
import com.example.habib.teamfav.model.Teams
import com.example.habib.teamfav.prev.TeamsView
import com.example.habib.teamfav.util.invisible
import com.example.habib.teamfav.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.support.v4.toast


class TeamFragment : Fragment(), AnkoComponent<Context>,TeamView {

    private var teams : MutableList<Teams> = mutableListOf()
    private lateinit var presenter : TeamPresenter
    private lateinit var adapter : TeamAdapter
    private lateinit var spinner : Spinner
    private lateinit var listevent : RecyclerView
    private lateinit var progress : ProgressBar
    private lateinit var swipe : SwipeRefreshLayout
    private lateinit var leagueName : String
    private lateinit var search: SearchView
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        val spinnerItem = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(ctx,android.R.layout.simple_spinner_dropdown_item,spinnerItem)
        spinner.adapter = spinnerAdapter

        adapter = TeamAdapter(teams){
            ctx.startActivity<TeamDetailActivity>("id" to "${it.teamId}","name" to "${it.teamName}")
        }
        listevent.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this,request,gson)
        spinner.onItemSelectedListener = object :
        AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        swipe.onRefresh {
            presenter.getTeamList(leagueName)
        }
    }
    override fun showLoading() {
        progress.visible()
    }

    override fun hideLoading() {
        progress.invisible()
    }

    override fun showTeamList(data: List<Teams>) {
        swipe.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showError() {
        toast("Connection Error")
    }
    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinner = spinner {
                id = R.id.spinner
            }
            swipe = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

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
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return createView(AnkoContext.create(ctx))
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.dashboard,menu)

        val searchItem = menu?.findItem(R.id.action_search)
        search = searchItem?.actionView as SearchView
        search.queryHint = "Search Match"
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                if (TextUtils.isEmpty(newText)){
                    leagueName = "English Premier League"
                    presenter.getTeamList(leagueName)
                }else{
                    presenter.getTeamSearch(newText?.replace(" ", " "))
                }
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

        })
    }
}
