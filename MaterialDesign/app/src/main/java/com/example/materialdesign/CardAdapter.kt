package com.example.materialdesign

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.materialdesign.databinding.ItemShipBinding

class CardAdapter(private val ships: List<PirateShip>)
    :RecyclerView.Adapter<CardAdapter.CardViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemShipBinding.inflate(from,parent,false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(ships[position])
    }

    override fun getItemCount(): Int {
        return  ships.size
    }


    class CardViewHolder(private val pirateShipCell:  ItemShipBinding)
    : RecyclerView.ViewHolder(pirateShipCell.root){
        fun bind(ship: PirateShip){
            with(pirateShipCell){
                shipTitleTextView.text = ship.title
                shipDescriptionTextView.text = ship.description
                shipSecondaryTextView.text = ship.id.toString()

Glide.with(this.root).load(ship.image)
    .into(shipImageView)

            }
        }
    }

}