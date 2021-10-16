import java.util.List;
import java.util.ArrayList;

public class Application {

    static String palavra;
    static String regra;
    static int iteracoes;
    static String svg = "";
    static char ch = '"';
    static int x = 750; // int para me auxiliar a desenhar a linha quando for esquerda ou direita.
    static int y = 750;

    // 1
    public static void leArquivo() {

        // na primeira linha do arquivo (primeiro elemento da lista) tem a palavra com
        // os comandos para desenhar.
        palavra = Arquivo.retornaLinhas("Palavra.txt").get(0);

        // na segunda linha do arquivo (segundo elemento da lista) tem a regra de
        // producao para eu iterar.
        regra = Arquivo.retornaLinhas("Palavra.txt").get(1);

        try {

            // na terceira linha do arquivo (terceiro elemento da lista) tem o numero de
            // vezes que eu vou iterar a regra de producao.
            iteracoes = Integer.parseInt(Arquivo.retornaLinhas("Palavra.txt").get(2));
        }

        catch (NumberFormatException ex) {
            System.out.println("Numero de iteracoes invalido. Corrija a terceira linha do arquivo Palavras.txt");
        }
    }

    // 2
    public static void montaSVG() {
        svg += "<svg height=" + ch + "2000" + ch + " " + "width=" + ch + "1500" + ch + ">" + "\n";

        int qtd = 0; // int que vai me auxiliar a iterar as regras de producao.

        boolean azul = false;
        boolean preto = true; // preto eh a cor padrao da linha.
        boolean amarelo = false;
        boolean vermelho = false;
        boolean move = false; // boolean para me ajudar a mover o cursor sem desenhar uma linha
        boolean verticalAsc = true; // por padrao, vai comecar a desenhar pela vertical ascendente
        boolean verticalDesc = false;
        boolean horizontalDir = false;
        boolean horizontalEsq = false;
        boolean direita = false;
        boolean esquerda = false;

        while (qtd != iteracoes) {

            for (int i = 0; i < palavra.length(); i++) {
                switch (palavra.charAt(i)) {

                    case 'A':
                        azul = true;
                        preto = false;
                        amarelo = false;
                        vermelho = false;
                        move = false;
                        break;

                    case 'P':
                        preto = true;
                        azul = false;
                        amarelo = false;
                        vermelho = false;
                        move = false;
                        break;

                    case 'Y':
                        amarelo = true;
                        azul = false;
                        preto = false;
                        vermelho = false;
                        move = false;
                        break;

                    case 'V':
                        vermelho = true;
                        azul = false;
                        amarelo = false;
                        preto = false;
                        move = false;
                        break;

                    case 'M':
                        azul = false;
                        preto = false;
                        vermelho = false;
                        amarelo = false;
                        move = true;
                        desenha(azul, preto, amarelo, vermelho, move, esquerda, direita, horizontalDir, horizontalEsq,
                                verticalAsc, verticalDesc);
                        break;

                    case 'D':
                        esquerda = false;
                        direita = true;
                        int k = 1;
                        if (verticalAsc == true && k == 1) {
                            verticalAsc = false;
                            horizontalDir = true;
                            verticalDesc = false;
                            horizontalEsq = false;
                            k--;
                        }

                        if (horizontalDir == true && k == 1) {
                            horizontalDir = false;
                            verticalDesc = true;
                            horizontalEsq = false;
                            verticalAsc = false;
                            k--;
                        }

                        if (verticalDesc == true && k == 1) {
                            verticalDesc = false;
                            horizontalEsq = true;
                            verticalAsc = false;
                            horizontalDir = false;
                            k--;
                        }

                        if (horizontalEsq == true && k == 1) {
                            horizontalEsq = false;
                            verticalAsc = true;
                            horizontalDir = false;
                            verticalDesc = false;
                            k--;
                        }

                        break;

                    case 'E':
                        esquerda = true;
                        direita = false;
                        int j = 1;
                        if (verticalAsc == true && j == 1) {
                            verticalAsc = false;
                            horizontalEsq = true;
                            horizontalDir = false;
                            verticalDesc = false;
                            j--;
                        }

                        if (horizontalEsq == true && j == 1) {
                            horizontalEsq = false;
                            verticalDesc = true;
                            horizontalDir = false;
                            verticalAsc = false;
                            j--;
                        }

                        if (verticalDesc == true && j == 1) {
                            verticalDesc = false;
                            horizontalDir = true;
                            verticalAsc = false;
                            horizontalEsq = false;
                            j--;
                        }

                        if (horizontalDir == true && j == 1) {
                            horizontalDir = false;
                            verticalAsc = true;
                            horizontalEsq = false;
                            verticalDesc = false;
                            j--;
                        }

                        break;

                    case 'L':
                        desenha(azul, preto, amarelo, vermelho, move, esquerda, direita, horizontalDir, horizontalEsq,
                                verticalAsc, verticalDesc);
                        break;

                }
            }

            // O codigo abaixo faz a regra de podrucao.
            String novaPalavra = "";
            char substituido = regra.charAt(0);
            String substituicao = "";

            for (int z = 2; z < regra.length(); z++) {
                substituicao += regra.charAt(z);
            }

            for (int p = 0; p < palavra.length(); p++) {
                if (palavra.charAt(p) == substituido) {
                    novaPalavra += substituicao;
                } else {
                    novaPalavra += palavra.charAt(p);
                }
            }

            palavra = "";
            palavra += novaPalavra;
            qtd++;
        }
        svg += "</svg>";
        System.out.println(svg);
    }

