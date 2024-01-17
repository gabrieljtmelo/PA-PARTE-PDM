package ipca.stock.projetoaplicado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Rececionista_Aviso_Novo_Aviso_Individual_Activity : AppCompatActivity() {
    val moradores = arrayListOf<ClasseUtilizador>()

    val moradoresAdapter = MoradorAdapter()

    var moradorEscolhido : ClasseUtilizador? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rececionista_aviso_novo_aviso_individual)

        val listViewMoradores = findViewById<ListView>(R.id.listViewMoradores)
        listViewMoradores.adapter = moradoresAdapter


        val editTextTitulo = findViewById<EditText>(R.id.editTextTituloIndividualAviso)
        val editTextMensagem = findViewById<EditText>(R.id.editTextAvisoIndividualMessage)


        lifecycleScope.launch(Dispatchers.Main) {
            requestAllMoradores()
        }

        findViewById<Button>(R.id.buttonEnviarAvisoIndividual).setOnClickListener{
            if(moradorEscolhido != null){
                Backend.addIndividualWarnings(
                    lifecycleScope,
                    ClasseEnviarAvisoIndividual(
                        editTextTitulo.text.toString(),
                        editTextMensagem.text.toString(),
                        moradorEscolhido!!.id
                    )
                ){
                    finish()
                }
            }
        }



        findViewById<Button>(R.id.buttonsair).setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
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




            rootView.setOnClickListener {
                moradorEscolhido = moradores[position]
                val textViewCurrentMorador = findViewById<TextView>(R.id.textViewCurrentMorador)
                val textViewCurrentAndar = findViewById<TextView>(R.id.textViewAndar)
                textViewCurrentMorador.text = moradorEscolhido!!.name
                textViewCurrentAndar.text = moradorEscolhido!!.floor
            }

            return rootView
        }

    }

    fun requestAllMoradores(){
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