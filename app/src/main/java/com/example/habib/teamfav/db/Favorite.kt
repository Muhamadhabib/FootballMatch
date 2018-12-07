package com.example.habib.teamfav.db

data class Favorite(val id: Long?,
                     val idEvent:String?,
                     val date:String?,
                     val home_score:String?,
                     val away_score:String?,
                     val home_name:String?,
                     val away_name:String?,
                     val idHome:String?,
                     val idAway:String?){
    companion object {
        const val TABLE_FAVORITE : String = "TABLE_FAVORITE"
        const val ID:String = "ID"
        const val EVENT_ID:String = "EVENT_ID"
        const val DATE:String = "DATE"
        const val HOME_SCORE:String = "HOME_SCORE"
        const val AWAY_SCORE:String = "AWAY_SCORE"
        const val HOME_NAME:String = "HOME_NAME"
        const val AWAY_NAME:String = "AWAY_NAME"
        const val HOME_ID:String = "HOME_ID"
        const val AWAY_ID:String = "AWAY_ID"
    }
}