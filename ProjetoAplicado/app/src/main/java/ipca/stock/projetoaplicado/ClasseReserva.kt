package ipca.stock.projetoaplicado

import org.json.JSONObject
import java.util.Date

 class ClasseReserva (
    var createdAt      : String? = null,
    var updatedAt      : String? = null,
    var id             : Int? = null,
    var date           : String? = null,
    var available      : Boolean? = null,
    var canceled       : Boolean? = null,
    var userId         : Int? = null,
    var commonAreaId   : Int? = null,

){
    fun toJson() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("createdAt"    , createdAt   )
        jsonObject.put("updatedAt"    , updatedAt   )
        jsonObject.put("id"           , id          )
        jsonObject.put("date"         , date        )
        jsonObject.put("available"    , available   )
        jsonObject.put("canceled"     , canceled    )
        jsonObject.put("userId"       , userId      )
        jsonObject.put("commonAreaId", commonAreaId)


        return jsonObject
    }

    companion object{
        fun fromJson(jsonObject: JSONObject) : ClasseReserva {
            return ClasseReserva(
                jsonObject["createdAt"     ] as String? ,
                jsonObject["updatedAt"     ] as String?,
                jsonObject["id"            ] as Int?    ,
                jsonObject["date"          ] as String? ,
                jsonObject["available"     ] as Boolean? ,
                jsonObject["canceled"      ] as Boolean? ,
                jsonObject["userId"        ] as Int?,
                jsonObject["commonAreaId" ] as Int?,
            )
        }
    }
}