# Requisitos do Sistema

## 1. Requisitos Funcionais

### RF01 — Cadastrar paciente
O sistema deve permitir aos utilizadores autorizados cadastrar novos pacientes.
### RF02 — Identificar paciente
O sistema deve permitir localizar um paciente já cadastrado através dos seus identificadores e outros dados de identificação.
### RF03 — Gerar identificador único do paciente
O sistema deve gerar automaticamente um identificador único para cada novo paciente cadastrado.
### RF04 — Actualizar dados do paciente
O sistema deve permitir a actualização dos dados cadastrais do paciente de acordo com o nível de acesso do utilizador.
### RF05 — Registar atendimento
O sistema deve permitir registar cada atendimento realizado ao paciente, incluindo a data, queixas apresentadas, condições clínicas e demais informações relevantes.
### RF06 — Registar triagem
O sistema deve permitir ao profissional responsável pela triagem registar a classificação de prioridade e os sinais vitais do paciente.
### RF07 — Registar diagnóstico
O sistema deve permitir ao médico registar o diagnóstico associado ao atendimento.
### RF08 — Registar prescrição médica
O sistema deve permitir ao médico registar a prescrição médica associada ao atendimento.
### RF09 — Registar observações clínicas
O sistema deve permitir aos profissionais autorizados registar observações relevantes durante o atendimento.
### RF10 — Consultar histórico clínico
O sistema deve permitir aos profissionais autorizados consultar o histórico clínico do paciente.
### RF11 — Visualizar atendimentos anteriores
O sistema deve permitir visualizar os atendimentos anteriores associados ao paciente, incluindo diagnósticos, prescrições, exames e observações registadas.
### RF12 — Agendar consulta
O sistema deve permitir aos utilizadores autorizados agendar consultas, associando o paciente ao profissional responsável, data e hora.
### RF13 — Validar dados de cadastro e atendimento
O sistema deve validar os dados introduzidos e impedir o registo quando informações obrigatórias estiverem ausentes ou forem inválidas.

## 2- Requisitos não funcionais

### RNF01 — Autenticação
O sistema deve exigir autenticação dos utilizadores antes de permitir o acesso às suas funcionalidades.
### RNF02 — Controlo de acesso
O sistema deve restringir o acesso às funcionalidades e informações de acordo com o perfil do utilizador.
### RNF03 — Protecção dos dados
Os dados clínicos dos pacientes devem ser protegidos contra acesso, alteração ou eliminação não autorizados.
### RNF04 — Persistência dos dados
As informações registadas devem permanecer armazenadas após o encerramento da aplicação.
### RNF05 — Backup
O sistema deve possuir um mecanismo de cópia de segurança dos dados.
### RNF06 — Integridade
O sistema deve garantir que os atendimentos, diagnósticos e prescrições permaneçam associados ao paciente correcto.
### RNF07 — Tempo de resposta
As operações comuns, como pesquisa de pacientes e consulta do histórico, devem apresentar um tempo de resposta adequado ao contexto de utilização.

## 3. Regras de Negócio

### RN01 — Cadastro de pacientes
Apenas utilizadores com perfil autorizado podem cadastrar novos pacientes.
### RN02 — Unicidade do paciente
Não é permitido criar um novo registo para um paciente que já esteja cadastrado.
### RN03 — Identificador único
Cada paciente deve possuir um identificador único no sistema.
### RN04 — Identificação através do BI
Quando disponível, o BI deve ser utilizado como um dos identificadores do paciente e não pode estar associado a mais de um paciente.
### RN05 — Paciente sem BI
Quando o paciente não possuir BI no momento do cadastro, deverá ser utilizado um identificador interno único juntamente com os dados de identificação disponíveis.
### RN06 — Atendimento associado ao paciente
Todo atendimento registado deve estar associado a um único paciente.
### RN07 — Histórico
Todos os atendimentos realizados devem permanecer associados ao histórico do respectivo paciente.
### RN08 — Não eliminação do histórico
Um atendimento concluído não deve ser eliminado do histórico do paciente através das operações normais do sistema.
### RN09 — Óbito
Em caso de óbito, o registo do paciente não deve ser eliminado. O estado do paciente deve ser actualizado para indicar o óbito.
### RN10 — Triagem
A triagem deve ser realizada e registada exclusivamente por utilizadores com permissão para exercer essa função.
### RN11 — Prioridade
Os pacientes devem receber uma classificação de prioridade de atendimento de acordo com o resultado da triagem.
### RN12 — Atendimento de emergência
Em situações de emergência, o atendimento clínico deve ter prioridade sobre o preenchimento completo dos dados administrativos, podendo estes ser completados posteriormente.
### RN13 — Consulta do histórico
O acesso ao histórico clínico deve ser permitido apenas a utilizadores autorizados.
### RN14 — Alteração de dados
A alteração dos dados do paciente deve estar condicionada às permissões do perfil do utilizador.

