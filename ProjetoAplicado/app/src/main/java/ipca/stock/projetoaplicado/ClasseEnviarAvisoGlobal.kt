package ipca.stock.projetoaplicado

import org.json.JSONObject

class ClasseEnviarAvisoGlobal (
    var title           : String? = null,
    var description    : String? = null,

    ){
    fun toJson() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("title"         , title        )
        jsonObject.put("description"  , description )
        return jsonObject
    }
}
