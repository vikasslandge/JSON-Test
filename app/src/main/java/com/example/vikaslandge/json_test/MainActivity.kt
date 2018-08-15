package com.example.vikaslandge.json_test

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insert.setOnClickListener {
            var emp = Employee(
                    et1.text.toString().toInt(),
                    et2.text.toString(),
                    et3.text.toString(),
                    et4.text.toString()
            )

            var fin = openFileInput("employees.json")
            var employees : MutableList<Employee>? = null
            var emps : Employees? =null
            var g = Gson()

            if(fin.available()>0){
                emps = g.fromJson(InputStreamReader(fin),
                        Employees::class.java)
                employees = emps.employees
                employees.add(emp)
            }else{
                employees = mutableListOf<Employee>()
                employees.add(emp)
                emps = Employees(employees)
            }
            var json_data = g.toJson(emps)
            var fos = openFileOutput("employees.json",
                    Context.MODE_PRIVATE)
            fos.write(json_data.toByteArray())
            fos.flush()
            fos.close()
        }  // Insert

        read.setOnClickListener {
            var fin = openFileInput("employees.json")
            var g= Gson()
            var emps:Employees = g.fromJson(InputStreamReader(fin),
                    Employees::class.java)
            var employees:MutableList<Employee> = emps.employees
            var temp_list = mutableListOf<String>()
            for( emp in employees){
                temp_list.add(emp.id.toString()+"\t"+emp.name+"\n"+
                        emp.desig+"\t"+emp.dept)
            }
            var adapter = ArrayAdapter<String>(this@MainActivity,
                    android.R.layout.simple_list_item_single_choice,temp_list)
            lview.adapter = adapter
        } // read
    }


}

