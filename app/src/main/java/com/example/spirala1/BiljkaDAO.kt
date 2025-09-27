package com.example.spirala1

import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update


@Dao
interface BiljkaDAO {
    
    @Query("SELECT * FROM biljka")
    suspend fun getAllBiljkas(): List<Biljka>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBiljka(biljka: Biljka): Long
    @Transaction
    suspend fun saveBiljka(biljka: Biljka): Boolean {
        val rez = insertBiljka(biljka)
        return rez != -1L
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(biljkaBitmap: BiljkaBitmap): Long

    @Query("SELECT * FROM BiljkaBitmap WHERE idBiljke = :biljkaId")
    suspend fun bitmapId(biljkaId: Long): BiljkaBitmap?

    @Transaction
    suspend fun addImage(idBiljke: Long, bitmap: Bitmap): Boolean {
        val biljka = getAllBiljkas().find{it.id==idBiljke}
        return if (biljka!=null && bitmapId(idBiljke)==null) {
            val mapa = BiljkaBitmap(idBiljke = idBiljke, bitmap = bitmap)
            insertImage(mapa)
            true
        }else{false}
    }


    @Query("SELECT * FROM biljka WHERE onlineChecked = 0")
    suspend fun getOfflineBiljke(): List<Biljka>

    @Update
    suspend fun updateBiljka(biljka: Biljka): Int


    @Transaction
    suspend fun fixOfflineBiljka(): Int {
        val biljke = getOfflineBiljke()
        var brojac = 0
        for (biljka in biljke) {
            val rez = TrefleDAO().fixData(biljka)
            if (biljka!=rez) {
                updateBiljka(rez.copy(onlineChecked = true))
                brojac++
            }
        }
        return brojac
    }




    @Query("DELETE FROM biljka")
    suspend fun clearBiljke()

    @Query("DELETE FROM BiljkaBitmap")
    suspend fun clearBitmape()

    @Transaction
    suspend fun clearData() {
        clearBiljke()
        clearBitmape()
    }
}