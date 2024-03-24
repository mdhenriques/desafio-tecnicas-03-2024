/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.desafio.tecnicas;

/**
 *
 * @author mdhen
 */
public class LivroBLL {
    public static void validaTitulo(char _op, Livro _umlivro)
    {
        Erro.setErro(false);
        if (_umlivro.getTitulo().equals("")){
            Erro.setErro("O campo TITULO é de preenchimento obrigatório");
            return;
        }
        
        if (Erro.getErro()) return;
        switch (_op)
        {
            case 'c': LivroDAL.consultaLivroPorTitulo(_umlivro.getTitulo());
            break;
            case 'd': LivroDAL.excluiLivro(_umlivro.getTitulo());
            break;
        }        
    }
    
    public static void validaDados(char _op, Livro _umlivro)
    {
        Erro.setErro(false);
        if (_umlivro.getTitulo().equals(""))
        {Erro.setErro("O campo TITULO é de preenchimento obrigatório..."); return;}
        if (_umlivro.getAutor().equals(""))
        {Erro.setErro("O campo AUTOR é de preenchimento obrigatório..."); return;}
        if (_umlivro.getEditora().equals(""))
        {Erro.setErro("O campo EDITORA é de preenchimento obrigatório..."); return;}
        if (_umlivro.getAnoEdicao().equals(""))
        {Erro.setErro("O campo ANO EDICAO é de preenchimento obrigatório"); return;}
        else
            try
            {
                Integer.parseInt(_umlivro.getAnoEdicao());
            }
            catch (Exception e){
                Erro.setErro("O campo ANO EDICAO deve ser numerico");
            }
        if (_umlivro.getLocalizacao().equals(""))
        {Erro.setErro("O campo LOCALIZACAO é de preenchimento obrigatório"); return;}
        
        if (Erro.getErro()) return;
        
        switch (_op)
        {
            case 'i': LivroDAL.insereLivro(_umlivro);
            break;
            case 'a': LivroDAL.alteraLivroPorTitulo(_umlivro.getTitulo(), _umlivro);
            break;
        }
    }
}
