package com.example.out_of_a_hat

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.out_of_a_hat.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Creating/Recovering hatList Array
        val hatList = mutableListOf<Hats>()
        hatList.addAll(recoverHats())

        val rvMain = binding.rvMain
//        rvMain.hasFixedSize()
//        val grid = GridLayoutManager(this, 2)
//        rvMain.layoutManager = grid
        rvMain.layoutManager = LinearLayoutManager(this)
        val hatAdapter = HatAdapter(hatList)
        rvMain.adapter = hatAdapter

        //Setting the closure for each hat
        hatAdapter.onItemClick = {
            val intent = Intent(this, HatActivity::class.java)
            intent.putExtra("EXTRA_TEST_ITEMS", Gson().toJson(it))
            startActivity(intent)
        }

        //FAB Button
        binding.fabAddHat.setOnClickListener {
            val layout = layoutInflater.inflate(R.layout.alert_add_hat, null)
            val addHat = AlertDialog.Builder(this)
                .setView(layout)
                .create()

            layout.findViewById<TextView>(R.id.tvAlertCancel).setOnClickListener {
                addHat.cancel()
            }
            layout.findViewById<TextView>(R.id.tvAlertAdd).setOnClickListener {
                val name = layout.findViewById<EditText>(R.id.etAddHat).text
                Log.d("FAB Add Hat Alert", "User put in name: $name")
                hatList.add(Hats(name.toString(), mutableListOf<String>()))
                saveHats(hatList)
                //Adding Hat

                addHat.cancel()
            }

            addHat.show()
        }
    }

    private fun saveHats(hatList: List<Hats>) {
        val save = applicationContext.getSharedPreferences("hats", 0)
        save.edit().also {
            it.putString("hatsString", Gson().toJson(hatList))
            Log.d("Saved Data: ", hatList.toString())
            it.apply()
        }
    }

    private fun recoverHats(): List<Hats> {
        val save = applicationContext.getSharedPreferences("hats", 0)
        val json = save.getString("hatsString", null)
        return if (json != null) {
            val type = object: TypeToken<MutableList<Hats>>() {}.type
            val data = Gson().fromJson<List<Hats>>(json, type)
            data
        } else {
            mutableListOf()
        }
    }

    //TODO("MENU ITEM THAT HELPS REMOVE HATS")
}