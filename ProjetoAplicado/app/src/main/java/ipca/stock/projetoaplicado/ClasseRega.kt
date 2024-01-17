package ipca.stock.projetoaplicado

import org.json.JSONObject

class ClasseRega(
    var initialDate    : String? = null,
    var temperature    : String? = null,
    var userId         : Int? = null
){

    companion object{
        fun fromJson(jsonObject: JSONObject) : ClasseRega {
            return ClasseRega(
                jsonObject["initialDate"     ] as String? ,
                jsonObject["temperature"     ] as String? ,
                jsonObject["userId"          ] as Int?,
            )
        }
    }
}
