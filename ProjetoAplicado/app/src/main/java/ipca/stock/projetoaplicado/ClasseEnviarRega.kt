package ipca.stock.projetoaplicado

import org.json.JSONObject

class ClasseEnviarRega (
    var temperature           : String? = null,
    ){
        fun toJson() : JSONObject {
            val jsonObject = JSONObject()
            jsonObject.put("temperature"   , temperature )
            return jsonObject
        }
    }