## 4. Utilizadores / Actores do Sistema
1. Recepcionista
2. Enfermeiro
3. Médico
4. Administrador

## 5. Casos de Uso


https://viewer.diagrams.net/?tags=%7B%7D&lightbox=1&highlight=0000ff&edit=_blank&layers=1&nav=1&title=Hospital_CasoDeUso(TrabalhoSemestral).drawio&dark=auto#R%3Cmxfile%3E%3Cdiagram%20name%3D%22P%C3%A1gina-1%22%20id%3D%22r3QU7_RVWZQAv9PuleW3%22%3E7V1bc9o4GP0t%2B8DM7kMYfIdHIGna2TKTKek2fRS2Ampty5VFIP31K%2FmCsexgx7GI05jJJJasiznf0XeTIANt7u2vCQg2C%2BxAd6COnP1Auxyo7KUr7A%2BveYxrxuNRXLEmyImrlKxiiX7DpDJttkUODHMNKcYuRUG%2B0sa%2BD22aqwOE4F2%2B2T1287MGYA0LFUsbuMXab8ihm%2BRdqFZW%2FxGi9SadWTEn8R0PpI2TdxJugIN3R1Xa1UCbE4xpfOXt59Dl4KW4xP0%2BPHH38GAE%2BrROhx9f4YUZTO8uH1eXO%2Frf9dcfq%2BVFyShJVUgfUwzYkwf8cuu5U5tiMtBmD5BQxFD6DFbQvcEhogj7rMkKU4q9owZTF635DYoDVruhnssKCrvEW%2BoiH84PchuxynjqB%2BBuk6mv%2FHtIPIgI%2FvsLtGFgs2lQSME%2FSUs2C9wfPXfy1q8h9iAlj6zJ5kg6ZiKKXSZJLalKBknZmbBVSXuAhEbrw8AZ0uwiAfsZwKtvB%2FhbghjLvTNBfii3DrnWccgXg7k2mE4cZOPzQK2PZEGtdxzqqeMhrkcIU8nkPGAbqiEJbKMO2ARvfQc6CSC7DaJwGQCb390xy50HsYBXM4jUIkYH8586CCNB4eqyKGnWQYm9PZqHogwpxhv8k1HM5Zy99LHP7s3ukesKVSAhqc1mhKSEvR5yHD71LC%2BdAvxLRlXoAe5GQfbrIw4DRIHbSCpaUSiKlReKqZ3LClp1hMLGYd4erKZtpFA%2B%2BSHzGJ8isg0cwJc9iWa1EZdMIxjHJTCqArctAUZplm0cjwudgi9bxBVviQ2rVfcx%2Fr4z5Z40Z7ILwhDZedjz5OUPsUw6Y0I3eI194F5ltTP2SOTxjjcfGmnxezJWVLjcp2NFpcfcsqCArCGt5pSgvU5L00gkQ6ALKHrIo3gkYuOEBJMZbjBikB9IoCiCGbCEIWKBJL2O3XdxIFO0J8JAMTCFgSJCHd52c45NWuRYyRp%2FHscyBo2UYwqxsqVX0Cgq3UCCGCJcK9dkbbdYZelDNUcH5pAMJ2YzajFFNRwdvRQrP7QyGU6OX9ZZiZcuoPMZCQLXPMbkNoKThZsMfhlnGRwUuelW9FvD3CbjMxgTzdDzQhH1SGvGRFFaXOnq%2BazJBVcGal4bmONJE21Q09KkzOyIUtAEX1qbNDQ1miEMJDrlsle82kVbw%2FmlGXl%2BaZZMY9NxAjIQweNRg4DzIXyanxND1GAM0NNEVA5NXthrMhmaqsDV%2BPn%2F9LVUK%2BvVpvWcUoJWW8St5xz74dalIJRlFXUR3VOkfZlV1FvUSdr5rCJbCqpgFHWtKtJ6kVHU3pJOYjG5qCt0tVLDlPRiCqKql26oQ2NS0atCL4nhpC6qk6bhZEHBydZLtbKWbeqlL5lXf0NgaJOCI%2B8dEvBgoJrA43P4qzDIRmtdgRm6IE9NmgIzu6TADuopr5qMKsXUmudkvKaWKq5GVaCBIWwW1F7WQs5RHwkDyV7WZ8%2Fopj4GX9cbtsD5Gp5pJNpEG81d5MdXZwnSTWE1G4qs1ZxO3LHVzIyZKTgbmffxOiHSq6aDn%2B2OWEV3xKx2R4q9DKOyl6nrQ02p6PVcd0TcKWrqjujiypGst9Q2k17yt1CK5Fe2nhUE3v47xAv3dvHtfvdxl%2BY%2B3gb3hfSAqQjHmcTAeqy%2FrL2Wa19J9MlQL0zIYqtmdDcFupvy6L78Or6%2Bvb27%2B7VeKtt%2F8e1FuFjUOzLQ0c3wgfbBxg%2FIUSaLvxoZ8pIdcDNvxy8EL8oSg6MmdvzUCn0dd8kBDuY7G%2BBwGoYteJzudUAvYGESIAggafkbS0izGdKAVrsA9BqSCMxRXMzizQz0w7E7aZiPBczNNk53lGKudcmmHvuowp6xKXfPuNJMq2%2FYTFsVZnc80k%2B1rzC7ulXB1boG11DPZnBLRVzrQGSF0gFhEB9pvEd7zuuWjulZJfbQEs4yqkJgO24jTVUKVK38QT3PBDMg7t1IQWyYbwH9Gr6FarrRyEpuQvPXlp%2FSn3mMJMi%2FiA6dTlmLUbCPRknvs6s1%2F3uJwJqA6MDeJdfqc66fYJiOzoDhE8RtG8lML4rssNJTxiv5hTpu48BAqcjGdUQW7pDngtg5xD5N9SaXgr1BrvMZPOIt7x1SYP9MS7MNJug3aw8OlpctVJp8TEQb5Vosec9kTOa5sDY36SMpQtUC7HMNP4MwPStsY9cFQYhWbmraY6nPkiPIjc7Pvvy8sVJm2XVhlQq7YcqojfxTqcwnJTI%2FwY3i2n1u2ODCe3oqaAh5bs9ff46aXepZzZcEQ%2F0JjZDYO%2FZsxoz9MMDmPA9tsGeds7KSldkPb04od%2B2YKFH0ziGjzg5y%2BjDlQgEFqwOxn8uSTxR6rEZpRJCyc72C1pamtEuPaPV0aIcOzax6DTpI8%2FuVMj%2B%2Fp0M7dNBk0UFejqPMQejp0HHtcEgbtM%2BH3nl4i3xo42hVOR9q5ebedwCxgI0%2FFlonctDFT3zJixxKDySeokW%2F%2FP%2Fs2KEsNdbzoevmQF7wULY32fPh%2FUYPZR%2Fc7vnQdXshMXwo2yLoCdF5QkiLH1Km9fHDqS%2FwiLeeViTbd4prHPSQVmXf%2BXO0U3V0X1r4Yar5vSqJ4UfpGYRTrOq1Rxe0h7TwQy1LPvR8eLfhh1o3PdHz4V2EH2rd9ETPhy7ZC3nhh1o3P9ETolOEkBd%2B1PpmufcdfuS%2Bu1VaGDFOc0VnCCPqJiFSdvRaoAtaQF4YUfeMQ8%2BH9xFG1D3j0PPhXYQR6dw9H96UvZAXRmi185A9IbpEiAZhBCtm%2F%2BUh%2FkBR9r8ytKv%2FAQ%3D%3D%3C%2Fdiagram%3E%3C%2Fmxfile%3E

## Observações Importantes Para O desenvolvimento
1. O sistema deve manter um único registo por paciente e associar os vários atendimentos a esse registo.
2. O BI pode funcionar como identificador oficial quando disponível, mas não precisa necessariamente ser a chave primária da base de dados. O sistema pode possuir um identificador interno próprio.
3. Dados administrativos e dados clínicos devem possuir diferentes níveis de acesso.
4. Regras relacionadas com menores, consentimento, emergência e outros procedimentos médicos devem ser confirmadas com a instituição ou legislação aplicável antes de serem transformadas em regras definitivas do sistema.
5. A estrutura das tabelas, classes Java, DAO, Service, DTO e demais decisões técnicas devem ser definidas depois da modelação do domínio e dos casos de uso.