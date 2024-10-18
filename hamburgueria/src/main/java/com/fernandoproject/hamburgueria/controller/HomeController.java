package com.fernandoproject.hamburgueria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

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


@Controller
public class HomeController {

    private static String caminhoImagens = "hamburgueria\\src\\main\\resources\\static\\img\\img-cardapio\\";

    @Autowired
    private PratoDao pratoRepositorio;
    

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pages/index");
        return mv;
    }

    @GetMapping("/cardapio")
    public ModelAndView cardapio() {
        return pratosCardapio();
    }

    @PostMapping("PratosCardapio")
    public ModelAndView pratosCardapio() {
        ModelAndView mv = new ModelAndView();
        List<Prato> pratos = pratoRepositorio.findAll();

        mv.addObject("listaPratos", pratos);
        mv.setViewName("pages/cardapio");
        
        return mv;
    }

    @GetMapping("/adicionar-prato")
    public ModelAndView adicionarPrato() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("pages/adicionar-prato");
        mv.addObject("prato", new Prato());
        return mv;
    }

    @GetMapping("/cardapio/mostrarImagem/{imagem}")
    @ResponseBody
    public byte[] retornarImagem(@PathVariable("imagem") String imagem ) throws IOException {
        File imagemArquivo = new File(caminhoImagens + imagem);
        if (imagem != null || imagem.trim().length() > 0) {
            return Files.readAllBytes(imagemArquivo.toPath());
        }
        return null;
    }
    

    @PostMapping("AdicionarPrato")
    public ModelAndView adicionarPrato(@Valid Prato prato, BindingResult br, @RequestParam("file") MultipartFile arquivo) {
        ModelAndView mv = new ModelAndView();

        if (br.hasErrors()) {
            mv.setViewName("pages/adicionar-prato");
            mv.addObject("prato");
            return mv;
        } 

        pratoRepositorio.save(prato);

        try {
            if (!arquivo.isEmpty()) {
                byte[] bytes = arquivo.getBytes();
                Path caminho = Paths.get(caminhoImagens + String.valueOf(prato.getId()) + arquivo.getOriginalFilename());
                Files.write(caminho, bytes);

                prato.setNomeImagem(String.valueOf(prato.getId()) + arquivo.getOriginalFilename());
                pratoRepositorio.save(prato);
                mv.setViewName("redirect:/cardapio");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return mv;
    }
}
