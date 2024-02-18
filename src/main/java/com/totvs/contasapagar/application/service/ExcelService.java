package com.totvs.contasapagar.application.service;

import com.totvs.contasapagar.domain.model.Conta;
import com.totvs.contasapagar.domain.repository.ContaRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ExcelService {

    private final ContaRepository contaRepository;

    public ExcelService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public String processarESalvarContas() throws Exception {
        List<Conta> contas = lerExcelParaContas();
        contaRepository.saveAll(contas);
        return "Arquivo processado e contas salvas com sucesso!";
    }

    private List<Conta> lerExcelParaContas() throws Exception {
        List<Conta> contas = new ArrayList<>();
        ClassPathResource classPathResource = new ClassPathResource("contaexcel.xlsx");
        InputStream inputStream = classPathResource.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rows = sheet.iterator();
        DataFormatter dataFormatter = new DataFormatter();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");

        if (rows.hasNext()) { // Pula o cabeçalho
            rows.next();
        }

        while (rows.hasNext()) {
            Row currentRow = rows.next();
            Conta conta = new Conta();

            // Assumindo a seguinte ordem de colunas: Data Vencimento, Data Pagamento, Descrição, Valor, Situação
            conta.setDataVencimento(LocalDate.parse(dataFormatter.formatCellValue(currentRow.getCell(0)), formatter));
            conta.setDataPagamento(LocalDate.parse(dataFormatter.formatCellValue(currentRow.getCell(1)), formatter));
            conta.setDescricao(dataFormatter.formatCellValue(currentRow.getCell(2)));
            conta.setValor(convertStringToBigDecimal(dataFormatter.formatCellValue(currentRow.getCell(3))));
            conta.setSituacao(Conta.SituacaoConta.valueOf(dataFormatter.formatCellValue(currentRow.getCell(4)).toUpperCase()));

            contas.add(conta);
        }

        workbook.close();
        inputStream.close();
        return contas;
    }

    private BigDecimal convertStringToBigDecimal(String valueStr) {
        try {
            return new BigDecimal(valueStr);
        } catch (NumberFormatException e) {
            System.err.println("Formato de número inválido: " + valueStr);
            return BigDecimal.ZERO; // Ou escolha uma maneira adequada de lidar com esse caso
        }
    }
}
