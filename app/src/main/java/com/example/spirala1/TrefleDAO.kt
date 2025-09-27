package com.example.spirala1

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL

class TrefleDAO {

    private val token = "crYbHTjeMQeRw8Dui_Z8aEFDe6XPjj4TGPB-Hx5otSE"
    private lateinit var context: Context


    fun setContext(newContext: Context) {
        context = newContext
    }

    private var defaultBitmap: Bitmap? = null
    fun getBitmap(context: Context): Bitmap? {
        if (defaultBitmap == null) {
            defaultBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.slika1)
        }
        return defaultBitmap!!
    }


    fun latinskiNaziv(naziv: String): String {
        if (naziv.contains("(") && naziv.contains(")")) {
            return naziv.substringAfter("(").substringBefore(")")
        } else {
            return naziv
        }
    }

    suspend fun getImage(biljka: Biljka): Bitmap? {
        return withContext(Dispatchers.IO) {

            val slika = ApiAdapter.retrofit.pretrazi(latinskiNaziv(biljka.naziv), token)

            if (slika.isSuccessful) {
                val r = slika.body()?.data
                if (r.isNullOrEmpty()) {
                    getBitmap(context)
                } else {
                    val url = URL(r[0].imageUrl)
                    BitmapFactory.decodeStream(url.openConnection().getInputStream())
                }
            } else {
                getBitmap(context)
            }

        }
    }


    suspend fun fixData(biljka: Biljka): Biljka {
        return withContext(Dispatchers.IO) {
            val nova = ApiAdapter.retrofit.pretrazi(latinskiNaziv(biljka.naziv), token)
            var rezultat = biljka
            if (nova.isSuccessful) {
                val r = nova.body()?.data
                if (r.isNullOrEmpty()) {
                    throw Exception("Nema biljke")
                } else {
                    val slug = r[0].slug
                    val detalji = ApiAdapter.retrofit.biljkaDetails(slug, token)
                    val d = detalji.body()?.data

                    if (d != null) {


                        rezultat.naziv = biljka.naziv;

//PORODICA
                        rezultat.porodica = d.family.name

//JESTIVO
                        var pomocna: List<String> = biljka.jela
                        if (d.mainSpecies?.edible == false) {
                            rezultat.medicinskoUpozorenje += if (rezultat.medicinskoUpozorenje.isEmpty()) "NIJE JESTIVO" else " NIJE JESTIVO"
                            pomocna = listOf();
                        }
                        rezultat.jela = pomocna;

//TOKSICNO
                        if (d.mainSpecies?.specfications?.toxicity != "none") {
                            if (!biljka.medicinskoUpozorenje.contains("TOKSIČNO")) {
                                rezultat.medicinskoUpozorenje += if (rezultat.medicinskoUpozorenje.isEmpty()) "TOKSIČNO" else " TOKSIČNO"
                            }
                        }

//ZEMLJISNI TIPOVI
                        val teksture = d.mainSpecies?.growth?.soilTexture?.toList()
                        var zemljista = biljka.zemljisniTipovi.toMutableList()
                        if (!teksture.isNullOrEmpty()) {
                            for (i in teksture) {
                                if (i == "1" || i == "2") {
                                    zemljista.add(Zemljiste.GLINENO)
                                }
                                if (i == "3" || i == "4") {
                                    zemljista.add(Zemljiste.PJESKOVITO)
                                }
                                if (i == "5" || i == "6") {
                                    zemljista.add(Zemljiste.ILOVACA)
                                }
                                if (i == "7" || i == "8") {
                                    zemljista.add(Zemljiste.CRNICA)
                                }
                                if (i == "9") {
                                    zemljista.add(Zemljiste.SLJUNKOVITO)
                                }
                                if (i == "10") {
                                    zemljista.add(Zemljiste.KRECNJACKO)
                                }
                            }
                            rezultat.zemljisniTipovi = zemljista.toList()
                        } else {
                            rezultat.zemljisniTipovi = emptyList();
                        }

//KLIMATKSI TIPOVI
                        val klime = mutableListOf<KlimatskiTip>()
                        val svijetla = d.mainSpecies?.growth?.light
                        val vlaga = d.mainSpecies?.growth?.atmosphericHumidity
                        if (vlaga != null && svijetla != null) {
                            if (svijetla in 6..9 && vlaga in 1..5) klime.add(KlimatskiTip.SREDOZEMNA)
                            if (svijetla in 8..10 && vlaga in 7..10) klime.add(KlimatskiTip.TROPSKA)
                            if (svijetla in 6..9 && vlaga in 5..8) klime.add(KlimatskiTip.SUBTROPSKA)
                            if (svijetla in 4..7 && vlaga in 3..7) klime.add(KlimatskiTip.UMJERENA)
                            if (svijetla in 7..9 && vlaga in 1..2) klime.add(KlimatskiTip.SUHA)
                            if (svijetla in 0..5 && vlaga in 3..7) klime.add(KlimatskiTip.PLANINSKA)
                        }
                        rezultat.klimatskiTipovi = klime.toList()

                        rezultat.onlineChecked = true;

                    } else {
                        throw Exception("Nema detalja za biljku")
                    }

                }

            } else {
                throw Exception("Biljka nije pronadjena")
            }

            return@withContext rezultat;
        }
    }



    suspend fun getPlantsWithFlowerColor(boja: String, substr: String): List<Biljka> {
        return withContext(Dispatchers.IO) {

            val rezultat = mutableListOf<Biljka>()
            var page = 1
            var total = 1
            while (page <= total) {
                val nova = ApiAdapter.retrofit.bojaCvijeta(boja.lowercase(), substr, token, page)
                if (nova.isSuccessful) {
                    val cvijet = nova.body()
                    if (cvijet != null) {
                        val r = cvijet.data
                        if (r != null) {
                            for (i in r) {
                                val detalji = ApiAdapter.retrofit.biljkaDetails(i.slug, token)
                                val d = detalji.body()?.data
                                    var pomocna = Biljka(
                                        naziv= d?.scientificName ?: "",
                                        porodica = d?.family?.name ?: "",
                                        medicinskoUpozorenje = "",
                                        medicinskeKoristi = listOf(),
                                        profilOkusa = null,
                                        jela = listOf(),
                                        klimatskiTipovi = listOf(),
                                        zemljisniTipovi = listOf(),
                                        onlineChecked = false
                                    )
//ZEMLJISNI TIPOVI
                                    val teksture = d?.mainSpecies?.growth?.soilTexture?.toList()
                                    var zemljista = pomocna.zemljisniTipovi.toMutableList()
                                    if (!teksture.isNullOrEmpty()) {
                                        for (i in teksture) {
                                            if (i == "1" || i == "2") {
                                                zemljista.add(Zemljiste.GLINENO)
                                            }
                                            if (i == "3" || i == "4") {
                                                zemljista.add(Zemljiste.PJESKOVITO)
                                            }
                                            if (i == "5" || i == "6") {
                                                zemljista.add(Zemljiste.ILOVACA)
                                            }
                                            if (i == "7" || i == "8") {
                                                zemljista.add(Zemljiste.CRNICA)
                                            }
                                            if (i == "9") {
                                                zemljista.add(Zemljiste.SLJUNKOVITO)
                                            }
                                            if (i == "10") {
                                                zemljista.add(Zemljiste.KRECNJACKO)
                                            }
                                        }
                                        pomocna.zemljisniTipovi = zemljista.toList()
                                    } else {
                                        pomocna.zemljisniTipovi = emptyList();
                                    }

//KLIMATKSI TIPOVI
                                    val klime = mutableListOf<KlimatskiTip>()
                                    val svijetla = d?.mainSpecies?.growth?.light
                                    val vlaga = d?.mainSpecies?.growth?.atmosphericHumidity
                                    if (vlaga != null && svijetla != null) {
                                        if (svijetla in 6..9 && vlaga in 1..5) klime.add(
                                            KlimatskiTip.SREDOZEMNA
                                        )
                                        if (svijetla in 8..10 && vlaga in 7..10) klime.add(
                                            KlimatskiTip.TROPSKA
                                        )
                                        if (svijetla in 6..9 && vlaga in 5..8) klime.add(
                                            KlimatskiTip.SUBTROPSKA
                                        )
                                        if (svijetla in 4..7 && vlaga in 3..7) klime.add(
                                            KlimatskiTip.UMJERENA
                                        )
                                        if (svijetla in 7..9 && vlaga in 1..2) klime.add(
                                            KlimatskiTip.SUHA
                                        )
                                        if (svijetla in 0..5 && vlaga in 3..7) klime.add(
                                            KlimatskiTip.PLANINSKA
                                        )
                                    }
                                    pomocna.klimatskiTipovi = klime.toList()
                                    rezultat.add(pomocna);
                            }

                        }
                        total = (cvijet.meta.total / 20) + 1
                        page++
                    } else {
                        break
                    }


                } else {
                    break
                }
            }
            return@withContext rezultat;
        }
    }
}