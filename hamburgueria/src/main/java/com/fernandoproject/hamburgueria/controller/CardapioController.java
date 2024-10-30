package com.fernandoproject.hamburgueria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fernandoproject.hamburgueria.dao.PratoDao;
import com.fernandoproject.hamburgueria.model.Prato;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller // Anotação que indica que a classe é um controlador Spring MVC
public class CardapioController {
    
    private static String caminhoImagens = "hamburgueria\\src\\main\\resources\\static\\img\\img-cardapio\\"; // Caminho para salvar as imagens dos pratos

    @Autowired // Injeção de dependência para o repositório de pratos
    private PratoDao pratoRepositorio;

    @GetMapping("/cardapio") // Mapeia a requisição GET para a rota "/cardapio"
    public ModelAndView cardapio() {
        return pratosCardapio(); // Chama o método para retornar a lista de pratos
    }

    @PostMapping("PratosCardapio") // Mapeia a requisição POST para a rota "PratosCardapio"
    public ModelAndView pratosCardapio() {
        ModelAndView mv = new ModelAndView(); // Criação de um novo objeto ModelAndView
        List<Prato> pratos = pratoRepositorio.findAll(); // Busca todos os pratos do repositório

        mv.addObject("listaPratos", pratos); // Adiciona a lista de pratos ao modelo
        mv.setViewName("pages/cardapio"); // Define a view a ser utilizada

        return mv; // Retorna o objeto ModelAndView
    }

    @GetMapping("/adicionar-prato") // Mapeia a requisição GET para a rota "/adicionar-prato"
    public ModelAndView adicionarPrato() {
        ModelAndView mv = new ModelAndView(); // Criação de um novo objeto ModelAndView
        mv.setViewName("pages/adicionar-prato"); // Define a view a ser utilizada
        mv.addObject("prato", new Prato()); // Adiciona um novo objeto Prato ao modelo
        return mv; // Retorna o objeto ModelAndView
    }

    @SuppressWarnings("null") // Supressão de warnings sobre null
    @GetMapping("/cardapio/mostrarImagem/{imagem}") // Mapeia a requisição GET para mostrar a imagem do prato
    @ResponseBody // Indica que o retorno é o corpo da resposta
    public byte[] retornarImagem(@PathVariable("imagem") String imagem) throws IOException {
        File imagemArquivo = new File(caminhoImagens + imagem); // Criação de um objeto File para a imagem
        if (imagem != null || imagem.trim().length() > 0) { // Verifica se o nome da imagem não é nulo ou vazio
            return Files.readAllBytes(imagemArquivo.toPath()); // Retorna os bytes da imagem
        }
        return null; // Retorna null se a imagem não for válida
    }

    @PostMapping("AdicionarPrato") // Mapeia a requisição POST para adicionar um prato
    public ModelAndView adicionarPrato(@Valid Prato prato, BindingResult br,
            @RequestParam("file") MultipartFile arquivo) {
        ModelAndView mv = new ModelAndView(); // Criação de um novo objeto ModelAndView

        if (br.hasErrors()) { // Verifica se houve erros de validação
            mv.setViewName("pages/adicionar-prato"); // Retorna à página de adição de prato
            mv.addObject("prato"); // Adiciona o objeto prato ao modelo
        } else if (arquivo.isEmpty()) { // Verifica se o arquivo de imagem está vazio
            mv.addObject("msgErroAdicionarPrato", "Não é possível adicionar um prato sem foto."); // Mensagem de erro
            mv.setViewName("pages/adicionar-prato"); // Retorna à página de adição de prato
        } else {
            salvarImagemPrato(arquivo, prato, mv); // Salva a imagem do prato
        }

        return mv; // Retorna o objeto ModelAndView
    }

    @GetMapping("/excluir/{id}") // Mapeia a requisição GET para excluir um prato pelo ID
    public String excluirPrato(@PathVariable("id") long id) {
        pratoRepositorio.deleteById(id); // Exclui o prato do repositório
        return "redirect:/cardapio"; // Redireciona para a página de cardápio
    }

    @GetMapping("/editar/{id}") // Mapeia a requisição GET para editar um prato pelo ID
    public ModelAndView editar(@PathVariable("id") long id) {
        ModelAndView mv = new ModelAndView(); // Criação de um novo objeto ModelAndView
        mv.setViewName("pages/editar"); // Define a view a ser utilizada
        Prato prato = pratoRepositorio.getReferenceById(id); // Busca o prato pelo ID
        mv.addObject("prato", prato); // Adiciona o prato ao modelo

        return mv; // Retorna o objeto ModelAndView
    }

    @PostMapping("editar") // Mapeia a requisição POST para editar um prato
    public ModelAndView editar(Prato prato, RedirectAttributes redirectAttributes,
            @RequestParam("file") MultipartFile arquivo) {
        ModelAndView mv = new ModelAndView(); // Criação de um novo objeto ModelAndView

        // Recupera o prato existente no banco de dados para comparar a imagem
        Prato pratoExistente = pratoRepositorio.getReferenceById(prato.getId());

        if (!arquivo.isEmpty()) { // Verifica se o arquivo não está vazio
            // Se o arquivo não está vazio, chama o método para salvar a imagem junto com as alterações
            salvarImagemPrato(arquivo, prato, mv);
            mv.setViewName("redirect:/cardapio"); // Redireciona para a página de cardápio
        } else {
            // Se o arquivo está vazio, mantém a imagem já existente e realiza as alterações
            prato.setNomeImagem(pratoExistente.getNomeImagem()); // Mantém o nome da imagem existente
            pratoRepositorio.save(prato); // Salva as alterações do prato
            mv.setViewName("redirect:/cardapio"); // Redireciona para a página de cardápio
        }

        return mv; // Retorna o objeto ModelAndView
    }

    public void salvarImagemPrato(@RequestParam("file") MultipartFile arquivo, @Valid Prato prato, ModelAndView mv) {
        pratoRepositorio.save(prato); // Salva o prato no repositório

        try {
            if (!arquivo.isEmpty()) { // Verifica se o arquivo não está vazio
                byte[] bytes = arquivo.getBytes(); // Obtém os bytes do arquivo
                Path caminho = Paths
                        .get(caminhoImagens + String.valueOf(prato.getId()) + arquivo.getOriginalFilename()); // Define o caminho da imagem
                Files.write(caminho, bytes); // Escreve os bytes no arquivo

                prato.setNomeImagem(String.valueOf(prato.getId()) + arquivo.getOriginalFilename()); // Define o nome da imagem no prato
                pratoRepositorio.save(prato); // Salva o prato com o novo nome da imagem
                mv.setViewName("redirect:/cardapio"); // Redireciona para a página de cardápio
            }
        } catch (IOException e) {
            e.printStackTrace(); // Imprime o stack trace em caso de erro
        }

    }
}
