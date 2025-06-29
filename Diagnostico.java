package br.com.veterinaria.model;

public class Diagnostico {
    private static int nextId = 1;
    private int id;
    private Consulta consulta; // A qual consulta este diagnóstico se refere
    private String descricao;  // A descrição detalhada do diagnóstico

    public Diagnostico(Consulta consulta, String descricao) {
        this.id = nextId++;
        this.consulta = consulta;
        this.descricao = descricao;
    }

    public int getId() { return id; }
    public Consulta getConsulta() { return consulta; }
    public String getDescricao() { return descricao; }

    public void setId(int id) { this.id = id; }
    public void setConsulta(Consulta consulta) { this.consulta = consulta; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public String toString() {
        return "Diagnóstico [ID=" + id + ", Consulta=" + consulta.getId() + ", Descrição=" + descricao + "]";
    }
}