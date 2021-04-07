   package com.example.todoapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.todoapp.databinding.ActivityPersonBinding
import com.example.todoapp.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityResultBinding.inflate(layoutInflater)
        var view=binding.root
        setContentView(view)
        var person = intent.getSerializableExtra("EXTRA_PERSON") as Person
        val result = "${person.name} has email ${person.email } and phone number ${person.phone}"

        binding.tvResult.text=result;

        binding.btnBack.setOnClickListener{
            finish()
        }

        binding.btnSelectImage.setOnClickListener{
            Intent(Intent.ACTION_GET_CONTENT).also {
                it.type ="image/*"
                startActivityForResult(it,0)
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK){
            val uri= data?.data
            Log.d("URI",uri.toString())
            binding.ivPreview.setImageURI(uri)
//        }
    }
}