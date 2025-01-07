# MyFlickScreens 🎬📱

Bem-vindo ao **MyFlickScreens**, um aplicativo de avaliação de filmes e séries. Com o MyFlickScreens, você pode explorar, avaliar e discutir seus filmes e séries favoritos, além de interagir com outros usuários por meio de comentários e tópicos.

## ✨ Funcionalidades Principais

- **Explorar Filmes e Séries:**
  - Listas de filmes populares, mais bem avaliados, e atualmente em exibição.
  - Pesquise títulos diretamente usando a funcionalidade de busca.

- **Detalhes de Filmes:**
  - Acesse informações detalhadas de filmes e séries, como sinopse e avaliações.

- **Interatividade:**
  - Avalie e comente sobre filmes e séries.
  - Participe de discussões em tópicos na aba de comentários.

- **Perfil Personalizável:**
  - Edite suas informações de perfil e faça upload de uma imagem personalizada.

## 🛠️ Tecnologias Utilizadas

- **Kotlin**: Linguagem de programação principal.
- **Firebase**: Autenticação, Firestore para banco de dados em tempo real, e Storage para upload de imagens.
- **Retrofit**: Comunicação com APIs REST, incluindo a integração com o The Movie Database (TMDb).
- **TMDb API**: Fonte de dados sobre filmes e séries.
- **Glide e Picasso**: Carregamento e exibição eficiente de imagens.

## 🔧 Configuração do Ambiente

### Pré-requisitos

- Android Studio 
- API Key do TMDb
- Configuração do Firebase (arquivo `google-services.json`)

📂 Estrutura do Projeto
MainActivity: Gerencia a navegação entre os principais fragmentos (Home, Busca, Perfil).
HomeFragment: Exibe filmes populares e mais bem avaliados.
SearchFragment: Permite pesquisar títulos.
UserProfileScreen: Gerencia o perfil do usuário, incluindo upload de imagem.
SettingsActivity: Configurações, como alteração de dados de conta e logout.
AllMoviesActivity: Exibe uma lista de filmes filtrados (popular, mais bem avaliados, em exibição).
Documento do aplicativo: https://docs.google.com/document/d/1d8n2bDt-9Siz95G0q6QfccADsC7l1BhKY5CoBCRd5k4/edit?usp=sharing

🧑‍💻 Contribuidores
Nome do autor: Joey Alan
Contato: joeyalandev@gmail.com

📜 Licença
Este projeto está licenciado sob a MIT License.

