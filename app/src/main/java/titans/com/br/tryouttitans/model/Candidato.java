package titans.com.br.tryouttitans.model;

import org.androidannotations.annotations.EBean;

import java.util.Arrays;

/**
 * Created by junior on 15/08/15.
 */

@EBean
public class Candidato {

    private Long id;

    private byte[] fotografia;

    private String nome;

    private Integer idade;

    private Integer peso;

    private Double altura;

    private String tamanhoCamisa;

    private Integer telefone;

    private Integer contatoEmergencia;

    private String nomeContatoEmergencia;

    private String email;

    private Boolean pagamentoEfetuado;

    private Boolean exportado;

    public Candidato() {
        this.exportado = false;
    }

    //    GETTERS && SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFotografia() {
        return fotografia;
    }

    public void setFotografia(byte[] fotografia) {
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

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public String getTamanhoCamisa() {
        return tamanhoCamisa;
    }

    public void setTamanhoCamisa(String tamanhoCamisa) {
        this.tamanhoCamisa = tamanhoCamisa;
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
                ", fotografia=" + Arrays.toString(fotografia) +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", peso=" + peso +
                ", altura=" + altura +
                ", tamanhoCamisa='" + tamanhoCamisa + '\'' +
                ", telefone=" + telefone +
                ", contatoEmergencia=" + contatoEmergencia +
                ", nomeContatoEmergencia='" + nomeContatoEmergencia + '\'' +
                ", email='" + email + '\'' +
                ", pagamentoEfetuado=" + pagamentoEfetuado +
                ", exportado=" + exportado +
                '}';
    }
}
