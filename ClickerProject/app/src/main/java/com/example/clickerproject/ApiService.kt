import com.example.clickerproject.Usuario
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("api/usuario")
    fun getAllUsuarios(): Call<List<Usuario>>

    @GET("api/usuario/{Userid}")
    fun getUsuario(@Path("Userid") userId: Int): Call<Usuario>

    @POST("api/usuario")
    fun criarUsuario(@Body usuario: Usuario): Call<Usuario>

    @PUT("api/usuario/{UsuarioId}")
    fun atualizarUsuario(@Path("UsuarioId") userId: Int, @Body usuario: Usuario): Call<Usuario>

    @DELETE("api/usuario/{Userid}")
    fun deletarUsuario(@Path("Userid") userId: Int): Call<Void>
}