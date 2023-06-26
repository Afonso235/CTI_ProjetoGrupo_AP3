class Peca {
    private String codigo;
    private String designacao;
    private String marca;
    private String categoria;
    private int quantidadeEmStock;
    private double preco;
    private Fornecedor fornecedor;

    public Peca(String codigo, String designacao, String marca, String categoria, int quantidadeEmStock,
                double preco, Fornecedor fornecedor) {
        this.codigo = codigo;
        this.designacao = designacao;
        this.marca = marca;
        this.categoria = categoria;
        this.quantidadeEmStock = quantidadeEmStock;
        this.preco = preco;
        this.fornecedor = fornecedor;
    }
}