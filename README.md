# Relatório Final do Projeto - MyFood
---

**Discente:** Diêgo de Araujo Correia   

**UML do Projeto (AB1):** https://drive.google.com/file/d/1dxjeuOuJi9NW2dDOQozZ_-yjm7DzAPOZ/view?usp=sharing

**UML do Projeto Completo (AB2):** https://drive.google.com/file/d/1-xQhMmDTulcTCvt44_k_CVZbJW9MNCp-/view?usp=sharing


## 1. Descrição Geral do Design Arquitetural

O design arquitetural do sistema foi construído sobre os pilares fundamentais da Programação Orientada a Objetos (POO), com o objetivo de criar um código limpo, organizado e fácil de manter. A arquitetura foi dividida em camadas lógicas para garantir que cada classe tenha uma única responsabilidade.

Para estruturar o sistema, foram aplicados os seguintes conceitos de POO:

- **Abstração e Herança:** para evitar repetição de código e modelar o sistema de forma inteligente, foram utilizadas classes abstratas como “moldes” gerais (`Usuario` e `Empresa`). A partir delas, foram criadas classes filhas que herdam seus atributos básicos e adicionam características próprias. Por exemplo, `Cliente`, `Dono` e `Entregador` herdam de `Usuario`, enquanto `Restaurante`, `Mercado` e `Farmacia` herdam de `Empresa`.
- **Polimorfismo:** para evitar lógicas engessadas e verificações ruins no código, como o uso de `instanceof` para descobrir o tipo de uma classe, foi aplicado polimorfismo. O sistema consegue tratar qualquer tipo de empresa simplesmente como `Empresa`, mas, quando uma ação específica é chamada, cada classe filha responde da sua própria maneira.
- **Encapsulamento:** os dados do sistema foram protegidos rigorosamente. As classes do pacote `Models` escondem seus atributos usando `private` e só permitem acesso e modificação por meio de métodos controlados. Além disso, foram utilizadas cópias das listas originais para garantir que listas internas não sejam modificadas acidentalmente por outras partes do sistema.
- **Alta Coesão e Baixo Acoplamento:** as classes que apenas guardam dados foram separadas das classes que pensam e tomam decisões. Os `Managers` cuidam das regras de negócio e do gerenciamento das coleções (`Map`), o que gera alta coesão. Toda a comunicação com o exterior passa exclusivamente por uma única classe central, a `Facade`, garantindo baixo acoplamento. Se o sistema interno mudar, a interface externa não quebra.

## 2. Principais Componentes e Suas Interações

A arquitetura está dividida em três pacotes principais e complementares: `Models`, `Services` e `Utils`.

### 2.1 Models

Classes básicas e estruturais que representam os objetos do mundo real no sistema. O projeto aplica herança para especializar comportamentos sem duplicação de código. Possuímos as classes abstratas `Usuario` (mãe de `Cliente`, `Dono` e `Entregador`) e `Empresa` (mãe de `Restaurante`, `Mercado` e `Farmacia`), além das entidades independentes `Produto`, `Pedido` e a recém-adicionada `Entrega`. Elas usam encapsulamento estrito para proteger seus atributos e servem apenas para armazenar o estado, sem realizar lógicas complexas de validação inter-classes. 

### 2.2 Services (Managers)

Atuam como os “cérebros” de cada domínio. O sistema é composto por `UsuarioManager`, `EmpresaManager`, `ProdutoManager`, `PedidoManager` e `EntregaManager`. Eles gerenciam os objetos em memória utilizando coleções estruturadas do tipo `Map`, controlam a geração de IDs únicos e aplicam todas as validações de regras de negócio. Para proteger os dados originais, os `Managers` utilizam retornos encapsulados, como cópias em coleções, ao expor informações. 

### 2.3 Utils

Contém a classe `PersistenciaXML`, responsável exclusivamente por converter o estado do sistema em disco, salvando e carregando dados em arquivos XML, isolando essa complexidade de I/O do restante do código. 

### 2.4 Interação entre os componentes

O fluxo de execução segue um pipeline claro: a classe `Facade` recebe as chamadas externas do script do EasyAccept, traduzindo parâmetros primitivos. Em seguida, a `Facade` delega a ação de forma orquestrada para os `Managers` correspondentes. O `Manager`, por sua vez, acessa e modifica os `Models` ou cruza dados com outros `Managers` de forma segura, e retorna o resultado final ao chamador. 

## 3. Padrões de Projeto Adotados

### I. Facade

#### Descrição Geral

É um padrão de projeto estrutural que fornece uma interface unificada e centralizada para simplificar o uso de um subsistema complexo contendo diversas partes. A Fachada atua como um “ponto de entrada”, recebendo requisições externas e coordenando as classes internas corretas para realizarem o trabalho, blindando o cliente da complexidade estrutural. 

#### Problema Resolvido

Resolve o problema de alto acoplamento. Sem ele, a ferramenta de testes (EasyAccept) precisaria instanciar e conhecer intimamente as diversas classes gerenciadoras do sistema e suas interdependências para conseguir realizar tarefas simples, quebrando o princípio do encapsulamento e gerando um código espaguete. 

#### Identificação de Oportunidade

Essa escolha se mostrou ideal no contexto do projeto porque requisições logísticas complexas exigem o cruzamento de dados. Por exemplo, para criar uma `Entrega`, o sistema precisa validar o estado do `Pedido` e checar a disponibilidade e validação do `Entregador`. A `Facade` atua perfeitamente como esse maestro orquestrador.

#### Aplicação no Projeto

A classe `Facade` foi alocada na raiz do projeto (`br.ufal.ic.myfood.Facade`). Ela inicializa as instâncias dos controladores/managers e organiza o tráfego. Como exemplo prático, no método `criarEntrega`, a `Facade` não manipula instâncias de objetos nativos; ela apenas invoca `pedidoManager.getEstado()` e `userManager.isEntregadorValido()` e, se tudo estiver correto, delega a criação ao `EntregaManager`. Um `Manager` nunca interage ou se acopla diretamente ao outro.

### II. Controller / Pure Fabrication (GRASP)

#### Descrição Geral

Padrões GRASP (General Responsibility Assignment Software Patterns) indicam que a lógica de negócio principal não deve estar acoplada nem à interface (`Facade`) e nem aos objetos de dados crus (`Models`). Cria-se uma classe intermediária (“fabricada puramente”) para gerenciar essas entidades.

#### Problema Resolvido

Evita o anti-padrão “God Class” (Classe Deus). Se a `Facade` concentrasse toda a lógica de negócio — validar, salvar em mapa, manipular os modelos — ela se tornaria gigantesca, difícil de manter e violaria o princípio da Responsabilidade Única (SRP).

#### Identificação de Oportunidade

Foi identificada a necessidade de dividir as lógicas massivas de validação do domínio do MyFood em setores específicos. Cada entidade vital precisava de um ambiente seguro para gerenciar seu próprio ciclo de vida.

#### Aplicação no Projeto

O padrão é a base do pacote `services`. Foram criados controladores especialistas: `UsuarioManager` gerencia apenas dados do usuário; `PedidoManager` lida apenas com o ciclo de preparo e fechamento de pedidos; e assim por diante. Essa delegação modularizada permitiu, por exemplo, que as regras complexas de herança de empresas — como horários diferenciados para `Mercado` e taxa de plantão 24h para `Farmacia` — ficassem isoladas no `EmpresaManager`.
