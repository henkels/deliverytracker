# deliverytracker
Delivery Tracker Repository

# Arquitetura do negócio
O delivery tracker é um produto que se destina a realizar o feedback de entregas de pacotes a serem realizadas a um recebedor. As entregas típicas são:
* Tele-Entrega
  * Comida (pizza, salgados, comida chinesa, etc);
  * Peças;
* Documentos;
* Compras na internet;

O produto é focado em realizar feedback no último trecho da entrega: quando a entrega toma o ultimo transporte (carro, caminhão, moto, ...) e se dirige ao endereço do consumidor para realizar a entrega.

Os atores envolvidos são:
* recebedor: quem recebe o pacote, geralmente um consumidor que está recebendo um produto ou pessoa autorizada a receber o pacote como por exemplo um vizinho, um porteiro;
* entregador: que realiza a entrega, um moto-boi, um motorista de van ou caminhão;
* enviador: quem está enviando um pacote, geralmente uma loja, um restaurante, uma fábrica, um escritório, ...
* transportadora: é a empresa contratada para entregar o pacote e podem haver de nenhuma, uma ou mais que uma empresas envolvidas na entrega;
* parceiro comercial: é uma empresa parceira do enviador que tem interesse na entrega do produto, como por exemplo o mercado livre, a máquina de vendas, etc... 

## Diferencias para o recebedor:
O produto tem os seguintes diferenciais do ponto de vista do recebedor:
* Feedback em tempo real sobre a localização do pacote;
* Atualização constante sobre a momento estimado da chegada do pacote(ETA - Estimated Time of Arrival);
* Feedback no momento ocorência sobre quem realizou e onde realizou-se um recebimento em seu nome;
* Autorização de outras pessoas para receber encomendas em seu nome;

## Diferencias para o entregador
O produto tem os seguintes diferenciais do ponto de vista do entregador:
* Geração simplificada da roteiro de entregas;
* Alteração de roteiro de entregas;
* Localização GPS/Google Maps do endereço de entrega; 
* Feedbeack em tempo real sobre a localização do recebedor;
* Automatização do processo registro da entrega no momento que ela ocorre;

## Diferencias para o enviador
O produto tem os seguintes deferencias do ponto de vista do enviador:
* Feedback em tempo real sobre a localização do pacote;
* Atualização constante sobre a momento estimado da chegada do pacote(ETA - Estimated Time of Arrival);
* Localização GPS/Google Maps do endereço de entrega; 
* Feedbeack em tempo real sobre a localização do recebedor;
* Economia para prestar feedback sobre a localização do pacote ao recebedor;
* Geração de estatísticas sobre as entregas;

## Diferencias para a transportadora
O produto tem os seguintes diferenciais do ponto de vista da transportadora:
* Feedback em tempo real sobre a localização GPS dos pacotes sobre seu controle;
* Atualização constante sobre a momento estimado da chegada do pacote(ETA - Estimated Time of Arrival);
* Localização GPS/Google Maps do endereço de entrega; 
* Feedbeack em tempo real sobre a localização do recebedor;
* Economia para prestar feedback sobre a localização do pacote ao enviador;
* Geração de estatísticas sobre as transportes realizados;

## Diferencias para o parceiro comercial
O produto tem os seguintes diferenciais do ponto de vista do parceiro comercial:
* Feedback em tempo real sobre a localização GPS dos pacotes de seu interrese;
* Atualização constante sobre a momento estimado da chegada do pacote(ETA - Estimated Time of Arrival);
* Localização GPS/Google Maps do endereço de entrega; 
* Feedbeack em tempo real sobre a localização do recebedor;
* Geração de estatísticas sobre as entregas;

## Modulos
O produto é formado pelos modulos que seguem.

### Receiver
É o principal módulo do produto. Tem uma versão mobile e outra WEB, sendo a versão mobile a principal. É o módulo que o recebedor usa. As principais para o funcionalidades deste módulo são:
* Feedback em tempo real sobre a localização do pacote é o próximo a ser entregue pelo entregador;
* Atualização constante sobre a momento estimado da chegada do pacote(ETA - Estimated Time of Arrival);
* Registro de recebimento;
* Autorização de outras pessoas para receber encomendas em seu nome;
* Histórico de pacotes já entregues;
* Chat com o entregador;

### Deliverer
É o módulo que o entregador usa. Apresenta apenas uma versão mobile. As principais funcionalidades deste módulo são:
* Visualização do roteiro de entregas;
* Alteração do roteiro de entregas;
* Localização GPS/Google Maps do endereço de entrega; 
* Feedbeack em tempo real sobre a localização do recebedor;
* Registro de entrega;
 * Para o recebedor;
 * Para outro entregador;
* Notificação de imprevistos;
* Pausa nas entregas;
* Definição de escala de paradas (almoço, intervalo, etc...);
* Chat com o recebedor e com a transportadora;

### Backoffice
É o modulo usado pele enviador, transportadora e parceiro comercial. Apresenta uma versão WEB e uma versão mobile, sendo a WEB a principal. As principais funcionalidades deste módulo são:
* Feedback em tempo real sobre a localização GPS/Google Maps dos pacotes sobre seu controle ou de seu interesse;
* Atualização constante sobre a momento estimado da chegada do pacote(ETA - Estimated Time of Arrival);
 * Quando o usuário for enviador ou parceiro comencial, o ETA dos pacotes é calculado em relação a entrega para o recebedor;
 * Quando o usuário for transportadora, o ETA dos pacotes é calculado em relação ao treho de sua responsabilidade que podem ser até o recebedor ou outra transportadora;
* Localização GPS/Google Maps do endereço de entrega; 
* Feedback em tempo real sobre a localização do recebedor;
* Quando o usuário for a transportadora, feedback em tempo real sobre as alterações realizadas nos roteiros de entrega, pausa nas entregas, sinalização de imprevistos, etc...
* Quando o usuário for a transportadora, geração e edição dos roteiros de entrega;
* Chat com o entregador e o recebedor.

# Análise de Dados

## Recebedor
* Email
* Nome/Apelido
* Lista de endereços
* Foto

## Entregador
* Email
* Nome/Apelido
* Foto

## Enviador
* Email
* Nome Fantasia
* Nome Comercial
* CNPJ
* Logo
* Site

## Transportadora
* Email
* Nome Fantasia
* Nome Comercial
* CNPJ
* Logo
* Site
* Lista de Veículos

## Parceiro Comercial
//TODO

## Endereço
* Localização
* CEP
* Pais
* Estado
* Cidade
* Rua
* N°

## Veículo
* Tipo
* Foto
* Placa
