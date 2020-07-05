package com.example.passguard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.passguard.R
import com.example.passguard.controller.RegisterController
import com.example.passguard.model.Register
import java.util.*
import kotlin.collections.ArrayList

class ListRegisterAdapter(private var myDataset : ArrayList<Register>)
    : RecyclerView.Adapter<ListRegisterAdapter.MyViewHolder>(), Filterable
{
    private val listCopy = ArrayList<Register>(myDataset.toMutableList())

    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v)
    {
        val passwordDescription : TextView = v.findViewById(R.id.passwordDescriptionTxt)
        val passwordContent : TextView = v.findViewById(R.id.passwordValueTxt)
        val imgDelete : ImageView = v.findViewById(R.id.trashImg)
        val imgEdit : ImageView = v.findViewById(R.id.editImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_password_card, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.passwordDescription.text = myDataset[position].passwordDescription
        holder.passwordContent.text = myDataset[position].passwordContent

        holder.imgDelete.setOnClickListener { v ->
            val password = myDataset[position]

            RegisterController(v.context).delete(password.id)
        }

        holder.imgEdit.setOnClickListener { v ->
            val password = myDataset[position]
        }
    }

    override fun getItemCount(): Int = myDataset.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val listFilter = ArrayList<Register>()

                if (constraint.isNullOrEmpty())
                    listFilter.addAll(listCopy)
                else
                {
                    val filterPattern = constraint.toString().toLowerCase(Locale.ROOT)

                    listCopy.forEach { item ->
                        if (item.passwordDescription.toLowerCase(Locale.ROOT).contains(filterPattern))
                            listFilter.add(item)
                    }
                }

                val results = FilterResults()
                results.values = listFilter

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                myDataset.clear()
                myDataset = results!!.values as ArrayList<Register>

                notifyDataSetChanged()
            }
        }
    }
}