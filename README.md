# Paradigmas de Linguagens de Programação

Repositório do projeto da disciplina Paradigmas de Linguagens de Programação ([in1007](https://www.cin.ufpe.br/~in1007/)) da pós-graduação em Ciência da Computação da UFPE, ministrada pelo Prof.Dr. Augusto Sampaio.

## Execução

* Local

```bash
cd <Linguagem>
mvn clean generate-sources compile exec:java
```

* Applet

```bash
mvn package
# Jar será gerado em ./Applet/target/
```

## Contribuições

* Unificar os projetos das linguagens e Applet no mesmo repositório - ([commit](https://github.com/AugustoSampaio/PLP/commit/5facfa1b4017536cd25730bcece9fbd94a49aa48/))
* Refactoring para adicionar o ambiente na LE1 - ([commit](https://github.com/AugustoSampaio/PLP/commit/244d8d01e036bcbec0acccf337e09c19d6ec434c/))
* Correção de problemas relativos à quantidade de argumentos em funções nas LFs - ([commit](https://github.com/AugustoSampaio/PLP/commit/14664755276b35d0aca704f7c1da8af09ea38081/))
* Separar ContextoExecucaoFuncional entre ContextoExecucao[Valor] e ContextoFuncional[DefFuncao], na LF1 - ([commit](https://github.com/AugustoSampaio/PLP/commit/a11a34df27dcdfa444c7a25f962c67f9d1480635/))
* Geração/atualização automática do Applet através do Maven - ([commit](https://github.com/AugustoSampaio/PLP/commit/bfdf84077162698587bcbcb24cc2b9d7c987b6bc/),
[commit](https://github.com/AugustoSampaio/PLP/commit/5facfa1b4017536cd25730bcece9fbd94a49aa48/))
* Refactoring na LE2 e LFs: aplicar o padrão composite na avaliação de Declarações - ([refactors](https://github.com/AugustoSampaio/PLP/commit/bab29438f5ddafa5662073ea08e166c1e04e49b9/),  [LF3](https://github.com/AugustoSampaio/PLP/commit/c60d5f92e792b89105b242163f5bd0f1409f72b4/),  [LF2](https://github.com/AugustoSampaio/PLP/commit/5481b2b8fd1ea1ff0659070a5608e1521d120193/))
* Correção da Linguagem Funcional 3 - ([commit](https://github.com/AugustoSampaio/PLP/commit/b523ebee335348ed12f03a4eecfd3234b703071d/))
* Mudança para utilizar ambiente auxiliar no elabora ao invés de map auxiliar - ([commit](https://github.com/AugustoSampaio/PLP/commit/43d900d6be77288786ad895c99f4bbc163e04244/))
* Atualizar a página da disciplina com as novas modificações
* Adicionar javacc sources para debug ([commit](https://github.com/fmca/PLP/commit/9606652f1f48717e75bc1a480c8ad8bff1bf5c3c))
* Corrigir problemas da LF3 ao executar quicksort ([commit](https://github.com/fmca/PLP/commit/31ea41c48fb3a7df3ac9e8ea4fbb980e08a66c78))
* Atualizar geração do applet ([commit](https://github.com/fmca/PLP/commit/9a9927f73eb57911ffbf46e3d60a0998e3842601))

## Observações

* O código disponível do github não tem ligação com o conteúdo da página da disciplina. Assim qualquer contribuição feita no repositório, não irá refletir no conteúdo da página, sendo necessário copiar os resultados para a página após o fim do período. A importância de estar dessa forma é porque assim, caso ocorra problema de conexão (ex.: o cin esteja sem internet), ainda será possível ter acesso ao conteúdo.

## Créditos

Baseado no projeto da equipe:

* Luana Martins (lms7@cin.ufpe.br)
* Marcel Rebouças (mscr@cin.ufpe.br)
* Renato Oliveira (ros3@cin.ufpe.br)
