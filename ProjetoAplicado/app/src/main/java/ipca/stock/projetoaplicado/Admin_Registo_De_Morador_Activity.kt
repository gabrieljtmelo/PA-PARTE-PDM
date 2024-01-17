package ipca.stock.projetoaplicado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope

class Admin_Registo_De_Morador_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_registo_de_morador)

        val editTextNome = findViewById<EditText>(R.id.etName)
        val editTextEmail = findViewById<EditText>(R.id.etEmail)
        val editTextPassword = findViewById<EditText>(R.id.etPassword)
        val editTextAndar = findViewById<EditText>(R.id.editTextAndar)
        val aniversario = "2002-01-16T17:39:19.309Z"
        val editTextUserRole = findViewById<EditText>(R.id.editTextUserRole)

        findViewById<Button>(R.id.btnCreateAccount).setOnClickListener{
            Backend.addUser(
                lifecycleScope,
                ClasseEnviarUtilizador(
                    editTextNome.text.toString(),
                    editTextEmail.text.toString(),
                    editTextPassword.text.toString(),
                    aniversario,
                    editTextAndar.text.toString(),
                    1
                )
            ){
                finish()
            }

        }

    }
}