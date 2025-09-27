package com.example.spirala1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var lista: RecyclerView
    private lateinit var biljkaAdapter: ListAdapter
    private lateinit var kuhAdapter: KAdapter
    private lateinit var botAdapter: BAdapter
    private var biljkeLista = getBiljke();

    private lateinit var trefleDAO: TrefleDAO



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lista = findViewById(R.id.biljkeRV)
        lista.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        biljkaAdapter = ListAdapter(listOf())
        kuhAdapter = KAdapter(listOf())
        botAdapter = BAdapter(listOf())

        lista.adapter = biljkaAdapter

        if(NovaBiljka.flag){
            biljkeLista = NovaBiljka.novaLista
        }

        biljkaAdapter.updateBiljke(biljkeLista)



        trefleDAO = TrefleDAO()
        trefleDAO.setContext(this)




        val spinner: Spinner = findViewById(R.id.modSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.mode,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
                var trenutni = parent.getItemAtPosition(pos)
                if (trenutni == "Medicinski") {
                    lista.adapter = biljkaAdapter
                    biljkaAdapter.updateBiljke(biljkeLista)
                }
                if (trenutni == "Kuharski") {
                    lista.adapter = kuhAdapter
                    kuhAdapter.updateBiljke(biljkeLista)
                }
                if (trenutni == "Botanički") {
                    lista.adapter = botAdapter
                    botAdapter.updateBiljke(biljkeLista)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                val spinner: Spinner = parent.findViewById(R.id.modSpinner)
                spinner.onItemSelectedListener = this
            }
        }


        var btn = findViewById<Button>(R.id.resetBtn)
        btn.setOnClickListener {
            biljkeLista = getBiljke();
            biljkaAdapter.updateBiljke(biljkeLista)
            kuhAdapter.updateBiljke(biljkeLista)
            botAdapter.updateBiljke(biljkeLista)
        }

        val btn1: Button = findViewById(R.id.novaBiljkaBtn)
        btn1.setOnClickListener {
            val intent = Intent(this, NovaBiljkaActivity::class.java)
            startActivity(intent)
        }




    }

}