package br.com.gft.auction.auction2.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.gft.auction.auction2.model.Leilao;
import br.com.gft.auction.auction2.model.LeilaoArrematado;
import br.com.gft.auction.auction2.service.LeilaoArrematadoService;

@Controller
public class LeilaoArrematadoRestController {

	// Injeta uma instancia de LeilaoService
	@Autowired
	private LeilaoArrematadoService arrematadoService;

	@Autowired
	private void setService(LeilaoArrematadoService arrematadoService) {
		this.arrematadoService = arrematadoService;
	}

	@RequestMapping(value = "/insertLeiilaoArrematado", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> insert(@RequestParam("leilao") Leilao leilao, LeilaoArrematado leilaoArrematado) {

		Timestamp dataAtual = new Timestamp(System.currentTimeMillis());
		long start = dataAtual.getTime();
		try {
			int pegaLeilao = arrematadoService.pegaLeilaoArrematado(leilao);
			return new ResponseEntity<>("Nadafaz", HttpStatus.OK);

		} catch (Exception e) {
			if (leilao.getDataFimLeilao().getTimeInMillis() < start) {
				try {
					arrematadoService.saveLeilãoArrematado(leilaoArrematado);
				} catch (Exception e2) {
					System.out.println("tentou inserir");
				}
				
			}
		}

		return new ResponseEntity<>("Nada", HttpStatus.OK);
	}

	@RequestMapping(value = "/listaLeiloesArrematados", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> getListaDeLeiloesArrematado() {
		List<LeilaoArrematado> arrematados = arrematadoService.findAll();
		return new ResponseEntity<>(arrematados, HttpStatus.OK);
	}

	@RequestMapping(value = "/umPegaUmLeilao", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> getProdutoById() {
		try {
			int leilao = arrematadoService.updateLeilaoConflito();
//			int ultIdLeilao = arrematadoService.pegaUltimoIdLeilaoArrematado();
//			if (leilao == ultIdLeilao) {
//				return new ResponseEntity<>("Leilão já existe", HttpStatus.NOT_FOUND);
//			}else
//				
			return new ResponseEntity<>(leilao, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Produto nÃ£o existe", HttpStatus.OK);
		}

	}

	@RequestMapping(value = "/listaDeLeiloesArrematadoPorUmUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<?> getListaDeLeiloesArrematadoPorUmUser(@RequestParam("netId") String netId) {
		List<LeilaoArrematado> arrematados = arrematadoService.meusLeilões(netId);
		return new ResponseEntity<>(arrematados, HttpStatus.OK);
	}

	@RequestMapping(value = "/leiloes_Arrematado", method = RequestMethod.GET)
	public void getFile(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connect = DriverManager.getConnection("jdbc:oracle:thin:@10.200.102.20:1521:xe", "LEILAO", "LEILAO#2017$");															

			Statement statement = connect.createStatement();
			ResultSet resultSet = statement
					.executeQuery("select * from leilao_arrematado ORDER BY ID_LEILAO_ARREMATADO DESC");
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet spreadsheet = workbook.createSheet("leilao_arrematado db");
			XSSFRow row1 = spreadsheet.createRow(0);
			XSSFCell cell;
			cell = row1.createCell((short) 0);
			XSSFCellStyle style2 = workbook.createCellStyle();
			style2.setAlignment(XSSFCellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
			cell.setCellValue("Leilões Arrematados");
			cell.setCellStyle(style2);
			// MEARGING CELLS
			// this statement for merging cells
			spreadsheet.addMergedRegion(new CellRangeAddress(0, // first row
																// (0-based)
					0, // last row (0-based)
					0, // first column (0-based)
					9 // last column (0-based)
			));
			XSSFRow row = spreadsheet.createRow(1);
			cell = row.createCell(0);
			cell.setCellValue("Leilão_Arrematado_ID");
			cell = row.createCell(1);
			cell.setCellValue("Id_leilao");
			cell = row.createCell(2);
			cell.setCellValue("NetId_Ganhador");
			cell = row.createCell(3);
			cell.setCellValue("Nome_Ganhador");
			cell = row.createCell(4);
			cell.setCellValue("Unidade_Ganhador");
			cell = row.createCell(5);
			cell.setCellValue("Produto");
			cell = row.createCell(6);
			cell.setCellValue("Unidade_Produto");
			cell = row.createCell(7);
			cell.setCellValue("N_Patrimonio");
			cell = row.createCell(8);
			cell.setCellValue("Valor_Arrematado");
			cell = row.createCell(9);
			cell.setCellValue("Data_Arremate");
			int i = 2;
			while (resultSet.next()) {
				row = spreadsheet.createRow(i);
				cell = row.createCell(0);
				cell.setCellValue(resultSet.getInt("ID_LEILAO_ARREMATADO"));
				cell = row.createCell(1);
				cell.setCellValue(resultSet.getInt("ID_LEILAO"));
				cell = row.createCell(2);
				cell.setCellValue(resultSet.getString("NetId_ganhador"));
				cell = row.createCell(3);
				cell.setCellValue(resultSet.getString("nome_ganhador"));
				cell = row.createCell(4);
				cell.setCellValue(resultSet.getString("unidade_ganhador"));
				cell = row.createCell(5);
				cell.setCellValue(resultSet.getString("Nome_produto"));
				cell = row.createCell(6);
				cell.setCellValue(resultSet.getString("unidade_produto"));
				cell = row.createCell(7);
				cell.setCellValue(resultSet.getString("N_patrimonio_produto"));
				cell = row.createCell(8);
				cell.setCellValue(resultSet.getDouble("valor_arrematado"));
				cell = row.createCell(9);
				cell.setCellValue(resultSet.getString("Data_arremate"));
				i++;
			}
			FileOutputStream out = new FileOutputStream(new File("exceldatabase.xlsx"));
			workbook.write(out);
			out.close();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ServletContext context = request.getServletContext();
		String fileToDowload = "exceldatabase.xlsx";
		File file = new File(fileToDowload);
		try {
			InputStream inputStream = new FileInputStream(file);
			response.setContentType(context.getMimeType(fileToDowload));
			response.setContentType("application/xlsx");
			response.setHeader("Content-Disposition", String.format("attachment: filename=\"%s\"", fileToDowload));
			response.setHeader("Contente-Length", String.valueOf(file.length()));
			IOUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("entrou aqui1");
		} catch (IOException ex) {
			System.out.println("entrou aqui2");

		}

	}

	@RequestMapping(value = "/pegaQuantidadeLeilao", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<?> pegaQuantidadeLeilao() {
		int leilao = arrematadoService.pegaQuantidadeLeilao();
		return new ResponseEntity<>(leilao, HttpStatus.OK);

	}

}
