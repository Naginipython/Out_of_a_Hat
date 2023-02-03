package com.example.out_of_a_hat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.out_of_a_hat.databinding.ActivityHatBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Adds a back button to the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Getting data from previous activity
        val data = intent.getStringExtra("EXTRA_TEST_ITEMS")
        val hat: Hats = receiveItems(data)
        Log.d("What data did I get?", hat.toString())

        //Setting up Recyler View
        val rview = binding.recyclerView
        rview.layoutManager = LinearLayoutManager(this)
        rview.adapter = HatItemAdapter(hat.items)

        //TODO("ADD SHUFFLE BUTTON, WITH ALERT THAT POPS UP ONE ITEM")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_hat_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.tvItem -> {}
            android.R.id.home -> finish()
        }
        return true
    }

    private fun receiveItems(data: String?): Hats {
        return if (data != "") {
            val type = object : TypeToken<Hats>() {}.type
            val list = Gson().fromJson<Hats>(data, type)
            list
        } else
            Hats("Error", mutableListOf("Error"))
    }

    //TODO("CREATE SAVE AND RESTORE FOR HAT ITEM DATA")
}