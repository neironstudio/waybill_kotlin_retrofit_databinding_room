package com.tetron.waybill.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tetron.waybill.model.model1c.UserInfo

@Dao
interface UserInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserInfo(userInfo: UserInfo): Long

    @Query("SELECT userId FROM userInfo LIMIT 1")
    suspend fun getUserId(): String?
}