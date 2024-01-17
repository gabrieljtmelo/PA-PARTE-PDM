import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class ClasseCustomDatePickerDialog(context: Context, private val onDateSetListener: OnDateSetListener, year: Int, month: Int, dayOfMonth: Int) :
    DatePickerDialog(context, onDateSetListener, year, month, dayOfMonth) {



    override fun onDateChanged(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        // Aqui você pode adicionar lógica personalizada para limitar datas, alterar cores, etc.
        // Por exemplo, para impedir a seleção de datas anteriores à data atual:
        val hoje = Calendar.getInstance()
        if (year < hoje.get(Calendar.YEAR) || (year == hoje.get(Calendar.YEAR) && month < hoje.get(Calendar.MONTH)) || (year == hoje.get(Calendar.YEAR) && month == hoje.get(Calendar.MONTH) && dayOfMonth < hoje.get(Calendar.DAY_OF_MONTH))) {
            // Impede a seleção de datas anteriores à data atual
            Toast.makeText(context, "Data inválida", Toast.LENGTH_SHORT).show()
            view.updateDate(hoje.get(Calendar.YEAR), hoje.get(Calendar.MONTH), hoje.get(Calendar.DAY_OF_MONTH))
        }
    }


}