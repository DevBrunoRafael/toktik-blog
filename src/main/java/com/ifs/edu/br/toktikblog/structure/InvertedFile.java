package com.ifs.edu.br.toktikblog.structure;

import com.ifs.edu.br.toktikblog.context.PersistenceContext;
import com.ifs.edu.br.toktikblog.models.Publication;
import org.springframework.stereotype.Component;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class InvertedFile {

    //      palavra -> [ {referenciaPost = ocorrenciasDaPalavraNoPost} ]
    TreeMap<String, HashMap<String, Integer>> vocabulary = new TreeMap<>();

    public void savePub(Publication publication) throws IOException {

        var textoDoPost = publication.getText();
        var textoLimpo = removerPontuacaoDeTexto(textoDoPost);
        var palavrasDoTexto = separarTextoEmPalavras(textoLimpo);
        var filtroDasPalavras = filtrarPalavrasMenoresQueDuasLetras(palavrasDoTexto);
        var palavras = filtroDasPalavras.stream()
                .map(this::transformarEmMinuscula).toList();

        // mapeando vocabulário

        // percorre palavra por palavra mapeando suas ocorrências nas publicações que
        // são cadastradas
        palavras.forEach(palavra -> {

            // se for uma nova palavra, é criada uma nova lista de referências para ela
            // adicionando o post em que ela está presente como primeira referência.
            var containsPalavra = this.vocabulary.containsKey(palavra);
            if (!containsPalavra){

                // se ainda não tem a palavra:
                // cria  lista de referencias e ocorrencias: { refPostAtual = 1 }
                // indico que palavra está apontando para lista de referencias criada: palavra -> [ { postAtual = 1 } ]
                HashMap<String, Integer> referenciaOcorrencia = new HashMap<>();
                referenciaOcorrencia.put(publication.getUuid(), 1);

                this.vocabulary.put(palavra, referenciaOcorrencia);

            } else {

                // se for uma palavra já existente, pegamos a lista de referencia dela
                // e atualizamos a quantidade de ocorrências desta palavra no post.
                var pubUuid = publication.getUuid();

                // se a palavra já está presente no vocabulário
                // pego a lista de referencias dela
                // verifico se a lista de referencia contem a referencia do post atual

                // se tiver a referencia, eu vou pegar dessa lista de referencia a quantidade
                // de ocorrencias da palavra e vou adicionar mais uma ocorrencia

                // após adicionar a nova ocorrencia, faço a atualização da lista de referencia

                // se não ouver referencia para o post atual, significa que é a primeira vez que
                // essa palavra apareceu na publicação
                HashMap<String, Integer> referenciaOcorrencia = this.vocabulary.get(palavra);
                if (referenciaOcorrencia.containsKey(pubUuid)){
                    var adicionaOcorrencia = referenciaOcorrencia.get(pubUuid) + 1;
                    referenciaOcorrencia.put(pubUuid, adicionaOcorrencia);
                } else {
                    referenciaOcorrencia.put(pubUuid, 1);
                }
            }
        });

        // após mapeado o vocabulário a publicação é salva
        PersistenceContext.publications.put(publication.getUuid(), publication);
    }

    public List<Publication> findByTextFragment(String texto) {

        List<Publication> pubReferencies = new ArrayList<>();

        var textoLimpo = removerPontuacaoDeTexto(texto);
        var palavrasDoTexto = separarTextoEmPalavras(textoLimpo);
        var filtroDasPalavras = filtrarPalavrasMenoresQueDuasLetras(palavrasDoTexto);
        var palavras = filtroDasPalavras.stream()
                .map(this::transformarEmMinuscula).toList();

        // percorre todas as palavras do vocabulário buscando por palavras semelhantes
        // ou iguais ás palavras contidas no texto informado para pesquisa, quando encontradas
        // a lista de referências dos posts que está associada a essa palavra é adicionada
        // a uma lista geral que contém todas as referencias de posts de palavras semelhantes
        // ou iguais ao conjunto de palavras informadas.

        // keep -> post1=2, post2=3, post3=1
        // 'keep'ed -> post2=1, post3=2, post1=1
        // 'keep's -> post7=2, post=8=1

        // listaDeHashMapsDeReferenciasOcorrencias
        // [
        //  post1=2, post2=3, post3=1
        //  post2=1, post3=2, post1=1
        //  post7=2, post=8=1
        // ]
        List<HashMap<String, Integer>> listaDeHashMapsDeReferenciasOcorrencias = new ArrayList<>();

        this.vocabulary.forEach((palavra, referenciasOcorrencias) -> {
            palavras.forEach(palavraDoTexto -> {
                if (palavra.contains(palavraDoTexto)){
                    listaDeHashMapsDeReferenciasOcorrencias.add(referenciasOcorrencias);
                }
            });
        });

        // reduz todas as listas de referencias contidas na lista anterior em uma única lista
        // contendo todas as referências
        HashMap<String, Integer> hashMapDeTodasReferenciasOcorrencias = new HashMap<>();

        // percorre a lista contendo todos os hashmaps de referencias e ocorrências
        // acima e faz uma compactação em um único hashmap de referencias e ocorrencias

        // keep -> post1=2, post2=3, post3=1
        // 'keep'ed -> post2=1, post3=2, post1=1
        // 'keep's -> post7=2, post=8=1

        // keep -> post1=3, post2=4, post3=3, post7=2, post=8=1
        listaDeHashMapsDeReferenciasOcorrencias.forEach(referenciasOcorrencias -> {
            referenciasOcorrencias.forEach((postRef, ocorrencias) -> {
                if (!hashMapDeTodasReferenciasOcorrencias.containsKey(postRef)) {
                    hashMapDeTodasReferenciasOcorrencias.put(postRef, ocorrencias);
                } else {
                    var novasOcorrencias = hashMapDeTodasReferenciasOcorrencias.get(postRef) + ocorrencias;
                    hashMapDeTodasReferenciasOcorrencias.put(postRef, novasOcorrencias);
                }
            });
        });

        hashMapDeTodasReferenciasOcorrencias.forEach((referencia, ocorrencias) -> {
            Publication publication = PersistenceContext.publications.get(referencia);
            pubReferencies.add(publication);
        });

        return pubReferencies;
    }

    // retorna todas as publicações do presentes no map do
    // context
    public List<Publication> getAllPublications() {
        List<Publication> pubList = new ArrayList<>();

        for (Map.Entry<String, Publication> entry : PersistenceContext.publications.entrySet()) {
            var publication = entry.getValue();
            pubList.add(publication);
        }
        return pubList;
    }


    private String removerPontuacaoDeTexto(String texto) {
        Pattern punctuationCharacters = Pattern.compile("[!\"#$%&'()*+,-./:;<=>?@\\[\\]^_`{|}~]");
        Matcher fraseReplace = punctuationCharacters.matcher(texto);
        return fraseReplace.replaceAll("");
    }

    private String[] separarTextoEmPalavras(String texto) {
        return texto.split(" ");
    }

    private List<String> filtrarPalavrasMenoresQueDuasLetras(String[] conjuntoPalavras) {
        return Arrays.stream(conjuntoPalavras).filter(palavra -> palavra.length() > 2)
                .toList();
    }

    private String transformarEmMinuscula(String palavra) {
        return palavra.toLowerCase();
    }
}