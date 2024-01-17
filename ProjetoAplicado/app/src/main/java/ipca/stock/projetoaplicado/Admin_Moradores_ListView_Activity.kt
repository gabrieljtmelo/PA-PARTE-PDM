package ipca.stock.projetoaplicado

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Admin_Moradores_ListView_Activity : AppCompatActivity() {

    val moradores = arrayListOf<ClasseUtilizador>()

    val moradoresAdapter = MoradorAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_moradores_list_view)

        val listViewMoradores = findViewById<ListView>(R.id.listViewMinhasReservas)
        listViewMoradores.adapter = moradoresAdapter

        lifecycleScope.launch(Dispatchers.Main) {
            requestData()
        }

        // Configurações para os cliques nos ícones
        findViewById<Button>(R.id.buttonsair).setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }


        findViewById<ImageView>(R.id.iconHome).setOnClickListener {
            val intent = Intent(this, Admin_Notificacoes_Pagina_Inicial::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.buttonsair).setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.iconVisitante).setOnClickListener {
            val intent = Intent(this, Admin_Reservas_Activity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.iconRega).setOnClickListener {
            val intent = Intent(this, Admin_Tickets_Historico_Activity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.iconAvisos).setOnClickListener {
            val intent = Intent(this, Admin_Avisos_Pagina_Inicial_Activity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.iconPerfilMorador).setOnClickListener {
            val intent = Intent(this, Admin_Registo_De_Morador_Activity::class.java)
            startActivity(intent)
        }

    }

    inner class MoradorAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return moradores.size
        }

        override fun getItem(position: Int): Any {
            return moradores[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_admin_moradores_list_view,parent, false)
            val textViewNome = rootView.findViewById<TextView>(R.id.textViewNomeMorador)
            val textViewAndar = rootView.findViewById<TextView>(R.id.textViewDescricao)
            val textViewDataNasc = rootView.findViewById<TextView>(R.id.textViewDataNascimento)


            textViewNome.text = moradores[position].name.toString()
            textViewAndar.text = moradores[position].floor.toString()
            textViewDataNasc.text = moradores[position].birth.toString()
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

    fun requestData(){
        Backend.fetchUsers().observe(this, Observer { result ->
            result.onSuccess {
                for (u in it){
                    if(u.role == 1) moradores.add(u)
                }
                moradoresAdapter.notifyDataSetChanged()

            }
            result.onNetworkError {

            }
            result.onError {
                println("Erro na hora de fazer o request dos tickets feitos por aquele morador")
            }
        })
    }
}
