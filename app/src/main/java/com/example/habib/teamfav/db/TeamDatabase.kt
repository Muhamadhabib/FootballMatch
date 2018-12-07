package com.example.habib.teamfav.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class TeamDatabase(ctx: Context):ManagedSQLiteOpenHelper(ctx,"teamFavo.db",null,1){
    companion object {
        private var instance: TeamDatabase? = null
        @Synchronized
        fun getInstance(ctx:Context): TeamDatabase{
            if(instance == null){
                instance = TeamDatabase(ctx.applicationContext)
            }
            return instance as TeamDatabase
        }
    }
    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.dropTable(TeamFavorite.TABLE_FAVORITES,true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(TeamFavorite.TABLE_FAVORITES,true,
                TeamFavorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                TeamFavorite.TEAM_ID to TEXT + UNIQUE,
                TeamFavorite.TEAM_NAME to TEXT,
                TeamFavorite.TEAM_BADGE to TEXT)
    }

}
val Context.databases: TeamDatabase
    get() = TeamDatabase.getInstance(applicationContext)