package com.example.passguard.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.example.passguard.R
import java.lang.ClassCastException

class AddRegisterDialogFragment : DialogFragment()
{
    private lateinit var listener : AddRegisterDialogListener

    interface AddRegisterDialogListener
    {
        fun onDialogPositiveClick(dialogFragment: DialogFragment, passwordDescription : String, passwordContent: String)
        fun onDialogNegativeClick(dialogFragment: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as AddRegisterDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() + "must implement interface"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            val mView = inflater.inflate(R.layout.fragment_add_register, null)

            val passwordDescriptionTxt = mView.findViewById<EditText>(R.id.descriptionEditText)
            val passwordContentTxt = mView.findViewById<EditText>(R.id.contentEditText)

            builder.setTitle(R.string.addPassword)
                .setView(mView)
                .setIcon(R.mipmap.ic_launcher) // trocar o icone
                .setPositiveButton(R.string.save, DialogInterface.OnClickListener { dialog, which ->
                    val passwordDescription = passwordDescriptionTxt.text.toString()
                    val passwordContent = passwordContentTxt.text.toString()

                    listener.onDialogPositiveClick(this, passwordDescription, passwordContent)
                })
                .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, which ->
                    listener.onDialogNegativeClick(this)
                })

            builder.create()
        }!!
    }
}