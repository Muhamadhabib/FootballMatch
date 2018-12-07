package com.example.habib.teamfav.detail

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.ActionBar
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.habib.teamfav.R
import com.example.habib.teamfav.R.id.add_fav
import com.example.habib.teamfav.R.id.fav
import com.example.habib.teamfav.R.menu.bottom_navigation_menu
import com.example.habib.teamfav.R.menu.detail_menu
import com.example.habib.teamfav.api.ApiRepository
import com.example.habib.teamfav.db.Favorite
import com.example.habib.teamfav.db.database
import com.example.habib.teamfav.model.Match
import com.example.habib.teamfav.model.Team
import com.example.habib.teamfav.util.invisible
import com.example.habib.teamfav.util.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.notification_template_lines_media.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout


//const val DETAIL = "DETAIL"

class DetailActivity : AppCompatActivity(),DetailView {
    private lateinit var presente:DetailPresente
    private lateinit var match:Match
    private lateinit var team:Team
    private lateinit var progress:ProgressBar
    private lateinit var swipeRefresh:SwipeRefreshLayout

    private lateinit var img_home:ImageView
    private lateinit var img_away:ImageView
    private lateinit var date:TextView
    private lateinit var score_home:TextView
    private lateinit var score_away:TextView
    private lateinit var team_home:TextView
    private lateinit var team_away:TextView

    private lateinit var goal_home:TextView
    private lateinit var goal_away:TextView
    private lateinit var shot_home:TextView
    private lateinit var shot_away:TextView
    private lateinit var gk_home:TextView
    private lateinit var gk_away:TextView
    private lateinit var def_home:TextView
    private lateinit var def_away:TextView
    private lateinit var mid_home:TextView
    private lateinit var mid_away:TextView
    private lateinit var fow_home:TextView
    private lateinit var fow_away:TextView
    private lateinit var sub_home:TextView
    private lateinit var sub_away:TextView
    private lateinit var a: String
    private lateinit var b: String
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var id: String
    private lateinit var id1: String
    private lateinit var id2: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val intent = intent
        //match = intent.getParcelableExtra(DETAIL)
        id = intent.getStringExtra("id")
        id1 = intent.getStringExtra("home")
        id2 = intent.getStringExtra("away")

