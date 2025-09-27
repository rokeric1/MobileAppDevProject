package com.example.spirala1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(
    private var biljke: List<Biljka>
) : RecyclerView.Adapter<ListAdapter.BiljkaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.medicinski_mod, parent, false)
        return BiljkaViewHolder(view)
    }

    override fun getItemCount(): Int = biljke.size

    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {

        val pomocna=biljke[position].medicinskeKoristi.map{it.opis}
        holder.naziv.text = biljke[position].naziv;
        holder.upozorenje.text = biljke[position].medicinskoUpozorenje;
        if(pomocna.size == 1){
        holder.korist1.text = pomocna[0];}
        if(pomocna.size == 2){
            holder.korist1.text = pomocna[0];
            holder.korist2.text = pomocna[1];}
        if(pomocna.size == 3){
            holder.korist1.text = pomocna[0];
            holder.korist2.text = pomocna[1];
            holder.korist3.text = pomocna[2];}

        holder.itemView.setOnClickListener {
            var tr = biljke[position].medicinskeKoristi
            var lista = mutableListOf<Biljka>()
            for(b in biljke){
                for(i in tr){
                    if(i in b.medicinskeKoristi){
                        lista.add(b);
                        break;
                    }
                }
            }
            updateBiljke(lista);

        }
    }

    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val naziv: TextView = itemView.findViewById(R.id.nazivItem)
        val upozorenje: TextView = itemView.findViewById(R.id.upozorenjeItem)
        val korist1: TextView = itemView.findViewById(R.id.korist1Item)
        val korist2: TextView = itemView.findViewById(R.id.korist2Item)
        val korist3: TextView = itemView.findViewById(R.id.korist3Item)
    }

    fun updateBiljke(biljke: List<Biljka>){
        this.biljke = biljke
        notifyDataSetChanged()
    }
}