package com.fernandoproject.hamburgueria.controller;

// Importação de bibliotecas necessárias para o funcionamento do controlador
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fernandoproject.hamburgueria.dao.CompraDao;
import com.fernandoproject.hamburgueria.dao.ItensCompraDao;
import com.fernandoproject.hamburgueria.dao.PratoDao;
import com.fernandoproject.hamburgueria.dao.UsuarioDao;
import com.fernandoproject.hamburgueria.model.Compra;
import com.fernandoproject.hamburgueria.model.ItensCompra;
import com.fernandoproject.hamburgueria.model.Prato;
import com.fernandoproject.hamburgueria.model.Usuario;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpSession;

@Controller // Indica que esta classe é um controlador Spring
public class CarrinhoController {

    // Injeção de dependências dos repositórios necessários
    @Autowired
    private PratoDao pratoRepositorio;

    @Autowired
    private UsuarioDao usuarioRepositorio;

    @Autowired
    private CompraDao compraRepositorio;

    @Autowired
    private ItensCompraDao itensCompraRepositorio;

    @Autowired
    private HttpSession session; // Usado para gerenciar sessões de usuário

    private Usuario usuario; // Armazena o usuário logado

    // Método para calcular o total da compra
    private void calculaTotal(List<ItensCompra> itensCompra, Compra compra) {
        compra.setValorTotal(0.0); // Inicializa o valor total da compra
        for (ItensCompra it : itensCompra) {
            // Adiciona o valor total de cada item à compra
            compra.setValorTotal(compra.getValorTotal() + it.getValorTotal());
        }
    }

    // Método para buscar o usuário logado na sessão
    private void buscarUsuarioLogado() {
        String user = session.getAttribute("clienteLogado").toString(); // Obtém o nome do usuário logado
        usuario = usuarioRepositorio.findByUser(user); // Busca o usuário no repositório
    }

    // Endpoint para confirmar a compra
    @PostMapping("/finalizar/confirmar")
    public ModelAndView confirmarCompra(String formaDePagamento, String formaDeEntrega,@Nullable String endereco, RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView(); // Cria um objeto ModelAndView

        // Verifica se os dados necessários foram preenchidos
        if (formaDePagamento.isEmpty() || formaDeEntrega.isEmpty() || (formaDeEntrega.equals("Entrega") && endereco.isEmpty())) {
            // Se faltam dados, adiciona uma mensagem de erro e redireciona para a página de finalização
            redirectAttributes.addFlashAttribute("erroCompra", "Para efetuar a compra, escolha uma opção de pagamento e de retirada!");
            mv.setViewName("redirect:/finalizar");
            return mv; // Retorna o ModelAndView
        }

        // Recupera a compra da sessão
        Compra compra = (Compra) session.getAttribute("compra");
        // Preenche os dados da compra
        compra.setUsuario(usuario);
        compra.setFormaPagamento(formaDePagamento);
        compra.setFormaEntrega(formaDeEntrega);
        if (endereco != null) { 
            compra.setEndereco(endereco); 
        }
        // Salva a compra no repositório
        compraRepositorio.saveAndFlush(compra);

        // Recupera os itens da compra da sessão
        @SuppressWarnings("unchecked")
        List<ItensCompra> itensCompra = (List<ItensCompra>) session.getAttribute("itensCompra");
        for (ItensCompra it : itensCompra) {
            // Associa cada item à compra e salva no repositório
            it.setCompra(compra);
            itensCompraRepositorio.saveAndFlush(it);
        }
        compra.setItensCompra(itensCompra); // Define os itens da compra
        compraRepositorio.saveAndFlush(compra); // Salva novamente a compra
        session.setAttribute("compra", null); // Limpa a sessão
        session.setAttribute("itensCompra", null); // Limpa os itens da compra na sessão

        // Adiciona uma mensagem de sucesso e redireciona para a página inicial
        redirectAttributes.addFlashAttribute("sucessoCompra", "Compra efetuada com sucesso!");
        mv.setViewName("redirect:/");

        return mv; // Retorna o ModelAndView
    }

    // Endpoint para exibir a página de finalização da compra
    @GetMapping("/finalizar")
    public ModelAndView finalizarCompraPage(RedirectAttributes redirectAttributes) {
        ModelAndView mv = new ModelAndView(); // Cria um objeto ModelAndView
        @SuppressWarnings("unchecked")
        List<ItensCompra> itensCompra = (List<ItensCompra>) session.getAttribute("itensCompra"); // Recupera os itens da compra
        if (itensCompra == null ) {
            itensCompra = new ArrayList<>(); // Inicializa a lista se não houver itens
        }

        // Verifica se há itens na compra
        if (itensCompra.isEmpty()) {
            // Adiciona uma mensagem de erro e redireciona para o carrinho
            redirectAttributes.addFlashAttribute("erroFinalizar", "Para finalizar um pedido é necessário ter pelo menos um item ao carrinho!");
            mv.setViewName("redirect:/carrinho");
            return mv; // Retorna o ModelAndView
        }

        // Recupera a compra da sessão
        Compra compra = (Compra) session.getAttribute("compra");
        if (compra == null) {
            compra = new Compra(); // Inicializa uma nova compra se não houver
        }

        buscarUsuarioLogado(); // Busca o usuário logado
        mv.addObject("cliente", usuario); // Adiciona o usuário à ModelAndView
        calculaTotal(itensCompra, compra); // Calcula o total da compra
        mv.addObject("compra", compra); // Adiciona a compra à ModelAndView
        mv.setViewName("pages/finalizar-compra"); // Define a view a ser renderizada
        mv.addObject("listaItens", itensCompra); // Adiciona a lista de itens à ModelAndView
        return mv; // Retorna o ModelAndView
    }
    

