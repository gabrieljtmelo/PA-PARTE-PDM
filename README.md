# Green Towers Web API

<h2 align="center">
  <img alt="Logo" src="./logo.jpg" />
</h2>

<h4 align="center"> 
	🚧  🚀 Em construção...  🚧
</h4>

<p align="center">
 <a href="#-sobre-o-projeto">Sobre</a> •
 <a href="#-layout">Layout</a> • 
 <a href="#-como-executar-o-projeto">Como executar</a> • 
 <a href="#-tecnologias">Tecnologias</a> • 
</p>

## 💻 Sobre o projeto

O projeto Green Towers surge pela necessidade de uma aplicação que centralize a comunicação, necessidades e agendamentos entre os condôminos e o administrador do condomínio (API 24)


## Sobre a Aplicação

### Login

Ao entrar na aplicação temos a activity de Login, onde serão diferenciados os tipos de utilizador pelo e-mail e senha.

![Login](screenshots/login.png)


### Tipos de Utilizadores

Existem 3 tipos de utilizadores na aplicação, cada um com funcionalidades e interfaces diferentes:

- Morador: Pode fazer Reservas de áreas comuns, enviar Tickets ao Admin(para reporta um problema qualquer), visualizar os próprios tickets enviados, visualizar a resposta desses tickets, casos haja uma, visualizar os avisos globais, visualizar os avisos individuais destinados a esse morador e visualizar suas informações pessoais.
- Admin: Pode enviar avisos globais (a todos os moradores), avisos individuais(aviso destinado a somente um morador), responder tickets(efetuados por moradores), visualizar todas as reservas dos moradores e registar um novo Rececionista ou Morador.
- Rececionista: Pode ligar e desligar as Regas do condomínio através da aplicação, enviar um aviso individual, registar um visitante e visualizar a lista de todos os moradores.
 


### Morador a utilizar a aplicação

Após efetuar o login como um morador, temos a página inicial de morador, contendo uma listView dos avisos globais. Após carregar em um dos avisos, o morador é direcionado para uma activity contendo os detalhes daquele Aviso.
Na barra inferior da aplicação, temos 5 icones, que são respectivamente:
- Icone para a Página inicial (página atual)
- Icone para a Página inicial de Reservas
- Icone para a Página incial de Tickets
- Icone para a Página inicial de Avisos
- Icone para a Página de Perfil do Morador

![](screenshots/morador/morador_pagina_inicial.png)


#### Página Inicial de Reservas

Clicando no botão minhas reservas, temos uma listView das reservas efetuadas. Clicando no botão nova reserva temos a opção de escolher piscina ou churrasqueira,
escolhendo um ou outro, um DatePickerDialog aparece na tela para escolhermos a data para qual queremos efetuar a reserva.

![](screenshots/morador/morador_pagina_inicial_reservas.png)


#### Página Inicial de Tickets

Clicando no botão meus tickets, temos uma listView dos tickets efetuados. É possível carregar no ticket para visualizar os seus detalhes (como se há ou não resposta daquele ticket). Clicando no botão novo ticket, é possível enviar um novo ticket para o admin.

![](screenshots/morador/morador_ticket_pagina_inicial.png)


#### Página inicial de Avisos
Na página inicial uma listView dos avisos globais e uma dos individuais. Assim como nos tickets, é possível visualizar os seus detalhes carregando no item da listtView. 

![](screenshots/morador/morador_avisos_pagina_inicial.png)

#### Página de Perfil do Morador
Clicando em cada botão, a respectiva informação aparece.

![](screenshots/morador/morador_pagina_perfil.png)


### Admin utilizando a aplicação

Após efetuar o login como um Admin, temos a página inicial de Admin, contendo uma listView das Reservas efetuadas por todos os moradores, e uma listView dos tickets não respondidos.
Na barra inferior da aplicação, temos 5 icones, que são respectivamente:
- Icone para a Página inicial (página atual)
- Icone para a Página inicial de Reservas
- Icone para a Página incial de Tickets
- Icone para a Página inicial de Avisos
- Icone para a Página sobre os moradores

![](screenshots/admin/admin_pagina_inicial.png)


#### Página inicial de Reservas
Página contendo uma listView de todas as reservas efetuadas por todos os moradores

![](screenshots/admin/admin_reservas_pagina_inicial.png)

#### Página inicial de Tickets
Temos uma activity com duas listViews, uma de tickets não respondidos e outra com tickets respondidos. Clicando em ticket da lista dos não respondidos, somos redirecionados para uma página de resposta do ticket.

![](screenshots/admin/admin_ticket_pagina_inicial.png)

#### Página inicial de Avisos
Clicando em meus avisos, temos duas listViews, uma de avisos Globais e outra de individuais. Clicando em Novo aviso Global, somos redirecionados para uma página de criação de um aviso global. Clicando em Novo Aviso Individual, somos redirecionados para uma página de criação de um novo aviso global, onde se pode selecionar um morador com a lista de todos os moradores.

![](screenshots/admin/admin_avisos.png)

#### Página sobre os moradores
Nesta página é possível visualizar todos os Moradores registados e registar um novo utilizador após clicar no quinto icone(da esquerda para a direita)

![](screenshots/admin/admin_moradores.png)


### Rececionista utilizando a aplicação
Após efetuar o login como um Rececionista, temos a página inicial das regas, contendo uma listView das Regas efetuadas, e um botão para ligar e desligar a rega.
Na barra inferior da aplicação, temos 3 icones, que são respectivamente:
- Icone para a Página das Regas (página atual)
- Icone para a Página inicial de Visitantes
- Icone para a Página incial de Avisos Individuais

![](screenshots/rececionista/Rececionista_avisos.png)

#### Página inicial de Visitantes
Temos uma listView de todos os visitantes já registados. Clicando em Registar Visitante, é possível registar um novo visitante.

![](screenshots/rececionista/Rececionista_visitantes.png)

#### Página inicial de Avisos Individuais
Temos uma listView com todos os avisos individuais enviados e um botão para criar um novo aviso Individual (idêntico ao do Admin)

![](screenshots/rececionista/Rececionista_avisos.png)

---

## 🎨 Layout

O layout da aplicação está disponível no Figma:

<a href="https://www.figma.com/file/Cx5744SNmQVbRGQlbtIanh/PDM%2FPA%2FISI?type=design&node-id=0-1&mode=design&t=qIiIiGcWoafJoabu-0">
  <img alt="Layout da app" src="data:image/png;base64,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">
</a>

---

## 🚀 Como executar o projeto

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
[Git](https://git-scm.com), [Android Studio](https://dotnet.microsoft.com/en-us/download/visual-studio-sdks)

🧭 Rodando a aplicação web API

bash
Basta abrir o diretório da aplicação android e fazer build


---

## 🛠 Tecnologias

As seguintes ferramentas estão sendo usadas na construção do projeto:

- **[Kotlin](https://dotnet.microsoft.com/en-us/download/dotnet/8.0)**
- **[Android Studio](https://learn.microsoft.com/en-us/ef/)**
- **[Android](https://www.docker.com/)**
