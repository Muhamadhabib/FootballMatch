package com.example.habib.teamfav.detail

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Gravity
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.habib.teamfav.R
import com.example.habib.teamfav.api.ApiRepository
import com.example.habib.teamfav.model.Player
import com.example.habib.teamfav.util.invisible
import com.example.habib.teamfav.util.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.notification_template_lines_media.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import org.w3c.dom.Text

class PlayerDetailActivity : AppCompatActivity(),PlayerView {
    private lateinit var progress:ProgressBar
    private lateinit var presenter: PlayerPresenter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var img: ImageView
    private lateinit var nam: TextView
    private lateinit var wei: TextView
    private lateinit var hei: TextView
    private lateinit var pos: TextView
    private lateinit var des: TextView
    private lateinit var name:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        name = intent.getStringExtra("name")
        supportActionBar?.title = name
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            swipeRefreshLayout = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                scrollView {
                    isVerticalScrollBarEnabled = false
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(10)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL

                            img = imageView {}.lparams(matchParent, wrapContent)

                            nam = textView {
                                this.gravity = Gravity.CENTER
                                textSize = 20f
                                textColor = ContextCompat.getColor(context, R.color.colorAccent)
                            }.lparams {
                                topMargin = dip(5)
                            }
                            textView {
                                this.gravity = Gravity.CENTER
                                text = "Weight : "
                            }
                            wei = textView {
                                this.gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)
                            }
                            textView{
                                this.gravity = Gravity.CENTER
                                text = "Height : "
                            }
                            hei = textView {
                                this.gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)
                            }
                            textView{
                                this.gravity = Gravity.CENTER
                                text = "Position : "
                            }
                            pos = textView {
                                this.gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, R.color.colorPrimaryDark)
                            }
                            des= textView().lparams {
                                topMargin = dip(20)
                            }

                        }
                        progress = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }
        }

        val api = ApiRepository()
        val gson= Gson()
        presenter = PlayerPresenter(this,api,gson)
        presenter.getDetailPlayer(name)
        swipeRefreshLayout.onRefresh {
            presenter.getDetailPlayer(name)
        }
    }
    override fun showLoading() {
        progress.visible()
    }

    override fun hideLoading() {
        progress.invisible()
    }

    override fun showPlayer(data: List<Player>) {
        swipeRefreshLayout.isRefreshing = false
        Picasso.get().load(data[0].strThumb).into(img)
        nam.text = data[0].strPlayer
        des.text = data[0].strDescriptionEN
        pos.text = data[0].strPosition
        hei.text = data[0].strHeight
        wei.text = data[0].strWeight
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
