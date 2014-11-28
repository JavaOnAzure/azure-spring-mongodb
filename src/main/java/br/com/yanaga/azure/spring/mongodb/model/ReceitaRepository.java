package br.com.yanaga.azure.spring.mongodb.model;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReceitaRepository extends MongoRepository<Receita, String> {

	public List<Receita> findByNomeLike(String nome);

}
