package titans.com.br.tryouttitans.rest;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;
import titans.com.br.tryouttitans.model.Candidato;

/**
 * Created by junior on 18/09/15.
 */
public interface TryoutTitansService {

    @POST("/candidatos/mobile")
    Call<Candidato> adicionarCandidato(@Body Candidato candidato);
}
