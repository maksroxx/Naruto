package com.example.borutoapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.borutoapp.data.local.dao.HeroDao
import com.example.borutoapp.data.local.dao.HeroRemoteKeyDao
import com.example.borutoapp.domain.model.Hero
import com.example.borutoapp.domain.model.HeroRemoteKeys

@Database(entities = [Hero::class, HeroRemoteKeys::class], version = 1, exportSchema = true)
@TypeConverters(DatabaseConverter::class)
abstract class BorutoDatabase : RoomDatabase() {

    companion object {
        fun create(context: Context, usedInMemory: Boolean): BorutoDatabase {
            val dataBaseBuilder = if (usedInMemory) {
                Room.inMemoryDatabaseBuilder(context, BorutoDatabase::class.java)
            } else {
                Room.databaseBuilder(
                    context = context,
                    BorutoDatabase::class.java,
                    "test_database.db"
                )
            }
            return dataBaseBuilder.fallbackToDestructiveMigration().build()
        }
    }

    abstract fun heroDao(): HeroDao
    abstract fun heroRemoteKeysDao(): HeroRemoteKeyDao
}