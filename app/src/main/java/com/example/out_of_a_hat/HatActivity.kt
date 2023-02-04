package com.example.out_of_a_hat

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.out_of_a_hat.databinding.ActivityHatBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.random.Random

class HatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHatBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Adds a back button to the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Getting data from previous activity
        val data = intent.getStringExtra("EXTRA_HAT_LIST")
        val pos = intent.getIntExtra("EXTRA_HAT_POS", -1)
        val hats: List<Hats> = receiveItems(data)
        Log.d("What data did I get?", hats[pos].toString())

        //Setting up Recycler View
        val rview = binding.recyclerView
        rview.layoutManager = LinearLayoutManager(this)
        rview.adapter = HatItemAdapter(hats[pos].items)

        //Deleting Hat Items
        val swipeToDeleteCallback = object : SwipeToDeleteCallback() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.absoluteAdapterPosition
                hats[pos].items.removeAt(position)
                rview.adapter?.notifyItemRemoved(position)
                saveHats(hats)
                //TODO("AWARE OF BUG WHERE UPDATE DOESN'T GO WITH BACK BUTTON")
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(rview)

        //Buttons
        binding.btnAddItem.setOnClickListener {
            val txt = binding.etAddItem.text.toString()
            hats[pos].items.add(txt)
            rview.adapter?.notifyItemInserted(hats[pos].items.size-1)
            saveHats(hats)
        }
        //TODO("ADD SHUFFLE BUTTON, WITH ALERT THAT POPS UP ONE ITEM")
        binding.btnShuffle.setOnClickListener {
            val rng = Random.nextInt(hats[pos].items.size)
            Log.d("Testing random", rng.toString())
            val alert = AlertDialog.Builder(this)
                .setMessage("${hats[pos].items[rng]}")
                .setNegativeButton("Dismiss") { _, _ ->
                    Log.d("Shuffle Alert", "User Dismissed")
                }
                .create()
            alert.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    private fun receiveItems(data: String?): List<Hats> {
        return if (data != "") {
            val type = object : TypeToken<List<Hats>>() {}.type
            val list = Gson().fromJson<List<Hats>>(data, type)
            list
        } else
            mutableListOf<Hats>(Hats("Error", mutableListOf("Error")))
    }

    private fun saveHats(hatList: List<Hats>) {
        val save = applicationContext.getSharedPreferences("hats", 0)
        save.edit().also {
            it.putString("hatsString", Gson().toJson(hatList))
            Log.d("Saved Data: ", hatList.toString())
            it.apply()
        }
    }
}