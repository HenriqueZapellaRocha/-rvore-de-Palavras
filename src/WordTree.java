
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
            this.father = this;
        }
        
        //Nodo final da palavra com significado
        public CharNode(char character, boolean isFinal, String signficado) {
            this.character = character;
            this.significado = signficado;
            this.isFinal = isFinal;
            this.children = new ArrayList<CharNode>();
            this.father = this;
        }

        /**
        * Adiciona um filho (character) no nodo. Não pode aceitar caracteres repetidos.
        * @param character - character a ser adicionado
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
            childNode.father = this;
            this.children.add(childNode);
            nChilds++;
        }
        
        public int getNumberOfChildren () {
            return nChilds;
        }
        
        /**
         * Verifica através do @param index se está dentro dos limites válidos
         * como maior ou igual a zero e menor que o tamanho da lista children
         * @return nodo filho, senão @return null
         */

        public CharNode getChild (int index) {
            if (index>=0 && index<children.size())
                return children.get(index);
            else
                return null;
        }

        /**
         * Obtém a palavra correspondente a este nodo, 
         * subindo até a raiz da árvore
         * @return a palavra
         */
        
        private String getWord() {
            String word = "";
            CharNode NodoAtual = CharNode.this;
            //Sobe a WordTree do NodoAtual até o root e concatena os nodos por onde passar
            while(NodoAtual!=null) {
                word = word + NodoAtual.character;
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
    * Adiciona palavra na estrutura em árvore
    * @param word
    * @param significado
    */

    public void addWord(String word, String significado) {
        // aux recebe root
        CharNode aux = root;

        // cria o root caso ele nao exista
        if(aux == null) {
            CharNode newNode = new CharNode(' ');
            root = newNode;
            aux = root;
        }

        // loop para o tamanho da palavra
        for(int j = 0; j < word.length(); j++) {
        // loop para aux
            for(int i = 0; i <= aux.getNumberOfChildren(); i++) {
                if(aux.getChild(i) != null &&  aux.getChild(i).character == word.charAt(j)) {
                    aux = aux.getChild(i);
                   j++;
                }
            }
            //Verifica se é nodo final
            if(j != word.length() - 1) {
                aux.addChild(word.charAt(j), false, null);
                if(aux != null) {
                    aux = aux.getChild(aux.getNumberOfChildren() - 1);
                    totalNodes++;
                }
            }
            // caso nao seja a letra recebe isFinal como true e o significado da palavra
            else{
                aux.addChild(word.charAt(j), true, significado);
                if(aux != null) {
                    aux = aux.getChild(aux.getNumberOfChildren() - 1);
                    totalNodes++;
                }
            }
        }
       totalWords++;
    }
    
    /**
    * Percorre a árvore e retorna uma lista com as palavras iniciadas pelo prefixo dado.
    * Tipicamente, um método recursivo.
    * @param prefix
    */

    private void positionsPre(List<Palavra> palavrasEncontradas, CharNode nodo, String prefix) {
        
    
        
        String palavraAtual = prefix + nodo.character;
        if (nodo.isFinal) {
            Palavra palavraEncontrada = new Palavra(palavraAtual, nodo.significado);
            palavrasEncontradas.add(palavraEncontrada);
        }
        for (CharNode child : nodo.children) {
            positionsPre(palavrasEncontradas, child, palavraAtual);
        }
    }

    /**
    Procura por todos os nodos filhos que começam com o prefixo
    passando recursivamente com o método positionsPre
    @param prefix
    @return Nodos que começam com o prefixo
     */

    public List<Palavra> searchAll(String prefix) {
        
        // apenas para nao repetir a letra
        String novaPrefix = "";
        for(int j = 0; j < prefix.length(); j++) {
        if(prefix.length() - 1  != j)
        novaPrefix += prefix.charAt(j);
        }

        if(this.root==null)
            return null;
        CharNode aux = root;
        Boolean achou = false;
        for(int j = 0; j< prefix.length(); j++) {
            for(int i = 0; i <= aux.getNumberOfChildren(); i++) {
                if(aux.getChild(i) != null &&  aux.getChild(i).character == prefix.charAt(j)) {
                    achou = true;
                  aux = aux.getChild(i);
                }
              
            }
              if (achou == false) {
                    return null;
                }
                else 
                {
                    achou = false;
                }
        }
        List<Palavra> palavrasEncontradas = new ArrayList<>();
        positionsPre(palavrasEncontradas, aux, novaPrefix);
        return palavrasEncontradas;
    } 
}
