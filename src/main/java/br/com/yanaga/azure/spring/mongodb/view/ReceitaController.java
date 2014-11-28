package br.com.yanaga.azure.spring.mongodb.view;

import br.com.yanaga.azure.spring.mongodb.model.Receita;
import br.com.yanaga.azure.spring.mongodb.model.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Scope("session")
@Controller
public class ReceitaController {

	@Autowired
	private ReceitaRepository receitaRepository;

	private Receita receita = new Receita();

	private String consulta;

	public List<Receita> find() {
		if (consulta == null) {
			return receitaRepository.findAll();
		} else {
			return receitaRepository.findByNomeLike(consulta);
		}
	}

	public String novo() {
		this.receita = new Receita();
		return "cadastro";
	}

	public String editar(Receita receita) {
		this.receita = receita;
		return "cadastro";
	}

	public String salvar() {
		receitaRepository.save(receita);
		return "lista";
	}

	public void excluir(Receita receita) {
		receitaRepository.delete(receita);
	}

	public Receita getReceita() {
		return receita;
	}

	public String getConsulta() {
		return consulta;
	}

	public void setConsulta(String consulta) {
		this.consulta = consulta;
	}
}