    public static void escreveSVG() {
        Arquivo.Write("Imagem.svg", svg);
    }

    public static void desenha(boolean a, boolean p, boolean yellow, boolean v, boolean m, boolean esquerda,
            boolean direita, boolean hDir, boolean hEsq, boolean vAsc, boolean vDesc) {
        if (a == true && p == false && yellow == false && v == false && m == false) {
            if (direita == false && esquerda == false) {
                svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch + " "
                        + "y2=" + ch + (y - 75) + ch + " " + "style=" + ch + "stroke:rgb(0,0,255);stroke-width:4" + ch
                        + " />" + "\n";
                y = y - 75;
            }

            else if (direita == true && esquerda == false) {
                if (vAsc == true && vDesc == false && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y - 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(0,0,255);stroke-width:4" + ch + " />" + "\n";
                    y = y - 75;
                }

                if (vAsc == false && vDesc == true && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y + 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(0,0,255);stroke-width:4" + ch + " />" + "\n";
                    y = y + 75;
                }

                if (vAsc == false && vDesc == false && hDir == true && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x + 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch
                            + "stroke:rgb(0,0,255);stroke-width:4" + ch + " />" + "\n";
                    x = x + 75;
                }

                if (vAsc == false && vDesc == false && hDir == false && hEsq == true) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x - 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch
                            + "stroke:rgb(0,0,255);stroke-width:4" + ch + " />" + "\n";
                    x = x - 75;
                }
            }

            else {
                if (vAsc == true && vDesc == false && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y - 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(0,0,255);stroke-width:4" + ch + " />" + "\n";
                    y = y - 75;
                }

                if (vAsc == false && vDesc == true && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y + 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(0,0,255);stroke-width:4" + ch + " />" + "\n";
                    y = y + 75;
                }

                if (vAsc == false && vDesc == false && hDir == true && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x + 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch
                            + "stroke:rgb(0,0,255);stroke-width:4" + ch + " />" + "\n";
                    x = x + 75;
                }

                if (vAsc == false && vDesc == false && hDir == false && hEsq == true) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x - 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch
                            + "stroke:rgb(0,0,255);stroke-width:4" + ch + " />" + "\n";
                    x = x - 75;
                }
            }
        }

        if (a == false && p == true && yellow == false && v == false && m == false) {
            if (direita == false && esquerda == false) {
                svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch + " "
                        + "y2=" + ch + (y - 75) + ch + " " + "style=" + ch + "stroke:rgb(0,0,0);stroke-width:4" + ch
                        + " />" + "\n";
                y = y - 75;
            }

            else if (direita == true && esquerda == false) {
                if (vAsc == true && vDesc == false && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y - 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(0,0,0);stroke-width:4" + ch + " />" + "\n";
                    y = y - 75;
                }

                if (vAsc == false && vDesc == true && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y + 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(0,0,0);stroke-width:4" + ch + " />" + "\n";
                    y = y + 75;
                }

                if (vAsc == false && vDesc == false && hDir == true && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x + 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch + "stroke:rgb(0,0,0);stroke-width:4"
                            + ch + " />" + "\n";
                    x = x + 75;
                }

                if (vAsc == false && vDesc == false && hDir == false && hEsq == true) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x - 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch + "stroke:rgb(0,0,0);stroke-width:4"
                            + ch + " />" + "\n";
                    x = x - 75;
                }
            }

            else {
                if (vAsc == true && vDesc == false && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y - 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(0,0,0);stroke-width:4" + ch + " />" + "\n";
                    y = y - 75;
                }

                if (vAsc == false && vDesc == true && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y + 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(0,0,0);stroke-width:4" + ch + " />" + "\n";
                    y = y + 75;
                }

                if (vAsc == false && vDesc == false && hDir == true && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x + 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch + "stroke:rgb(0,0,0);stroke-width:4"
                            + ch + " />" + "\n";
                    x = x + 75;
                }

                if (vAsc == false && vDesc == false && hDir == false && hEsq == true) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x - 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch + "stroke:rgb(0,0,0);stroke-width:4"
                            + ch + " />" + "\n";
                    x = x - 75;
                }
            }
        }

        if (a == false && p == false && yellow == true && v == false && m == false) {
            if (direita == false && esquerda == false) {
                svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch + " "
                        + "y2=" + ch + (y - 75) + ch + " " + "style=" + ch + "stroke:rgb(255,255,0);stroke-width:4" + ch
                        + " />" + "\n";
                y = y - 75;
            }

            else if (direita == true && esquerda == false) {
                if (vAsc == true && vDesc == false && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y - 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(255,255,0);stroke-width:4" + ch + " />" + "\n";
                    y = y - 75;
                }

                if (vAsc == false && vDesc == true && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y + 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(255,255,0);stroke-width:4" + ch + " />" + "\n";
                    y = y + 75;
                }

                if (vAsc == false && vDesc == false && hDir == true && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x + 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch
                            + "stroke:rgb(255,255,0);stroke-width:4" + ch + " />" + "\n";
                    x = x + 75;
                }

                if (vAsc == false && vDesc == false && hDir == false && hEsq == true) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x - 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch
                            + "stroke:rgb(255,255,0);stroke-width:4" + ch + " />" + "\n";
                    x = x - 75;
                }
            }

            else {
                if (vAsc == true && vDesc == false && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y - 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(255,255,0);stroke-width:4" + ch + " />" + "\n";
                    y = y - 75;
                }

                if (vAsc == false && vDesc == true && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y + 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(255,255,0);stroke-width:4" + ch + " />" + "\n";
                    y = y + 75;
                }

                if (vAsc == false && vDesc == false && hDir == true && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x + 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch
                            + "stroke:rgb(255,255,0);stroke-width:4" + ch + " />" + "\n";
                    x = x + 75;
                }

                if (vAsc == false && vDesc == false && hDir == false && hEsq == true) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x - 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch
                            + "stroke:rgb(255,255,0);stroke-width:4" + ch + " />" + "\n";
                    x = x - 75;
                }
            }
        }

        if (a == false && p == false && yellow == false && v == true && m == false) {
            if (direita == false && esquerda == false) {
                svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch + " "
                        + "y2=" + ch + (y - 75) + ch + " " + "style=" + ch + "stroke:rgb(255,0,0);stroke-width:4" + ch
                        + " />" + "\n";
                y = y - 75;
            }

            else if (direita == true && esquerda == false) {
                if (vAsc == true && vDesc == false && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y - 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(255,0,0);stroke-width:4" + ch + " />" + "\n";
                    y = y - 75;
                }

                if (vAsc == false && vDesc == true && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y + 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(255,0,0);stroke-width:4" + ch + " />" + "\n";
                    y = y + 75;
                }

                if (vAsc == false && vDesc == false && hDir == true && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x + 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch
                            + "stroke:rgb(255,0,0);stroke-width:4" + ch + " />" + "\n";
                    x = x + 75;
                }

                if (vAsc == false && vDesc == false && hDir == false && hEsq == true) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x - 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch
                            + "stroke:rgb(255,0,0);stroke-width:4" + ch + " />" + "\n";
                    x = x - 75;
                }
            }

            else {
                if (vAsc == true && vDesc == false && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y - 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(255,0,0);stroke-width:4" + ch + " />" + "\n";
                    y = y - 75;
                }

                if (vAsc == false && vDesc == true && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y + 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(255,0,0);stroke-width:4" + ch + " />" + "\n";
                    y = y + 75;
                }

                if (vAsc == false && vDesc == false && hDir == true && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x + 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch
                            + "stroke:rgb(255,0,0);stroke-width:4" + ch + " />" + "\n";
                    x = x + 75;
                }

                if (vAsc == false && vDesc == false && hDir == false && hEsq == true) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x - 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch
                            + "stroke:rgb(255,0,0);stroke-width:4" + ch + " />" + "\n";
                    x = x - 75;
                }
            }
        }

        if (a == false && p == false && yellow == false && v == false && m == true) {
            if (direita == false && esquerda == false) {
                svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch + " "
                        + "y2=" + ch + (y - 75) + ch + " " + "style=" + ch + "stroke:rgb(255,255,255);stroke-width:4"
                        + ch + " />" + "\n";
                y = y - 75;
            }

            else if (direita == true && esquerda == false) {
                if (vAsc == true && vDesc == false && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y - 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(255,255,255);stroke-width:4" + ch + " />" + "\n";
                    y = y - 75;
                }

                if (vAsc == false && vDesc == true && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y + 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(255,255,255);stroke-width:4" + ch + " />" + "\n";
                    y = y + 75;
                }

                if (vAsc == false && vDesc == false && hDir == true && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x + 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch
                            + "stroke:rgb(255,255,255);stroke-width:4" + ch + " />" + "\n";
                    x = x + 75;
                }

                if (vAsc == false && vDesc == false && hDir == false && hEsq == true) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x - 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch
                            + "stroke:rgb(255,255,255);stroke-width:4" + ch + " />" + "\n";
                    x = x - 75;
                }
            }

            else {
                if (vAsc == true && vDesc == false && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y - 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(255,255,255);stroke-width:4" + ch + " />" + "\n";
                    y = y - 75;
                }

                if (vAsc == false && vDesc == true && hDir == false && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + x + ch
                            + " " + "y2=" + ch + (y + 75) + ch + " " + "style=" + ch
                            + "stroke:rgb(255,255,255);stroke-width:4" + ch + " />" + "\n";
                    y = y + 75;
                }

                if (vAsc == false && vDesc == false && hDir == true && hEsq == false) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x + 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch
                            + "stroke:rgb(255,255,255);stroke-width:4" + ch + " />" + "\n";
                    x = x + 75;
                }

                if (vAsc == false && vDesc == false && hDir == false && hEsq == true) {
                    svg += "\t" + "<line x1=" + ch + x + ch + " " + "y1=" + ch + y + ch + " " + "x2=" + ch + (x - 75)
                            + ch + " " + "y2=" + ch + y + ch + " " + "style=" + ch
                            + "stroke:rgb(255,255,255);stroke-width:4" + ch + " />" + "\n";
                    x = x - 75;
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            leArquivo();
            montaSVG();
            escreveSVG();
        }

        catch (OutOfMemoryError erro) {
            System.out.println("Memoria insuficiente para processar tantas iteracoes de regra de producao. Reduza alguns comandos.");
        }
    }

}