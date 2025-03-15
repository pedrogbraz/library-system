import java.util.*;

class Autor {
    private static int contadorId = 1;
    private int id;
    private String nome;
    private String dataNascimento;

    public Autor(String nome, String dataNascimento) {
        this.id = contadorId++;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getDataNascimento() { return dataNascimento; }
}

class Livro {
    private static int contadorId = 1;
    private int id;
    private String titulo;
    private Autor autor;
    private boolean disponivel;
    private Date dataCadastro;
    private Date dataAtualizacao;

    public Livro(String titulo, Autor autor) {
        this.id = contadorId++;
        this.titulo = titulo;
        this.autor = autor;
        this.disponivel = true;
        this.dataCadastro = new Date();
        this.dataAtualizacao = new Date();
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public Autor getAutor() { return autor; }
    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
}

class Emprestimo {
    private String nomeCliente;
    private Livro livro;
    private Date dataEmprestimo;

    public Emprestimo(String nomeCliente, Livro livro) {
        this.nomeCliente = nomeCliente;
        this.livro = livro;
        this.dataEmprestimo = new Date();
        this.livro.setDisponivel(false);
    }

    public Livro getLivro() { return livro; }
}

class Biblioteca {
    private List<Livro> livros = new ArrayList<>();
    private List<Autor> autores = new ArrayList<>();
    private List<Emprestimo> emprestimos = new ArrayList<>();

    public void adicionarAutor(Autor autor) {
        autores.add(autor);
    }

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }

    public void listarLivrosDisponiveis() {
        for (Livro livro : livros) {
            if (livro.isDisponivel()) {
                System.out.println(livro.getId() + " - " + livro.getTitulo() + " (Autor: " + livro.getAutor().getNome() + ")");
            }
        }
    }

    public Livro buscarLivroPorId(int id) {
        for (Livro livro : livros) {
            if (livro.getId() == id) return livro;
        }
        return null;
    }

    public void realizarEmprestimo(String nomeCliente, Livro livro) {
        Emprestimo emprestimo = new Emprestimo(nomeCliente, livro);
        emprestimos.add(emprestimo);
        System.out.println("Livro emprestado com sucesso!");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();

        Autor autor1 = new Autor("Machado de Assis", "21/06/1839");
        Autor autor2 = new Autor("J.K. Rowling", "31/07/1965");
        biblioteca.adicionarAutor(autor1);
        biblioteca.adicionarAutor(autor2);

        Livro livro1 = new Livro("Dom Casmurro", autor1);
        Livro livro2 = new Livro("Harry Potter e a Pedra Filosofal", autor2);
        biblioteca.adicionarLivro(livro1);
        biblioteca.adicionarLivro(livro2);

        while (true) {
            System.out.print("Deseja ver os livros disponíveis? (S/N): ");
            String resposta = scanner.nextLine().toUpperCase();

            if (resposta.equals("N")) {
                System.out.println("Aplicação encerrada. Até mais!");
                break;
            }

            biblioteca.listarLivrosDisponiveis();

            System.out.print("Digite o ID do livro que deseja emprestar: ");
            int idLivro = scanner.nextInt();
            scanner.nextLine();

            Livro livroEscolhido = biblioteca.buscarLivroPorId(idLivro);

            if (livroEscolhido != null && livroEscolhido.isDisponivel()) {
                System.out.print("Digite seu nome: ");
                String nomeCliente = scanner.nextLine();

                biblioteca.realizarEmprestimo(nomeCliente, livroEscolhido);
            } else {
                System.out.println("Livro indisponível ou não encontrado.");
            }
        }

        scanner.close();
    }
}