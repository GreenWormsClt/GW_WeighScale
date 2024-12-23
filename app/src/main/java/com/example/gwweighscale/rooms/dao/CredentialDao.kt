package com.example.gwweighscale.rooms.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.gwweighscale.rooms.entities.Credential
import com.example.gwweighscale.rooms.entities.Tare
import kotlinx.coroutines.flow.Flow

@Dao
interface CredentialDao {
    @Query("SELECT * FROM credentials WHERE machineCode = :machineCode AND password = :password LIMIT 1")
    suspend fun validateCredentials(machineCode: String, password: String): Credential?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCredential(credential: Credential)

    @Query("SELECT * FROM credentials")
    fun getAllCredentials(): Flow<List<Credential>>

    @Update
    suspend fun updateCredential(credential: Credential)

    @Delete // Add this
    suspend fun deleteCredential(credential: Credential)// Delete a specific trolley
}
