# Games Vault

Aplicativo Android nativo para Armazenamento e acompanhamento de progresso de jogos, desenvolvido em **Kotlin** com **Android Views (XML)**.

Projeto prático desenvolvido para a disciplina de **Programação Mobile I**.

---

## Objetivo do Aplicativo

O **Games Vault** é uma biblioteca pessoal de jogos que permite aos usuários:
1. **Listar jogos:** Visualizar uma coleção de jogos com suas capas, gêneros e horas já jogadas.
2. **Visualizar detalhes completos:** Acessar informações como sinopse, desenvolvedora, publicadora, ano de lançamento, nota de avaliação e tags dinâmicas.
3. **Controlar progresso em tempo real:** Acompanhar uma barra visual de progresso de campanha com botões interativos para aumentar e decrementar horas jogadas.

---

## Como Rodar o Projeto

O projeto foi configurado com o **Gradle Wrapper** e não possui chaves de API nem arquivos `.env`.

### Pré-requisitos
* **Android Studio:** Versão Hedgehog (2023.1.1) ou superior.
* **JDK:** Java 11 ou superior.
* **Android SDK:** Suporte a `minSdk 24` (Android 7.0) e `targetSdk 37`.

### Passo a Passo
1. **Clonar o Repositório:**
   ```bash
   git clone https://github.com/Mayhemvc/GamesVault.git
   ```
2. **Abrir no Android Studio:**
   * Abra o Android Studio.
   * Selecione **File > Open...** (ou *Open* na tela inicial).
   * Navegue até a pasta clonada e selecione o diretório raiz do projeto.
3. **Sincronizar o Gradle:**
   * O Android Studio iniciará a sincronização automática das dependências que pode levar um tempo. Aguarde a barra de progresso concluir.
4. **Executar:**
   * Selecione um emulador ou dispositivo físico conectado com Android 7.0 ou superior.
   * Clique no botão **Run** (Shift + F10).

---

## Bibliotecas e Dependências Utilizadas

O projeto adota uma abordagem didática com **100% de dependências oficiais do ecossistema Android**, evitando bibliotecas de terceiros:

* **AndroidX Core KTX (`androidx.core:core-ktx`):** Extensões do Kotlin para APIs do sistema Android.
* **AndroidX AppCompat (`androidx.appcompat:appcompat`):** Suporte a componentes visuais e temas com retrocompatibilidade entre diferentes versões do Android.
* **Material Components for Android (`com.google.android.material:material`):** Componentes do **Material Design 3**, utilizados para `MaterialToolbar`, `FloatingActionButton`, `LinearProgressIndicator` e `MaterialDivider`.
* **RecyclerView (`androidx.recyclerview:recyclerview`):** Exibição eficiente e performática da lista de jogos na tela inicial com reciclagem de views em memória.
* **ConstraintLayout (`androidx.constraintlayout:constraintlayout`):** Suporte a layouts flexíveis.
* **ViewBinding (Nativo do Android Gradle Plugin):** Substitui o `findViewById` gerando classes de binding fortemente tipadas em tempo de compilação, prevenindo erros de `NullPointerException`.

---

## Arquitetura

* **Padrão Monolítico Didático:** A estrutura do aplicativo centraliza cada tela e seus adaptadores diretamente nos arquivos principais (`MainActivity.kt` e `DetailActivity.kt`), simplificando a navegação de código e o ciclo de vida.
* **Navegação com Intent Explícita:** A transição da tela de listagem para a tela de detalhes é realizada por meio de uma `Intent` explícita, transmitindo apenas o ID (`EXTRA_GAME_ID`). O objeto detalhado é resgatado a partir do repositório/mock data local.
* **Imutabilidade e Segurança de Tipos:** Modelos de dados representados por `data class` (`GameListItem` e `GameDetail`) com propriedades imutáveis (`val`). Manipulações de estado utilizam funções puras com o método `.copy()`.
* **Componentes Reutilizáveis:** O layout `badge_layout.xml` funciona como componente reutilizável, sendo usados via `<include>` na lista e inflado via `BadgeLayoutBinding.inflate()` na tela de detalhes.
