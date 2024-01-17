package ipca.stock.projetoaplicado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer

class Morador_Perfil_Activity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morador_perfil)

        val textViewProfile = findViewById<TextView>(R.id.profile_name)

        findViewById<Button>(R.id.button_profile2).setOnClickListener{
            requestData(textViewProfile, true)
        }

        findViewById<Button>(R.id.button_apartment2).setOnClickListener{
            requestData(textViewProfile, false)
        }


        // region Botões Menu
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

        findViewById<ImageView>(R.id.imageView).setOnClickListener{
            val intent = Intent(this,Morador_Perfil_Activity::class.java)
            startActivity(intent)
        }
        // endregion



    }

    fun requestData(tv: TextView, boolean: Boolean){
        Backend.fetchUserByToken().observe(this, Observer { result ->
            result.onSuccess {
                if(boolean) tv.setText(it.name)
                    else tv.setText(it.floor)
            }
            result.onNetworkError {

            }
            result.onError {
                println("Erro na hora de fazer o request dos avisos individuais feitos por aquele morador")
            }
        })
    }
}