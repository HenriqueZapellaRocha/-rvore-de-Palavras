
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class App {
    public static void main(String[] args) {
        LinkedList<Palavra> lista = new LinkedList<>();
        String aux[];
                
        WordTree wd = new WordTree();
        Path path1 = Paths.get("dicionario.csv");

        //Leitura do dicionario
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.forName("UTF-8"))) {
            String line = reader.readLine();
            while (line != null) {
                aux = line.split(";"); //Le as palavras separadas por ";"
                Palavra p = new Palavra(aux[0],aux[1]);
                lista.add(p);

                //adiciona as palavras do dicionario na WordTree
                wd.addWord(aux[0], aux[1]);
                line = reader.readLine();
            }
        } catch (IOException e) {
            System.err.format("Erro na leitura do arquivo: ", e);
        }  
        
   
        
        // estancia Scanner
        Scanner sc = new Scanner(System.in);
        // string apenas para logica de decisao se deseja continuar pesquisando
        char continuar = 's';

        int index = 0;
        int contador =0;

        while(continuar == 's') {
            System.out.print("Escolha o que deseja pesquisar: \n");
            String escolha = sc.nextLine();
            List<Palavra> palavrasEncontradas = wd.searchAll(escolha);
            if(palavrasEncontradas == null) {
                System.out.println("Palavra não encontrada!!");
            }
            else{
            for(Palavra listaDePalvras : palavrasEncontradas) {
                contador++;
                System.out.println( contador + "-" + listaDePalvras.getPalavra());
                
             }
             System.out.println("Qual palavra deseja saber o signficado: ");
             index = sc.nextInt() - 1;
             System.out.println(palavrasEncontradas.get(index).getSignificado());
             contador = 0;
            }

            System.out.println("Deseja continuar? (s/n)");
            continuar = sc.next().charAt(0);
            sc.nextLine();
        }


      

    }

}
