package titans.com.br.tryouttitans.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.io.File;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import titans.com.br.tryouttitans.R;
import titans.com.br.tryouttitans.model.Candidato;


@EActivity(R.layout.activity_tela_principal)
public class TelaPrincipal extends AppCompatActivity {

    private int contadorImagens;
    private final int TIRAR_FOTOGRAFIA = 1;
    private Uri imagemUri;
    private  Bitmap foto;

    @Bean
    Candidato candidato;

    @ViewById
    ImageView fotografia;
    @ViewById
    EditText nome;
    @ViewById
    EditText idade;
    @ViewById
    EditText altura;
    @ViewById
    EditText peso;
    @ViewById
    EditText telefone;
    @ViewById
    EditText contatoEmergencia;
    @ViewById
    EditText nomeContatoEmergencia;
    @ViewById
    EditText email;
    @ViewById
    CheckBox pagamento;
    @ViewById
    RadioGroup tamanhos;

//    @ViewById
//    FloatingActionButton fab;

    @ViewById
    Toolbar tb_main;

    @AfterViews
    public void configurarAplicacao(){
        setSupportActionBar(tb_main);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        configurarMascaras();
    }

    private void configurarMascaras() {
        MaskEditTextChangedListener mascaraTelefone = new MaskEditTextChangedListener("#####-####", telefone);
        MaskEditTextChangedListener mascaraEmergencia = new MaskEditTextChangedListener("#####-####", contatoEmergencia);
        MaskEditTextChangedListener mascaraAltura = new MaskEditTextChangedListener("#.##", altura);

        telefone.addTextChangedListener(mascaraTelefone);
        contatoEmergencia.addTextChangedListener(mascaraEmergencia);
        altura.addTextChangedListener(mascaraAltura);
    }

    @Click(R.id.fotografia)
    public void ativarCamera(){
        startCamera();
    }

//    @Click(R.id.fab)
//    public void ativarCameraFab(){
//        startCamera();
//    }

    private void startCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        StringBuilder nomeFotografia = montarNomeFotografia();

        File imagem = new File(getExternalFilesDir(null), nomeFotografia.toString());
        imagemUri = Uri.fromFile(imagem);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, imagemUri);
        startActivityForResult(camera, TIRAR_FOTOGRAFIA);
    }

    @NonNull
    private StringBuilder montarNomeFotografia() {
        StringBuilder nomeFotografia = new StringBuilder();
        nomeFotografia.append("candidato");
        nomeFotografia.append(contadorImagens);
        nomeFotografia.append(".jpg");
        contadorImagens++;
        return nomeFotografia;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        foto = BitmapFactory.decodeFile(imagemUri.getPath());
        fotografia.setImageBitmap(Bitmap.createScaledBitmap(foto, 130, 130, true));
//        fab.setImageBitmap(Bitmap.createScaledBitmap(foto, 130, 130, true));
    }

    @Click(R.id.botaoSalvar)
    public void salvar(){

        preencherCamposCandidato();

        Toast.makeText(this,  "Candiato Salvo", Toast.LENGTH_LONG).show();

//        for(Field campo : Candidato.class.getFields()){
//
//            if(!campo.getType().equals(Long.class) && campo.get(candidato) == null){
//                Toast.makeText(this, "Campo " + campo.getName() + " é obrigatório", Toast.LENGTH_LONG).show();
//            }else{
//                Toast.makeText(this, candidato.toString(), Toast.LENGTH_LONG).show();
//            }
//
//        }




    }

    private void preencherCamposCandidato() {
        candidato.setFotografia(obterFotografiaEmArrayDeBytes());
        candidato.setNome(obterValorString(nome));
        candidato.setIdade(obterValorInteiro(idade));
        candidato.setPeso(obterValorInteiro(peso));
        candidato.setAltura(obterValorDouble(altura));
        candidato.setTamanhoCamisa(obterValorRadioButtonSelecionado());
        candidato.setTelefone(obterValorTelefone(telefone));
        candidato.setContatoEmergencia(obterValorTelefone(contatoEmergencia));
        candidato.setNomeContatoEmergencia(obterValorString(nomeContatoEmergencia));
        candidato.setEmail(obterValorString(email));
        candidato.setPagamentoEfetuado(obterValorBoolean(pagamento));
    }

    private byte[] obterFotografiaEmArrayDeBytes(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        foto.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private String obterValorRadioButtonSelecionado() {
        RadioButton radioButtonSelecionado = (RadioButton) findViewById(tamanhos.getCheckedRadioButtonId());
        return radioButtonSelecionado.getText().toString();
    }

    private String obterValorString(EditText campo){
        return campo.getText().toString();
    }

    private Integer obterValorInteiro(EditText campo){
        return Integer.parseInt(obterValorString(campo));
    }

    private Double obterValorDouble(EditText campo){
        return Double.parseDouble(campo.getText().toString());
    }

    private Integer obterValorTelefone(EditText campo){

        String  valorCampo = obterValorString(campo);
        return Integer.parseInt(valorCampo.replace("-", ""));
    }

    private Boolean obterValorBoolean(CheckBox campo){
        return campo.isChecked();
    }
}
