package com.icanerdogan.androidtestapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.icanerdogan.androidtestapp.model.Art

@Database(entities = [Art::class], version = 1)
abstract class ArtDatabase : RoomDatabase(){

}