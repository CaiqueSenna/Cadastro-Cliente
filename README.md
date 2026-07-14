# Cadastro de Clientes - Android 🏢👤

Este é um aplicativo Android robusto para gerenciamento de clientes, desenvolvido seguindo as melhores práticas de desenvolvimento moderno, como a arquitetura **MVVM (Model-View-ViewModel)**. O projeto permite o cadastro completo de Pessoas Físicas (PF) e Jurídicas (PJ), com integração a APIs externas e armazenamento local.

## 🚀 Funcionalidades

- **Gerenciamento Completo (CRUD)**: Listar, cadastrar, editar e excluir clientes.
- **Diferenciação PF/PJ**: Campos dinâmicos que se ajustam dependendo do tipo de pessoa selecionada.
- **Busca de CNPJ Automática**: Integração com a API **ReceitaWS** para preenchimento automático de dados cadastrais e endereço através do CNPJ.
- **Persistência de Dados**: Armazenamento local utilizando **Room Database**, garantindo que os dados fiquem salvos no dispositivo.
- **Busca em Tempo Real**: Filtro na tela principal que permite buscar por Nome, Nome Fantasia ou CPF/CNPJ.
- **Validação e Segurança**: Bloqueio de cadastros duplicados (impede cadastrar o mesmo CPF/CNPJ duas vezes).
- **Máscaras de Entrada**: Formatação automática para campos de CPF, CNPJ e CEP.
- **Interface Responsiva**: Uso de `NestedScrollView` e `ConstraintLayout` para garantir que o app funcione bem em diferentes tamanhos de tela.

## 🛠 Tecnologias Utilizadas

- **Linguagem**: [Kotlin](https://kotlinlang.org/)
- **Arquitetura**: MVVM (Model-View-ViewModel)
- **Banco de Dados**: [Room](https://developer.android.com/training/data-storage/room)
- **Rede/API**: [Retrofit](https://square.github.io/retrofit/) & [Gson](https://github.com/google/gson)
- **Injeção de Dependência/Componentes**: ViewModel, LiveData, ViewBinding.
- **UI/Layout**: Material Design Components, ConstraintLayout, RecyclerView, CardView.
- **Assincronismo**: Kotlin Coroutines.

## 🏗 Estrutura do Projeto

```text
com.caiquesenna.cadastrocliente/
├── adapter/      # Gerenciamento da lista (RecyclerView)
├── api/          # Configuração Retrofit e interfaces de API
├── data/         # Configuração do banco de dados Room (DAO/Database)
├── model/        # Classes de dados (Entities)
├── repository/   # Camada de abstração de dados (Local e Remoto)
├── ui/           # Activities e controle de interface
├── utils/        # Máscaras, validadores e formatadores
└── viewmodel/    # Lógica de negócio e ponte entre UI e Dados
```

## 🌐 API Utilizada
A busca de dados de empresas é realizada através da API pública da [ReceitaWS](https://www.receitaws.com.br/).

---
Desenvolvido por **Caique Senna** 🚀

<p align="center">
  <img src="https://github.com/user-attachments/assets/9a5ca4dc-42a2-4315-9aaa-10dc3d39c5e3" width="245"/>
  <img src="https://github.com/user-attachments/assets/c995c444-162b-48a5-9715-71800307bc8e" width="250"/>
  <img src="https://github.com/user-attachments/assets/3a9d57cf-a171-47b4-8be4-ac6afc6f732d" width="250"/>
</p>





