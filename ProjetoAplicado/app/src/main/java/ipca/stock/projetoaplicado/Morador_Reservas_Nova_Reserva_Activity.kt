package ipca.stock.projetoaplicado

import ClasseCustomDatePickerDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import org.threeten.bp.format.DateTimeParseException
import java.text.SimpleDateFormat
import org.threeten.bp.Instant
import org.threeten.bp.ZonedDateTime
import java.util.Calendar
import org.threeten.bp.ZoneId



class Morador_Reservas_Nova_Reserva_Activity : AppCompatActivity() {

    var currentArea = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_morador_reservas_nova_reserva)

        val calendario = Calendar.getInstance()

        val calendarioLable = LocalDateTime.now()

        val datePickerListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            // Atualiza a data selecionada
            calendario.set(Calendar.YEAR, year)
            calendario.set(Calendar.MONTH, month)
            calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            // Chama o método para imprimir a data ou realizar outras ações
            println("data antes do input: " + calendario.toString())

            updateLable(calendario)
        }

        // Piscina
        findViewById<Button>(R.id.buttonMinhasReservas).setOnClickListener {
            currentArea = 1
            val datePicker = ClasseCustomDatePickerDialog(
                this,
                datePickerListener,
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }

        // Churrasqueira
        findViewById<Button>(R.id.buttonNovaReserva).setOnClickListener {
            currentArea = 2
            val datePicker = ClasseCustomDatePickerDialog(
                this,
                datePickerListener,
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
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

    fun updateLable(calendario: Calendar) {
        val formato = "yyyy-MM-dd'T'HH:mm:ss.SSSX"
        val sdf = SimpleDateFormat(formato, Locale.ITALY)

        val dataSelecionada = sdf.format(calendario.time)
        println("data selecionado: " + dataSelecionada)
        sendData(dataSelecionada)
    }

    fun sendData(data: String){
        Backend.addSchedule(
            lifecycleScope,
            ClasseEnviarReserva(
                data,
                currentArea,
            )
        ){
            Toast.makeText(this, "Êxito na reserva", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}