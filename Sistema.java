import java.sql.SQLException;
import java.util.Scanner;

public class Sistema {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        DatabaseManager db = new DatabaseManager();

        // Conecta ao banco de dados (cria clinica.db se não existir)
        try {
            db.conectar();
        } catch (SQLException e) {
            System.out.println("Erro fatal ao conectar ao banco: " + e.getMessage().toString());
            System.out.println("Verifique se o driver sqlite-jdbc está no classpath.");
            return;
        }

        boolean encerrar = false;

        while (!encerrar) {
            try {
                System.out.println("\nO que deseja? (escolha com o numero)");
                System.out.println("1 - Cadastrar Paciente");
                System.out.println("2 - Consultar fichas");
                System.out.println("3 - Excluir ficha");
                System.out.println("0 - Sair");
                System.out.print("> ");

                int escolha = scan.nextInt();
                scan.nextLine();

                if (escolha != 0 && escolha != 1 && escolha != 2 && escolha != 3) {
                    throw new AlgumErroException("Número inválido. Escolha 0, 1, 2 ou 3.");
                }

                // -------------------------------------------------------
                // 1 – CADASTRAR
                // -------------------------------------------------------
                if (escolha == 1) {

                    if (db.contarPacientes() >= 100) {
                        System.out.println("Consultório cheio! Máximo de 100 pacientes.");
                    } else {
                        System.out.println("Digite o nome do paciente: ");
                        System.out.print("> ");
                        String nome = scan.nextLine();

                        System.out.println("Digite a idade: ");
                        System.out.print("> ");
                        int idade = scan.nextInt();
                        scan.nextLine();

                        System.out.println("Digite o CPF: ");
                        System.out.print("> ");
                        String cpf = scan.nextLine();

                        if (db.cpfExiste(cpf)) {
                            System.out.println("Erro: CPF já cadastrado.");
                        } else {
                            System.out.println("Digite o endereço: ");
                            System.out.print("> ");
                            String endereco = scan.nextLine();

                            int nficha = db.inserirPaciente(nome, idade, cpf, endereco);
                            System.out.println("\nPaciente cadastrado com sucesso!");
                            System.out.println("Número da ficha: " + nficha);
                        }
                    }

                // -------------------------------------------------------
                // 2 – CONSULTAR
                // -------------------------------------------------------
                } else if (escolha == 2) {

                    Paciente[] lista = db.listarPacientes();

                    if (lista.length == 0) {
                        System.out.println("Sem pacientes cadastrados no momento.");
                    } else {
                        for (Paciente p : lista) {
                            System.out.println("-----------------------------------");
                            System.out.println("Ficha: " + p.getNficha());
                            System.out.println(p);
                            System.out.println("-----------------------------------");
                        }
                    }

                // -------------------------------------------------------
                // 3 – EXCLUIR
                // -------------------------------------------------------
                } else if (escolha == 3) {

                    System.out.println("Insira o CPF para excluir: ");
                    System.out.print("> ");
                    String cpf = scan.nextLine();

                    boolean removido = db.excluirPorCpf(cpf);
                    if (removido) {
                        System.out.println("Registro excluído com sucesso!");
                    } else {
                        System.out.println("CPF não encontrado.");
                    }

                // -------------------------------------------------------
                // 0 – SAIR
                // -------------------------------------------------------
                } else if (escolha == 0) {
                    System.out.println("Saindo do sistema.....");
                    encerrar = true;
                }

            } catch (AlgumErroException e) {
                System.out.println(e.getMessage());

            } catch (SQLException e) {
                System.out.println("Erro no banco de dados: " + e.getMessage());

            } catch (RuntimeException e) {
                System.out.println("Entrada inválida ou valor incorreto. Por favor, tente novamente.");
                scan.nextLine();
            }
        }

        db.desconectar();
        scan.close();
    }
}
