# Relatório do Projeto - MyFood (AB1 - P2)

**Discente:** Diêgo de Araujo Correia
---
*UML do Projeto:* https://drive.google.com/file/d/1dxjeuOuJi9NW2dDOQozZ_-yjm7DzAPOZ/view?usp=sharing

## 1. Descrição Geral do Design Arquitetural
O sistema foi desenvolvido focando na separação de responsabilidades. O design isola as classes que apenas guardam dados (Modelos) das classes que executam as regras de negócio (Managers) e da lógica de salvamento de arquivos (Persistência). Essa abordagem garante baixo acoplamento e alta coesão, permitindo que alterações nas regras de validação não quebrem a estrutura dos dados. Toda a comunicação com os testes do EasyAccept é feita por meio de uma única classe central, protegendo o encapsulamento do sistema.

## 2. Principais Componentes e Suas Interações
A arquitetura está dividida nos seguintes pacotes principais:

* **Models:** Classes básicas que representam os objetos do sistema (`Usuario`, `Dono`, `Cliente`, `Empresa`, `Produto`, `Pedido`). Elas usam encapsulamento para proteger seus atributos e servem apenas para armazenar o estado e os dados, sem realizar lógicas complexas.
* **Services (Managers):** Classes responsáveis pelas regras de negócio (`UsuarioManager`, `EmpresaManager`, `ProdutoManager`, `PedidoManager`). Elas gerenciam os objetos utilizando coleções do tipo `Map`, geram os IDs únicos e fazem todas as validações do sistema.
* **Utils:** Contém a classe `PersistenciaXML`, responsável exclusivamente por salvar e carregar os dados em arquivos XML, separando essa complexidade do resto do código.
* **Interação:** O fluxo de execução funciona da seguinte forma: A classe `Facade` recebe as chamadas do EasyAccept, identifica os IDs e textos informados e delega a ação para o Manager correspondente. O Manager, por sua vez, acessa ou modifica os Models e retorna o resultado.

## 3. Padrões de Projeto Adotados

### Facade (Fachada)

* **Descrição Geral:** É um padrão de projeto que fornece uma classe centralizada para simplificar o uso de um sistema cheio de classes. A Fachada recebe as chamadas externas e distribui para as classes corretas trabalharem, escondendo a complexidade interna.
* **Problema Resolvido:** Resolve o problema de alto acoplamento. Sem ele, a ferramenta de testes (EasyAccept) precisaria instanciar e conhecer intimamente as diversas classes gerenciadoras do sistema para conseguir realizar tarefas simples, quebrando o princípio do encapsulamento.
* **Identificação da Oportunidade:** O uso do padrão Facade foi o design arquitetural proposto pelo professor para a disciplina. Essa escolha se mostrou ideal no contexto do projeto porque o EasyAccept envia requisições que envolvem várias partes do código ao mesmo tempo (por exemplo: para criar um pedido, o sistema precisa olhar para o Usuário e para a Empresa). A Facade atua como um maestro para organizar isso.
* **Aplicação no Projeto:** A classe `Facade` foi criada na raiz do projeto (`br.ufal.ic.myfood.Facade`). Ela cria as instâncias dos controladores (`UsuarioManager`, `PedidoManager`, etc.) e organiza a comunicação entre eles.
* **Exemplo Prático:** No método `adicionarProduto`, a `Facade` não faz validações com condições de controle lógico. Ela apenas busca o objeto no `ProdutoManager` usando o ID fornecido e repassa esse objeto diretamente para o método correspondente no `PedidoManager`. Dessa forma, um Manager não precisa interagir diretamente com o outro, mantendo o sistema bem modularizado.

---
*Nota: Os arquivos PDF do relatório e do Diagrama UML também se encontram anexados na raiz deste repositório.*