        linearLayout {
            lparams(width = matchParent,height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorPrimaryDark,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)
                scrollView {
                    isVerticalScrollBarEnabled = false
                    relativeLayout {
                        lparams(width = matchParent,height = wrapContent)
                        linearLayout {
                            lparams(width = matchParent,height = matchParent)
                            padding = dip(10)
                            orientation = LinearLayout.VERTICAL
                            date = textView {
                                this.gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                                textSize = 20f
                            }
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_HORIZONTAL
                                linearLayout {
                                    //padding = dip(16)
                                    orientation = LinearLayout.VERTICAL
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    img_home = imageView {}.lparams(height = dip(100),width = dip(100))
                                    team_home = textView {
                                        textSize = 18f
                                        gravity = Gravity.CENTER
                                        setTypeface(null,Typeface.BOLD)
                                        text = "0"
                                    }
                                }.lparams(matchParent, wrapContent,1f)
                                linearLayout {
                                    padding = dip(8)
                                    gravity = Gravity.CENTER_VERTICAL
                                    score_home = textView {
                                        padding = dip(8)
                                        textSize = 32f
                                        setTypeface(null, Typeface.BOLD)
                                        text = "0"
                                    }
                                    textView {
                                        text= "VS"
                                        textColor = ContextCompat.getColor(ctx,R.color.Prim)
                                        textSize = 25f
                                    }
                                    score_away = textView {
                                        padding = dip(8)
                                        textSize = 32f
                                        setTypeface(null,Typeface.BOLD)
                                        text = "0"
                                    }
                                }
                                linearLayout {
                                    //padding = dip(16)
                                    orientation = LinearLayout.VERTICAL
                                    gravity =Gravity.CENTER_HORIZONTAL
                                    img_away = imageView {}.lparams(height = dip(100),width = dip(100))
                                    team_away = textView {
                                        textSize = 18f
                                        gravity = Gravity.CENTER
                                        setTypeface(null,Typeface.BOLD)
                                        text = "0"
                                    }
                                }.lparams(matchParent, wrapContent,1f)
                            }
                            //Goals
                            view {
                                backgroundColor = Color.LTGRAY
                            }.lparams(matchParent, dip(1)) {
                                topMargin = dip(8)
                            }
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_HORIZONTAL
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    gravity =Gravity.CENTER_HORIZONTAL
                                    goal_home = textView {
                                        textSize = 16f
                                        gravity = Gravity.CENTER
                                        padding = dip(4)
                                    }
                                }.lparams(matchParent, wrapContent,1f)
                                linearLayout{
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    textView {
                                        text = "Goals"
                                        padding = dip(4)
                                        textSize = 18f
                                        setTypeface(null,Typeface.BOLD)
                                    }
                                }
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    gravity =Gravity.CENTER_HORIZONTAL
                                    goal_away = textView {
                                        textSize = 16f
                                        gravity = Gravity.CENTER
                                        padding = dip(4)
                                    }
                                }.lparams(matchParent, wrapContent,1f)
                            }
                            //Shots
                            view {
                                backgroundColor = Color.LTGRAY
                            }.lparams(matchParent, dip(1)) {
                                topMargin = dip(8)
                            }
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_HORIZONTAL
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    gravity =Gravity.CENTER_HORIZONTAL
                                    shot_home = textView {
                                        textSize = 16f
                                        gravity = Gravity.CENTER
                                        padding = dip(4)
                                    }
                                }.lparams(matchParent, wrapContent,1f)
                                linearLayout{
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    textView {
                                        padding = dip(4)
                                        text = "Shots"
                                        textSize = 18f
                                        setTypeface(null,Typeface.BOLD)
                                    }
                                }
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    gravity =Gravity.CENTER_HORIZONTAL
                                    shot_away = textView {
                                        textSize = 16f
                                        gravity = Gravity.CENTER
                                        padding = dip(4)
                                    }
                                }.lparams(matchParent, wrapContent,1f)
                            }
                            view {
                                backgroundColor = Color.LTGRAY
                            }.lparams(matchParent, dip(1)) {
                                topMargin = dip(8)
                            }
                            //Goal Keeper
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_HORIZONTAL
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    gravity =Gravity.CENTER_HORIZONTAL
                                    gk_home = textView {
                                        padding = dip(4)
                                        textSize = 16f
                                        gravity = Gravity.CENTER
                                    }
                                }.lparams(matchParent, wrapContent,1f)
                                linearLayout{

                                    gravity = Gravity.CENTER_HORIZONTAL
                                    textView {
                                        padding = dip(4)
                                        text = "Goal Keeper"
                                        textSize = 18f
                                        setTypeface(null,Typeface.BOLD)
                                    }
                                }
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    gravity =Gravity.CENTER_HORIZONTAL
                                    gk_away = textView {
                                        textSize = 16f
                                        padding = dip(4)
                                        gravity = Gravity.CENTER
                                    }
                                }.lparams(matchParent, wrapContent,1f)
                            }
                            //Defense
                            view {
                                backgroundColor = Color.LTGRAY
                            }.lparams(matchParent, dip(1)) {
                                topMargin = dip(8)
                            }
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_HORIZONTAL
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    gravity =Gravity.CENTER_HORIZONTAL
                                    def_home = textView {
                                        padding = dip(4)
                                        textSize = 16f
                                        gravity = Gravity.CENTER
                                    }
                                }.lparams(matchParent, wrapContent,1f)
                                linearLayout{

                                    gravity = Gravity.CENTER_HORIZONTAL
                                    textView {
                                        padding = dip(4)
                                        text = "Defense"
                                        textSize = 18f
                                        setTypeface(null,Typeface.BOLD)
                                    }
                                }
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    gravity =Gravity.CENTER_HORIZONTAL
                                    def_away = textView {
                                        textSize = 16f
                                        padding = dip(4)
                                        gravity = Gravity.CENTER
                                    }
                                }.lparams(matchParent, wrapContent,1f)
                            }
                            //Midfield
                            view {
                                backgroundColor = Color.LTGRAY
                            }.lparams(matchParent, dip(1)) {
                                topMargin = dip(8)
                            }
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_HORIZONTAL
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    gravity =Gravity.CENTER_HORIZONTAL
                                    mid_home = textView {
                                        padding = dip(4)
                                        textSize = 16f
                                        gravity = Gravity.CENTER
                                    }
                                }.lparams(matchParent, wrapContent,1f)
                                linearLayout{

                                    gravity = Gravity.CENTER_HORIZONTAL
                                    textView {
                                        padding = dip(4)
                                        text = "Midfield"
                                        textSize = 18f
                                        setTypeface(null,Typeface.BOLD)
                                    }
                                }
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    gravity =Gravity.CENTER_HORIZONTAL
                                    mid_away = textView {
                                        textSize = 16f
                                        padding = dip(4)
                                        gravity = Gravity.CENTER
                                    }
                                }.lparams(matchParent, wrapContent,1f)
                            }
                            //forward
                            view {
                                backgroundColor = Color.LTGRAY
                            }.lparams(matchParent, dip(1)) {
                                topMargin = dip(8)
                            }
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_HORIZONTAL
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    gravity =Gravity.CENTER_HORIZONTAL
                                    fow_home = textView {
                                        padding = dip(4)
                                        textSize = 16f
                                        gravity = Gravity.CENTER
                                    }
                                }.lparams(matchParent, wrapContent,1f)
                                linearLayout{

                                    gravity = Gravity.CENTER_HORIZONTAL
                                    textView {
                                        padding = dip(4)
                                        text = "Forward"
                                        textSize = 18f
                                        setTypeface(null,Typeface.BOLD)
                                    }
                                }
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    gravity =Gravity.CENTER_HORIZONTAL
                                    fow_away = textView {
                                        textSize = 16f
                                        padding = dip(4)
                                        gravity = Gravity.CENTER
                                    }
                                }.lparams(matchParent, wrapContent,1f)
                            }
                            //Subtitutes
                            view {
                                backgroundColor = Color.LTGRAY
                            }.lparams(matchParent, dip(1)) {
                                topMargin = dip(8)
                            }
                            linearLayout {
                                orientation = LinearLayout.HORIZONTAL
                                gravity = Gravity.CENTER_HORIZONTAL
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    gravity =Gravity.CENTER_HORIZONTAL
                                    sub_home = textView {
                                        padding = dip(4)
                                        textSize = 16f
                                        gravity = Gravity.CENTER
                                    }
                                }.lparams(matchParent, wrapContent,1f)
                                linearLayout{
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    textView {
                                        padding = dip(4)
                                        text = "Substitutes"
                                        setTypeface(null,Typeface.BOLD)
                                        textSize = 18f
                                    }
                                }
                                linearLayout {
                                    orientation = LinearLayout.VERTICAL
                                    gravity =Gravity.CENTER_HORIZONTAL
                                    sub_away = textView {
                                        textSize = 16f
                                        padding = dip(4)
                                        gravity = Gravity.CENTER
                                    }
                                }.lparams(matchParent, wrapContent,1f)
                            }
                        }
                        progress = progressBar {
                        }.lparams{
                            centerHorizontally()
                        }
                    }
                }
            }
        }
        setFav()
        val request = ApiRepository()
        val gson = Gson()
        presente = DetailPresente(this,request,gson)

        presente.getTeam(id1,id2)
        //val event = match.idEvent?.toInt()
        presente.getMatch(id)
        swipeRefresh.onRefresh {
            //val event = match.idEvent?.toInt()
            presente.getMatch(id)
        }
    }
    private fun setFav(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
    override fun showLoading() {
        progress.visible()
    }

    override fun hideLoading() {
        progress.invisible()
    }

    override fun showError() {
        toast("Connection Error")
    }
    override fun showDetail(data: List<Match>) {
        match = Match(
                data[0].idEvent,
                data[0].strEvent,
                data[0].strLeague,
                data[0].idHomeTeam,
                data[0].strHomeTeam,
                data[0].intHomeScore,
                data[0].strHomeGoalDetails,
                data[0].strHomeLineupGoalKeeper,
                data[0].strHomeLineupDefense,
                data[0].strHomeLineupMidfield,
                data[0].strHomeLineupForward,
                data[0].strHomeLineupSubstitutes,
                data[0].idAwayTeam,
                data[0].strAwayTeam,
                data[0].intAwayScore,
                data[0].strAwayGoalDetails,
                data[0].strAwayLineupGoalkeeper,
                data[0].strAwayLineupDefense,
                data[0].strAwayLineupMidfield,
                data[0].strAwaylineupForward,
                data[0].strAwayLineupSubstitutes,
                data[0].intHomeShots,
                data[0].intAwayShots,
                data[0].strDate)
        swipeRefresh.isRefreshing = false
        date.text = data[0].strDate
        score_home.text = data[0].intHomeScore
        score_away.text = data[0].intAwayScore
        team_home.text = data[0].strHomeTeam
        team_away.text = data[0].strAwayTeam

        goal_home.text = data[0].strHomeGoalDetails
        goal_away.text = data[0].strAwayGoalDetails
        shot_home.text = data[0].intHomeShots
        shot_away.text = data[0].intAwayShots
        gk_home.text = data[0].strHomeLineupGoalKeeper
        gk_away.text = data[0].strAwayLineupGoalkeeper
        def_home.text = data[0].strHomeLineupDefense
        def_away.text = data[0].strAwayLineupDefense
        mid_home.text = data[0].strHomeLineupMidfield
        mid_away.text = data[0].strAwayLineupMidfield
        fow_home.text = data[0].strHomeLineupForward
        fow_away.text = data[0].strAwaylineupForward
        sub_home.text = data[0].strHomeLineupSubstitutes
        sub_away.text = data[0].strAwayLineupSubstitutes

    }

    override fun showlogo(data1: List<Team>, data2: List<Team>) {
        Picasso.get().load(data1[0].strTeamBadge).into(img_home)
        Picasso.get().load(data2[0].strTeamBadge).into(img_away)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu,menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home ->{
                finish()
                true
            }
            add_fav ->{
                if(isFavorite)removeFavorite() else  addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun addToFavorite(){
        try{
            database.use{
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.EVENT_ID to match.idEvent,
                        Favorite.DATE to match.strDate,
                        Favorite.HOME_SCORE to match.intHomeScore,
                        Favorite.AWAY_SCORE to match.intAwayScore,
                        Favorite.HOME_NAME to match.strHomeTeam,
                        Favorite.AWAY_NAME to match.strAwayTeam,
                        Favorite.HOME_ID to match.idHomeTeam,
                        Favorite.AWAY_ID to match.idAwayTeam
                        )
            }
            snackbar(swipeRefresh, "Added to favorite").show()
        }catch (e:SQLiteConstraintException){
            snackbar(swipeRefresh,e.localizedMessage).show()
        }
    }
    private fun removeFavorite(){
        try{
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(EVENT_ID = {id})",
                        "id" to id)
            }
            snackbar(swipeRefresh, "Removed to favorite").show()
        }catch (e:SQLiteConstraintException){
            snackbar(swipeRefresh,e.localizedMessage).show()
        }
    }
    private fun setFavorite(){
        if(isFavorite){
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        }else{
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
        }
    }
}
