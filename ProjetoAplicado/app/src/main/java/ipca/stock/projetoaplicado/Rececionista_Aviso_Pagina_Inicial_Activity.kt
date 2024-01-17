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

class Rececionista_Aviso_Pagina_Inicial_Activity : AppCompatActivity() {

    val avisosIndividuais = arrayListOf<ClasseAvisoIndividual>()
    val avisosIndividuaisAdapter = AvisoIndividualAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rececionista_aviso_pagina_inicial)

        val listViewAvisosIndividuais = findViewById<ListView>(R.id.listViewRegas)
        listViewAvisosIndividuais.adapter = avisosIndividuaisAdapter

        lifecycleScope.launch(Dispatchers.Main) {
            requestData()
        }

        findViewById<Button>(R.id.buttonLigar2).setOnClickListener{
            startActivity(Intent(this,Rececionista_Aviso_Novo_Aviso_Individual_Activity::class.java))
        }


        findViewById<Button>(R.id.buttonsair).setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        findViewById<ImageView>(R.id.iconVisitante).setOnClickListener{
            val intent = Intent(this,Rececionista_Visitantes_Historico_Activity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.iconRega).setOnClickListener{
            val intent = Intent(this,Rececionista_Rega_Activity::class.java)
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.iconAvisos).setOnClickListener{
            val intent = Intent(this,Rececionista_Aviso_Pagina_Inicial_Activity::class.java)
            startActivity(intent)
        }
        findViewById<Button>(R.id.buttonsair).setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

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
                val intent = Intent(this@Rececionista_Aviso_Pagina_Inicial_Activity,Morador_Avisos_Detalhes_Aviso_Activity::class.java )
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

    fun requestData(){
        Backend.fetchIndividualWarnings().observe(this, Observer { result ->
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


    }

}