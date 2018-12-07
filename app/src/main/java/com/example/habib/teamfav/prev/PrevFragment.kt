package com.example.habib.teamfav.prev

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
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
import com.example.habib.teamfav.R.color.colorPrimaryDark
import com.example.habib.teamfav.api.ApiRepository
import com.example.habib.teamfav.detail.DetailActivity
import com.example.habib.teamfav.model.Match
import com.example.habib.teamfav.util.invisible
import com.example.habib.teamfav.util.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.jetbrains.anko.support.v4.toast


class PrevFragment : Fragment(),AnkoComponent<Context>,TeamsView {
    private var prev : MutableList<Match> = mutableListOf()
    private lateinit var prevPreseter: PrevPresenter
    private lateinit var prevAdapter: PrevAdapter
    private lateinit var spinner : Spinner
    private lateinit var listEvent: RecyclerView
    private lateinit var progress: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    //private lateinit var league:League
    private lateinit var name:String
    private lateinit var id:String
    private lateinit var search: SearchView
    //private var leg :MutableList<League> = mutableListOf()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        val spinnerItem = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(ctx,android.R.layout.simple_spinner_dropdown_item,spinnerItem)
        spinner.adapter = spinnerAdapter

        prevAdapter = PrevAdapter(prev){
            ctx.startActivity<DetailActivity>("id" to "${it.idEvent}","home" to "${it.idHomeTeam}","away" to "${it.idAwayTeam}")
        }
        listEvent.adapter = prevAdapter

        val request = ApiRepository()
        val gson = Gson()
        prevPreseter = PrevPresenter(this,request,gson)

        spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                name = spinner.selectedItem.toString()
                //presenter.getTeamList(leagueName)
                if (name.equals("English Premier League")){
                    id = 4328.toString()
                    prevPreseter.getPrevList(id)
                }else if (name.equals("English League Championship")){
                    id = 4329.toString()
                    prevPreseter.getPrevList(id)
                }else if (name.equals("German Bundesliga")){
                    id = 4331.toString()
                    prevPreseter.getPrevList(id)
                }else if (name.equals("Italian Serie A")){
                    id = 4332.toString()
                    prevPreseter.getPrevList(id)
                }else if (name.equals("French Ligue 1")){
                    id = 4334.toString()
                    prevPreseter.getPrevList(id)
                }else if(name.equals("Spanish La Liga")){
                    id = 4335.toString()
                    prevPreseter.getPrevList(id)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        swipeRefresh.onRefresh {
            prevPreseter.getPrevList(id)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams(width = matchParent,height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)
            spinner = spinner {
                id = R.id.spinner
            }
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorPrimaryDark,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent,height = wrapContent)
                    listEvent = recyclerView {
                        id = R.id.listPrev
                        lparams(width = matchParent,height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }
                    progress = progressBar{
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progress.visible()
    }

    override fun hideLoading() {
        progress.invisible()
    }

    override fun showTeamList(data: List<Match>) {
        swipeRefresh.isRefreshing = false
        prev.clear()
        prev.addAll(data)
        prevAdapter.notifyDataSetChanged()
    }

    override fun showError() {
        toast("Connection Error")
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
                    id = 4328.toString()
                    prevPreseter.getPrevList(id)
                }else{
                    prevPreseter.getEventSearch(newText?.replace(" ", " "))
                }
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

        })
    }
}
