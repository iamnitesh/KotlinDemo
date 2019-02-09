package com.kotlindemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassowrd: EditText
    private lateinit var editConfirmPassword: EditText

    private lateinit var txtSignUp: TextView
    private lateinit var imgProfile: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)
        editPassowrd = findViewById(R.id.editPassword)
        editConfirmPassword = findViewById(R.id.editConfirmPassword)
        txtSignUp = findViewById(R.id.txtSignUp)
        imgProfile = findViewById(R.id.imgProfile)

        val tinyDB = TinyDB(this)

        val onClickListner = View.OnClickListener { view ->

            when (view.id) {

                R.id.txtSignUp -> {
                    val isFieldValid =
                        !editName.text.toString().isEmpty() || !editEmail.text.toString().isEmpty() || !editPassowrd.text.toString().isEmpty() || !editConfirmPassword.text.toString().isEmpty()
                    if (isFieldValid) {

                        val checkEmailValidOrNot = Patterns.EMAIL_ADDRESS.matcher(editEmail.text.trim()).matches()
                        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
                        val passWordMatcher = Regex(passwordPattern)
                        val isPasswordMatch = passWordMatcher.find(editPassowrd.text.toString()) != null ?: false

                        val bothPasswordMatch = editPassowrd.text.toString() == editConfirmPassword.text.toString()

                        if (!checkEmailValidOrNot) {
                            Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show()
                        } else if (!isPasswordMatch) {
                            Toast.makeText(this, "password validation is not Good ", Toast.LENGTH_SHORT).show()
                        } else if (!bothPasswordMatch) {
                            Toast.makeText(this, "Both Password does not match", Toast.LENGTH_SHORT).show()
                        } else {

                            val userModel = UserModel(
                                editName.text.toString().trim(),
                                editEmail.text.toString().trim().toLowerCase(),
                                editConfirmPassword.text.toString().trim().toLowerCase().hashCode()
                            )

                            val data = Gson().toJson(userModel).toString()
                            val newActivity = Intent(this, Main2Activity::class.java)
                            newActivity.putExtra("userData", data)
                            startActivity(newActivity)
                        }


                    } else {
                        Toast.makeText(this, "field can not be empty", Toast.LENGTH_SHORT).show()
                    }

                }
                else -> {

                }

            }

        }


        txtSignUp.setOnClickListener(onClickListner)


    }
}
