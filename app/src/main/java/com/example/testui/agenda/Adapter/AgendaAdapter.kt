package com.example.craxinnotest.agenda.Adapter

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.craxinnotest.agenda.model.Data
import com.example.testui.R
import com.example.testui.databinding.AgendaItemsBinding
import com.example.testui.utils.formatDateTime

class AgendaAdapter(val agendlist: List<Data>, val listener : ItemClickListener)  : RecyclerView.Adapter<AgendaAdapter.AgendaDataHolder>(){
    class AgendaDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = AgendaItemsBinding.bind(itemView.rootView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AgendaDataHolder {
        return AgendaDataHolder(LayoutInflater.from(parent.context).inflate(R.layout.agenda_items,parent,false))
    }

    override fun getItemCount(): Int {
        return agendlist.size
    }

    override fun onBindViewHolder(holder: AgendaDataHolder, position: Int) {

        val data = agendlist[position]

        holder.binding.agendaName.text = data.name

        val context = holder.itemView.context
        holder.binding.startEndTime.text = "${context.formatDateTime(data.start_date)} - ${context.formatDateTime(data.end_date)} EST"

        holder.binding.userRecyclerview.adapter = UsersAdapter(data.attendees)
        holder.itemView.setOnClickListener {
            listener.onItemClicked(data.id.toString())
        }

    }


    interface ItemClickListener{
        fun onItemClicked(aid: String)
    }
}