package ipca.stock.projetoaplicado

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope

class Admin_Tickets_Resposta_Ticket_Activity : AppCompatActivity() {
    var ticketId = -1
    var titulo = ""
    var description = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_tickets_resposta_ticket)

        val textViewTitulo = findViewById<TextView>(R.id.textViewTituloTicketResposta)
        val textViewConteudo = findViewById<TextView>(R.id.textViewDescriptionTicketResposta)
        val editTextViewResposta = findViewById<EditText>(R.id.etMessage)


        intent.extras?.let {
            val position = it.getInt(Admin_Tickets_Resposta_Ticket_Activity.DATA_TICKET_POSITION, -1)
            ticketId = it.getInt(Admin_Tickets_Resposta_Ticket_Activity.DATA_TICKET_ID, -1)
            titulo = it.getString(Admin_Tickets_Resposta_Ticket_Activity.DATA_TICKET_NAME, "Sem titulo")
            description = it.getString(Admin_Tickets_Resposta_Ticket_Activity.DATA_TICKET_DESCRIPTION, "Sem conteudo")
            textViewConteudo.setText(description)
            textViewTitulo.setText(titulo)
        }



        findViewById<Button>(R.id.btnSend).setOnClickListener{
            Backend.RespostaTicket(
                lifecycleScope,
                ClasseEnviarResposta(
                    titulo.toString(),
                    description.toString(),
                    editTextViewResposta.text.toString()
                ),
                ticketId.toString()
            ){
                setResult(Activity.RESULT_OK)
                finish()
            }

        }


    }

    companion object {
        const val DATA_TICKET_DATA = "data_ticket_data"
        const val DATA_TICKET_ID = "data_ticket_id"
        const val DATA_TICKET_NAME = "data_ticket_name"
        const val DATA_TICKET_DESCRIPTION = "data_ticket_description"
        const val DATA_TICKET_POSITION = "data_ticket_position"
        const val DATA_TICKET_REPLY = "data_ticket_reply"
    }
}