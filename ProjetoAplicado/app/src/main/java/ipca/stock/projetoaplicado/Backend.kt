package ipca.stock.projetoaplicado

import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.Base64


sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val code: Int? = null, val error: String? = null) :
        ResultWrapper<Nothing>()

    object NetworkError : ResultWrapper<Nothing>()

    inline fun onSuccess(action: (value: T) -> Unit): ResultWrapper<T> {
        if (this is Success) action(value)
        return this
    }

    inline fun onError(action: (error: Error) -> Unit): ResultWrapper<T> {
        if (this is Error) action(this)
        return this
    }

    inline fun onNetworkError(action: () -> Unit): ResultWrapper<T> {
        if (this is NetworkError) action()
        return this
    }
}

object Backend {

    private const val BASE_API = "http://13.94.159.149/api/"
    //private const val BASE_API = "http://10.0.2.2:8000/api/"
    private const val PATH_LOGIN =  "auth/login"
    private const val PATH_REGISTER = "auth/register"
    private const val PATH_TICKET = "Ticket"
    private const val PATH_GLOBALWARNING = "GlobalWarning"
    private const val PATH_INDIVIDUALWARNING = "IndividualWarning"
    private const val PATH_USER = "User"
    private const val PATH_SCHEDULE = "Schedule"
    private const val PATH_REGA = "Rega"
    private const val PATH_VISITOR = "Visitor"

