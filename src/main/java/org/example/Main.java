package org.example;


import org.example.dao.*;
import org.example.model.*;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner SC = new Scanner(System.in);
    public static void main(String[] args) {

        inicio();
    }
    public static void inicio(){
        boolean sair = false;
        System.out.println("|=====================================|");
        System.out.println("|          MENU DE MANUTENÇÃO         |");
        System.out.println("|=====================================|");
        System.out.println("""
                | 1. Cadastrar Fornecedor;            |
                | 2. Cadastrat material;              |
                | 3. Registrar nota de entrada;       |
                | 4. Criar requisição de material;    |
                | 5. Atender requisição;              |
                | 6. Cancelar requisição;             |
                |                                     |
                |-------------------------------------|
                |                          | 0. Sair. |
                ´=====================================`
                """);
        System.out.print("Digite a sua opção: ");
        int opcao = SC.nextInt();
        SC.nextLine();

        switch (opcao){
            case 1->{
                cadastrarFornecedor();
                break;
            }
            case 2 ->{
                cadastrarMaterial();
                break;
            }
            case 3 ->{
                registrarNotaEntrada();
                break;
            }
            case 4 ->{
                criarRequisicao();
                break;
            }
            case 5 ->{
                criarRequisicao();
                break;
            }
            case 6 ->{

                break;
            }

        }
        if (!sair){
            inicio();
        }
    }
    public static void cadastrarFornecedor(){
        System.out.println("\n=== CADASTRAR FORNECEDOR ===");
        System.out.println("Digite o nome: ");
        String nome = SC.nextLine();

        System.out.println("Digite o CNPJ: ");
        String cnpj = SC.nextLine();

        var fornecedor = new Fornecedor(nome, cnpj);
        var fornDao = new FornecedorDAO();

        try{
            if(!nome.isEmpty() && !cnpj.isEmpty()) {
                fornDao.cadastrarFornecedor(fornecedor);
            } else {
                System.out.println("Digite todos os dados.");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void cadastrarMaterial(){
        System.out.println("\n=== CADASTRAR MATERIAL ===");
        System.out.println("Digite o nome do material: ");
        String nome = SC.nextLine();

        System.out.println("Digite a unidade de medida (kg, g, peça...): ");
        String unidade = SC.nextLine();

        System.out.println("Digite a quantidade inicial em estoque: ");
        int estoque = SC.nextInt();

        var material = new Material(nome, unidade, estoque);
        var matDao = new MaterialDAO();

        if(!nome.isEmpty() && !unidade.isEmpty() && estoque >= 0){
            try{
                matDao.cadastrarMaterial(material);
            } catch (SQLException e){
                e.printStackTrace();
            }
        } else {
            System.out.println("Por favor, digite todos os campos.");
        }
    }
    public static void registrarNotaEntrada() { // Atualizar quantidade no estoque
        var fornecedorDao = new FornecedorDAO();
        var matDao = new MaterialDAO();
        int idMaterial = 0;
        int quantidade = 0;

        System.out.println("=== REGISTRAR NOTA DE ENTRADA ===");
        System.out.println("Digite o CNPJ do fornecedor: "); // Verificar se existe
        String cnpj = SC.nextLine();

        if (cnpj.isEmpty()) {
            System.out.println("Por favor, digite um CNPJ.");
            return;
        }

        int idPegoForn = 0; // Converte o CNPJ para ID conforme Insert
        try {
            idPegoForn = fornecedorDao.pegarIDForn(cnpj);
            if(idPegoForn == -1){
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Digite a data de entrada AAAA-MM-DD (atual): ");
        String dataStr = SC.nextLine();
        LocalDate data = LocalDate.parse(dataStr);

        List<Material> materiais = new ArrayList<>(); // Cria a lista de materiais
        try {
            materiais = matDao.listarMateriais();
        } catch (SQLException e) {
             e.printStackTrace();
        }

        var notaEntradaDao = new NotaEntradaDAO();
        var notaItemDao = new NotaEntradaItemDAO();

        var notaEntrada = new NotaEntrada(idPegoForn, data); // cria a notaEntrada
        try {
            notaEntradaDao.registrarNotaEntrada(notaEntrada); // REGISTRA
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int idNotaEntrada = 0; // pega o id da Nota para atribuir ao notaItem
        try {
            idNotaEntrada = notaEntradaDao.pegarIDNota(idPegoForn, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int adicionarMais = 1;
        List<NotaEntradaItem> listaItem = new ArrayList<>();
        List<Integer> idsJaSelecionados = new ArrayList<>();

        while (!materiais.isEmpty() && adicionarMais == 1) { // Enquanto o user digitar "sim" e a lista tiver materiais...
            System.out.println("\nMateriais cadastrados: ");
            materiais.forEach(material -> {
                System.out.println("ID: " + material.getId());
                System.out.println("Nome: " + material.getNome());
                System.out.println("Unidade: " + material.getUnidade());
                System.out.println("Estoque: " + material.getEstoque());
                System.out.println("------------------------------------------");
            });

            System.out.println("Digite o ID do material: "); // Verificar se existe
            idMaterial = SC.nextInt();
            SC.nextLine();

            System.out.println("Digite a quantidade: ");
            quantidade = SC.nextInt();
            SC.nextLine();

            if(quantidade <= 0){
                System.out.println("Digite uma quantidade disponível!");
                return;
            }

            idsJaSelecionados.add(idMaterial);

            if(idsJaSelecionados.contains(idMaterial)){
                System.out.println("Esse material já foi associado antes!");
            } else {
                continue;
            }

            System.out.println("Deseja adicionar mais algum material? \n1-Sim; \n2-Não");
            adicionarMais = SC.nextInt();
            SC.nextLine();

            var notaItem = new NotaEntradaItem(idNotaEntrada, idMaterial, quantidade); // cria o notaItem
            try {
                notaItemDao.registrarNotaItem(notaItem); // REGISTRA
            } catch (SQLException e) {
                e.printStackTrace();
            }


        } // Fecha While
    }
    public static void criarRequisicao(){
        var materialDao = new MaterialDAO();
        var reqDao = new RequisicaoDAO();
        var reqItemDao = new RequisicaoItemDAO();

        System.out.println("=== CRIAR REQUISIÇÃO ===");
        System.out.println("Digite o nome do setor requisitante: ");
        String setor = SC.nextLine();

        List<Material> listaMateriais = new ArrayList<>();
        try {
            listaMateriais = materialDao.listarMateriais();
        } catch (SQLException e) {
            System.err.println("Erro ao listar materiais.");
            e.printStackTrace();
        }
        listaMateriais.forEach(material -> {
            System.out.println("ID: "+material.getId());
            System.out.println("Nome: " + material.getNome());
            System.out.println("Estoque: " + material.getEstoque());
            System.out.println("------------------------------------------");
        });
        System.out.print("Digite o ID do material que deseja associar: ");
        int id = SC.nextInt();
        SC.nextLine();

        System.out.print("Digite a quantidade que deseja requisitar: ");
        double quantidade = SC.nextDouble();
        SC.nextLine();

        System.out.println("Digite a data: ");
        String dataSt = SC.nextLine();
        LocalDate data = LocalDate.parse(dataSt);

        var reqObj = new Requisicao(setor, data, "PENDENTE");
        try {
            reqDao.registrarRequisicao(reqObj);
        } catch (SQLException e) {
            System.err.println("Erro ao registrar requisição.");
            e.printStackTrace();
        }

        int idReq = 0;
        try {
            idReq = reqDao.pegarIDRequisicao(setor, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        var reqItemObj = new RequisicaoItem(idReq, id, quantidade);
        try {
            reqItemDao.registrarReqItem(reqItemObj);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}