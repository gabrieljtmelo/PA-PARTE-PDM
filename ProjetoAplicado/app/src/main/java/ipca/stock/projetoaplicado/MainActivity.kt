package ipca.stock.projetoaplicado

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(this,PreLogin::class.java)
        startActivity(intent)

        /*val buttonAdmin = findViewById<CardView>(R.id.cardViewAdmin)
        val buttonMorador = findViewById<CardView>(R.id.cardViewMorador)
        val buttonRececionista = findViewById<CardView>(R.id.cardViewRececionista)

        buttonAdmin.setOnClickListener{
            val intent = Intent(this,PreLogin::class.java)
            intent.putExtra(MainActivity.DATA_USER, 0)
            startActivity(intent)
        }

        buttonMorador.setOnClickListener{
            val intent = Intent(this,PreLogin::class.java)
            intent.putExtra(MainActivity.DATA_USER, 1)
            startActivity(intent)
        }

        buttonRececionista.setOnClickListener{
            val intent = Intent(this,PreLogin::class.java)
            intent.putExtra(MainActivity.DATA_USER, 2)
            startActivity(intent)
        }
        */
    }

    companion object{
        const val DATA_USER = "data_user"
    }
}
