package ipca.stock.projetoaplicado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.Date

class Morador_Tickets_Detalhes_Ticket_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morador_tickets_detalhes_ticket)

        val textViewConteudo = findViewById<TextView>(R.id.textViewConteudoMensagem)
        val textViewRespostaAdmin = findViewById<TextView>(R.id.textViewRespostaAdmin)
        val textViewTitulo = findViewById<TextView>(R.id.textViewTituloTicket)
        val textViewEstadoTicket = findViewById<TextView>(R.id.textViewEstadoTicket)

        findViewById<Button>(R.id.buttonsair).setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        intent.extras?.let {
            val position = it.getInt(Morador_Tickets_Detalhes_Ticket_Activity.DATA_TICKET_POSITION, -1)
            val titulo = it.getString(Morador_Tickets_Detalhes_Ticket_Activity.DATA_TICKET_NAME, "Sem titulo")
            val reply = it.getString(Morador_Tickets_Detalhes_Ticket_Activity.DATA_TICKET_REPLY, "Sem Resposta")
            val description = it.getString(Morador_Tickets_Detalhes_Ticket_Activity.DATA_TICKET_DESCRIPTION, "Sem conteudo")
            textViewConteudo.setText(description)
            textViewRespostaAdmin.setText(reply)
            textViewTitulo.setText(titulo)
            if(reply == "Sem Resposta") textViewEstadoTicket.setText("Ticket Não Respondido")
            else {
                textViewEstadoTicket.setText("Ticket Respondido")
                textViewEstadoTicket.setTextColor(255)
            }
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

    companion object {
        const val DATA_TICKET_DATA = "data_ticket_data"
        const val DATA_TICKET_NAME = "data_ticket_name"
        const val DATA_TICKET_DESCRIPTION = "data_ticket_description"
        const val DATA_TICKET_POSITION = "data_ticket_position"
        const val DATA_TICKET_REPLY = "data_ticket_reply"
    }
}