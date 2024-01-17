package ipca.stock.projetoaplicado

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Admin_Notificacoes_Pagina_Inicial : AppCompatActivity() {

    val reservas = arrayListOf<ClasseReserva>()
    val reservasAdapter = ReservaAdapter()

    val tickets = arrayListOf<ClasseTicket>()
    val ticketsAdapter = TicketAdapter()

    val resultLauncher =
        registerForActivityResult(
            ActivityResultContracts
                .StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK){
                tickets.clear()
                requestData()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_notificacoes_pagina_inicial)

        val listViewReservas = findViewById<ListView>(R.id.listViewMinhasReservas)
        listViewReservas.adapter = reservasAdapter

        val listViewTickets = findViewById<ListView>(R.id.listViewMeusTickets)
        listViewTickets.adapter = ticketsAdapter

        lifecycleScope.launch(Dispatchers.Main) {
            requestData()
        }

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

        findViewById<ImageView>(R.id.iconPerfilMorador).setOnClickListener{
            val intent = Intent(this,Admin_Moradores_ListView_Activity::class.java)
            startActivity(intent)
        }



    }

    inner class ReservaAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return reservas.size
        }

        override fun getItem(position: Int): Any {
            return reservas[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_morador_avisos_item,parent, false)
            val textViewData = rootView.findViewById<TextView>(R.id.textViewRowData)
            val textViewTitulo = rootView.findViewById<TextView>(R.id.textViewRowTituloAviso)


            textViewData.text = reservas[position].date.toString()
            if(reservas[position].canceled == true) textViewData.setTextColor(Color.RED)
            if(reservas[position].commonAreaId == 1) textViewTitulo.text = "Piscina"
            else textViewTitulo.text = "Churrasqueira"
            //textView



            /*rootView.setOnClickListener {
                val intent = Intent(this@Morador_Tickets_Historico_Activity,Morador_Tickets_Detalhes_Ticket_Activity::class.java )
                //intent.putExtra(Morador_Avisos_Detalhes_Aviso_Activity.DATA_TICKET_DATA, tickets[position].data)
                intent.putExtra(Morador_Tickets_Detalhes_Ticket_Activity.DATA_TICKET_NAME, tickets[position].name)
                intent.putExtra(Morador_Tickets_Detalhes_Ticket_Activity.DATA_TICKET_DESCRIPTION, tickets[position].description)
                intent.putExtra(Morador_Tickets_Detalhes_Ticket_Activity.DATA_TICKET_REPLY, tickets[position].reply)
                intent.putExtra(Morador_Tickets_Detalhes_Ticket_Activity.DATA_TICKET_POSITION, position)
                startActivity(intent)
            }*/

            return rootView
        }

    }

    inner class TicketAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return tickets.size
        }

        override fun getItem(position: Int): Any {
            return tickets[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_morador_avisos_item,parent, false)
            val textViewData = rootView.findViewById<TextView>(R.id.textViewRowData)
            val textViewTitulo = rootView.findViewById<TextView>(R.id.textViewRowTituloAviso)


            textViewData.text = tickets[position].name.toString()
            textViewTitulo.text = tickets[position].description.toString()
            //textView



            rootView.setOnClickListener {
                val intent = Intent(this@Admin_Notificacoes_Pagina_Inicial,Admin_Tickets_Resposta_Ticket_Activity::class.java)
                //intent.putExtra(Morador_Avisos_Detalhes_Aviso_Activity.DATA_TICKET_DATA, tickets[position].data)
                intent.putExtra(Admin_Tickets_Resposta_Ticket_Activity.DATA_TICKET_ID, tickets[position].id)
                intent.putExtra(Admin_Tickets_Resposta_Ticket_Activity.DATA_TICKET_NAME, tickets[position].name)
                intent.putExtra(Admin_Tickets_Resposta_Ticket_Activity.DATA_TICKET_DESCRIPTION, tickets[position].description)
                intent.putExtra(Admin_Tickets_Resposta_Ticket_Activity.DATA_TICKET_POSITION, position)
                resultLauncher.launch(intent)
            }

            return rootView
        }

    }

    fun requestData(){
        Backend.fetchSchedules().observe(this, Observer { result ->
            result.onSuccess {
                for (r in it){
                    reservas.add(r)
                }
                reservasAdapter.notifyDataSetChanged()
            }
            result.onNetworkError {
            }
            result.onError {
                println("Erro na hora de fazer o request das reservas feitas por aquele morador")
            }
        })

        Backend.fetchTickets().observe(this, Observer { result ->
            result.onSuccess {
                for (t in it){
                    if(t.reply == null){
                        tickets.add(t)
                    }
                }
                ticketsAdapter.notifyDataSetChanged()
            }
            result.onNetworkError {
            }
            result.onError {
                println("Erro na hora de fazer o request dos tickets feitos por aquele morador")
            }
        })
    }
}