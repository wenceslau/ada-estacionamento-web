package com.ada.estacionamento.dominio.veiculos;

import com.ada.estacionamento.dominio.Veiculo;

public class Moto extends Veiculo {

    public Moto(String placa) {
        super(placa);
    }

    @Override
    public VeiculoTipo getTipo() {
        return VeiculoTipo.CARRO;
    }

    @Override
    public double valorHora() {
        return 5.00;
    }

    @Override
    public double valorHoraAdicional() {
        return 6.00;
    }

}
