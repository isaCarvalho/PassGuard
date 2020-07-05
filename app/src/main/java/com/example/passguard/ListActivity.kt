package com.example.passguard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passguard.adapter.ListRegisterAdapter
import com.example.passguard.controller.RegisterController
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

    fun updateRecycler(user: User)
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

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

        if (hasFocus)
            hideSystemUI()
        else
            showSystemUI()
    }

    private fun hideSystemUI()
    {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    private fun showSystemUI()
    {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }

    fun logout(v: MenuItem)
    {
        Session.destroy()
        finish()
    }
}