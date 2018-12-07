package com.example.habib.teamfav.favorite

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
import com.example.habib.teamfav.db.Favorite
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoriteAdapter(private val favorite: List<Favorite>,private val listener:(Favorite)->Unit)
    :RecyclerView.Adapter<FavoriteViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): FavoriteViewHolder {
        return FavoriteViewHolder(TeamUI().createView(AnkoContext.create(parent.context,parent)))
    }

    override fun getItemCount(): Int =favorite.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindItem(favorite[position],listener)
    }

}
class TeamUI : AnkoComponent<ViewGroup>{
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
                        id = tv_date1
                        textColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                        gravity = Gravity.CENTER
                        text="10/10/10"
                    }.lparams(matchParent, wrapContent)

                    linearLayout {
                        gravity = Gravity.CENTER_VERTICAL
                        textView {
                            id = tv_home1
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
                                id = tv_home_score1
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
                                id = tv_away_score1
                                padding = dip(8)
                                textSize = 20f
                                setTypeface(null,Typeface.BOLD)
                                text = "0"
                            }
                        }
                        textView{
                            id = tv_away1
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
class FavoriteViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val date:TextView = view.find(tv_date1)
    private val home:TextView = view.find(tv_home1)
    private val home_score:TextView = view.find(tv_home_score1)
    private val away_score:TextView = view.find(tv_away_score1)
    private val away:TextView = view.find(tv_away1)

    fun bindItem(favo: Favorite,listener: (Favorite) -> Unit){
        date.text = favo.date
        home.text = favo.home_name
        home_score.text = favo.home_score
        away_score.text = favo.away_score
        away.text = favo.away_name
        itemView.onClick {
            listener(favo)
        }
    }
}
