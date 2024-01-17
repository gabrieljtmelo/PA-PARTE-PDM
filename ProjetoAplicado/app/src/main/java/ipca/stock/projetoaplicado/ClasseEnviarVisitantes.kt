package ipca.stock.projetoaplicado

import org.json.JSONObject

class ClasseEnviarVisitantes (
    var name       : String? = null,
    var visitDate   : String? = null,
    var userId       : Int? = null

){
    fun toJson() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("name"       , name      )
        jsonObject.put("visitDate"  , visitDate )
        jsonObject.put("userId"     , userId    )
        return jsonObject
    }
}