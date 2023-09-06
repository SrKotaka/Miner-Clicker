import com.example.clickerproject.Usuario
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("usuario")
    fun getUsuarios(): Call<List<Usuario>>

    @GET("usuario/{id}")
    fun getUsuario(@Path("id") id: Int): Call<Usuario>

    @POST("usuario")
    fun criarUsuario(@Body usuario: Usuario): Call<Usuario>

    @PUT("usuario/{id}")
    fun atualizarUsuario(@Path("id") id: Int, @Body usuario: Usuario): Call<Usuario>

    @DELETE("usuario/{id}")
    fun deletarUsuario(@Path("id") id: Int): Call<Usuario>
}