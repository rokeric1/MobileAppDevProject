package com.example.spirala1

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NovaBiljkaActivity : AppCompatActivity() {


    private lateinit var jlv : ListView
    private lateinit var jeloBtn : Button
    private lateinit var unos : Button
    private lateinit var slika : Button
    private lateinit var imeJela : EditText
    private lateinit var naziv : EditText
    private lateinit var porodica : EditText
    private lateinit var med : EditText
    private var jela = mutableListOf<String>()
    private lateinit var arrayAdapter5: ArrayAdapter<*>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nova_biljka)

           fun eror(editText: EditText, errorMessage: String) {
               editText.error = errorMessage
               editText.postDelayed({editText.error = null} ,3000)                           
           }



        val arrayAdapter1: ArrayAdapter<*>
        val mk = MedicinskaKorist.values()
        var mklv = findViewById<ListView>(R.id.medicinskaKoristLV)
        arrayAdapter1 = ArrayAdapter(this,
            android.R.layout.simple_list_item_multiple_choice, mk)
        mklv.adapter = arrayAdapter1







        val arrayAdapter2: ArrayAdapter<*>
        val kt = KlimatskiTip.values()
        var ktlv = findViewById<ListView>(R.id.klimatskiTipLV)
        arrayAdapter2 = ArrayAdapter(this,
            android.R.layout.simple_list_item_multiple_choice, kt)
        ktlv.adapter = arrayAdapter2








        val arrayAdapter3: ArrayAdapter<*>
        val zt = Zemljiste.values()
        var ztlv = findViewById<ListView>(R.id.zemljisniTipLV)
        arrayAdapter3 = ArrayAdapter(this,
            android.R.layout.simple_list_item_multiple_choice, zt)
        ztlv.adapter = arrayAdapter3






        val arrayAdapter4: ArrayAdapter<*>
        val po = ProfilOkusaBiljke.values()
        var polv = findViewById<ListView>(R.id.profilOkusaLV)
        arrayAdapter4 = ArrayAdapter(this,
            android.R.layout.simple_list_item_single_choice, po)
        polv.adapter = arrayAdapter4

     
     
     




         jeloBtn = findViewById<Button>(R.id.dodajJeloBtn)
         imeJela = findViewById<EditText>(R.id.jeloET)
         jlv = findViewById<ListView>(R.id.jelaLV)
         arrayAdapter5 = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, jela)
         jlv.adapter = arrayAdapter5


        var select = ""
        
        jeloBtn.setOnClickListener {
            val a = imeJela.text.toString()
                if (jeloBtn.text == "Izmijeni jelo") {
                    val i = jela.indexOf(select)
                    if (a == "") {
                        jela.remove(select)
                    } else {
                        jela[i] = a
                    }
                } else {
                    if (jela.any { it.equals(a, ignoreCase = true) }) {
                    } else {
                        jela.add(a)
                    }
                }
                arrayAdapter5.notifyDataSetChanged();
                jeloBtn.text = "Dodaj jelo"
                imeJela.setText("")


        }

        jlv.setOnItemClickListener { parent, view, position, id ->
            select = parent.getItemAtPosition(position).toString()
            imeJela.setText(select)
            jeloBtn.text = "Izmijeni jelo"
        }


        unos = findViewById(R.id.dodajBiljkuBtn)
        naziv = findViewById<EditText>(R.id.nazivET)
        porodica = findViewById<EditText>(R.id.porodicaET)
        med = findViewById<EditText>(R.id.medicinskoUpozorenjeET)




        unos.setOnClickListener {
             var n = naziv.text.toString()
             var p = porodica.text.toString()
             var m = med.text.toString()
            if( n.length<3 || n.length>19 )eror(naziv,"Neispravan unos")
            if( p.length<3 || p.length>19 )eror(porodica,"Neispravan unos")
            if( m.length<3 || m.length>19 )eror(med,"Neispravan unos")
            if(jela.size == 0 )eror(imeJela,"Neispravan unos")
            if(mklv.checkedItemCount == 0 || ktlv.checkedItemCount == 0 || ztlv.checkedItemCount == 0 || polv.checkedItemCount != 1)eror(naziv,"Odaberite barem jedan element u svakoj listi")




            val odabraniMK = mklv.checkedItemPositions
            var MK = mutableListOf<MedicinskaKorist?>()
            for (i in 0 until odabraniMK.size()) {
                val position = odabraniMK.keyAt(i)
                if (odabraniMK.get(position)) {
                    val item = arrayAdapter1.getItem(position)
                    MK.add(item)
                }
            }
            val pomocnaMK = MK.filterNotNull();
            val mk = pomocnaMK.toList();




            val odabraniPO = polv.checkedItemPositions
            var PO = mutableListOf<ProfilOkusaBiljke?>()
            for (i in 0 until odabraniPO.size()) {
                val position = odabraniPO.keyAt(i)
                if (odabraniPO.get(position)) {
                    val item = arrayAdapter4.getItem(position)
                    PO.add(item)
                }
            }
            val pomocnaPO = PO.filterNotNull();
            val po = pomocnaPO[0]




            var J = mutableListOf<String>()
            for (i in 0 until arrayAdapter5.count) {
                val item = arrayAdapter5.getItem(i)
                val it = item.toString()
                    J.add(it)

            }



            val odabraniKT = ktlv.checkedItemPositions
            var KT = mutableListOf<KlimatskiTip?>()
            for (i in 0 until odabraniKT.size()) {
                val position = odabraniKT.keyAt(i)
                if (odabraniKT.get(position)) {
                    val item = arrayAdapter2.getItem(position)
                    KT.add(item)
                }
            }
            val pomocnaKT = KT.filterNotNull();
            val kt = pomocnaKT.toList();



            val odabraniZT = ztlv.checkedItemPositions
            var ZT = mutableListOf<Zemljiste?>()
            for (i in 0 until odabraniZT.size()) {
                val position = odabraniZT.keyAt(i)
                if (odabraniZT.get(position)) {
                    val item = arrayAdapter3.getItem(position)
                    ZT.add(item)
                }
            }
            val pomocnaZT = ZT.filterNotNull();
            val zt = pomocnaZT.toList();




            val nova = Biljka(naziv =n,porodica= p, medicinskoUpozorenje = m, medicinskeKoristi = mk, profilOkusa = po, jela = J, klimatskiTipovi = kt, zemljisniTipovi = zt, onlineChecked = false)
            NovaBiljka.novaLista.add(nova)
            NovaBiljka.flag = true;


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

          }


//        slika = findViewById(R.id.uslikajBiljkuBtn)
//        slika.setOnClickListener {
//
//            val intent = Intent(this, ::class.java)
//            startActivity(intent)
//        }



    }
}