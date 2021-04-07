package com.example.todoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.todoapp.databinding.ActivityPersonBinding

class PersonActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPersonBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPersonBinding.inflate(layoutInflater)
        var view = binding.root
        setContentView(view)
        binding.btnSubmit.setOnClickListener{
            val name=binding.etName.text.toString()
            val email=binding.etEmail.text.toString()
            val phone=binding.etPhone.text.toString().toInt()
            val person=Person(name,email,phone)
            Intent(this,ResultActivity::class.java).also{
                it.putExtra("EXTRA_PERSON",person)
                startActivity(it)
            }
        }

        val customList = listOf<String>("first","Second","Third")
        val adapter= ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item)

        binding.smonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(
                        this@PersonActivity,
                        "You Selected ${adapterView?.getItemAtPosition(position).toString()}",
                        Toast.LENGTH_SHORT
                ).show()
            }
        }

//        val dynamicFragment= dynamicFragment()
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.FLdynamic,dynamicFragment)
//            commit()
//        }
        val firstFragment=FirstFragment()
        val secondFragment=SecondFragment()
        val thirdFragment=ThirdFragment()

        binding.BNVid.setOnNavigationItemReselectedListener {
            when(it.itemId){
                R.id.btnFirstFragment -> setCurrentFragment(firstFragment)
                R.id.btnSecondFragment -> setCurrentFragment(secondFragment)
                R.id.btnThirdFragment -> setCurrentFragment(thirdFragment)
            }
        }
//        binding.btnChangeFragment.setOnClickListener{
//            val staticFragment=StaticFragment()
//            supportFragmentManager.beginTransaction().apply {
//                replace(R.id.fragment,staticFragment)
//            }
//        }


    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.BNVid,fragment)
            commit()
        }
    }
}