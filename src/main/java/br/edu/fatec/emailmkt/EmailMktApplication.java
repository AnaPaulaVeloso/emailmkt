package br.edu.fatec.emailmkt;

import br.edu.fatec.emailmkt.service.ConsomeApi;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jdi.IntegerValue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class EmailMktApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(EmailMktApplication.class, args);
	}

	public void run(String... args) throws  Exception{
		ConsomeApi lerApi = new ConsomeApi();
		String dados = lerApi.obterDados("http://api.escuelajs.co/api/v1/products");
		System.out.println(dados);
		ObjectMapper objMapper = new ObjectMapper();
		JsonNode jsonNode = objMapper.readTree(dados);
		List<JsonNode> jsonList = new ArrayList<>();
		jsonNode.forEach(jsonList::add);

		List<String> imperdiveis = jsonList.stream()
				.filter(n -> n.get("price").asInt() <=30)
				.map(n -> {
					String titulo = n.get("title").asText().toUpperCase();
					int preco = n.get("price").asInt();
					return titulo + " " + preco;
				})
				.toList();


		imperdiveis.forEach(System.out::println);



	}
}
