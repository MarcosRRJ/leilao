package br.com.gft.auction.auction2.infra;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.gft.auction.auction2.model.Produto;
import br.com.gft.auction.auction2.service.ProdutoService;

@Component
public class FileSaver {

	// Injeta uma instancia de HttpServletRequest
	@Autowired
	private HttpServletRequest request;

	// Injeta uma instancia de ProdutoService
	@Autowired
	private ProdutoService produtoService;

	/**
	 * Metodo resposavel o upload de uma imagem
	 *
	 * @param baseFolder
	 * @param file
	 * @return
	 */
	Erro erro = new Erro();

	List<Integer> listErr = new ArrayList();

	public String write(String baseFolder, MultipartFile file) {
		HttpServletRequest req = (HttpServletRequest) request;
		String name = String.valueOf(System.currentTimeMillis()) + ".jpg";
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				String realPath = request.getServletContext().getRealPath("/" + baseFolder);
				String item = (new File(file.getName()).getName());
				String path = realPath + "/" + item;
				File newpath = new File(path);
				if (!newpath.exists())
					;

				// Create the file on server
				File serverFile = new File(newpath.getAbsolutePath() + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				//
				// logger.info("Server File Location="
				// + serverFile.getAbsolutePath());

				return baseFolder + "/" + item + name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}

	/**
	 * Metodo resposavel por ler os dados da planilha
	 *
	 * @param baseFolder
	 * @param file
	 * @return
	 */
	public String excel(String baseFolder, MultipartFile file) {

		listErr = new ArrayList();
		erro.setContaLinha(0);;
		if (!file.isEmpty()) {
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@10.230.8.38:1521:XE", "checagem_de_ponto", "checagem_de_ponto");

				try {

					// Get the workbook instance for XLS file
					XSSFWorkbook workbook = new XSSFWorkbook(convert(baseFolder, file));

					// Get first sheet from the workbook
					XSSFSheet sheet = workbook.getSheetAt(0);

					Iterator<Row> rowIterator = sheet.iterator();

					int contar = 0;

					while (rowIterator.hasNext()) {

						contar += 1;

						Row row = rowIterator.next();
						Iterator<Cell> cellIterator = row.cellIterator();
						Produto produtos = new Produto();

						while (cellIterator.hasNext()) {

							Cell cell = cellIterator.next();
							switch (cell.getColumnIndex()) {
							case 0:

								try {
									produtos.setNomeProduto(cell.getStringCellValue());
								} catch (Exception e) {
								}

								break;
							case 1:
								try {
									produtos.setDescricao(cell.getStringCellValue());
								} catch (Exception e) {
								}

								break;
							case 2:
								try {
									produtos.setCodPatrimonio(cell.getStringCellValue());
								} catch (Exception e) {
									Long valorLong = (long) cell.getNumericCellValue();
									produtos.setCodPatrimonio(valorLong.toString());
								}
								break;
							case 3:
								try {
									produtos.setUnidade(cell.getStringCellValue());
								} catch (Exception e) {
								}
								break;
							case 4:
								try {
									produtos.setValorInicial(cell.getNumericCellValue());
								} catch (Exception e) {
									// TODO: handle exception
								}
								break;
							case 5:
								try {
									produtos.setValorPorLance(cell.getNumericCellValue());
								} catch (Exception e) {
								}
								break;
							}

						}

						if (produtos.getNomeProduto() == null || produtos.getNomeProduto().trim().equals("")) {
							listErr.add(contar);

						}else if (produtos.getDescricao() == null) {
							listErr.add(contar);

						}else if (produtos.getCodPatrimonio() == null || produtoService.countfindProdutoBycodPatrimonio(produtos.getCodPatrimonio()) > 0) {
							listErr.add(contar);

						}else if (produtos.getUnidade() == null) {
							listErr.add(contar);

						}else if (produtos.getValorInicial() == 0 ) {
							listErr.add(contar);

						}else if (produtos.getValorPorLance() == 0 ) {
							listErr.add(contar);
						}
						else {
							try {
										String sql1 = "SELECT unidade_id FROM unidade WHERE nome_unidade = '"
												+ produtos.getUnidade() + "'";

										PreparedStatement st1 = connect.prepareStatement(sql1);

										ResultSet rs = st1.executeQuery();
										if (!rs.next()) {
											listErr.add(contar);
										} else {

											// st.setString(1, sql1);
											ResultSet rs1 = st1.executeQuery();

											while (rs1.next()) {
												int id1 = rs1.getInt(1);

												String sql = "insert into produto (id_produto,nome_produto,descricao,cod_patrimonio,unidade_id,valor_inicial,valor_por_lance,situacao)"
														+ "Values(produto_seq.nextval,?,?,?,?,?,?,1)";
												erro.setContaLinha(1 + erro.getContaLinha());
												PreparedStatement st = connect.prepareStatement(sql);
												st.setString(1, produtos.getNomeProduto());
												st.setString(2, produtos.getDescricao());
												st.setString(3, produtos.getCodPatrimonio());
												st.setDouble(4, id1);
												st.setObject(5, produtos.getValorInicial());
												st.setObject(6, produtos.getValorPorLance());
												st.executeUpdate();

											}
										}
									} catch (Exception e) {
										// TODO: handle exception
									}
						}
//						listErr.add(contar);
//
					}

					workbook.close();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
					System.out.println("Arquivo Excel não encontrado!");
				} catch (IOException e) {
					e.printStackTrace();
				}
				String item = (new File(file.getName()).getName());
				return baseFolder + "/" + item;
			} catch (Exception e) {
				e.printStackTrace();
				return "You failed to upload " + file.getOriginalFilename() + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + file.getOriginalFilename() + " because the file was empty.";
		}
	}

	/**
	 * Metodo Resposavel por converter MultipartFile para File
	 *
	 * @param multipart
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */

	public File convert(String baseFolder, MultipartFile file) throws IOException {
		try {
			String realPath = request.getServletContext().getRealPath("/" + baseFolder);
			String item = (new File(file.getName()).getName());
			String path = realPath + "/" + item;
			File convFile = new File(path);
			convFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(convFile);
			fos.write(file.getBytes());
			fos.close();
			return convFile;

		} catch (IOException e) {
			return null;
		}

	}

	public Object importarPlanilha() {
		erro.getContaLinha();
		erro.setLista(listErr);
		return erro;
	}

}
