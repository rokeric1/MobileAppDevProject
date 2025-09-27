package com.example.spirala1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BAdapter(
    private var biljke: List<Biljka>
) : RecyclerView.Adapter<BAdapter.BiljkaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.botanicki_mod, parent, false)
        return BiljkaViewHolder(view)
    }

    override fun getItemCount(): Int = biljke.size

    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {

        val pomocna=biljke[position].zemljisniTipovi.map{it.naziv}
        val pomocna1=biljke[position].klimatskiTipovi.map{it.opis}
        holder.naziv.text = biljke[position].naziv;
        holder.porodica.text = biljke[position].porodica;
        holder.klimatski.text = pomocna1[0];
        holder.zemljani.text = pomocna[0];
    }



    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val naziv: TextView = itemView.findViewById(R.id.nazivItem)
        val porodica: TextView = itemView.findViewById(R.id.porodicaItem)
        val klimatski: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        val zemljani: TextView = itemView.findViewById(R.id.zemljisniTipItem)

    }

    fun updateBiljke(biljke: List<Biljka>){
        this.biljke = biljke
        notifyDataSetChanged()
    }
}