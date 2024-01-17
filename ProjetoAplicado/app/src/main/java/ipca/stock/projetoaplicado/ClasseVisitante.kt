package ipca.stock.projetoaplicado

import org.json.JSONObject
import java.util.Date

data class ClasseVisitante (
    var createdAt      : String? = null,
    var updatedAt      : String? = null,
    var id             : Int? = null,
    var name           : String? = null,
    var visitDate      : String? = null,
    var userId         : Int? = null,
){
    fun toJson() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("createdAt"    , createdAt  )
        jsonObject.put("updatedAt"    , updatedAt  )
        jsonObject.put("id"           , id         )
        jsonObject.put("name"         , name       )
        jsonObject.put("visitDate"    , visitDate  )
        jsonObject.put("userId"       , userId     )


        return jsonObject
    }

    companion object{
        fun fromJson(jsonObject: JSONObject) : ClasseVisitante {
            return ClasseVisitante(
                jsonObject["createdAt"  ] as String?    ,
                jsonObject["updatedAt"  ] as String? ,
                jsonObject["id"         ] as Int? ,
                jsonObject["name"       ] as String? ,
                jsonObject["visitDate"  ] as String?,
                jsonObject["userId"     ] as Int?,
            )
        }
    }
}

