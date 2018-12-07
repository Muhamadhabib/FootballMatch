package com.example.habib.teamfav.team

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.habib.teamfav.R.id.team_badge
import com.example.habib.teamfav.R.id.team_name
import com.example.habib.teamfav.model.Teams
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.w3c.dom.Text

class TeamAdapter(private val teams:List<Teams>,private val listener:(Teams)->Unit)
    : RecyclerView.Adapter<TeamsViewHolder>(){
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TeamsViewHolder {
        return TeamsViewHolder(TeamsUI().createView(AnkoContext.create(p0.context,p0)))
    }

    override fun getItemCount(): Int = teams.size

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        holder.bindItem(teams[position],listener)
    }

}

class TeamsUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui){
            linearLayout {
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

class TeamsViewHolder(view:View):RecyclerView.ViewHolder(view) {
    private val teamBadge:ImageView = view.find(team_badge)
    private val teamName : TextView = view.find(team_name)
    fun bindItem(teams:Teams,listener : (Teams)->Unit){
        Picasso.get().load(teams.teamBadge).into(teamBadge)
        teamName.text = teams.teamName
        itemView.onClick { listener(teams) }
    }
}
