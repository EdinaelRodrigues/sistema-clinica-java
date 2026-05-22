Readme · MDCopiar🏥 Sistema de Clínica - Java + PostgreSQL
Sistema de gerenciamento de pacientes para clínica, desenvolvido em Java com banco de dados PostgreSQL.
Meu primeiro projeto em Java, criado aos 16 anos.

💡 Funcionalidades

Cadastrar paciente (nome, idade, CPF, endereço)
Listar todas as fichas cadastradas
Excluir paciente por CPF
Limite de 100 pacientes
Validação de CPF duplicado
Banco de dados real com PostgreSQL


🛠️ Tecnologias utilizadas

Java (sem frameworks)
PostgreSQL
JDBC (conexão com banco de dados)
Driver postgresql-42.7.3.jar


📁 Estrutura do projeto
├── Paciente.java              # Classe modelo do paciente
├── DatabaseManager.java       # Gerencia conexão e operações no banco
├── Sistema.java               # Menu principal e interação com o usuário
├── AlgumErroException.java    # Exceção personalizada
├── rodar.bat                  # Script para executar no Windows
└── postgresql-42.7.3.jar      # Driver JDBC do PostgreSQL

▶️ Como rodar
Pré-requisitos

Java instalado
PostgreSQL instalado e rodando
Banco de dados chamado clinica criado

Configuração
Abra o arquivo DatabaseManager.java e ajuste as configurações:
javaprivate static final String HOST    = "localhost";
private static final String PORTA   = "5432";
private static final String BANCO   = "clinica";
private static final String USUARIO = "seu_usuario";
private static final String SENHA   = "sua_senha";
Compilar e executar
bashjavac -cp ".;postgresql-42.7.3.jar" *.java
java -cp ".;postgresql-42.7.3.jar" Sistema
Ou no Windows, basta executar o rodar.bat.

📚 Aprendizados
A lógica do sistema foi desenvolvida do zero. A integração com o banco de dados (SQL, JDBC, PreparedStatement) foi aprendida com auxílio de IA como orientadora — cada conceito foi estudado e entendido antes de ser escrito.

👤 Autor
Dev Galego
GitHub
