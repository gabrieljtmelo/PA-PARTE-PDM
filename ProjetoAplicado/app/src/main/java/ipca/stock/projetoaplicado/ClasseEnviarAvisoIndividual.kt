package ipca.stock.projetoaplicado

import org.json.JSONObject

class ClasseEnviarAvisoIndividual (
    var title           : String? = null,
    var description     : String? = null,
    var userId          : Int? = null

    ){
    fun toJson() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("title"         , title       )
        jsonObject.put("description"   , description )
        jsonObject.put("userId"        , userId      )
        return jsonObject
    }
}