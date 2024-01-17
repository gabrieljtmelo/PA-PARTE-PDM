package ipca.stock.projetoaplicado

import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ClasseAvisoGlobal(
    var createdAt      : String? = null,
    var updatedAt      : String? = null,
    var id             : Int? = null,
    var title          : String? = null,
    var description    : String? = null
){
    fun toJson() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("createdAt"    , createdAt   )
        jsonObject.put("updatedAt"    , updatedAt   )
        jsonObject.put("id"           , id          )
        jsonObject.put("title"         , title        )
        jsonObject.put("description"  , description )


        return jsonObject
    }

    companion object{
        fun fromJson(jsonObject: JSONObject) : ClasseAvisoGlobal {
            return ClasseAvisoGlobal(
                jsonObject["createdAt"    ] as String? ,
                jsonObject["updatedAt"    ] as String?,
                jsonObject["id"           ] as Int?    ,
                jsonObject["title"        ] as String? ,
                jsonObject["description"  ] as String? ,
            )
        }
    }
}