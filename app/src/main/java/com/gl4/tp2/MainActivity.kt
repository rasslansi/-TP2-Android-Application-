package com.gl4.tp2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class MainActivity : AppCompatActivity() {
    val spinner : Spinner by lazy { findViewById(R.id.spinner) }
    val studentList = arrayListOf(
        Student("Emma", "Watson", Student.Gender.WOMAN, Student.Subject.Cours),
        Student("Liam", "Hemsworth", Student.Gender.MAN, Student.Subject.Cours),
        Student("Olivia", "Williams", Student.Gender.WOMAN, Student.Subject.TP),
        Student("Noah", "Thompson", Student.Gender.MAN, Student.Subject.Cours),
        Student("Ava", "Mitchell", Student.Gender.WOMAN, Student.Subject.TP),
        Student("Ethan", "Rodriguez", Student.Gender.MAN, Student.Subject.Cours),
        Student("Sophia", "Taylor", Student.Gender.WOMAN, Student.Subject.TP),
        Student("Mason", "Davis", Student.Gender.MAN, Student.Subject.Cours),
        Student("Isabella", "Brown", Student.Gender.WOMAN, Student.Subject.TP),
        Student("James", "Lee", Student.Gender.MAN, Student.Subject.Cours),
        Student("Grace", "Turner", Student.Gender.WOMAN, Student.Subject.TP),
        Student("Logan", "Martin", Student.Gender.MAN, Student.Subject.TP),
        Student("Zoe", "Clark", Student.Gender.WOMAN, Student.Subject.TP),
        Student("Elijah", "Cooper", Student.Gender.MAN, Student.Subject.TP)
    )
    var filteredList: MutableList<Student> = mutableListOf()

    //var filteredStudentList: List<Student> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //var adapter = StudentListAdapter(this,studentList)
        var matieres = listOf<String>("Cours","TP")
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        spinner.adapter = ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,matieres)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val itemSelected = adapterView?.getItemAtPosition(position).toString()

                filteredList = when (itemSelected) {
                    Student.Subject.TP.toString() -> studentList.filter { it.subject == Student.Subject.TP }
                        .toMutableList()
                    Student.Subject.Cours.toString() -> studentList.filter { it.subject == Student.Subject.Cours }
                        .toMutableList()
                    else -> studentList
                }

                recyclerView.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = StudentListAdapter(this@MainActivity,filteredList)
                }
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }

        }
        val search: EditText = findViewById(R.id.searchEditText)

        search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                recyclerView.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = StudentListAdapter(this@MainActivity,filteredList)
                }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    recyclerView.apply {
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = StudentListAdapter(this@MainActivity,filteredList)
                    }

            }

            override fun afterTextChanged(s: Editable?) {
                if(s.toString().isEmpty()){
                    recyclerView.apply {
                        filteredList.addAll(studentList)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = StudentListAdapter(this@MainActivity,filteredList)
                    }
                } else{
                    filter(s.toString())
                }
            }

        })
    }
    private fun showToast(message: String){
        Toast.makeText(this@MainActivity,message,Toast.LENGTH_SHORT).show()
    }
    private fun filter(s: String) {
        filteredList.clear()
        if (s.isEmpty()) {
            filteredList.addAll(studentList)
        } else {
            for (student in studentList) {
                val prenom = student.firstName
                val nom = student.lastName
                if (nom.equals(s, ignoreCase = true)|| nom.startsWith(s,ignoreCase = true) || prenom.equals(s, ignoreCase = true) || prenom.startsWith(s,ignoreCase = true)) {
                    filteredList.add(student)
                }
            }
        }
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = StudentListAdapter(this@MainActivity, filteredList)
        }
    }
}