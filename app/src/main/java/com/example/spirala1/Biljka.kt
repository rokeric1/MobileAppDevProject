package com.example.spirala1

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "biljka")
data class Biljka(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo var naziv: String,
    @ColumnInfo(name = "family") var porodica: String,
    @ColumnInfo(name = "medicinskoUpozorenje") var medicinskoUpozorenje: String,
    @TypeConverters(MKConverter::class)
    @ColumnInfo(name = "medicinskeKoristi") var medicinskeKoristi: List<MedicinskaKorist>,
    @TypeConverters(POBConverter::class)
    @ColumnInfo(name = "profilOkusa") var profilOkusa: ProfilOkusaBiljke?,
    @TypeConverters(JConverter::class)
    @ColumnInfo(name = "jela") var jela: List<String>,
    @TypeConverters(KTConverter::class)
    @ColumnInfo(name = "klimatskiTipovi") var klimatskiTipovi: List<KlimatskiTip>,
    @TypeConverters(ZTConverter::class)
    @ColumnInfo(name = "zemljisniTipovi") var zemljisniTipovi: List<Zemljiste>,
    @ColumnInfo(name = "onlineChecked") var onlineChecked: Boolean = false,
)


