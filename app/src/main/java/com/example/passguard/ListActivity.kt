package com.example.passguard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.passguard.controller.RegisterController
import com.example.passguard.fragment.AddRegisterDialogFragment
import com.example.passguard.model.User
import com.example.passguard.session.Session
import com.google.android.material.bottomnavigation.BottomNavigationView

class ListActivity : AppCompatActivity(), AddRegisterDialogFragment.AddRegisterDialogListener {

    private lateinit var bottomNav : BottomNavigationView
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        user = Session.getInstance().getUser()

        bottomNav = findViewById(R.id.bottomNav)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        bottomNav.menu.clear()
        bottomNav.inflateMenu(R.menu.menu_bottom)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onDialogPositiveClick(
        dialogFragment: DialogFragment,
        passwordDescription: String,
        passwordContent: String
    ) {
        if (RegisterController(this).insert(passwordDescription, passwordContent, Session.getInstance().getUser()!!.id))
            Toast.makeText(this, "Registro adicionado com sucesso", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Não foi possível adicionar registro", Toast.LENGTH_SHORT).show()
    }

    override fun onDialogNegativeClick(dialogFragment: DialogFragment) {
        Log.d("CANCEL", "cancelado")
    }

    fun showAddRegisterDialog(v: MenuItem)
    {
        AddRegisterDialogFragment().show(supportFragmentManager, "AddRegisterDialog")
    }

    fun logout(v: MenuItem)
    {
        Session.destroy()
        finish()
    }
}