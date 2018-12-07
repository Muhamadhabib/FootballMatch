package com.example.habib.teamfav.prev

import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.habib.teamfav.R
import com.example.habib.teamfav.R.id.*
import com.example.habib.teamfav.model.Match
import kotlinx.android.synthetic.main.notification_template_media.view.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class PrevAdapter(private val prev: List<Match>,private val listener:(Match)->Unit)
    : RecyclerView.Adapter<TeamViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): TeamViewHolder {
        return TeamViewHolder(TeamUI().createView(AnkoContext.create(parent.context, parent)));
    }

    override fun getItemCount(): Int = prev.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindItem(prev[position],listener)
    }

}
class TeamUI :AnkoComponent<ViewGroup>{
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui){
            linearLayout {
                lparams(matchParent, wrapContent)
                orientation = LinearLayout.VERTICAL
                backgroundColor = ContextCompat.getColor(ctx, R.color.shadow)
                linearLayout {
                    backgroundColor = Color.WHITE
                    orientation = LinearLayout.VERTICAL
                    padding = dip(4)

                    textView {
                        id = tv_date
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        gravity = Gravity.CENTER
                        text="10/10/10"
                    }.lparams(matchParent, wrapContent)

                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL
                        textView {
                            id = tv_home
                            gravity = Gravity.CENTER
                            textSize = 18f
                            textColor = ContextCompat.getColor(ctx,R.color.dark)
                            text = "home"
                            maxLines = 1
                            ellipsize = TextUtils.TruncateAt.END
                        }.lparams(matchParent, wrapContent,1f)
                        linearLayout{
                            gravity = Gravity.CENTER_VERTICAL

                            textView{
                                id = tv_home_score
                                padding = dip(8)
                                textSize = 20f
                                setTypeface(null,Typeface.BOLD)
                                text = "0"
                            }
                            textView {
                                text = "VS"
                                textColor = ContextCompat.getColor(ctx,R.color.Prim)
                            }
                            textView{
                                id = tv_away_score
                                padding = dip(8)
                                textSize = 20f
                                setTypeface(null,Typeface.BOLD)
                                text = "0"
                            }
                        }
                        textView{
                            id = tv_away
                            gravity = Gravity.CENTER
                            textSize = 18f
                            text = "away"
                            textColor = ContextCompat.getColor(ctx,R.color.dark)
                            maxLines = 1
                            ellipsize = TextUtils.TruncateAt.END
                        }.lparams(matchParent, wrapContent,1f)
                    }
                }.lparams(matchParent, matchParent){
                    setMargins(dip(8),dip(4),dip(8),dip(4))
                }
            }
        }
    }

}
class TeamViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val date:TextView = view.find(tv_date);
    private val home:TextView = view.find(tv_home);
    private val home_score:TextView = view.find(tv_home_score);
    private val away_score:TextView = view.find(tv_away_score);
    private val away:TextView = view.find(tv_away);

    fun bindItem(item: Match,listener: (Match) -> Unit){
        date.text = item.strDate
        home.text = item.strHomeTeam
        home_score.text = item.intHomeScore
        away_score.text = item.intAwayScore
        away.text = item.strAwayTeam
        itemView.onClick {
            listener(item)
        }
    }
}
