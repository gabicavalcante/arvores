import java.util.Calendar;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * Write a description of class Tree here.
 * 
 * @author Roberto D.; Gabriela C.; Gustavo A.
 * @version 1.0
 */

/* 
 *  Calendar nascimento = Calendar.getInstance();
 *  nascimento.set(2000,Calendar.AUGUST,20);
 */
    
public class Tree
{
    private Node root;
    
    /**
     * Construtor que inicializar a raiz como nula
     */
    public Tree(){ 
        root = null;
    } 
    
    /**
     * addNode - m�todo chamado para adicionar um n� na �rvore
     * 
     * @params pessoa O objeto pessoa que seja adicionado em um n� da �rvore
     */
    public void addNode(Pessoa pessoa) {
        if (root == null) { // caso a raiz ainda esteja vazia
            root = new Node();    // inicializa o Node raiz root
            root.setData(pessoa); // adiciona o parametro pessoa na raiz
            root.setLeft(null);   // seta o n� esquerdo como null
            root.setRight(null);  // seta o n� direito como null
        }
        else {
            if (hasNode(pessoa.getCPF())) { // verifica se j� existe um registro com esse CPF
                System.out.print("Essa pessoa j� existe");
            }
            else {
                add(root, pessoa); // chama o m�todo add passando a raiz e a pessoa
            }
        }
    }
    
    /**
     * add - m�todo que adiciona efetivamente um valor em um determinado n�. Ele verifica pelo valor o nome se a 
     * inst�ncia passada de Pessoa deve ficar a esquerda ou a direita do n�.
     * 
     * @params node n� que ter� os filhos verificados para um deles ser preenchido
     * @params pessoa inst�ncia que ser� atribuida ao campo Data do n� adicionado
     */
    private void add(Node node, Pessoa pessoa) { 
        if (pessoa.getName().compareTo(node.getData().getName()) < 0) { // se o nome deve ficar a esquerda
            if (node.getLeft() != null) {
                add(node.getLeft(), pessoa);
            }
            else {
                node.setLeft(new Node(pessoa));
                node.getLeft().setParent(node); 
            }
        }
        else {
            if (node.getRight() != null) {
                add(node.getRight(), pessoa);
            }
            else {
                node.setRight(new Node(pessoa));
                node.getRight().setParent(node); 
            } 
        }
    }
    
    /**
     * search - m�todo chamada para pesquisar registro por nome. 
     * 
     * @params name String nome que ser� usada para a busca
     */
    public void search(String name) {
        searchName(root, name, 0);
    }
    
    /**
     * searchName - m�todo chamada no search para fazer uma busca por profundidade na �rvare, usando o valor do nome
     * 
     * @params node n� atual usadao para a busca
     * @params name String com valor usado como par�metro para a busca
     * @params flag flag que indica se j� foi encontrado ou n�o o n� com valor buscado
     */
    private void searchName(Node node, String name, int flag) {
        if (node.getData().getName().compareTo(name) == 0) {
            flag = 1;
        }
        else {
            if (node.getData().getName().compareTo(name) < 0) {
                if (node.getLeft() == null) {
                    flag = 2;
                }
                else {
                    node = node.getLeft();
                }
            }
            else {
                if (node.getRight() == null) {
                    flag = 3;
                }
                else {
                    node = node.getRight();
                }
            }
            if (flag < 1) {
                searchName(node, name, flag);
            }
        }
    }
    
    /**
     * hasNode - m�todo chamado quando queremos verificar se h� registro com o CPF passado por par�metro
     * 
     * @params CPF valor do CPF que ser� usado para verificar a exist�ncia de um registro semelhante
     * @return True caso tenha encontrado, ou False caso contr�rio
     */
    public boolean hasNode(String CPF) {
        return seachCPF(root, CPF);
    }
    
    private boolean seachCPF(Node node, String CPF) {
        if (node != null){
             if (node.getData().getCPF().equals(CPF)) {
                 return true;
             }
             seachCPF(node.getLeft(), CPF);
             seachCPF(node.getRight(), CPF);
        }
        return false;
    }
    
