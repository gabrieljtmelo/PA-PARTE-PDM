package ipca.stock.projetoaplicado

import org.json.JSONObject
import java.util.Date

class ClasseTicket (
    var createdAt      : String? = null,
    var updatedAt      : String? = null,
    var id             : Int? = null,
    var name           : String? = null,
    var description    : String? = null,
    var reply          : String? = null,
    var userId         : Int? = null,
){
    fun toJson() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("createdAt"    , createdAt   )
        jsonObject.put("updatedAt"    , updatedAt   )
        jsonObject.put("id"           , id          )
        jsonObject.put("name"         , name        )
        jsonObject.put("description"  , description )
        jsonObject.put("reply"        , reply       )
        jsonObject.put("userId"       , userId      )


        return jsonObject
    }

    companion object{
        fun fromJson(jsonObject: JSONObject) : ClasseTicket {
            return ClasseTicket(
                jsonObject["createdAt"    ] as String? ,
                jsonObject["updatedAt"    ] as String?,
                jsonObject["id"           ] as Int?    ,
                jsonObject["name"         ] as String? ,
                jsonObject["description"  ] as String? ,
                jsonObject["reply"        ] as? String? ,
                jsonObject["userId"       ] as Int?
                )
        }
    }
}