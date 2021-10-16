import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;

// Essa classe é toda a lógica de ler e salvar arquivo-texto. 
public class Arquivo {
    public static String Read(String Caminho) {
        String conteudo = "";
        try {
            FileReader arq = new FileReader(Caminho);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha = "";
            try {
                linha = lerArq.readLine();
                while (linha != null) {
                    conteudo += linha + "\n";
                    linha = lerArq.readLine();
                }
                arq.close();
                lerArq.close();
                return conteudo;
            } catch (IOException ex) {
                System.out.println("Erro: Nao foi possivel ler o arquivo!");
                return "";
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Erro: Arquivo nao encontrado!");
            return "";
        }
    }

    public static boolean Write(String Caminho, String Texto) {
        try {
            FileWriter arq = new FileWriter(Caminho);
            PrintWriter gravarArq = new PrintWriter(arq);
            gravarArq.println(Texto);
            gravarArq.close();
            return true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // Nessa função eu vou ler um arquivo-texto e vou guardar cada linha em uma lista.
    // Por fim, eu retorno essa lista de linhas.
    public static List<String> retornaLinhas(String Caminho) {
        List<String> linhas = new ArrayList<>();
        try (FileReader arq = new FileReader(Caminho)) {
            try (BufferedReader lerArq = new BufferedReader(arq)) {
                String linha = "";
                    while ((linha = lerArq.readLine()) != null) {
                        linhas.add(linha);
                    }
                }
            catch (FileNotFoundException ex) {
                System.out.println("Erro: Arquivo nao encontrado!");
            }
        }
        catch (IOException ex) {
            System.out.println("Erro: Nao foi possivel ler o arquivo!");
        }
        return linhas;
    }
}