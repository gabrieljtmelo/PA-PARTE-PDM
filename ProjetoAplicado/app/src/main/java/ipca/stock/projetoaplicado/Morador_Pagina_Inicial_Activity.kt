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
import java.util.Date

class Morador_Pagina_Inicial_Activity : AppCompatActivity() {


    val avisos = arrayListOf<ClasseAvisoGlobal>()

    val avisosAdapter = AvisoAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morador_pagina_inicial)

        val listViewAvisos = findViewById<ListView>(R.id.listViewHomePage)
        listViewAvisos.adapter = avisosAdapter

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

    inner class AvisoAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return avisos.size
        }

        override fun getItem(position: Int): Any {
            return avisos[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_morador_avisos_item,parent, false)
            val textViewData = rootView.findViewById<TextView>(R.id.textViewRowData)
            val textViewTitulo = rootView.findViewById<TextView>(R.id.textViewRowTituloAviso)


            textViewTitulo.text = avisos[position].title
            textViewData.text = avisos[position].createdAt.toString()


            rootView.setOnClickListener {
                val intent = Intent(this@Morador_Pagina_Inicial_Activity,Morador_Avisos_Detalhes_Aviso_Activity::class.java )
                intent.putExtra(Morador_Avisos_Detalhes_Aviso_Activity.DATA_AVISO_GLOBAL_DATA, avisos[position].createdAt)
                intent.putExtra(Morador_Avisos_Detalhes_Aviso_Activity.DATA_AVISO_GLOBAL_TITULO, avisos[position].title)
                intent.putExtra(Morador_Avisos_Detalhes_Aviso_Activity.DATA_AVISO_GLOBAL_CONTEUDO, avisos[position].description)
                intent.putExtra(Morador_Avisos_Detalhes_Aviso_Activity.DATA_AVISO_GLOBAL_POSITION, position)
                startActivity(intent)
            }

            return rootView
        }

    }


    fun requestData(){
        Backend.fetchGlobalWarnings().observe(this, Observer { result ->
            result.onSuccess {
                var allTickets = ""
                for (u in it){
                    avisos.add(u)
                }
                avisosAdapter.notifyDataSetChanged()

            }
            result.onNetworkError {

            }
            result.onError {

            }
        })
    }


}

