package cl.desafiolatam.desafiounobase

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    lateinit var nameInput: TextInputEditText
    lateinit var advance: Button
    lateinit var container: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nameInput = findViewById(R.id.name_input)
        advance = findViewById(R.id.login_button)
        container = findViewById(R.id.container)
        setUpListeners()
    }

    private fun setUpListeners() {
        advance.setOnClickListener {
            if (nameInput.text!!.isNotEmpty()) {
                val name = nameInput.text.toString()
                val intent: Intent

                if (hasSeenWelcome(name)) {
                    intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("current_user", name)
                } else {
                    intent = Intent(this, WelcomeActivity::class.java)
                    intent.putExtra("current_user", name)
                }
                saveUser(name)
                startActivity(intent)
            } else {
                Snackbar.make(container, "El nombre no puede estar vacío", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveUser(name:String){
        val sharedPreferences = getSharedPreferences("database", Context.MODE_PRIVATE)
        val users = sharedPreferences.getStringSet("users", setOf()) ?: setOf()
        val editor = sharedPreferences.edit()
        editor.putStringSet("users", users.plus(name))
        editor.apply()

    }
    private fun hasSeenWelcome(name:String): Boolean {
        val sharedPreferences = getSharedPreferences("database", Context.MODE_PRIVATE)
        val users = sharedPreferences.getStringSet("users", setOf()) ?: setOf()
        return users.contains(name)
    }
}
