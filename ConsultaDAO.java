package br.com.veterinaria.dao;

import br.com.veterinaria.model.Consulta;
import java.util.List;

public interface ConsultaDAO {
    void salvar(Consulta consulta);
    Consulta buscarPorId(int id);
    List<Consulta> buscarTodos();
    void atualizar(Consulta consulta);
    void deletar(Consulta consulta);
}