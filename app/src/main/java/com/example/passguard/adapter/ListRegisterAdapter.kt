package com.example.passguard.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
            notifyDataSetChanged()
        }

        holder.imgEdit.setOnClickListener { v ->
            val password = myDataset[position]

            val mView = LayoutInflater.from(v.context).inflate(R.layout.fragment_add_register, null)

            val passwordDescriptionTxt = mView.findViewById<EditText>(R.id.descriptionEditText)
            val passwordContentTxt = mView.findViewById<EditText>(R.id.contentEditText)

            passwordDescriptionTxt.setText(password.passwordDescription)
            passwordContentTxt.setText(password.passwordContent)

            AlertDialog.Builder(v.context)
                .setView(mView)
                .setTitle(R.string.edit)
                .setIcon(R.mipmap.ic_launcher)
                .setPositiveButton(R.string.save, DialogInterface.OnClickListener { dialog, which ->
                    val passwordDescription = passwordDescriptionTxt.text.toString()
                    val passwordContent = passwordContentTxt.text.toString()

                    password.passwordDescription = passwordDescription
                    password.passwordContent = passwordContent

                    if (RegisterController(v.context).edit(password))
                    {
                        Toast.makeText(v.context, "Editado com sucesso", Toast.LENGTH_SHORT).show()
                        notifyDataSetChanged()
                    }
                    else
                        Toast.makeText(v.context, "Não foi possível editar", Toast.LENGTH_SHORT).show()
                })
                .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
                .show()
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