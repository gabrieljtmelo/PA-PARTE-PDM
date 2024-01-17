package ipca.stock.projetoaplicado

import org.json.JSONObject

data class ClasseAreaComum (
    var createdAt      : String? = null,
    var updatedAt      : String? = null,
    var id             : Int? = null,
    var name           : String? = null
){
    fun toJson() : JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("createdAt"    , createdAt  )
        jsonObject.put("updatedAt"    , updatedAt  )
        jsonObject.put("id"           , id         )
        jsonObject.put("name"         , name       )


        return jsonObject
    }

    companion object{
        fun fromJson(jsonObject: JSONObject) : ClasseAreaComum {
            return ClasseAreaComum(
                jsonObject["createdAt"      ] as String?    ,
                jsonObject["updatedAt"      ] as String? ,
                jsonObject["id"             ] as Int? ,
                jsonObject["name"           ] as? String? ,

            )
        }
    }
}