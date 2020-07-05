package com.example.passguard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.passguard.model.User
import com.example.passguard.session.Session
import com.google.android.material.bottomnavigation.BottomNavigationView

class ListActivity : AppCompatActivity() {

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

    fun logout(v: MenuItem)
    {
        Session.destroy()
        finish()
    }
}