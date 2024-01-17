package ipca.stock.projetoaplicado

import org.json.JSONObject

class ClasseEnviarResposta (

    var name           : String? = null,
    var description    : String? = null,
    var reply       : String? = null
    ){
    fun toJson() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("name"         , name        )
        jsonObject.put("description"  , description )
        jsonObject.put("reply", reply)
        return jsonObject
    }
}

