package ipca.stock.projetoaplicado

import org.json.JSONObject
import java.util.Date

class ClasseEnviarTicket (
    var name           : String? = null,
    var description    : String? = null,

){
    fun toJson() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("name"         , name        )
        jsonObject.put("description"  , description )
        return jsonObject
    }
}