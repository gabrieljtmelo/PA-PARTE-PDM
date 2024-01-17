package ipca.stock.projetoaplicado

import android.app.Activity
import java.util.Base64
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PreLogin : AppCompatActivity() {

    var currentUser = -1
    var listUsers = arrayListOf<ClasseUtilizador>()
    var email = "xxx"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pre_login)


        /*intent.extras?.let {
            currentUser = it.getInt(DATA_USER, -1)

        }*/

        val etEmail = findViewById<EditText>(R.id.editTextEmail)
        val etPassword = findViewById<EditText>(R.id.editTextPassword)
        val switchMostrarSenha = findViewById<Switch>(R.id.switchMostrarSenha)
        val buttonAcessar = findViewById<Button>(R.id.buttonAcessar)

        switchMostrarSenha.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Se o interruptor estiver ativo, mostra a senha
                etPassword.transformationMethod = null
            } else {
                // Caso contrário, esconde a senha
                etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            // Coloca o cursor no final do texto após a mudança
            etPassword.setSelection(etPassword.text.length)
        }

        buttonAcessar.setOnClickListener{
            email = etEmail.text.toString()
            val password = etPassword.text.toString()

            lifecycleScope.launch(Dispatchers.IO) {
                Backend.loginRequest(email, password)
                    .onSuccess {
                        lifecycleScope.launch(Dispatchers.Main) {
                            requestData()/*
                            when (currentUser) {
                                0 -> {
                                    IrAdmin()
                                }
                                1 -> {
                                    IrMorador()
                                }
                                2 -> {
                                    IrRececionista()
                                }
                                -1 -> {
                                    println("erro 222")
                                }
                                else -> println("error")
                            }*/

                        }
                    }
                    .onNetworkError {
                        lifecycleScope.launch(Dispatchers.Main) {
                            println("erro de net")
                        }
                    }
                    .onError {
                        lifecycleScope.launch(Dispatchers.Main) {
                            println("erro do login")
                        }
                    }
            }

            // Chama a função para realizar login


        }


    }


    fun requestData() {

        Backend.fetchUserByToken().observe(this, Observer { result ->
            result.onSuccess {
                        when (it.role) {
                            0 -> {
                                IrAdmin()
                            }
                            1 -> {
                                IrMorador()
                            }
                            2 -> {
                                IrRececionista()
                            }
                            -1 -> {
                                println("erro 222")
                            }
                            else -> println("error")
                        }
            }
            result.onNetworkError {

            }
            result.onError {
                println("Erro no fetchUserByToken")
            }
        })

    }




    fun IrMorador(){
        startActivity(Intent(this, Morador_Pagina_Inicial_Activity::class.java))
    }

    fun IrAdmin(){
        startActivity(Intent(this, Admin_Notificacoes_Pagina_Inicial::class.java))
    }

    fun IrRececionista(){
        startActivity(Intent(this, Rececionista_Visitantes_Historico_Activity::class.java))
    }

    companion object{
        const val DATA_USER = "data_user"
    }

}