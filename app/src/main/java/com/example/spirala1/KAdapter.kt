package com.example.spirala1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class KAdapter(
    private var biljke: List<Biljka>
) : RecyclerView.Adapter<KAdapter.BiljkaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.kuharski_mod, parent, false)
        return BiljkaViewHolder(view)
    }

    override fun getItemCount(): Int = biljke.size

    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {

        val pomocna=biljke[position].jela.map{it}
        holder.naziv.text = biljke[position].naziv;
        holder.profil.text = biljke[position].profilOkusa?.opis;
        if(pomocna.size == 1){
            holder.jelo1.text = pomocna[0];}
        if(pomocna.size == 2){
            holder.jelo1.text = pomocna[0];
            holder.jelo2.text = pomocna[1];}
        if(pomocna.size == 3){
            holder.jelo1.text = pomocna[0];
            holder.jelo2.text = pomocna[1];
            holder.jelo3.text = pomocna[2];}
    }

    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val slika: ImageView = itemView.findViewById(R.id.slikaItem)
        val naziv: TextView = itemView.findViewById(R.id.nazivItem)
        val profil: TextView = itemView.findViewById(R.id.profilOkusaItem)
        val jelo1: TextView = itemView.findViewById(R.id.jelo1Item)
        val jelo2: TextView = itemView.findViewById(R.id.jelo2Item)
        val jelo3: TextView = itemView.findViewById(R.id.jelo3Item)
    }

    fun updateBiljke(biljke: List<Biljka>){
        this.biljke = biljke
        notifyDataSetChanged()
    }
}