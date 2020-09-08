package com.desafio.tech;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.desafio.tech.model.Cliente;
import com.desafio.tech.model.Item;
import com.desafio.tech.model.Venda;
import com.desafio.tech.model.Vendedor;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class WatchServiceExecutor {

	private final int CPF_VENDEDOR = 1;
	private final int NOME_VENDEDOR = 2;
	private final int SALARIO_VENDEDOR = 3;
	private final int CNPJ_CLIENTE = 1;
	private final int NOME_CLIENTE = 2;
	private final int AREA_DE_VENDA_CLIENTE = 3;
//	private final int ID_VENDA = 0;
//	private final int SALE_ID_VENDA = 0;
//	private final int ITENS_VENDA = 0;

	@Autowired
	public final WatchService watchService;

	@PostConstruct
	public void launchMonitoring() {

		try {
			Map<String, Vendedor> vendedoresNoArquivo = new HashMap<String, Vendedor>();
			Map<String, Cliente> clientesNoArquivo = new HashMap<String, Cliente>();
			StringBuilder outText = new StringBuilder();
			List<Venda> vendas = new ArrayList<Venda>();
			WatchKey key;
			while ((key = watchService.take()) != null) {
				for (WatchEvent<?> event : key.pollEvents()) {

					System.out.println("Event kind: {}; File affected: " + event.kind() + " " + event.context());
					BufferedReader br = null;
					try {

						File source = new File(Constants.IN + event.context().toString());
						File dest = new File(Files.createTempDirectory("tempfiles").toString());

				        Path newFile = Files.copy(source.toPath(), dest.toPath(),
				                StandardCopyOption.REPLACE_EXISTING);

						br = new BufferedReader(new FileReader(newFile.toString()));

						String line;
						while ((line = br.readLine()) != null) {
							

							String[] splittedLine = line.toString().split("ç");

							int type = Integer.valueOf(splittedLine[0]);
							Vendedor vendedor = null;
							Venda venda = null;
							List<Item> itensVenda = new ArrayList<Item>();
							switch (type) {
							case 1:
								vendedor = new Vendedor(splittedLine[CPF_VENDEDOR], splittedLine[NOME_VENDEDOR],
										Double.valueOf(splittedLine[SALARIO_VENDEDOR]));
								vendedoresNoArquivo.put(String.valueOf(vendedor.getName()), vendedor);
								break;
							case 2:
								Cliente cliente = new Cliente(splittedLine[CNPJ_CLIENTE], splittedLine[NOME_CLIENTE],
										splittedLine[AREA_DE_VENDA_CLIENTE]);
								clientesNoArquivo.put(cliente.getCnpj(), cliente);

								break;
							case 3:
								venda = new Venda(splittedLine[1], itensVenda);
								String[] splittedItens = splittedLine[2].replace("[", "").replace("]", "").split(",");
								for (int i = 0; i < splittedItens.length; i++) {
									String[] itemSplit = splittedItens[i].split("-");
									for (int y = 0; y < itemSplit.length; y++) {
										Vendedor ven = vendedoresNoArquivo.get(splittedLine[3]);
										Item item = new Item(Integer.valueOf(itemSplit[0]),
												Integer.valueOf(itemSplit[1]), Double.valueOf(itemSplit[2]), ven);
										itensVenda.add(item);
									}
								}
								vendas.add(venda);

								break;

							default:
								break;
							}
						}

						outText.append("Clientes no arquivo: " + clientesNoArquivo.size());
						outText.append("\n");
						outText.append("Vendedores no arquivo: " + vendedoresNoArquivo.size());
						outText.append("\n");
						double max = 0;
						Item piorItem = null;
						for (Venda venda : vendas) {
							 max = venda.getItens()
								      .stream()
								      .mapToDouble(v -> v.getDoublePrice())
								      .max().orElseThrow(NoSuchElementException::new);
							 
							 piorItem = venda.getItens()
								      .stream()
								      .min(Comparator.comparing(Item::getDoublePrice))
								      .orElseThrow(NoSuchElementException::new);
							
						}
						
						
						outText.append("Venda mais cara: " + max);
						outText.append("\n");
						outText.append("Pior Vendedor: " + piorItem.getVendedor().getName());
						outText.append("\n");

						BufferedWriter writer = new BufferedWriter(
								new FileWriter(Constants.OUT + UUID.randomUUID() + ".txt"));

						writer.write(outText.toString());

						writer.close();
						// TODO Auto-generated catch block

						// ...

					} catch (FileNotFoundException e) {

						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {

						if (br != null) {
							try {
								br.close();
								br = null;
							} catch (IOException e) {

								e.printStackTrace();
							}
						}
					}

				}
				key.reset();
			}
		} catch (InterruptedException e) {
			System.out.println("interrupted exception for monitoring service");
		}
	}

}
