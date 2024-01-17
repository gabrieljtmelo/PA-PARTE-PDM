package ipca.stock.projetoaplicado

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ipca.stock.projetoaplicado.MQTT
import ipca.stock.projetoaplicado.MQTT.MqttMessageListener


class Rececionista_Rega_Activity : AppCompatActivity() {

    val regas = arrayListOf<ClasseRega>()

    private lateinit var mqttHandler: MQTT
    private lateinit var textViewTA2: TextView

    val regasAdapter = RegaAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rececionista_rega)

        textViewTA2 = findViewById(R.id.textViewTA2)

        fun subscribeToTopic(topic: String) {
            mqttHandler.subscribe(topic)
        }

        mqttHandler = MQTT().apply {
            setListener(object : MQTT.MqttMessageListener {
                override fun onMessageReceived(topic: String, message: String) {
                    runOnUiThread {
                        if (topic == "actualTemp") {
                            textViewTA2.text = "$message graus"
                        }
                    }
                }
            })
            connect("tcp://192.168.1.20:1883", "AndroidClient")
            subscribe("actualTemp")
        }

        fun publishMessage(topic: String, message: String) {
            mqttHandler.subscribe(topic)
            mqttHandler.publish(topic, message)
        }


        findViewById<Button>(R.id.buttonLigar2).setOnClickListener { button ->
            val botao = button as Button
            val comando = if (botao.text.toString() == "OFF") "ON" else "OFF"
            botao.text = comando
            botao.setBackgroundColor(
                if (comando == "ON") ContextCompat.getColor(this, R.color.green)
                else ContextCompat.getColor(this, R.color.red)
            )

            if (comando == "ON") {
                val temperaturaAtual = textViewTA2.text.toString().split(" ")[0]
                regas.add(ClasseRega(temperaturaAtual))
                regasAdapter.notifyDataSetChanged()
                mqttHandler.publish("rega", comando)
                Backend.addRega(lifecycleScope, ClasseEnviarRega(temperaturaAtual)) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        requestData()
                    }
                }
            } else {
                mqttHandler.publish("rega", comando)
            }
        }


        val listViewRegas = findViewById<ListView>(R.id.listViewRegas)
        listViewRegas.adapter = regasAdapter

        lifecycleScope.launch(Dispatchers.Main) {
            requestData()
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
    fun messageArrived(topic: String, message: String) {
        if (topic == "actualTemp") {
            textViewTA2.text = "$message graus"
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        mqttHandler.disconnect()
    }



    inner class RegaAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return regas.size
        }

        override fun getItem(position: Int): Any {
            return regas[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val view = convertView ?: layoutInflater.inflate(R.layout.row_morador_avisos_item, parent, false)
            val textViewData = view.findViewById<TextView>(R.id.textViewRowData)
            val textViewTitulo = view.findViewById<TextView>(R.id.textViewRowTituloAviso)


            textViewTitulo.text = regas[position].temperature
            textViewData.text = regas[position].initialDate

            return view
        }


    }

    fun requestData(){
        Backend.fetchRegas().observe(this, Observer { result ->
            result.onSuccess {
                for (u in it){
                    Log.d("Rececionista_Rega_Activity", "Rega recebida: $u")
                    regas.add(u)
                }
                regasAdapter.notifyDataSetChanged()

            }
            result.onNetworkError {

            }
            result.onError {
                println("Erro na hora de fazer o request dos tickets feitos por aquele morador")
            }
        })
    }
}