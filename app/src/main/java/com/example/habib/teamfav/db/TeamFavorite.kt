package com.example.habib.teamfav.db

data class TeamFavorite(val id : Long?,
                        val teamId: String?,
                        val teamName: String?,
                        val teamBadge: String?){
    companion object {
        const val TABLE_FAVORITES: String = "TABLE_FAVORITES"
        const val ID: String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
    }
}