    suspend fun <T> wrap(apiCall: suspend () -> T): ResultWrapper<T> {
        return try {
            ResultWrapper.Success(apiCall())
        } catch (throwable: Throwable) {
            Log.e("Repository", throwable.toString())
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                else -> {
                    ResultWrapper.Error(0, throwable.message)
                }
            }
        }
    }

    private val client = OkHttpClient()
    private var token = ""

    // ------------ PARTE DO LOGIN ------------------------

    fun login(email: String, password: String) {
        val payload = "{\"email\":\"$email\",\"password\":\"$password\"}"
        val mediaTypeJson = "application/json; charset=utf-8".toMediaType()
        val okHttpClient = OkHttpClient()
        val requestBody = payload.toRequestBody(mediaTypeJson)
        val request = Request.Builder()
            .post(requestBody)
            .url("${BASE_API}${PATH_LOGIN}")
            .build()
        val result = okHttpClient.newCall(request).execute()
        val jsonObject = JSONObject(result.body?.string())
        token = jsonObject["token"] as String
    }

    suspend fun loginRequest(email: String, password: String): ResultWrapper<Unit> {
        return wrap { login(email, password) }
    }

    // ---------- FIM DA PARTE DO LOGIN --------------------------------


    // -------------------- REFERENTE A TICKETS -----------------------------

    fun fetchTickets(): LiveData<ResultWrapper<List<ClasseTicket>>> =
        liveData(Dispatchers.IO) {
            emit( wrap { requestTickets() })
        }
    fun fetchTicketsByUser(): LiveData<ResultWrapper<List<ClasseTicket>>> =
        liveData(Dispatchers.IO) {
            emit( wrap { requestTicketsByUserId() })
        }


    fun requestTickets(): List<ClasseTicket>{
        val request = Request.Builder()
            .url("${BASE_API}${PATH_TICKET}")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()
        val result = client.newCall(request).execute()
        val jsonArray = JSONArray(result.body?.string())
        var tickets : MutableList<ClasseTicket> = arrayListOf()
        for ( i in 0..< jsonArray.length()){
            val jsonObject = jsonArray[i] as JSONObject
            val ticket = ClasseTicket.fromJson(jsonObject)
            tickets.add(ticket)
        }
        return tickets.toList()
    }

    fun fetchTicket(id:String): LiveData<ResultWrapper<ClasseTicket>> =
        liveData(Dispatchers.IO) {
            emit( wrap { requestTicket(id) })
        }

    fun requestTicket(id:String): ClasseTicket {
        val request = Request.Builder()
            .url("${BASE_API}${PATH_TICKET}/$id")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()
        val result = client.newCall(request).execute()
        val ticket = ClasseTicket.fromJson(JSONObject(result.body?.string()))
        return ticket
    }

    fun requestTicketsByUserId(): List<ClasseTicket> {
        val request = Request.Builder()
            .url("${BASE_API}${PATH_TICKET}/my")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()
        val result = client.newCall(request).execute()
        val jsonArray = JSONArray(result.body?.string())
        var tickets : MutableList<ClasseTicket> = arrayListOf()
        for ( i in 0..< jsonArray.length()){
            val jsonObject = jsonArray[i] as JSONObject
            val ticket = ClasseTicket.fromJson(jsonObject)
            tickets.add(ticket)
        }
        return tickets.toList()
    }

    fun addTicket(lifecycleScope: LifecycleCoroutineScope, ticket: ClasseEnviarTicket, function: () -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) {


            val payload = ticket.toJson().toString()
            val mediaTypeJson = "application/json; charset=utf-8".toMediaType()
            val okHttpClient = OkHttpClient()
            val requestBody = payload.toRequestBody(mediaTypeJson)
            val request = Request.Builder()
                .post(requestBody)
                .url("${BASE_API}${PATH_TICKET}")
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle this
                    Log.d("shoppinglist", e.message.toString())
                    function.invoke()
                }

                override fun onResponse(call: Call, response: Response) {
                    // Handle this
                    Log.d("shoppinglist", response.toString())
                    function.invoke()
                }
            })
        }
    }

    fun RespostaTicket(lifecycleScope: LifecycleCoroutineScope, resposta: ClasseEnviarResposta, ticketId: String, function: () -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) {


            val payload = resposta.toJson().toString()
            val mediaTypeJson = "application/json; charset=utf-8".toMediaType()
            val okHttpClient = OkHttpClient()
            val requestBody = payload.toRequestBody(mediaTypeJson)
            val request = Request.Builder()
                .put(requestBody)
                .url("${BASE_API}${PATH_TICKET}/$ticketId")
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle this
                    Log.d("shoppinglist", e.message.toString())
                    function.invoke()
                    println("NÃO DEU CERTOFASDFAS")
                }

                override fun onResponse(call: Call, response: Response) {
                    // Handle this
                    Log.d("shoppinglist", response.toString())
                    function.invoke()
                    println("EITA POHA DEU SEMI-CERTO")
                }
            })
        }
    }

    // --------------------- FIM DOS REFERENTES A TICKETS -----------------------------

    // -------------------------- REFERENTE A USERS ------------------------------------

    fun fetchUser(id:String): LiveData<ResultWrapper<ClasseUtilizador>> =
        liveData(Dispatchers.IO) {
            emit( wrap { requestUser(id) })
        }

    fun fetchUserByToken(): LiveData<ResultWrapper<ClasseUtilizador>> =
        liveData(Dispatchers.IO) {
            emit( wrap { requestUserByToken() })
        }

    fun requestUserByToken(): ClasseUtilizador {
        val request = Request.Builder()
            .url("${BASE_API}${PATH_USER}/my")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()
        val result = client.newCall(request).execute()
        val user = ClasseUtilizador.fromJson(JSONObject(result.body?.string()))
        return user
    }

    fun fetchUsers(): LiveData<ResultWrapper<List<ClasseUtilizador>>> =
        liveData(Dispatchers.IO) {
            emit( wrap { requestUsers() })
        }

    fun requestUsers(): List<ClasseUtilizador>{
        val request = Request.Builder()
            .url("${BASE_API}${PATH_USER}")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()
        val result = client.newCall(request).execute()
        val jsonArray = JSONArray(result.body?.string())
        var users : MutableList<ClasseUtilizador> = arrayListOf()
        for ( i in 0..< jsonArray.length()){
            val jsonObject = jsonArray[i] as JSONObject
            val user = ClasseUtilizador.fromJson(jsonObject)
            users.add(user)

        }
        if(users.isEmpty()){
            println("erro x")

        }
        return users.toList()
    }

    // Request a user
    fun requestUser(id:String): ClasseUtilizador {
        val request = Request.Builder()
            .url("${BASE_API}${PATH_USER}/$id")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()
        val result = client.newCall(request).execute()
        val user = ClasseUtilizador.fromJson(JSONObject(result.body?.string()))
        return user
    }

    fun addUser(lifecycleScope: LifecycleCoroutineScope, user: ClasseEnviarUtilizador, function: () -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) {


            val payload = user.toJson().toString()
            val mediaTypeJson = "application/json; charset=utf-8".toMediaType()
            val okHttpClient = OkHttpClient()
            val requestBody = payload.toRequestBody(mediaTypeJson)
            val request = Request.Builder()
                .post(requestBody)
                .url("${BASE_API}${PATH_REGISTER}")
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle this
                    Log.d("shoppinglist", e.message.toString())
                    function.invoke()
                }

                override fun onResponse(call: Call, response: Response) {
                    // Handle this
                    Log.d("shoppinglist", response.toString())
                    function.invoke()
                }
            })
        }
    }



    // ---------------------------- FIM DOS REFERENTES A USERS ---------------------------

    // --------------------- REFERENTE A AVISOS GLOBAIS -----------------------------

    fun fetchGlobalWarnings(): LiveData<ResultWrapper<List<ClasseAvisoGlobal>>> =
        liveData(IO) {
            emit( wrap { requestGlobalWarnings() })
        }

    fun requestGlobalWarnings(): List<ClasseAvisoGlobal>{
        val request = Request.Builder()
            .url("${BASE_API}${PATH_GLOBALWARNING}")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()
        val result = client.newCall(request).execute()
        val jsonArray = JSONArray(result.body?.string())
        var avisoglobal : MutableList<ClasseAvisoGlobal> = arrayListOf()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val avisoGlobal = ClasseAvisoGlobal.fromJson(jsonObject)
            avisoglobal.add(avisoGlobal)
        }
        return avisoglobal.toList()
    }

    fun addGlobalWarnings(lifecycleScope: LifecycleCoroutineScope, aviso: ClasseEnviarAvisoGlobal, function: () -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) {


            val payload = aviso.toJson().toString()
            val mediaTypeJson = "application/json; charset=utf-8".toMediaType()
            val okHttpClient = OkHttpClient()
            val requestBody = payload.toRequestBody(mediaTypeJson)
            val request = Request.Builder()
                .post(requestBody)
                .url("${BASE_API}${PATH_GLOBALWARNING}")
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle this
                    Log.d("shoppinglist", e.message.toString())
                    function.invoke()
                }

                override fun onResponse(call: Call, response: Response) {
                    // Handle this
                    Log.d("shoppinglist", response.toString())
                    function.invoke()
                }
            })
        }
    }

    // -------------- FIM DOS AVISOS GLOBAIS ----------------------------

    // -------------- REFERENTE AOS AVISOS INDIVIDUAIS --------------------

    fun fetchIndividualWarnings(): LiveData<ResultWrapper<List<ClasseAvisoIndividual>>> =
        liveData(IO) {
            emit( wrap { requestIndividualWarnings() })
        }

    fun fetchIndividualWarningsByUserId(): LiveData<ResultWrapper<List<ClasseAvisoIndividual>>> =
        liveData(IO) {
            emit( wrap { requestIndividualWarningsByUserId() })
        }


    fun requestIndividualWarningsByUserId(): List<ClasseAvisoIndividual> {
        val request = Request.Builder()
            .url("${BASE_API}${PATH_INDIVIDUALWARNING}/my")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()
        val result = client.newCall(request).execute()
        val jsonArray = JSONArray(result.body?.string())
        var avisos : MutableList<ClasseAvisoIndividual> = arrayListOf()
        for ( i in 0..< jsonArray.length()){
            val jsonObject = jsonArray[i] as JSONObject
            val avisoIndividual = ClasseAvisoIndividual.fromJson(jsonObject)
            avisos.add(avisoIndividual)
        }
        return avisos.toList()
    }

    fun requestIndividualWarnings(): List<ClasseAvisoIndividual>{
        val request = Request.Builder()
            .url("${BASE_API}${PATH_INDIVIDUALWARNING}")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()
        val result = client.newCall(request).execute()
        val jsonArray = JSONArray(result.body?.string())
        var avisos : MutableList<ClasseAvisoIndividual> = arrayListOf()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val avisoIndividual = ClasseAvisoIndividual.fromJson(jsonObject)
            avisos.add(avisoIndividual)
        }
        return avisos.toList()
    }

    fun addIndividualWarnings(lifecycleScope: LifecycleCoroutineScope, aviso: ClasseEnviarAvisoIndividual, function: () -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) {


            val payload = aviso.toJson().toString()
            val mediaTypeJson = "application/json; charset=utf-8".toMediaType()
            val okHttpClient = OkHttpClient()
            val requestBody = payload.toRequestBody(mediaTypeJson)
            val request = Request.Builder()
                .post(requestBody)
                .url("${BASE_API}${PATH_INDIVIDUALWARNING}")
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle this
                    Log.d("shoppinglist", e.message.toString())
                    function.invoke()
                }

                override fun onResponse(call: Call, response: Response) {
                    // Handle this
                    Log.d("shoppinglist", response.toString())
                    function.invoke()
                }
            })
        }
    }




    // -------------- FIM DOS AVISOS INDIVIDUAIS -------------------------

    // -------------- REFERENTE AS RESERVAS ----------------------------

    fun fetchSchedules(): LiveData<ResultWrapper<List<ClasseReserva>>> =
        liveData(Dispatchers.IO) {
            emit( wrap { requestSchedules() })
        }
    fun fetchSchedulesByUser(): LiveData<ResultWrapper<List<ClasseReserva>>> =
        liveData(Dispatchers.IO) {
            emit( wrap { requestSchedulesByUser() })
        }


    fun requestSchedules(): List<ClasseReserva>{
        val request = Request.Builder()
            .url("${BASE_API}${PATH_SCHEDULE}")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()
        val result = client.newCall(request).execute()
        val jsonArray = JSONArray(result.body?.string())
        var listSchedules : MutableList<ClasseReserva> = arrayListOf()
        for ( i in 0..< jsonArray.length()){
            val jsonObject = jsonArray[i] as JSONObject
            val schedule = ClasseReserva.fromJson(jsonObject)
            listSchedules.add(schedule)
        }
        return listSchedules.toList()
    }

    fun fetchSchedule(id:String): LiveData<ResultWrapper<ClasseReserva>> =
        liveData(Dispatchers.IO) {
            emit( wrap { requestSchedule(id) })
        }

    fun requestSchedule(id:String): ClasseReserva {
        val request = Request.Builder()
            .url("${BASE_API}${PATH_SCHEDULE}/$id")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()
        val result = client.newCall(request).execute()
        var schedule : ClasseReserva
         schedule = ClasseReserva.fromJson(JSONObject(result.body?.string()))
        return schedule
    }

    fun requestSchedulesByUser(): List<ClasseReserva> {
        val request = Request.Builder()
            .url("${BASE_API}${PATH_SCHEDULE}/my")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()
        val result = client.newCall(request).execute()
        val jsonArray = JSONArray(result.body?.string())
        var listSchedules : MutableList<ClasseReserva> = arrayListOf()
        for ( i in 0..< jsonArray.length()){
            val jsonObject = jsonArray[i] as JSONObject
            val schedule = ClasseReserva.fromJson(jsonObject)
            listSchedules.add(schedule)
        }
        return listSchedules.toList()
    }

    fun addSchedule(lifecycleScope: LifecycleCoroutineScope, reserva: ClasseEnviarReserva, function: () -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) {


            val payload = reserva.toJson().toString()
            val mediaTypeJson = "application/json; charset=utf-8".toMediaType()
            val okHttpClient = OkHttpClient()
            val requestBody = payload.toRequestBody(mediaTypeJson)
            val request = Request.Builder()
                .post(requestBody)
                .url("${BASE_API}${PATH_SCHEDULE}")
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle this
                    Log.d("shoppinglist", e.message.toString())
                    function.invoke()
                }

                override fun onResponse(call: Call, response: Response) {
                    // Handle this
                    Log.d("shoppinglist", response.toString())
                    function.invoke()
                }
            })
        }
    }

    // ------------------ FIM DAS RESERVAS --------------------------

    // -------------- REFERENTE A REGA ----------------------------

    fun fetchRegas(): LiveData<ResultWrapper<List<ClasseRega>>> =
        liveData(Dispatchers.IO) {
            emit(wrap { requestRegas() })
        }

    fun requestRegas(): List<ClasseRega> {
        val request = Request.Builder()
            .url("${BASE_API}${PATH_REGA}")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()
        val result = client.newCall(request).execute()
        val jsonArray = JSONArray(result.body?.string())
        var regas: MutableList<ClasseRega> = arrayListOf()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val rega = ClasseRega.fromJson(jsonObject)
            regas.add(rega)
        }
        return regas.toList()

    }
    fun fetchRegaById(id: String): LiveData<ResultWrapper<ClasseRega>> =
        liveData(Dispatchers.IO) {
            emit(wrap { requestRegaById(id) })
        }
    fun requestRegaById(id: String): ClasseRega {
        val request = Request.Builder()
            .url("${BASE_API}${PATH_REGA}/$id")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()
        val result = client.newCall(request).execute()
        val Rega = ClasseRega.fromJson(JSONObject(result.body?.string()))
        return Rega
    }


    fun addRega(lifecycleScope: LifecycleCoroutineScope, rega: ClasseEnviarRega, function: () -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) {


            val payload = rega.toJson().toString()
            val mediaTypeJson = "application/json; charset=utf-8".toMediaType()
            val okHttpClient = OkHttpClient()
            val requestBody = payload.toRequestBody(mediaTypeJson)
            val request = Request.Builder()
                .post(requestBody)
                .url("${BASE_API}${PATH_REGA}")
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle this
                    Log.d("Addrega", e.message.toString())
                    function.invoke()
                }

                override fun onResponse(call: Call, response: Response) {
                    // Handle this
                    Log.d("Addrega", response.toString())
                    function.invoke()
                }
            })
        }
    }



    // ------------------ FIM DA REGA --------------------------

    // ------------------ REFERENTE AOS VISITANTES--------------

    fun fetchVisitantes(): LiveData<ResultWrapper<List<ClasseVisitante>>> =
        liveData(Dispatchers.IO) {
            emit( wrap { requestVisitantes() })
        }
    fun fetchVisitantesByUser(): LiveData<ResultWrapper<List<ClasseVisitante>>> =
        liveData(Dispatchers.IO) {
            emit( wrap { requestVisitantesByUserId() })
        }


    fun requestVisitantes(): List<ClasseVisitante>{
        val request = Request.Builder()
            .url("${BASE_API}${PATH_VISITOR}")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()
        val result = client.newCall(request).execute()
        val jsonArray = JSONArray(result.body?.string())
        var visitantes : MutableList<ClasseVisitante> = arrayListOf()
        for ( i in 0..< jsonArray.length()){
            val jsonObject = jsonArray[i] as JSONObject
            val visitor = ClasseVisitante.fromJson(jsonObject)
            visitantes.add(visitor)
        }
            return visitantes.toList()
    }

    fun requestVisitantesByUserId(): List<ClasseVisitante> {
        val request = Request.Builder()
            .url("${BASE_API}${PATH_VISITOR}/my")
            .addHeader("Authorization", "Bearer $token")
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .build()
        val result = client.newCall(request).execute()
        val jsonArray = JSONArray(result.body?.string())
        var visitantes : MutableList<ClasseVisitante> = arrayListOf()
        for ( i in 0..< jsonArray.length()){
            val jsonObject = jsonArray[i] as JSONObject
            val visitor = ClasseVisitante.fromJson(jsonObject)
            visitantes.add(visitor)
        }
        return visitantes.toList()
    }

    fun addVisitante(lifecycleScope: LifecycleCoroutineScope, visitante: ClasseEnviarVisitantes, function: () -> Unit) {
        lifecycleScope.launch(Dispatchers.IO) {


            val payload = visitante.toJson().toString()
            val mediaTypeJson = "application/json; charset=utf-8".toMediaType()
            val okHttpClient = OkHttpClient()
            val requestBody = payload.toRequestBody(mediaTypeJson)
            val request = Request.Builder()
                .post(requestBody)
                .url("${BASE_API}${PATH_VISITOR}")
                .addHeader("Authorization", "Bearer $token")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle this
                    Log.d("shoppinglist", e.message.toString())
                    function.invoke()
                }

                override fun onResponse(call: Call, response: Response) {
                    // Handle this
                    Log.d("shoppinglist", response.toString())
                    function.invoke()
                }
            })
        }
    }


    // ----------------- FIM DOS VISITANTES -----------------

    /*
    fun decodeJwt(token: String): String{
        val parts = token.split(".") // Dividir o token em suas partes
        if (parts.size != 3) {
            throw IllegalArgumentException("Invalid JWT token.")
        }


        val payload = String(Base64.getUrlDecoder().decode(parts[1]))

        return payload
    }*/

}