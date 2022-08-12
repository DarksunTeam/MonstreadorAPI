package com.darksun.MonstreadorAPI.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="MONSTRO")
public class Monstro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private String nome;

    private Integer forca;
    private Integer destreza;
    private Integer constituicao;
    private Integer inteligencia;
    private Integer sabedoria;
    private Integer carisma;

    private Integer pontosDeVida;
    private Integer pontosDeMana;
    private Integer iniciativa;
    private Integer percepcao;
    private Integer defesa;
    private Integer fortitude;
    private Integer reflexo;
    private Integer vontade;

    private Double velocidadeAndar;
    private Double velocidadeEscalar;
    private Double velocidadeNadar;
    private Double velocidadeVoar;

    private Integer nd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getForca() {
        return forca;
    }

    public void setForca(Integer forca) {
        this.forca = forca;
    }

    public Integer getDestreza() {
        return destreza;
    }

    public void setDestreza(Integer destreza) {
        this.destreza = destreza;
    }

    public Integer getConstituicao() {
        return constituicao;
    }

    public void setConstituicao(Integer constituicao) {
        this.constituicao = constituicao;
    }

    public Integer getInteligencia() {
        return inteligencia;
    }

    public void setInteligencia(Integer inteligencia) {
        this.inteligencia = inteligencia;
    }

    public Integer getSabedoria() {
        return sabedoria;
    }

    public void setSabedoria(Integer sabedoria) {
        this.sabedoria = sabedoria;
    }

    public Integer getCarisma() {
        return carisma;
    }

    public void setCarisma(Integer carisma) {
        this.carisma = carisma;
    }

    public Integer getPontosDeVida() {
        return pontosDeVida;
    }

    public void setPontosDeVida(Integer pontosDeVida) {
        this.pontosDeVida = pontosDeVida;
    }

    public Integer getPontosDeMana() {
        return pontosDeMana;
    }

    public void setPontosDeMana(Integer pontosDeMana) {
        this.pontosDeMana = pontosDeMana;
    }

    public Integer getIniciativa() {
        return iniciativa;
    }

    public void setIniciativa(Integer iniciativa) {
        this.iniciativa = iniciativa;
    }

    public Integer getPercepcao() {
        return percepcao;
    }

    public void setPercepcao(Integer percepcao) {
        this.percepcao = percepcao;
    }

    public Integer getDefesa() {
        return defesa;
    }

    public void setDefesa(Integer defesa) {
        this.defesa = defesa;
    }

    public Integer getFortitude() {
        return fortitude;
    }

    public void setFortitude(Integer fortitude) {
        this.fortitude = fortitude;
    }

    public Integer getReflexo() {
        return reflexo;
    }

    public void setReflexo(Integer reflexo) {
        this.reflexo = reflexo;
    }

    public Integer getVontade() {
        return vontade;
    }

    public void setVontade(Integer vontade) {
        this.vontade = vontade;
    }

    public Double getVelocidadeAndar() {
        return velocidadeAndar;
    }

    public void setVelocidadeAndar(Double velocidadeAndar) {
        this.velocidadeAndar = velocidadeAndar;
    }

    public Double getVelocidadeEscalar() {
        return velocidadeEscalar;
    }

    public void setVelocidadeEscalar(Double velocidadeEscalar) {
        this.velocidadeEscalar = velocidadeEscalar;
    }

    public Double getVelocidadeNadar() {
        return velocidadeNadar;
    }

    public void setVelocidadeNadar(Double velocidadeNadar) {
        this.velocidadeNadar = velocidadeNadar;
    }

    public Double getVelocidadeVoar() {
        return velocidadeVoar;
    }

    public void setVelocidadeVoar(Double velocidadeVoar) {
        this.velocidadeVoar = velocidadeVoar;
    }

    public Integer getNd() {
        return nd;
    }

    public void setNd(Integer nd) {
        this.nd = nd;
    }
}
