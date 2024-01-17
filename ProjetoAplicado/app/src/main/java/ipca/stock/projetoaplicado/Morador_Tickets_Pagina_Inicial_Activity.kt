package ipca.stock.projetoaplicado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class Morador_Tickets_Pagina_Inicial_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morador_tickets_pagina_inicial)

        findViewById<Button>(R.id.buttonMinhasReservas).setOnClickListener{
            val intent = Intent(this,Morador_Tickets_Historico_Activity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.buttonNovaReserva).setOnClickListener{
            val intent = Intent(this,Morador_Tickets_Novo_Ticket_Activity::class.java)
            startActivity(intent)
        }

        // region Botões Menu
        findViewById<Button>(R.id.buttonsair).setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        findViewById<ImageView>(R.id.iconHome).setOnClickListener{
            val intent = Intent(this,Morador_Pagina_Inicial_Activity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.iconReservas).setOnClickListener{
            val intent = Intent(this,Morador_Reservas_Pagina_Inicial_Activity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.iconTickets).setOnClickListener{
            val intent = Intent(this,Morador_Tickets_Pagina_Inicial_Activity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.iconAvisos).setOnClickListener{
            val intent = Intent(this,Morador_Avisos_Pagina_Inicial_Activity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.iconPerfilMorador).setOnClickListener{
            val intent = Intent(this,Morador_Perfil_Activity::class.java)
            startActivity(intent)
        }
        // endregion

    }
}