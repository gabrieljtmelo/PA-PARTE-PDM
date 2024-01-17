package ipca.stock.projetoaplicado

import org.json.JSONObject

data class ClasseAvisoIndividual (
    var createdAt      : String? = null,
    var updatedAt      : String? = null,
    var id             : Int? = null,
    var title          : String? = null,
    var description    : String? = null,
    var userId         : Int? = null,
){
    fun toJson() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("createdAt"    , createdAt   )
        jsonObject.put("updatedAt"    , updatedAt   )
        jsonObject.put("id"           , id          )
        jsonObject.put("title"         , title        )
        jsonObject.put("description"  , description )
        jsonObject.put("userId"       , userId      )


        return jsonObject
    }

    companion object{
        fun fromJson(jsonObject: JSONObject) : ClasseAvisoIndividual {
            return ClasseAvisoIndividual(
                jsonObject["createdAt"    ] as String? ,
                jsonObject["updatedAt"    ] as String?,
                jsonObject["id"           ] as Int?    ,
                jsonObject["title"        ] as String? ,
                jsonObject["description"  ] as String? ,
                jsonObject["userId"       ] as Int?
            )
        }
    }
}



