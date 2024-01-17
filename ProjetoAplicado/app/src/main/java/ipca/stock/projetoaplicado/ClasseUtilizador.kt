package ipca.stock.projetoaplicado

import org.json.JSONObject
import java.util.Date

class ClasseUtilizador(

    var id : Int? = null,
    var name : String? = null,
    var email : String? = null,
    var floor : String? = null,
    var birth : String? = null,
    var createdAt      : String? = null,
    var updatedAt      : String? = null,
    var role : Int? = null
    ){
    fun toJson() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("id"           , id         )
        jsonObject.put("name"         , name       )
        jsonObject.put("email"        , email      )
        jsonObject.put("floor"        , floor      )
        jsonObject.put("birth"        , birth      )
        jsonObject.put("createdAt"    , createdAt  )
        jsonObject.put("updatedAt"    , updatedAt  )
        jsonObject.put("role"         , role       )


        return jsonObject
    }

    companion object{
        fun fromJson(jsonObject: JSONObject) : ClasseUtilizador {
            return ClasseUtilizador(
                jsonObject["id"         ] as Int?    ,
                jsonObject["name"       ] as String? ,
                jsonObject["email"      ] as String? ,
                jsonObject["floor"      ] as String? ,
                jsonObject["birth"      ] as String?,
                jsonObject["createdAt"  ] as String?,
                jsonObject["updatedAt"  ] as String?,
                jsonObject["role"       ] as Int?
            )
        }
    }
}