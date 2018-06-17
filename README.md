
# PipelineScript



## Scripted Pipeline

>Scripted Pipeline, like [Declarative Pipeline](https://jenkins.io/doc/book/pipeline/syntax/#declarative-pipeline), is built on top of the underlying Pipeline sub-system. Unlike Declarative, Scripted Pipeline is effectively a general purpose DSL [[2](https://jenkins.io/doc/book/pipeline/syntax/#_footnote_2 "View footnote.")] built with [Groovy](http://groovy-lang.org/syntax.html). Most functionality provided by the Groovy language is made available to users of Scripted Pipeline, which means it can be a very expressive and flexible tool with which one can author continuous delivery pipelines.
[Jenkins - Scripted-pipeline](https://jenkins.io/doc/book/pipeline/syntax/#scripted-pipeline)

## Pré-requisitos

- Tools:
	- Jenkins 2.x
	- Plugins
		- Pipeline
		- Groovy
		- SCM
		- Timestamper
		- SonarQube
- Aplicação
	- Node.js
	- Docker

## Steps

- Checkout
	- Checkout do repositório do Projeto. Desenvolvimento basiado em trunk.
- Build
	- Fase de construção dos entregáveis que deverão passar pelas próximas fases.
- Install Dependencies
	- Fase de instação das depedências do Projeto 
- Testes
	- Fase de testes da aplicação
- Code Quality
	- Fase de análise de código. Nessa fase poderá ser usada alguma ferramenta de análise estatica como o SonarQube
- Deploy Pré-Produção
	- Deploy para ambiente pré-produção
- Release
	- Fase de release da aplicação. Nessa fase,  aplicação deverá ser versionada e disponibilidade em um repositório de artefatos.
- Aprovação Deploy Produção
	- Nessa fase irá solicitar uma aprovação do usuário para passar para a próxima fase. Deploy em ambiente produtivo
- Deploy Produção
	- Fase de deploy do artefato em Ambiente produtivo.

## Continuous Delivery X Continuous Deployment
![enter image description here](https://sdtimes.com/wp-content/uploads/2015/02/CDvsCD.jpg)
