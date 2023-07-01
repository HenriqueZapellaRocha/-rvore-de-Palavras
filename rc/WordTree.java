// 4645G-04 - Algoritmos e Estruturas de Dados I
// 2023-1

import java.util.ArrayList;
import java.util.List;

public class WordTree {
    
    // Classe interna
    private class CharNode {
        private char character;
	    private String significado;
        private boolean isFinal;
        public int nChilds;
        private List<CharNode> children;
        private CharNode father;

        //Nodo de character
        public CharNode(char character) {
            this.character = character;
            this.nChilds = 0;
            this.children = new ArrayList<CharNode>();
            this.isFinal = false;
            this.father = null;
        }
        
        //Nodo final da palavra com significado
        public CharNode(char character, boolean isFinal, String signficado) {
            this.character = character;
            this.significado = signficado;
            this.isFinal = isFinal;
            this.children = new ArrayList<CharNode>();
            this.father = null;
        }

        /**
        * Adiciona um filho (caracter) no nodo. Não pode aceitar caracteres repetidos.
        *
        * @param character - caracter a ser adicionado
        * @param isFinal - se é final da palavra ou não
        * @param significado - significado da palavra
        */
        
        public void addChild (char character, boolean isFinal, String significado){
            //Verifica se ja existe um filho com o caracter. Se sim, encerra.
            for (CharNode child : this.children){
                if (child.character == character)
                    return;
            }
            
            CharNode childNode = new CharNode(character, isFinal, significado);
            childNode.father = CharNode.this;
            children.add(childNode);
            totalNodes++;
            nChilds++;
        }
        
        public int getNumberOfChildren () {
            return nChilds;
        }
        
        public CharNode getChild (int index) {
            if (index>=0 && index<children.size())
                return children.get(index);
            else
                return null;
        }

        /**
         * Obtém a palavra correspondente a este nodo, subindo até a raiz da árvore
         * @return a palavra
         */
        
        private String getWord() {
            String word = "";
            CharNode NodoAtual = CharNode.this;
            
            //Concatena os caracteres dos nodos e vai subindo a árvore
            while(NodoAtual!=null) {
                word = NodoAtual.character + word;
                NodoAtual = NodoAtual.father;
            }
            return word;
        }
        
        /**
        * Encontra e retorna o nodo que tem determinado caracter.
        * @param character - caracter a ser encontrado.
        */

        public CharNode findChildChar (char character) {
            for (CharNode child : this.children) {
                if (child.character == character)
                    return child;
            }
            return null;
        }
        
    }

    // Atributos
    private CharNode root;
    private int totalNodes;
    private int totalWords;
    
    // Construtor
    public WordTree() {
      this.root = null;
      this.totalNodes = 0;
      this.totalWords = 0;
    }

    // Metodos
    public int getTotalWords(int totalWords) {
        return totalWords;
    }

    public int getTotalNodes(int totalNodes) {
        return totalNodes;
    }
    
    /**
    *Adiciona palavra na estrutura em árvore
    *@param word
    */

    public void addWord(String word, String significado) {
        // aux recebe root
        CharNode aux = root;

        // loop para word
        for(int j = 0; j < word.length(); j++) {
        // loop para aux
            for(int i = 0; i < aux.getNumberOfChildren(); i++) {
                if(aux.getChild(i).character == word.charAt(j)) {
                    aux = aux.getChild(i);
                    i = 0;
                }
                // caso a letra ainda nao exista 
                else {
                    if(j != word.length())
                        aux.addChild(word.charAt(j), true, significado);
                    else{
                        aux.addChild(word.charAt(j), false, null);
                    }
                    totalNodes++;
                    totalWords++;
                }
            }
        }
        
        /*
        for(int i=0; i < word.length(); i++) {
            CharNode aux = findCharNodeForWord(word);
            if(aux != null) {
                if(i > word.length()) {
                aux.addChild(word.charAt(i), false, null);
                }
                else {
                    aux.addChild(word.charAt(i), true, significado);
                }
            }
        }
        totalWords++;
        */
    }
    
    /**
     * Vai descendo na árvore até onde conseguir encontrar a palavra
     * @param word
     * @return o nodo final encontrado
     */

    private CharNode findCharNodeForWord(String word) {
        if(totalNodes > 0) {
            CharNode tmp =null;
            for(int i=0; i<totalNodes; i++) {
                root.children.get(i);
                tmp = findCharNodeForWord(word);
                if(tmp != null) {
                    return tmp;
                }
            }

        }
        return null;
    }

    /**
    * Percorre a árvore e retorna uma lista com as palavras iniciadas pelo prefixo dado.
    * Tipicamente, um método recursivo.
    * @param prefix
    */

    private void positionsPre(List<String> lista, CharNode nodo, String prefix){
        String charAtual = String.valueOf(nodo.character);
        String palavraAtual = prefix + charAtual;
        //if (palavraAtual.startsWith(prefix))
            lista.add(palavraAtual);
		for(int index=0; index<nodo.nChilds; index++)
            positionsPre(lista, nodo.children.get(index), prefix);
	}
    
    public List<String> searchAll(String prefix) {
        if(this.root==null)
            return null;
        List<String> palavrasEncontradas = new ArrayList<>();
        positionsPre(palavrasEncontradas, root, prefix);
        return palavrasEncontradas;
    } 


}
