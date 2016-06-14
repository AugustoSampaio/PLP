# Instruções para executar o projeto

Esse projeto foi atualizado para utilizar o Maven como framework de gerenciamento de dependências. 

## Executando o projeto
* Instalar o plugin do Maven para eclipse
* Criar "Maven Configuration" do Eclipse para execução de comando "Clean install"
   * Utilizar como Goals: "clean install"
   * Para permitir que a mesma configuração seja utilizada por outros projetos, adicionar no "Base directory" a string "${project_loc}" e clicar no projeto quando for realizar o clean
* Utilizar as "Run configurations" existentes na pasta "eclipse" de cada projeto
    * Observação: Cada configuração irá executar o programa existente no arquivo "input" de cada projeto
    * Verificar o arquivo de entrada da configuration criada na aba "Arguments"

