package titans.com.br.tryouttitans.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.j256.ormlite.dao.Dao;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OrmLiteDao;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.SQLException;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import titans.com.br.tryouttitans.R;
import titans.com.br.tryouttitans.dao.BancoDadosHelper;
import titans.com.br.tryouttitans.excessoes.CampoObrigatorioException;
import titans.com.br.tryouttitans.model.Candidato;


@EActivity(R.layout.activity_tela_principal)
public class TelaPrincipal extends AppCompatActivity {

    private int contadorImagens;
    private final int TIRAR_FOTOGRAFIA = 1;
    private Uri imagemUri;
    private  Bitmap foto;

    @Bean
    Candidato candidato;

    @OrmLiteDao(helper = BancoDadosHelper.class, model = Candidato.class)
    Dao<Candidato, Long> candidatoDao;

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

    @ViewById
    Button botaoSalvar;

    private ToolTipView ttv;

    @ViewById
    Toolbar tb_main;

    @AfterViews
    public void configurarAplicacao(){
        setSupportActionBar(tb_main);
        getSupportActionBar().setLogo(R.mipmap.soutitans);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.soutitans);
        configurarMascaras();
    }

    private void configurarMascaras() {
        MaskEditTextChangedListener mascaraTelefone = new MaskEditTextChangedListener("#####-####", telefone);
        MaskEditTextChangedListener mascaraEmergencia = new MaskEditTextChangedListener("####-####", contatoEmergencia);
        MaskEditTextChangedListener mascaraAltura = new MaskEditTextChangedListener("#.##", altura);

        telefone.addTextChangedListener(mascaraTelefone);
        contatoEmergencia.addTextChangedListener(mascaraEmergencia);
        altura.addTextChangedListener(mascaraAltura);
    }

    @Click(R.id.fotografia)
    public void ativarCamera(){
        startCamera();
    }

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

        try {
            validarPreenchimentoCampos();
            preencherCamposCandidato();
            candidatoDao.createIfNotExists(candidato);
            candidatoDao.queryForAll().size();

            Snackbar.make(findViewById(R.id.corpo), "Ok" , Snackbar.LENGTH_LONG).show();
        } catch (CampoObrigatorioException e) {
            Snackbar.make(findViewById(R.id.corpo), e.getMessage(), Snackbar.LENGTH_LONG).setActionTextColor(R.color.myPrimaryColor).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void validarPreenchimentoCampos() throws CampoObrigatorioException {

        validarFotografia();
        validarCampoTexto(nome);
        validarCampoTexto(idade);
        validarCampoTexto(peso);
        validarCampoTexto(altura);
        validarCampoTexto(telefone);
        validarCampoTexto(contatoEmergencia);
        validarCampoTexto(nomeContatoEmergencia);
        validarCampoTexto(email);
        validarRadioGroup();



    }

    private void validarFotografia() throws CampoObrigatorioException {
        if(foto == null){

            ToolTipRelativeLayout toolTipContainer = (ToolTipRelativeLayout) findViewById(R.id.toolTipFotografia);
            ToolTip toolTip = new ToolTip().withText(getResources().getString(R.string.campoObrigatorio)).withShadow().withColor(getResources().getColor(R.color.myPrimaryColor)).withTextColor(Color.WHITE);
            toolTip.withTypeface(Typeface.DEFAULT_BOLD);
            toolTipContainer.showToolTipForView(toolTip, fotografia);
            fotografia.requestFocus();

            throw new CampoObrigatorioException();

        }
    }

    private void validarCampoTexto(EditText campo) throws CampoObrigatorioException {
        if(campo.getText().toString().isEmpty()){
            ToolTipRelativeLayout toolTipContainer = (ToolTipRelativeLayout) ((TextInputLayout) campo.getParent()).getChildAt(1);
            ToolTip toolTip = new ToolTip().withText(getResources().getString(R.string.campoObrigatorio)).withShadow().withColor(getResources().getColor(R.color.myPrimaryColor)).withTextColor(Color.WHITE);
            toolTip.withTypeface(Typeface.DEFAULT_BOLD);
            toolTipContainer.showToolTipForView(toolTip, campo);
            campo.requestFocus();

            throw new CampoObrigatorioException();
        }
    }

    private void validarRadioGroup() throws CampoObrigatorioException {
        if( findViewById(tamanhos.getCheckedRadioButtonId()) == null){

            ToolTipRelativeLayout toolTipContainer = (ToolTipRelativeLayout) findViewById(R.id.toolTipTamanhos);
            ToolTip toolTip = new ToolTip().withText(getResources().getString(R.string.campoObrigatorio)).withShadow().withColor(getResources().getColor(R.color.myPrimaryColor)).withTextColor(Color.WHITE);
            toolTip.withTypeface(Typeface.DEFAULT_BOLD);
            toolTipContainer.showToolTipForView(toolTip, tamanhos);
            tamanhos.requestFocus();

            throw new CampoObrigatorioException();
        }
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

        if(foto != null){
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            foto.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        return null;
    }

    private String obterValorRadioButtonSelecionado() {
        return getRadioButtonSelecionado() == null ? null : getRadioButtonSelecionado().getText().toString();
    }

    private RadioButton getRadioButtonSelecionado() {
        return (RadioButton) findViewById(tamanhos.getCheckedRadioButtonId());
    }

    private String obterValorString(EditText campo){
        return campo.getText().toString();
    }

    private Integer obterValorInteiro(EditText campo){
        return  campo.getText().toString().isEmpty() ? null : Integer.parseInt(obterValorString(campo));
    }

    private Double obterValorDouble(EditText campo){
        return  campo.getText().toString().isEmpty() ? null : Double.parseDouble(campo.getText().toString());
    }

    private Integer obterValorTelefone(EditText campo){

        String  valorCampo = obterValorString(campo);
        return  campo.getText().toString().isEmpty() ? null :  Integer.parseInt(valorCampo.replace("-", ""));
    }

    private Boolean obterValorBoolean(CheckBox campo){
        return campo.isChecked();
    }
}
