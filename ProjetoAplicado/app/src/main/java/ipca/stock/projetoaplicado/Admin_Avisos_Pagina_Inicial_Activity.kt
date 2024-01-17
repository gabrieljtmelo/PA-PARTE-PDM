package ipca.stock.projetoaplicado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class Admin_Avisos_Pagina_Inicial_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_avisos_pagina_inicial)

        findViewById<Button>(R.id.buttonsair).setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }


        findViewById<ImageView>(R.id.iconHome).setOnClickListener{
            val intent = Intent(this,Admin_Notificacoes_Pagina_Inicial::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.iconVisitante).setOnClickListener{
            val intent = Intent(this,Admin_Reservas_Activity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.iconRega).setOnClickListener{
            val intent = Intent(this,Admin_Tickets_Historico_Activity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.iconAvisos).setOnClickListener{
            val intent = Intent(this,Admin_Avisos_Pagina_Inicial_Activity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.iconPerfilMorador).setOnClickListener {
            val intent = Intent(this, Admin_Moradores_ListView_Activity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.buttonMeusAvisos).setOnClickListener{
            val intent = Intent(this, Admin_Avisos_Meus_Avisos_Activity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.buttonAvisoGlobal).setOnClickListener{
            val intent = Intent(this, Admin_Avisos_Novo_Global_Activity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.buttonAvisoIndividual).setOnClickListener{
            val intent = Intent(this, Admin_Avisos_Novo_Individual_Activity::class.java)
            startActivity(intent)

        }


    }
}