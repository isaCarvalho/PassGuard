package com.example.passguard

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passguard.adapter.ListRegisterAdapter
import com.example.passguard.controller.LoginController
import com.example.passguard.controller.RegisterController
import com.example.passguard.controller.UserController
import com.example.passguard.fragment.AddRegisterDialogFragment
import com.example.passguard.model.User
import com.example.passguard.session.Session
import com.google.android.material.bottomnavigation.BottomNavigationView

class ListActivity : AppCompatActivity(), AddRegisterDialogFragment.AddRegisterDialogListener {

    private lateinit var bottomNav : BottomNavigationView
    private lateinit var listRecycler : RecyclerView
    private lateinit var viewAdapter : ListRegisterAdapter
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        user = Session.getInstance().getUser()

        val registers = RegisterController(this).list(user!!.id)
        viewAdapter = ListRegisterAdapter(registers)

        updateRecycler(user!!)

        bottomNav = findViewById(R.id.bottomNav)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        bottomNav.menu.clear()
        bottomNav.inflateMenu(R.menu.menu_bottom)

        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem = menu!!.findItem(R.id.app_bar_search)
        val searchView : SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewAdapter.filter.filter(newText)
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onDialogPositiveClick(
        dialogFragment: DialogFragment,
        passwordDescription: String,
        passwordContent: String
    ) {
        val user = Session.getInstance().getUser()!!

        if (RegisterController(this).insert(passwordDescription, passwordContent, user.id))
            Toast.makeText(this, "Registro adicionado com sucesso", Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, "Não foi possível adicionar registro", Toast.LENGTH_SHORT).show()

        updateRecycler(user)
    }

    override fun onDialogNegativeClick(dialogFragment: DialogFragment) {
        Log.d("CANCEL", "cancelado")
    }

    fun showAddRegisterDialog(v: MenuItem)
    {
        AddRegisterDialogFragment().show(supportFragmentManager, "AddRegisterDialog")
    }

    private fun updateRecycler(user: User)
    {
        val registers = RegisterController(this).list(user.id)

        val viewManager = LinearLayoutManager(this)
        viewAdapter = ListRegisterAdapter(registers)

        listRecycler = findViewById<RecyclerView>(R.id.listRecycler)
        listRecycler.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    fun help(v : MenuItem)
    {
        val mView = layoutInflater.inflate(R.layout.fragment_help, null)

        AlertDialog.Builder(this)
            .setView(mView)
            .setTitle(R.string.help)
            .setIcon(R.mipmap.ic_launcher)
            .setPositiveButton(R.string.close, DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            .show()
    }

    fun settings(v: MenuItem)
    {
        val mView = layoutInflater.inflate(R.layout.fragment_settings, null)

        val name = mView.findViewById<EditText>(R.id.nameEditText)
        val email = mView.findViewById<EditText>(R.id.emailEditText)
        val password = mView.findViewById<EditText>(R.id.passwordEditText)

        name.setText(user!!.name)
        email.setText(user!!.email)
        password.setText(user!!.password)

        AlertDialog.Builder(this)
            .setView(mView)
            .setTitle(R.string.edit)
            .setIcon(R.mipmap.ic_launcher)
            .setPositiveButton(R.string.save, DialogInterface.OnClickListener { dialog, which ->
                user!!.name = name.text.toString()
                user!!.email = email.text.toString()
                user!!.password = password.text.toString()

                if (UserController(this).edit(user!!))
                {
                    Session.update(user!!)
                    Toast.makeText(this, "Dados editados com sucesso", Toast.LENGTH_SHORT).show()
                }
                else
                    Toast.makeText(this, "Não foi possível editar os dados", Toast.LENGTH_SHORT).show()
            })
            .setNegativeButton(R.string.cancel, DialogInterface.OnClickListener { dialog, which ->
                dialog.dismiss()
            })
            .show()
    }

    fun logout(v: MenuItem)
    {
        LoginController.logout()
        finish()
    }

    fun deleteAccount(v : MenuItem)
    {
        if (UserController(this).delete(user!!.id))
        {
            Session.destroy()
            finish()
            Toast.makeText(this, "Conta excluída com sucesso", Toast.LENGTH_SHORT).show()
        }
        else
            Toast.makeText(this, "Não foi possível excluir a conta", Toast.LENGTH_SHORT).show()
    }
}