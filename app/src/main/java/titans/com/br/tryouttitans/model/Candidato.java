package titans.com.br.tryouttitans.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;

import org.androidannotations.annotations.EBean;

import java.io.IOException;
import java.util.Arrays;

import okio.BufferedSink;

/**
 * Created by junior on 15/08/15.
 */

@DatabaseTable(tableName = "candidatos")
@EBean
public class Candidato extends RequestBody{

    @DatabaseField(generatedId = true)
    private Long id;

    @DatabaseField
    private String fotografia;

    @DatabaseField
    private String nome;

    @DatabaseField
    private Integer idade;

    @DatabaseField
    private Double peso;

    @DatabaseField
    private Double altura;

    @DatabaseField
    private String camisa;

    @DatabaseField
    private Integer telefone;

    @DatabaseField
    private Integer contatoEmergencia;

    @DatabaseField
    private String nomeContatoEmergencia;

    @DatabaseField
    private String email;

    @DatabaseField
    private Boolean pagamentoEfetuado;

    @DatabaseField
    private Boolean exportado;

    public Candidato() {
        this.exportado = false;
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("application/json; charset=utf-8");
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        sink.writeUtf8("teste");
    }

    //    GETTERS && SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public String getCamisa() {
        return camisa;
    }

    public void setCamisa(String camisa) {
        this.camisa = camisa;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public void setTelefone(Integer telefone) {
        this.telefone = telefone;
    }

    public Integer getContatoEmergencia() {
        return contatoEmergencia;
    }

    public void setContatoEmergencia(Integer contatoEmergencia) {
        this.contatoEmergencia = contatoEmergencia;
    }

    public String getNomeContatoEmergencia() {
        return nomeContatoEmergencia;
    }

    public void setNomeContatoEmergencia(String nomeContatoEmergencia) {
        this.nomeContatoEmergencia = nomeContatoEmergencia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getPagamentoEfetuado() {
        return pagamentoEfetuado;
    }

    public void setPagamentoEfetuado(Boolean pagamentoEfetuado) {
        this.pagamentoEfetuado = pagamentoEfetuado;
    }

    public Boolean getExportado() {
        return exportado;
    }

    public void setExportado(Boolean exportado) {
        this.exportado = exportado;
    }

    @Override
    public String toString() {
        return "Candidato{" +
                "id=" + id +
                ", fotografia=" + fotografia +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", peso=" + peso +
                ", altura=" + altura +
                ", tamanhoCamisa='" + camisa + '\'' +
                ", telefone=" + telefone +
                ", contatoEmergencia=" + contatoEmergencia +
                ", nomeContatoEmergencia='" + nomeContatoEmergencia + '\'' +
                ", email='" + email + '\'' +
                ", pagamentoEfetuado=" + pagamentoEfetuado +
                ", exportado=" + exportado +
                '}';
    }
}