    public void remove(Node toremove){
        Node left = toremove.getLeft();
        Node right = toremove.getRight();
        Node parent = toremove.getParent();
        
        if (parent.getLeft() == toremove){
            parent.setLeft(null);
        }
        else {
            parent.setRight(null);
        }
        
        parent.addExistentNode(parent, left);
        parent.addExistentNode(parent, right);
        
    } 
    
    private void printPrefix(Node node){
        if (node != null){
            System.out.print(" " + node.getData().getCPF() + " ");
            printPrefix(node.getLeft());
            printPrefix(node.getRight());
        }
    }
    
    private void printPosfix(Node node){
        if (node != null){
            printPosfix(node.getLeft());
            printPosfix(node.getRight());
            System.out.print(" " + node.getData().getCPF() + " ");
        }
    }
    
    private void printOrdered(Node node){
        if (node != null){
            printOrdered(node.getLeft());
            System.out.print(" " + node.getData().getCPF() + " ");
            printOrdered(node.getRight());
        }
    }
    
    public void printTreeOrdered(){
        System.out.println();
        printOrdered(root);
    }
    
    public void printTreePrefix(){
        System.out.println();
        printPrefix(root);
    }
    
    public void printTreePosfix(){
        System.out.println();
        printPosfix(root);
    }
    
    public void getHeight(Node node) {
        if (node != null) {
            getHeight(node.getLeft());
            getHeight(node.getRight());
            calcHeight(node);
        }
    }
    
    public void calcHeight(Node node) {
        int alt1, alt2 = 0;
        if (node.getLeft() == null) {
            alt1 = 0;
        }
        else {
            alt1 = node.getLeft().getHeight();
        }
        if (node.getRight() == null) {
            alt2 = 0;
        }
        else {
            alt2 = node.getRight().getHeight();
        }
        if (alt1 > alt2) {
            node.setHeight(alt1 + 1);
        }
        else {
            node.setHeight(alt2 + 1);
        }
    }
    
    public void searchWidth(String name) {
        ArrayList<Node> left = new ArrayList<Node>();
        ArrayList<Node> right = new ArrayList<Node>();
        roamPrefix(root.getLeft(), left);
        roamPrefix(root.getRight(), right); 
        
        boolean flag_encontrou = false;
         
        Iterator<Node> itL = left.iterator();
        Iterator<Node> itR = right.iterator();
        
        while(itL.hasNext() || itR.hasNext()) {
            Node n1 = null;
            Node n2 = null;
            if (itL.hasNext()) {
                n1 = itL.next();
            }
            else if (itR.hasNext()) {
                n2 = itR.next();
            }
            if (n1 != null) {
                if (n1.getData().getName().equals(name)) {
                    System.out.print(n1.getData().getName() + " ");
                    flag_encontrou = true;
                    break;
                }
            }
            if (n2 != null) {
                if (n2.getData().getName().equals(name)) {
                    System.out.print(n2.getData().getName() + " ");
                    flag_encontrou = true;
                    break;
                }
            }
        }
        if (!flag_encontrou) {
            System.out.print("N�o encontrou registro");
        }
    }
    
    private void roamPrefix(Node node, ArrayList<Node> array){
        if (node != null){
            array.add(node); 
            roamPrefix(node.getLeft(), array);
            roamPrefix(node.getRight(), array);
        }
    }
    
    public int biggerValue(Node node, int size) {
        if (node != null){
            if (node.getData().getName().length() > size) {
                size = node.getData().getName().length();
            } 
            biggerValue(node.getLeft(), size);
            biggerValue(node.getRight(), size);
        }
        return size;
    }
    
    public int lessValue(Node node, int size) {
        if (node != null){
            if (node.getData().getName().length() < size) {
                size = node.getData().getName().length();
            } 
            biggerValue(node.getLeft(), size);
            biggerValue(node.getRight(), size);
        }
        return size;
    }
}
