package com.totvs.contasapagar.service;

import com.totvs.contasapagar.application.service.ExcelService;
import com.totvs.contasapagar.domain.model.Conta;
import com.totvs.contasapagar.domain.repository.ContaRepository;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExcelServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ExcelService excelService;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void processarESalvarContas_Sucesso() throws Exception {
        // Caminho para um arquivo Excel de teste localizado em src/test/resources
        String testExcelFile = "contaexcel.xlsx";
        ClassPathResource classPathResource = new ClassPathResource(testExcelFile);

        // Simula a leitura do arquivo e prepara dados fictícios para retornar
        List<Conta> contasTeste = List.of(new Conta()); // Adicione detalhes conforme necessário

        // Configura o mock do repositório para simular o comportamento do saveAll
        when(contaRepository.saveAll(anyList())).thenReturn(contasTeste);

        // Executa o método a ser testado
        String resultado = excelService.processarESalvarContas(testExcelFile);

        // Verificações
        verify(contaRepository, times(1)).saveAll(anyList());
        assertEquals("Arquivo processado e contas salvas com sucesso!", resultado);
    }


}

