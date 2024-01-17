package ipca.stock.projetoaplicado

import org.json.JSONObject

class ClasseEnviarUtilizador (
    var email      : String? = null,
    var name       : String? = null,
    var password   : String? = null,
    var birth      : String? = null,
    var floor      : String? = null,
    var role       : Int? = null

    ){
    fun toJson() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("email"     , email    )
        jsonObject.put("name"      , name     )
        jsonObject.put("password"  , password )
        jsonObject.put("birth"     , birth    )
        jsonObject.put("floor"     , floor    )
        jsonObject.put("role"      , role     )
        return jsonObject
    }
}