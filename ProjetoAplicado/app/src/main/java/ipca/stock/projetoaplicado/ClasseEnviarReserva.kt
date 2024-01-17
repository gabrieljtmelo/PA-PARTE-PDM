package ipca.stock.projetoaplicado

import org.json.JSONObject

class ClasseEnviarReserva (
    var date           : String? = null,
    var commonAreaId   : Int? = null,

    ){
    fun toJson() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("date"           , date         )
        jsonObject.put("commonAreaId"   , commonAreaId )
        return jsonObject
    }
}
