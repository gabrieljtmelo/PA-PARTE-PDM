package ipca.stock.projetoaplicado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Morador_Avisos_Historico_Activity : AppCompatActivity() {

    val avisosIndividuais = arrayListOf<ClasseAvisoIndividual>()
    val avisosIndividuaisAdapter = AvisoIndividualAdapter()

    val avisosGlobais = arrayListOf<ClasseAvisoGlobal>()
    val  avisosGlobaisAdapter = AvisoGlobalAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morador_avisos_historico)

        val listViewAvisosIndividuais = findViewById<ListView>(R.id.listViewMinhasReservas)
        listViewAvisosIndividuais.adapter = avisosIndividuaisAdapter

        val listViewAvisosGlobais = findViewById<ListView>(R.id.listViewTicketsRespondidos)
        listViewAvisosGlobais.adapter = avisosGlobaisAdapter

        lifecycleScope.launch(Dispatchers.Main) {
            requestData()
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

    inner class AvisoIndividualAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return avisosIndividuais.size
        }

        override fun getItem(position: Int): Any {
            return avisosIndividuais[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_morador_avisos_item,parent, false)
            val textViewData = rootView.findViewById<TextView>(R.id.textViewRowData)
            val textViewTitulo = rootView.findViewById<TextView>(R.id.textViewRowTituloAviso)


            textViewData.text = avisosIndividuais[position].title.toString()
            textViewTitulo.text = avisosIndividuais[position].description.toString()
            //textView



            rootView.setOnClickListener {
                val intent = Intent(this@Morador_Avisos_Historico_Activity,Morador_Avisos_Detalhes_Aviso_Activity::class.java )
                //intent.putExtra(Morador_Avisos_Detalhes_Aviso_Activity.DATA_TICKET_DATA, tickets[position].data)
                intent.putExtra(Morador_Avisos_Detalhes_Aviso_Activity.DATA_AVISO_GLOBAL_TITULO, avisosIndividuais[position].title)
                intent.putExtra(Morador_Avisos_Detalhes_Aviso_Activity.DATA_AVISO_GLOBAL_CONTEUDO, avisosIndividuais[position].description)
                intent.putExtra(Morador_Avisos_Detalhes_Aviso_Activity.DATA_AVISO_GLOBAL_USER, "Mim")
                intent.putExtra(Morador_Avisos_Detalhes_Aviso_Activity.DATA_AVISO_GLOBAL_POSITION, position)
                startActivity(intent)
            }

            return rootView
        }

    }

    inner class AvisoGlobalAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return avisosGlobais.size
        }

        override fun getItem(position: Int): Any {
            return avisosGlobais[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_morador_avisos_item,parent, false)
            val textViewData = rootView.findViewById<TextView>(R.id.textViewRowData)
            val textViewTitulo = rootView.findViewById<TextView>(R.id.textViewRowTituloAviso)


            textViewData.text = avisosGlobais[position].title.toString()
            textViewTitulo.text = avisosGlobais[position].description.toString()
            //textView



            rootView.setOnClickListener {
                val intent = Intent(this@Morador_Avisos_Historico_Activity,Morador_Avisos_Detalhes_Aviso_Activity::class.java )
                //intent.putExtra(Morador_Avisos_Detalhes_Aviso_Activity.DATA_TICKET_DATA, tickets[position].data)
                intent.putExtra(Morador_Avisos_Detalhes_Aviso_Activity.DATA_AVISO_GLOBAL_TITULO, avisosGlobais[position].title)
                intent.putExtra(Morador_Avisos_Detalhes_Aviso_Activity.DATA_AVISO_GLOBAL_CONTEUDO, avisosGlobais[position].description)
                intent.putExtra(Morador_Avisos_Detalhes_Aviso_Activity.DATA_AVISO_GLOBAL_USER, "Todos")
                intent.putExtra(Morador_Avisos_Detalhes_Aviso_Activity.DATA_AVISO_GLOBAL_POSITION, position)
                startActivity(intent)
            }

            return rootView
        }

    }

    fun requestData(){
        Backend.fetchIndividualWarningsByUserId().observe(this, Observer { result ->
            result.onSuccess {
                for (u in it){
                    avisosIndividuais.add(u)

                }
                avisosIndividuaisAdapter.notifyDataSetChanged()
            }
            result.onNetworkError {

            }
            result.onError {
                println("Erro na hora de fazer o request dos avisos individuais feitos por aquele morador")
            }
        })

        Backend.fetchGlobalWarnings().observe(this, Observer { result ->
            result.onSuccess {
                for (u in it){
                    avisosGlobais.add(u)

                }
                avisosGlobaisAdapter.notifyDataSetChanged()
            }
            result.onNetworkError {

            }
            result.onError {
                println("Erro na hora de fazer o request dos avisos globais feitos por aquele morador")
            }
        })

    }
}