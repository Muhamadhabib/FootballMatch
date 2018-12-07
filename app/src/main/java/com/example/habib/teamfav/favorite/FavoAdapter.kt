package com.example.habib.teamfav.favorite

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.habib.teamfav.R.id.team_badge
import com.example.habib.teamfav.R.id.team_name
import com.example.habib.teamfav.db.TeamFavorite
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class FavoAdapter(private val favorite: List<TeamFavorite>,private val listener:(TeamFavorite)->Unit)
    :RecyclerView.Adapter<FavoViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FavoViewHolder {
        return FavoViewHolder(TeamsUI().createView(AnkoContext.create(p0.context,p0)))
    }

    override fun getItemCount(): Int = favorite.size

    override fun onBindViewHolder(holder: FavoViewHolder, p1: Int) {
        holder.bindItem(favorite[p1],listener)
    }

}
class TeamsUI : AnkoComponent<ViewGroup>{
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui){
            linearLayout{
                lparams(width = matchParent, height = wrapContent)
                padding = dip(16)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = team_badge
                }.lparams{
                    height = dip(50)
                    width = dip(50)
                }

                textView {
                    id = team_name
                    textSize = 16f
                }.lparams{
                    margin = dip(15)
                }

            }
        }
    }

}
class FavoViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val teamBadge: ImageView = view.find(team_badge)
    private val teamName: TextView = view.find(team_name)
    fun bindItem(favo:TeamFavorite,listener: (TeamFavorite) -> Unit){
        Picasso.get().load(favo.teamBadge).into(teamBadge)
        teamName.text = favo.teamName
        itemView.onClick { listener(favo) }
    }
}