    // Endpoint para exibir a página do carrinho
    @GetMapping("/carrinho")
    public ModelAndView carrinhoPage() {
        @SuppressWarnings("unchecked")
        List<ItensCompra> itensCompra = (List<ItensCompra>) session.getAttribute("itensCompra"); // Recupera os itens da compra
        if (itensCompra == null) {
            itensCompra = new ArrayList<>(); // Inicializa a lista se não houver itens
        }
        Compra compra = (Compra) session.getAttribute("compra"); // Recupera a compra da sessão
        if (compra == null) {
            compra = new Compra(); // Inicializa uma nova compra se não houver
        }

        ModelAndView mv = new ModelAndView(); // Cria um objeto ModelAndView
        calculaTotal(itensCompra, compra); // Calcula o total da compra
        mv.addObject("compra", compra); // Adiciona a compra à ModelAndView
        mv.setViewName("pages/carrinho"); // Define a view a ser renderizada
        mv.addObject("listaItens", itensCompra); // Adiciona a lista de itens à ModelAndView
        return mv; // Retorna o ModelAndView
    }

    // Endpoint para alterar a quantidade de um item no carrinho
    @GetMapping("/alterarQuantidade/{id}/{acao}")
    public ModelAndView alterarQuantidade(@PathVariable long id, @PathVariable Integer acao) {
        @SuppressWarnings("unchecked")
        List<ItensCompra> itensCompra = (List<ItensCompra>) session.getAttribute("itensCompra"); // Recupera os itens da compra
        if (itensCompra == null) {
            itensCompra = new ArrayList<>(); // Inicializa a lista se não houver itens
        }

        // Itera sobre os itens da compra
        for (ItensCompra it : itensCompra) {
            if (it.getPrato().getId() == id) { // Verifica se o ID do prato corresponde
                if (acao == 1) { // Ação para aumentar a quantidade
                    it.setQuantidade(it.getQuantidade() + 1);
                } else if (acao == 0 && it.getQuantidade() != 1) { // Ação para diminuir a quantidade, não permitindo valores menores que 1
                    it.setQuantidade(it.getQuantidade() - 1);
                }
                it.setValorTotal(it.getQuantidade() * it.getValorUnitario()); // Atualiza o valor total do item
                break; // Sai do loop após encontrar o item
            }
        }
        session.setAttribute("itensCompra", itensCompra); // Atualiza os itens na sessão
        return new ModelAndView("redirect:/carrinho"); // Redireciona para a página do carrinho
    }

    // Endpoint para remover um prato do carrinho
    @GetMapping("/removerPrato/{id}")
    public ModelAndView removerPratoCarrinho(@PathVariable long id) {
        @SuppressWarnings("unchecked")
        List<ItensCompra> itensCompra = (List<ItensCompra>) session.getAttribute("itensCompra"); // Recupera os itens da compra
        if (itensCompra == null) {
            itensCompra = new ArrayList<>(); // Inicializa a lista se não houver itens
        }

        // Itera sobre os itens da compra
        for (ItensCompra it : itensCompra) {
            if (it.getPrato().getId() == id) { // Verifica se o ID do prato corresponde
                itensCompra.remove(it); // Remove o item da lista
                break; // Sai do loop após encontrar o item
            }
        }
        session.setAttribute("itensCompra", itensCompra); // Atualiza os itens na sessão
        return new ModelAndView("redirect:/carrinho"); // Redireciona para a página do carrinho
    }

    // Endpoint para adicionar um prato ao carrinho
    @SuppressWarnings("unchecked")
    @GetMapping("/adicionarCarrinho/{id}")
    public ModelAndView adicionarCarrinho(@PathVariable long id) {
        List<ItensCompra> itensCompra = (List<ItensCompra>) session.getAttribute("itensCompra"); // Recupera os itens da compra
        if (itensCompra == null) {
            itensCompra = new ArrayList<>(); // Inicializa a lista se não houver itens
        }
        Compra compra = (Compra) session.getAttribute("compra"); // Recupera a compra da sessão
        if (compra == null) {
            compra = new Compra(); // Inicializa uma nova compra se não houver
        }

        Optional<Prato> pratoClicado = pratoRepositorio.findById(id); // Busca o prato pelo ID
        Prato prato = pratoClicado.get(); // Obtém o prato clicado

        int controle = 0; // Controla se o prato já está no carrinho
        for (ItensCompra it : itensCompra) {
            if (it.getPrato().getId() == prato.getId()) { // Verifica se o prato já está no carrinho
                controle = 1; // Marca que o prato já existe
                it.setQuantidade(it.getQuantidade() + 1); // Aumenta a quantidade do item
                it.setValorTotal(it.getQuantidade() * it.getValorUnitario()); // Atualiza o valor total do item
                break; // Sai do loop após encontrar o item
            }
        }

        // Se o prato não estava no carrinho, cria um novo item
        if (controle == 0) {
            ItensCompra item = new ItensCompra(); // Cria um novo item de compra
            item.setPrato(prato); // Define o prato
            item.setValorUnitario(prato.getPreco()); // Define o preço do prato
            item.setQuantidade(1); // Iniciar quantidade com 1
            item.setValorTotal(item.getQuantidade() * item.getValorUnitario()); // Calcula o valor total
            itensCompra.add(item); // Adiciona o item à lista
        }

        session.setAttribute("itensCompra", itensCompra); // Atualiza os itens na sessão
        session.setAttribute("compra", compra); // Atualiza a compra na sessão

        return new ModelAndView("redirect:/carrinho"); // Redireciona para a página do carrinho
    }
}
