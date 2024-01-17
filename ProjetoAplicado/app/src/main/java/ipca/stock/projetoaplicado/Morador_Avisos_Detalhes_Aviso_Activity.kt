package ipca.stock.projetoaplicado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import java.util.Date

class Morador_Avisos_Detalhes_Aviso_Activity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morador_avisos_detalhes_aviso)

        val textViewTitulo = findViewById<TextView>(R.id.textViewTituloAviso)
        val textViewConteudo = findViewById<TextView>(R.id.textViewConteudoMensagem)
        val textViewParaQuem = findViewById<TextView>(R.id.textViewParaQuem)

        findViewById<Button>(R.id.buttonsair).setOnClickListener{
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        intent.extras?.let {
            val position = it.getInt(DATA_AVISO_GLOBAL_POSITION, -1)
            val user = it.getString(DATA_AVISO_GLOBAL_USER, "Para quem")
            val titulo = it.getString(DATA_AVISO_GLOBAL_TITULO, "Sem titulo")
            val conteudo = it.getString(DATA_AVISO_GLOBAL_CONTEUDO, "Sem conteudo")
            textViewTitulo.setText(titulo)
            textViewConteudo.setText(conteudo)
            textViewParaQuem.setText("Para " + user)
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
        const val DATA_AVISO_GLOBAL_DATA = "data_aviso_global_data"
        const val DATA_AVISO_GLOBAL_TITULO = "data_aviso_global_titulo"
        const val DATA_AVISO_GLOBAL_CONTEUDO = "data_aviso_global_conteudo"
        const val DATA_AVISO_GLOBAL_POSITION = "data_aviso_global_position"
        const val DATA_AVISO_GLOBAL_USER = "data_aviso_global_user"
    }
}


