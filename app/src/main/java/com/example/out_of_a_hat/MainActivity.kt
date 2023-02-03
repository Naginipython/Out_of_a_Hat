package com.example.out_of_a_hat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.out_of_a_hat.databinding.ActivityMainBinding
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val example = mutableListOf<Hats>(
            Hats("Test", mutableListOf<String>("Test", "test2")),
            Hats("Test2", mutableListOf<String>("Test3", "test4")),
            Hats("Test3", mutableListOf<String>("Test5", "test6")),
            Hats("Test4", mutableListOf<String>("Test7", "test8")),
            Hats("Test5", mutableListOf<String>("Test9", "test10"))
        )

        val rvMain = binding.rvMain
//        rvMain.hasFixedSize()
//        val grid = GridLayoutManager(this, 2)
//        rvMain.layoutManager = grid
        rvMain.layoutManager = LinearLayoutManager(this)
        val hatAdapter = HatAdapter(example)
        rvMain.adapter = hatAdapter

        //Setting the closure for each hat
        hatAdapter.onItemClick = {
            val intent = Intent(this, HatActivity::class.java)
            intent.putExtra("EXTRA_TEST_ITEMS", Gson().toJson(it))
            startActivity(intent)
        }

        //FAB Button
        binding.fabAddHat.setOnClickListener {
            //TODO("CREATE ALERT DIALOGUE TO CREATE A NEW HAT")
        }
    }
    //TODO("CREATE SAVE AND RESTORE DATA FOR HATS")
}