package cl.desafiolatam.desafiounobase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class HomeActivity : AppCompatActivity() {
    private lateinit var userName: String
    private lateinit var homeTitle: TextView
    private lateinit var nicknameTitle: TextView
    private lateinit var spanishCheckBox: CheckBox
    private lateinit var englishCheckBox: CheckBox
    private lateinit var germanCheckBox: CheckBox
    private lateinit var otherCheckBox: CheckBox
    private lateinit var otherTextInput: TextInputEditText
    private lateinit var nickNameInput: TextInputEditText
    private lateinit var ageInput: TextInputEditText
    private lateinit var save: Button
    private lateinit var container: ConstraintLayout
// para almacenar los datos en shared preferences utilice claves que contengan el nombre del usuario y el nombre de el campo guardado.
    //esta recomendación no aplica para todos los valores, pero ayuda con varios
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setUpViews()
        loadData()
        save.setOnClickListener {
            saveNickNameAndAge()
            saveLanguages()
            Snackbar.make(container, "Datos guardados", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setUpViews() {
        homeTitle = findViewById(R.id.home_title)
        nicknameTitle = findViewById(R.id.nickname_title)
        spanishCheckBox = findViewById(R.id.language_one)
        englishCheckBox = findViewById(R.id.language_two)
        germanCheckBox = findViewById(R.id.language_three)
        otherCheckBox = findViewById(R.id.language_other)
        otherTextInput = findViewById(R.id.other_language_input)
        nickNameInput = findViewById(R.id.nickname_input)
        ageInput = findViewById(R.id.age_input)
        save = findViewById(R.id.save_button)
        container = findViewById(R.id.container)
    }
    private fun loadData() {
        userName = intent?.extras?.getString("current_user").orEmpty()
        val title = "BienvenidoEsta es la pantalla inicial, aquí $userName podrá ver todas sus preferencias"
        homeTitle.text = title
        handleLanguages()
        loadProfile()
    }

    private fun loadProfile() {
        //crear las claves para buscar y almacenar los datos, recuerde asociar las mismas al usuario de alguna manera
        val sharedPreferences = getSharedPreferences(userName, Context.MODE_PRIVATE)
        val nicknameKey = "nickname"
        val ageKey = "age"
        val nickNameString = sharedPreferences.getString(nicknameKey, "")
        nickNameInput.setText(nickNameString)
        nicknameTitle.text = nickNameString
        val ageString = sharedPreferences.getString(ageKey, "")
        ageInput.setText(ageString)
        handleLanguages()
    }

    private fun handleLanguages() {
        val sharedPreferences = getSharedPreferences(userName, Context.MODE_PRIVATE)
        val languagesKey = "languages"
        val languages = sharedPreferences.getStringSet(languagesKey, mutableSetOf())
        for (language in languages.orEmpty()) {
            resolveLanguage(language)
        }
        val otherLanguage = sharedPreferences.getString("other_language", "").orEmpty()
        if (otherLanguage.isNotEmpty()) {
            otherCheckBox.isChecked = true
            otherTextInput.setText(otherLanguage) // ocupar el mismo delimitador para almacenar el valor de este campo
        }
        //val languages = mutableSetOf()
        //ocupar resolveLanguage para cargar los datos iniciales en los checkboxs
    }

    private fun resolveLanguage(language: String) {
        if (language.isNotEmpty() && language == "Spanish") {
            spanishCheckBox.isChecked = true
        }
        if (language.isNotEmpty() && language == "English") {
            englishCheckBox.isChecked = true
        }
        if (language.isNotEmpty() && language == "German") {
            germanCheckBox.isChecked = true
        }
    }

    private fun saveLanguages() {
        val sharedPreferences = getSharedPreferences(userName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val selectedLanguages = mutableSetOf<String>()
        if (spanishCheckBox.isChecked) {
            selectedLanguages.add("Spanish")
        }
        if (englishCheckBox.isChecked) {
            selectedLanguages.add("English")
        }
        if (germanCheckBox.isChecked) {
            selectedLanguages.add("German")
        }
        if (otherCheckBox.isChecked) {
            val otherLanguage = otherTextInput.text.toString()
            editor.putString("other_language", otherLanguage)
        }
        editor.putStringSet("languages", selectedLanguages)
        editor.apply()
    }

    private fun saveNickNameAndAge() {
        val sharedPreferences = getSharedPreferences(userName, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val nicknameKey = "nickname"
        val nicknameValue = nickNameInput.text.toString()
        if (nicknameValue.isNotEmpty()) {
            editor.putString(nicknameKey, nicknameValue)
        } else {
            editor.remove(nicknameKey)
        }
        val ageKey = "age"
        val ageValue = ageInput.text.toString()
        if (ageValue.isNotEmpty()) {
            editor.putString(ageKey,ageValue)
        }else{
            editor.remove(ageKey)
        }
        editor.apply()
    }
}
