package com.example.contact_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.contact_list.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = ContactAdapter()
    private var no =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extra = intent.getStringExtra(CategoryActivity.CATEGORY_ARGS)
        if (extra != null){
            title = extra
        }
        binding.contactsRv.adapter = adapter
        binding.contactsRv.addItemDecoration(
            DividerItemDecoration(
                this, LinearLayout.VERTICAL
            )
        )
        setUpDialog(binding)
    }

    private fun setUpDialog(binding: ActivityMainBinding) {
        val builder = AlertDialog.Builder(this)
        val inflater= this.layoutInflater
        val dialogView: View = inflater.inflate(R.layout.add_contact_dialog, null)
        builder.setView(dialogView)

        val name = dialogView.findViewById<TextView>(R.id.nameEt)
        val no = dialogView.findViewById<TextView>(R.id.numberEt)
        val saveBtn = dialogView.findViewById<TextView>(R.id.saveBt)

        no.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                saveBtn.isEnabled = s?.length == 11
                }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        val alertDialog = builder.create()
        saveBtn.setOnClickListener {
            val contact = Contact(name.text.toString(),no.text.toString())
            val contacts = mutableListOf(contact)
            adapter.setupContacts(contacts)
            name.text = ""
            no.text = ""
            alertDialog.dismiss()

        }
        binding.fab.setOnClickListener {
            alertDialog.show()
        }
    }
}