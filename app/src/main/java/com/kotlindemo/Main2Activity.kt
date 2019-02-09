package com.kotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson

open class Main2Activity : AppCompatActivity(), ItemClickListner {

    override fun onItemClick(pos: Int) {
        Toast.makeText(this, "User Name is : $pos", Toast.LENGTH_SHORT).show()
    }

    private lateinit var userData: String;
    private val listUser: ArrayList<UserModel> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        userData = intent.getStringExtra("userData")
        val userModel = Gson().fromJson(userData, UserModel::class.java)


        for (i in 0..10) {
            listUser.add(userModel)
        }

        val rec = findViewById<RecyclerView>(R.id.rec)
        rec.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rec.adapter = UserAdapter(listUser, this)


    }


    class UserAdapter(
        val listUser: ArrayList<UserModel>,
        var itemClickListner: ItemClickListner) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(p0.context).inflate(R.layout.adapter, p0, false)

            return CustomHolder(view)
        }


        override fun getItemCount(): Int {
            return listUser.size
        }

        override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {

            if (p0 is CustomHolder) {

                p0.txtEmailName.text = listUser[p1].email
                p0.txtUserName.text = listUser[p1].userName

                p0.linView.setOnClickListener { itemClickListner.onItemClick(p1) }
            }


        }


         class CustomHolder(view: View) : RecyclerView.ViewHolder(view) {

            val txtUserName = view.findViewById<TextView>(R.id.txtUserName)!!
            val txtEmailName = view.findViewById<TextView>(R.id.txtUserEmail)!!
            var linView = view.findViewById<LinearLayout>(R.id.linAdapterView)!!




        }

    }
}
