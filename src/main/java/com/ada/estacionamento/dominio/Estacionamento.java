package com.ada.estacionamento.dominio;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Estacionamento {

    private Long id;
    private Integer capacidade;
    private final LocalDate dataReferencia;
    private final List<Registro> registros;

    public Estacionamento(LocalDate dataReferencia, Integer capacidade) {
        if (capacidade <= 0){
            throw new IllegalArgumentException("Capacidade deve ser maior que zero");
        }
        this.dataReferencia = dataReferencia;
        this.capacidade = capacidade;
        this.registros = new ArrayList<>();
    }

    public void atualizaCapacidade(Integer capacidade) {
        if (capacidade == null || capacidade <= 0){
            throw new IllegalArgumentException("Capacidade deve ser maior que zero");
        }
        this.capacidade = capacidade;
    }

    public Registro registrarVeiculo(Veiculo veiculo) {

        if (veiculo == null){
            throw new IllegalArgumentException("Veiculo não pode ser nulo");
        }

        if (capacidade == null){
            throw new IllegalArgumentException("O estacionamento não foi inicializado");
        }

        var optional = procurarRegistro(veiculo);

        if (optional.isEmpty()) {
            return registrarEntrada(veiculo);
        }else {
            Registro registro = optional.get();
            return registrarSaida(registro);
        }
    }

    private Optional<Registro> procurarRegistro(Veiculo veiculo) {
        return registros.stream()
                .filter(r -> r.getVeiculo().getPlaca().equals(veiculo.getPlaca()))
                .filter(r -> r.getHoraSaida() == null)
                .findFirst();
    }

    private Registro registrarEntrada(Veiculo veiculo) {
        int count = registros.stream()
                .filter(r -> r.getHoraSaida() == null)
                .mapToInt(r -> 1)
                .sum();

        if (count == capacidade) {
            throw new IllegalStateException("Estacionamento lotado");
        }

        var registro = new Registro(veiculo, LocalDateTime.now());
        registros.add(registro);
        return registro;
    }

    private Registro registrarSaida(Registro registration) {
        registration.setHoraSaida(LocalDateTime.now());
        return registration;
    }

    public List<Registro> getRegistros() {
        return registros;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public LocalDate getDataReferencia() {
        return dataReferencia;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
