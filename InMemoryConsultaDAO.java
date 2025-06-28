package br.com.veterinaria.dao;

import br.com.veterinaria.model.Consulta;
import java.util.ArrayList;
import java.util.List;

public class InMemoryConsultaDAO implements ConsultaDAO {
    private static final List<Consulta> consultas = new ArrayList<>();
    private static int nextId = 0;

    @Override
    public void salvar(Consulta consulta) {
        // Simula a atribuição de um ID para a consulta
        consulta.setId(++nextId);
        consultas.add(consulta);
        System.out.println("LOG: Consulta salva em memória. ID: " + consulta.getId());
    }

    @Override
    public Consulta buscarPorId(int id) {
        for (Consulta c : consultas) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null; // Retorna null se não encontrar
    }

    @Override
    public List<Consulta> buscarTodos() {
        return new ArrayList<>(consultas); // Retorna uma cópia para evitar modificações externas
    }

    @Override
    public void atualizar(Consulta consulta) {
        // Implementação de atualização em memória (simplificada)
        // Em um banco de dados real, você executaria um comando UPDATE
        System.out.println("LOG: Consulta atualizada em memória. ID: " + consulta.getId());
    }

    @Override
    public void deletar(Consulta consulta) {
        consultas.remove(consulta);
        System.out.println("LOG: Consulta removida da memória. ID: " + consulta.getId());
    }
}