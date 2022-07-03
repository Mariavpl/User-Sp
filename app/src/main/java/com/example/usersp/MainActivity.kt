package com.example.usersp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.usersp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity(),OnClickListener {

    private lateinit var userAdapter: UserAdapter
    private lateinit var LinearLayoutManager: RecyclerView.LayoutManager

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getPreferences(Context.MODE_PRIVATE)
        val isFirstTime = preferences.getBoolean(getString(R.string.sp_frist_time), true)
        Log.i("SP", "${getString(R.string.sp_frist_time)} = isFirstTime")


        if (isFirstTime){
            val dialogView = layoutInflater.inflate(R.layout.dilalog_register, null)
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_title)
                .setNeutralButton(R.string.dialog_invitado, null)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_confirm)  { _, _ ->
                    val username = dialogView.findViewById<TextInputEditText>(R.id.Username)
                        .text.toString()
                    with(preferences.edit()){
                        putBoolean(getString(R.string.sp_frist_time), false).apply()
                      putString(getString(R.string.sp_username), username)
                          .apply()
                    }
                    Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT)
                        .show()
                }
                .show()

        }else{
            val username = preferences.getString(getString(R.string.sp_username),getString(R.string.hint_username))
            Toast.makeText(this, "Bienvenido $username", Toast.LENGTH_SHORT) .show()

        }
        userAdapter = UserAdapter(getUsers(),this)
        LinearLayoutManager = LinearLayoutManager(this)

        binding.recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager
            adapter = userAdapter
        }
    }
    private fun getUsers(): MutableList<User>{
        val users = mutableListOf<User>()

        val alain = User(1, "Alain", "Nicolas", "https://images.bfmtv.com/MF2fNMXX_5j7-9R0mI5MIYFGNGI=/0x103:2048x1255/800x0/images/Alain-Griset-373301.jpg")
        val samanta = User(2, "Samanta", "Mesa", "https://3.bp.blogspot.com/-RGZivLgZGJk/UhcxT775IZI/AAAAAAAAEwo/Vikq7uqUOuw/s1600/8568-1-22.jpg")
        val emma = User(3, "Emma", "Gomez", "https://www3.pictures.zimbio.com/gi/Emma+Slater+5th+Annual+Streamy+Awards+Red+CjaO-Ch35RVx.jpg")
        val javier = User(4, "Javier", "Cruz", "https://i2.wp.com/www.tvmasmagazine.com/wp-content/uploads/2018/08/JAVIER-VEGA-FOTO-APAISADA.jpg")



        users.add(alain)
        users.add(samanta)
        users.add(emma)
        users.add(javier)
        return users

    }

    override fun onClick(user: User, position: Int) {
        Toast.makeText(this, "$position: ${user.getFullName()}", Toast.LENGTH_SHORT).show()

    }
}