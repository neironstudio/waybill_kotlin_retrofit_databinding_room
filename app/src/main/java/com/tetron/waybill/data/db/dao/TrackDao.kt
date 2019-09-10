package com.tetron.waybill.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.tetron.waybill.model.track.WayItem
import com.tetron.waybill.model.track.WayPath

@Dao
interface TrackDao {

    @Insert(onConflict = REPLACE)
    suspend fun addWayPath(wayPath: WayPath): Long

    @Update
    suspend fun updateWayPath(wayPath: WayPath)

    @Update
    suspend fun updateWayPathList(wayPath: List<WayPath>): Int

    @Query("select * from waypath where activ = :isActive AND :paramsAll<>1")
    suspend fun getWayPathList(isActive: Boolean, paramsAll: Int): List<WayPath>

    @Query("select * from waypath where wayType= :wayType")
    fun getAllWayPath(wayType: String): LiveData<List<WayPath>>

    @Query("select * from waypath where id = :wayPathId")
    suspend fun getWayPath(wayPathId: Int): WayPath

    @Insert(onConflict = REPLACE)
    suspend fun addWayItem(wayItem: WayItem)

    @Query("select * from wayitem order by id desc limit 1")
    suspend fun getWayItemMaxId(): WayItem

    @Update
    fun updateWayItem(wayItem: WayItem)

    @Query("select * from wayitem where wayPathId = :wayPathId")
    suspend fun getWayItemList(wayPathId: Int): List<WayItem>


}