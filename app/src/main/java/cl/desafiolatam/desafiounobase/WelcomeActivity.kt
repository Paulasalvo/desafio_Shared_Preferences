package cl.desafiolatam.desafiounobase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class WelcomeActivity : AppCompatActivity() {
    lateinit var nameUser: TextView
    lateinit var advance: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        nameUser = findViewById(R.id.welcome_name)
        advance = findViewById(R.id.advance_button)
        setUpViewsAndListener()
    }

    private fun setUpViewsAndListener() {
        val currentName = intent?.extras?.getString("current_user").orEmpty()
        val userName = currentName // obtener el nombre de usuario de alguna manera y mostrarlo en nameUser
        nameUser.text = userName
        advance.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
