package titans.com.br.tryouttitans.excessoes;

/**
 * Created by junior on 24/08/15.
 */
public class CampoObrigatorioException extends Exception{


    @Override
    public String getMessage() {

        StringBuilder builder = new StringBuilder();
        builder.append("Por favor preencher campos em branco.");
        return builder.toString();
    }
}
