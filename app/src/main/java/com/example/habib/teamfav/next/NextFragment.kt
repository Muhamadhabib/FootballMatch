package com.example.habib.teamfav.next

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
import com.example.habib.teamfav.api.ApiRepository
import com.example.habib.teamfav.detail.DetailActivity
import com.example.habib.teamfav.model.Match
import com.example.habib.teamfav.prev.PrevAdapter
import com.example.habib.teamfav.prev.PrevPresenter
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


class NextFragment : Fragment(),AnkoComponent<Context>,TeamsView {

    private var next : MutableList<Match> = mutableListOf()
    private lateinit var nextPreseter: PrevPresenter
    private lateinit var nextAdapter: PrevAdapter
    private lateinit var listEvent: RecyclerView
    private lateinit var progress: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var spinner:Spinner
    private lateinit var name:String
    private lateinit var id:String
    private lateinit var search: SearchView
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
        val spinnerItem = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(ctx,android.R.layout.simple_spinner_dropdown_item,spinnerItem)
        spinner.adapter = spinnerAdapter
        nextAdapter = PrevAdapter(next){
            ctx.startActivity<DetailActivity>("id" to "${it.idEvent}","home" to "${it.idHomeTeam}","away" to "${it.idAwayTeam}")
        }
        listEvent.adapter = nextAdapter
        val request = ApiRepository()
        val gson = Gson()
        nextPreseter = PrevPresenter(this,request,gson)
        spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                name = spinner.selectedItem.toString()
                //presenter.getTeamList(leagueName)
                if (name.equals("English Premier League")){
                    id = 4328.toString()
                    nextPreseter.getNextList(id)
                }else if (name.equals("English League Championship")){
                    id = 4329.toString()
                    nextPreseter.getNextList(id)
                }else if (name.equals("German Bundesliga")){
                    id = 4331.toString()
                    nextPreseter.getNextList(id)
                }else if (name.equals("Italian Serie A")){
                    id = 4332.toString()
                    nextPreseter.getNextList(id)
                }else if (name.equals("French Ligue 1")){
                    id = 4334.toString()
                    nextPreseter.getNextList(id)
                }else if(name.equals("Spanish La Liga")){
                    id = 4335.toString()
                    nextPreseter.getNextList(id)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

        swipeRefresh.onRefresh {
            nextPreseter.getNextList(id)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }
    override fun showLoading() {
        progress.visible()
    }

    override fun hideLoading() {
        progress.invisible()
    }

    override fun showTeamList(data: List<Match>) {
        swipeRefresh.isRefreshing = false
        next.clear()
        next.addAll(data)
        nextAdapter.notifyDataSetChanged()
    }

    override fun showError() {
        toast("Connection Error")
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
                setColorSchemeResources(R.color.colorPrimaryDark,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent,height = wrapContent)
                    listEvent = recyclerView {
                        id = R.id.listNext
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
                    nextPreseter.getNextList(id)
                }else{
                    nextPreseter.getEventSearch(newText?.replace(" ", " "))
                }
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

        })
    }
}
