package com.example.habib.teamfav.detail

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.habib.teamfav.R
import com.example.habib.teamfav.model.Player
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class PlayerAdapter(private val player:List<Player>,private val listener:(Player)->Unit)
    :RecyclerView.Adapter<PlayerViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): PlayerViewHolder {
        return PlayerViewHolder(PlayerUI().createView(AnkoContext.create(p0.context,p0)))
    }

    override fun getItemCount(): Int = player.size

    override fun onBindViewHolder(holder: PlayerViewHolder, p1: Int) {
        holder.bindItem(player[p1],listener)
    }

}
class PlayerUI : AnkoComponent<ViewGroup> {
    override fun createView(ui: AnkoContext<ViewGroup>): View {
        return with(ui){
            linearLayout {
                lparams(width = matchParent, height = wrapContent)
                backgroundColor = Color.WHITE
                padding = dip(4)
                orientation = LinearLayout.HORIZONTAL

                imageView {
                    id = R.id.p_img
                }.lparams{
                    height = dip(75)
                    width = dip(75)
                }
                linearLayout{
                    lparams(width = matchParent, height = wrapContent)
                    orientation =LinearLayout.VERTICAL
                    textView {
                        id = R.id.p_name
                        textSize = 16f
                    }
                    textView {
                        id = R.id.p_pos
                        textSize = 16f
                    }
                }

            }
        }
    }

}
class PlayerViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val img:ImageView = view.find(R.id.p_img)
    private val name:TextView = view.find(R.id.p_name)
    private val pos:TextView  = view.find(R.id.p_pos)
    fun bindItem(player : Player,listener: (Player) -> Unit){
        Picasso.get().load(player.strCutout).into(img)
        name.text = player.strPlayer
        pos.text = player.strPosition
        itemView.onClick { listener(player) }
    }
}
