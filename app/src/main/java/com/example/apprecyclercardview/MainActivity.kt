package com.example.apprecyclercardview

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apprecyclercardview.DetailActivity.Companion.EXTRA_CONTACT
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity(), ClickItemContactListener {

    private val rvList: RecyclerView by lazy {
        findViewById<RecyclerView>(R.id.rv_list)
    }

    private val adapter = ContactAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDrawer()
        fetchListContact()
        bindViews()
    }

    private fun fetchListContact() {
        val list =  arrayListOf(
            Contact(
                "Tommy Oliver",
                "(+1) 99199-9999",
                "tommy.png"
            ),
            Contact(
                "Jason Lee Scott",
                "(+1) 11111-9999",
                "jason.png"
            ),
            Contact(
                "Kimberly Hart",
                "(+1) 99999-9999",
                "kim.png"
            ),
            Contact(
                "Billy Cranston",
                "(+1) 95555-5555",
                "billy.png"
            ),
            Contact(
                "Trini Kwan",
                "(+1) 96666-6666",
                "trini.png"
            ),
            Contact(
                "Zack Taylor",
                "(+1) 97777-7777",
                "zack.png"
            ),
            Contact(
                "Lord Zedd",
                "(+999) 96666-6666",
                "zedd.png"
            ),
            Contact(
                "Rita Repulsa",
                "(+999) 96666-6666",
                "rita.png"
            )
        )

        getInstanceSharedPreferences().edit {
            val json = Gson().toJson(list)
            putString("contacts", json)
            commit()
        }
    }

    private fun getInstanceSharedPreferences(): SharedPreferences {
        return getSharedPreferences("com.example.apprecyclercardview.PREFERENCES", Context.MODE_PRIVATE)
    }

    private fun initDrawer() {
        val drawerLayout = findViewById<View>(R.id.drawerLayout) as DrawerLayout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun bindViews() {
        rvList.adapter = adapter
        rvList.layoutManager = LinearLayoutManager(this)
        updateList()
    }

    private fun getListContact() : List<Contact> {
        val list = getInstanceSharedPreferences().getString("contacts", "[]")
        val turnsType = object : TypeToken<List<Contact>>() {}.type
        return Gson().fromJson(list, turnsType)
    }

    private fun updateList() {
        val list = getListContact()
        adapter.updateList(list)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_menu_1 -> {
                showToast("É hora de morfar")
                true
            }
            R.id.item_menu_2 -> {
                showToast("Vá para a Alameda dos Anjos")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun clickItemContact(contact: Contact) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_CONTACT, contact)
        startActivity(intent)
    }
}